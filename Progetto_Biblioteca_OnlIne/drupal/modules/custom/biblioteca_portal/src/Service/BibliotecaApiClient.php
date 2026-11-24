<?php

declare(strict_types=1);

namespace Drupal\biblioteca_portal\Service;

use Drupal\Core\Config\ConfigFactoryInterface;
use GuzzleHttp\ClientInterface;
use GuzzleHttp\Exception\GuzzleException;
use GuzzleHttp\Exception\RequestException;

/**
 * Calls the Spring Boot library application from the Drupal server.
 */
final class BibliotecaApiClient {

  private readonly string $baseUrl;

  private readonly int $timeout;

  public function __construct(
    private readonly ClientInterface $httpClient,
    ConfigFactoryInterface $configFactory,
  ) {
    $settings = $configFactory->get('biblioteca_portal.settings');
    $baseUrl = rtrim((string) $settings->get('api_base_url'), '/');
    if (!filter_var($baseUrl, FILTER_VALIDATE_URL) || !in_array(parse_url($baseUrl, PHP_URL_SCHEME), ['http', 'https'], TRUE)) {
      throw new \InvalidArgumentException('The library API base URL must be an absolute HTTP or HTTPS URL.');
    }

    $this->baseUrl = $baseUrl;
    $this->timeout = max(1, (int) $settings->get('timeout'));
  }

  /**
   * Lists catalog entries, optionally filtering to available books.
   *
   * @return array<int, array<string, mixed>>
   *   Book records from the application API.
   */
  public function getBooks(bool $availableOnly = FALSE): array {
    $books = $this->request('GET', '/api/books', NULL, ['availableOnly' => $availableOnly ? 'true' : 'false']);
    if (!array_is_list($books)) {
      throw new BibliotecaApiException('The library API returned an invalid book list.');
    }
    return $books;
  }

  /**
   * Lists registered readers.
   *
   * @return array<int, array<string, mixed>>
   *   Reader records from the application API.
   */
  public function getReaders(): array {
    $readers = $this->request('GET', '/api/readers');
    if (!array_is_list($readers)) {
      throw new BibliotecaApiException('The library API returned an invalid reader list.');
    }
    return $readers;
  }

  /**
   * Registers a reader.
   *
   * @return array<string, mixed>
   *   The created reader.
   */
  public function createReader(string $name): array {
    return $this->requestObject('POST', '/api/readers', ['nominativo' => $name]);
  }

  /**
   * Lists active loans.
   *
   * @return array<int, array<string, mixed>>
   *   Active loan records from the application API.
   */
  public function getActiveLoans(): array {
    $loans = $this->request('GET', '/api/loans', NULL, ['activeOnly' => 'true']);
    if (!array_is_list($loans)) {
      throw new BibliotecaApiException('The library API returned an invalid loan list.');
    }
    return $loans;
  }

  /**
   * Registers a loan for a reader and book.
   *
   * @return array<string, mixed>
   *   The created loan.
   */
  public function createLoan(int $bookId, int $readerId): array {
    return $this->requestObject('POST', '/api/loans', [
      'libroId' => $bookId,
      'lettoreId' => $readerId,
    ]);
  }

  /**
   * Registers a loan return.
   *
   * @return array<string, mixed>
   *   The updated loan.
   */
  public function returnLoan(int $loanId): array {
    return $this->requestObject('POST', '/api/loans/' . $loanId . '/return');
  }

  /**
   * Sends a request that must return a JSON object.
   *
   * @param array<string, mixed>|null $body
   *   Optional JSON request body.
   *
   * @return array<string, mixed>
   *   Decoded JSON object.
   */
  private function requestObject(string $method, string $path, ?array $body = NULL): array {
    $payload = $this->request($method, $path, $body);
    if ($payload === [] || array_is_list($payload)) {
      throw new BibliotecaApiException('The library API returned an invalid object response.');
    }
    return $payload;
  }

  /**
   * Sends a JSON request and decodes the JSON object or list response.
   *
   * @param array<string, string> $query
   *   Query parameters.
   *
   * @return array<mixed>
   *   Decoded JSON response.
   */
  private function request(string $method, string $path, ?array $body = NULL, array $query = []): array {
    $options = [
      'headers' => ['Accept' => 'application/json'],
      'query' => $query,
      'timeout' => $this->timeout,
      'connect_timeout' => $this->timeout,
    ];
    if ($body !== NULL) {
      $options['json'] = $body;
      $options['headers']['Content-Type'] = 'application/json';
    }

    try {
      $response = $this->httpClient->request($method, $this->baseUrl . $path, $options);
    }
    catch (RequestException $exception) {
      $response = $exception->getResponse();
      if ($response !== NULL) {
        $payload = json_decode((string) $response->getBody(), TRUE);
        $message = is_array($payload) && isset($payload['error']) && is_string($payload['error'])
          ? $payload['error']
          : sprintf('The library API returned HTTP %d.', $response->getStatusCode());
        throw new BibliotecaApiException($message, $response->getStatusCode(), $exception);
      }
      throw new BibliotecaApiException('The library API could not be reached.', 0, $exception);
    }
    catch (GuzzleException $exception) {
      throw new BibliotecaApiException('The library API could not be reached.', 0, $exception);
    }

    try {
      $payload = json_decode((string) $response->getBody(), TRUE, 512, JSON_THROW_ON_ERROR);
    }
    catch (\JsonException $exception) {
      throw new BibliotecaApiException('The library API returned invalid JSON.', 0, $exception);
    }
    if (!is_array($payload)) {
      throw new BibliotecaApiException('The library API returned an invalid JSON response.');
    }
    return $payload;
  }

}

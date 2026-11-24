<?php

declare(strict_types=1);

namespace Drupal\Tests\biblioteca_portal\Unit\Service;

use Drupal\biblioteca_portal\Service\BibliotecaApiClient;
use Drupal\biblioteca_portal\Service\BibliotecaApiException;
use Drupal\Core\Config\ConfigFactoryInterface;
use Drupal\Core\Config\ImmutableConfig;
use GuzzleHttp\Client;
use GuzzleHttp\Handler\MockHandler;
use GuzzleHttp\HandlerStack;
use GuzzleHttp\Psr7\Response;
use PHPUnit\Framework\TestCase;

/**
 * Tests the Drupal-to-Spring API client contract.
 *
 * @coversDefaultClass \Drupal\biblioteca_portal\Service\BibliotecaApiClient
 * @group biblioteca_portal
 */
final class BibliotecaApiClientTest extends TestCase {

  /**
   * Tests book lists are decoded and the availability filter is sent.
   *
   * @covers ::getBooks
   * @covers ::request
   */
  public function testGetBooksSendsAvailabilityFilter(): void {
    $history = [];
    $stack = HandlerStack::create(new MockHandler([
      new Response(200, ['Content-Type' => 'application/json'], '[{"id":1,"titolo":"Libro","autore":"Autore","disponibile":true}]'),
    ]));
    $stack->push(\GuzzleHttp\Middleware::history($history));
    $client = new BibliotecaApiClient(
      new Client(['handler' => $stack]),
      $this->configFactory(),
    );

    self::assertSame(
      [['id' => 1, 'titolo' => 'Libro', 'autore' => 'Autore', 'disponibile' => TRUE]],
      $client->getBooks(TRUE),
    );
    self::assertSame('availableOnly=true', $history[0]['request']->getUri()->getQuery());
  }

  /**
   * Tests a Spring API conflict is surfaced to the caller.
   *
   * @covers ::createLoan
   * @covers ::request
   */
  public function testLoanConflictIsNotHidden(): void {
    $client = new BibliotecaApiClient(
      new Client(['handler' => HandlerStack::create(new MockHandler([
        new Response(409, ['Content-Type' => 'application/json'], '{"error":"Il libro ha già un prestito attivo."}'),
      ]))]),
      $this->configFactory(),
    );

    $this->expectException(BibliotecaApiException::class);
    $this->expectExceptionMessage('Il libro ha già un prestito attivo.');
    $client->createLoan(1, 2);
  }

  /**
   * Creates the API settings dependency for a unit test.
   */
  private function configFactory(): ConfigFactoryInterface {
    $config = $this->createMock(ImmutableConfig::class);
    $config->method('get')->willReturnMap([
      ['api_base_url', 'http://spring.test:8080'],
      ['timeout', 5],
    ]);
    $factory = $this->createMock(ConfigFactoryInterface::class);
    $factory->method('get')
      ->with('biblioteca_portal.settings')
      ->willReturn($config);
    return $factory;
  }

}

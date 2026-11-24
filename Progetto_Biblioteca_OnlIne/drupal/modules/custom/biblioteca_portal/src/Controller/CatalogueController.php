<?php

declare(strict_types=1);

namespace Drupal\biblioteca_portal\Controller;

use Drupal\biblioteca_portal\Service\BibliotecaApiClient;
use Drupal\biblioteca_portal\Service\BibliotecaApiException;
use Drupal\Core\Controller\ControllerBase;
use Symfony\Component\DependencyInjection\ContainerInterface;

/**
 * Displays the book catalogue in the Drupal portal.
 */
final class CatalogueController extends ControllerBase {

  public function __construct(
    private readonly BibliotecaApiClient $apiClient,
  ) {}

  public static function create(ContainerInterface $container): self {
    return new self($container->get('biblioteca_portal.api_client'));
  }

  /**
   * Builds the catalogue page.
   */
  public function page(): array {
    try {
      $books = $this->apiClient->getBooks();
    }
    catch (BibliotecaApiException $exception) {
      return $this->apiError($exception);
    }

    $rows = [];
    foreach ($books as $book) {
      $rows[] = [
        ['data' => ['#plain_text' => (string) ($book['titolo'] ?? '')]],
        ['data' => ['#plain_text' => (string) ($book['autore'] ?? '')]],
        ['data' => ['#plain_text' => !empty($book['disponibile']) ? $this->t('Disponibile') : $this->t('In prestito')]],
      ];
    }

    return [
      'books' => [
        '#type' => 'table',
        '#header' => [$this->t('Titolo'), $this->t('Autore'), $this->t('Disponibilita')],
        '#rows' => $rows,
        '#empty' => $this->t('Nessun libro presente nel catalogo.'),
      ],
      '#cache' => ['max-age' => 0],
    ];
  }

  /**
   * Renders an explicit error instead of presenting a failed request as empty.
   */
  private function apiError(BibliotecaApiException $exception): array {
    return [
      'error' => [
        '#type' => 'container',
        '#attributes' => ['class' => ['messages', 'messages--error']],
        'message' => ['#plain_text' => $exception->getMessage()],
      ],
      '#cache' => ['max-age' => 0],
    ];
  }

}

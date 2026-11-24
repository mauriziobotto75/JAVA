<?php

declare(strict_types=1);

namespace Drupal\biblioteca_portal\Controller;

use Drupal\biblioteca_portal\Service\BibliotecaApiClient;
use Drupal\biblioteca_portal\Service\BibliotecaApiException;
use Drupal\Core\Controller\ControllerBase;
use Drupal\Core\Link;
use Drupal\Core\Url;
use Symfony\Component\DependencyInjection\ContainerInterface;

/**
 * Displays active loans and links to library staff actions.
 */
final class LoanController extends ControllerBase {

  public function __construct(
    private readonly BibliotecaApiClient $apiClient,
  ) {}

  public static function create(ContainerInterface $container): self {
    return new self($container->get('biblioteca_portal.api_client'));
  }

  /**
   * Builds the active-loans page.
   */
  public function page(): array {
    try {
      $loans = $this->apiClient->getActiveLoans();
    }
    catch (BibliotecaApiException $exception) {
      return [
        'error' => [
          '#type' => 'container',
          '#attributes' => ['class' => ['messages', 'messages--error']],
          'message' => ['#plain_text' => $exception->getMessage()],
        ],
        '#cache' => ['max-age' => 0],
      ];
    }

    $rows = [];
    foreach ($loans as $loan) {
      $rows[] = [
        ['data' => ['#plain_text' => (string) ($loan['titoloLibro'] ?? '')]],
        ['data' => ['#plain_text' => (string) ($loan['nominativoLettore'] ?? '')]],
        ['data' => ['#plain_text' => (string) ($loan['dataPrestito'] ?? '')]],
      ];
    }

    return [
      'actions' => [
        '#type' => 'container',
        'new_loan' => Link::fromTextAndUrl($this->t('Registra prestito'), Url::fromRoute('biblioteca_portal.loan'))->toRenderable(),
        'return_loan' => Link::fromTextAndUrl($this->t('Registra restituzione'), Url::fromRoute('biblioteca_portal.return'))->toRenderable(),
      ],
      'loans' => [
        '#type' => 'table',
        '#header' => [$this->t('Libro'), $this->t('Lettore'), $this->t('Data prestito')],
        '#rows' => $rows,
        '#empty' => $this->t('Non ci sono prestiti attivi.'),
      ],
      '#cache' => ['max-age' => 0],
    ];
  }

}

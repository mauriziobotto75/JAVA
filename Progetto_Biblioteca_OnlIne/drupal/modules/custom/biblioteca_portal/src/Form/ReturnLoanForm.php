<?php

declare(strict_types=1);

namespace Drupal\biblioteca_portal\Form;

use Drupal\biblioteca_portal\Service\BibliotecaApiClient;
use Drupal\biblioteca_portal\Service\BibliotecaApiException;
use Drupal\Core\Form\FormBase;
use Drupal\Core\Form\FormStateInterface;
use Symfony\Component\DependencyInjection\ContainerInterface;

/**
 * Records a return for one of the currently active loans.
 */
final class ReturnLoanForm extends FormBase {

  public function __construct(
    private readonly BibliotecaApiClient $apiClient,
  ) {}

  public static function create(ContainerInterface $container): self {
    return new self($container->get('biblioteca_portal.api_client'));
  }

  public function getFormId(): string {
    return 'biblioteca_portal_return_loan_form';
  }

  public function buildForm(array $form, FormStateInterface $form_state): array {
    try {
      $loans = $this->apiClient->getActiveLoans();
    }
    catch (BibliotecaApiException $exception) {
      $form['api_error'] = [
        '#type' => 'container',
        '#attributes' => ['class' => ['messages', 'messages--error']],
        'message' => ['#plain_text' => $exception->getMessage()],
      ];
      return $form;
    }

    $options = [];
    foreach ($loans as $loan) {
      if (isset($loan['id'])) {
        $options[$loan['id']] = sprintf(
          '%s — %s',
          (string) ($loan['titoloLibro'] ?? $this->t('Libro')),
          (string) ($loan['nominativoLettore'] ?? $this->t('Lettore')),
        );
      }
    }
    if ($options === []) {
      $form['notice'] = ['#plain_text' => $this->t('Non ci sono prestiti attivi da restituire.')];
      return $form;
    }

    $form['prestito_id'] = [
      '#type' => 'select',
      '#title' => $this->t('Prestito attivo'),
      '#options' => $options,
      '#required' => TRUE,
    ];
    $form['actions'] = ['#type' => 'actions'];
    $form['actions']['submit'] = [
      '#type' => 'submit',
      '#value' => $this->t('Registra restituzione'),
    ];
    return $form;
  }

  public function submitForm(array &$form, FormStateInterface $form_state): void {
    try {
      $this->apiClient->returnLoan((int) $form_state->getValue('prestito_id'));
      $this->messenger()->addStatus($this->t('Restituzione registrata.'));
      $form_state->setRedirect('biblioteca_portal.loans');
    }
    catch (BibliotecaApiException $exception) {
      $this->messenger()->addError($exception->getMessage());
    }
  }

}

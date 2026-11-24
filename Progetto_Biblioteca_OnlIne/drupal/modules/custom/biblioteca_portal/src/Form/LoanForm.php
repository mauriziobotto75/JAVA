<?php

declare(strict_types=1);

namespace Drupal\biblioteca_portal\Form;

use Drupal\biblioteca_portal\Service\BibliotecaApiClient;
use Drupal\biblioteca_portal\Service\BibliotecaApiException;
use Drupal\Core\Form\FormBase;
use Drupal\Core\Form\FormStateInterface;
use Symfony\Component\DependencyInjection\ContainerInterface;

/**
 * Starts a loan for an available book and a registered reader.
 */
final class LoanForm extends FormBase {

  public function __construct(
    private readonly BibliotecaApiClient $apiClient,
  ) {}

  public static function create(ContainerInterface $container): self {
    return new self($container->get('biblioteca_portal.api_client'));
  }

  public function getFormId(): string {
    return 'biblioteca_portal_loan_form';
  }

  public function buildForm(array $form, FormStateInterface $form_state): array {
    try {
      $books = $this->apiClient->getBooks(TRUE);
      $readers = $this->apiClient->getReaders();
    }
    catch (BibliotecaApiException $exception) {
      $form['api_error'] = [
        '#type' => 'container',
        '#attributes' => ['class' => ['messages', 'messages--error']],
        'message' => ['#plain_text' => $exception->getMessage()],
      ];
      return $form;
    }

    $book_options = [];
    foreach ($books as $book) {
      if (isset($book['id'])) {
        $book_options[$book['id']] = (string) ($book['titolo'] ?? $this->t('Libro senza titolo'));
      }
    }
    $reader_options = [];
    foreach ($readers as $reader) {
      if (isset($reader['id'])) {
        $reader_options[$reader['id']] = (string) ($reader['nominativo'] ?? $this->t('Lettore'));
      }
    }

    $form['libro_id'] = [
      '#type' => 'select',
      '#title' => $this->t('Libro disponibile'),
      '#options' => $book_options,
      '#empty_option' => $this->t('- Seleziona un libro -'),
      '#required' => TRUE,
      '#disabled' => $book_options === [],
    ];
    $form['lettore_id'] = [
      '#type' => 'select',
      '#title' => $this->t('Lettore'),
      '#options' => $reader_options,
      '#empty_option' => $this->t('- Seleziona un lettore -'),
      '#required' => TRUE,
      '#disabled' => $reader_options === [],
    ];
    if ($book_options === [] || $reader_options === []) {
      $form['notice'] = [
        '#plain_text' => $book_options === []
          ? $this->t('Non ci sono libri disponibili per il prestito.')
          : $this->t('Registra prima almeno un lettore.'),
      ];
      return $form;
    }

    $form['actions'] = ['#type' => 'actions'];
    $form['actions']['submit'] = [
      '#type' => 'submit',
      '#value' => $this->t('Registra prestito'),
    ];
    return $form;
  }

  public function submitForm(array &$form, FormStateInterface $form_state): void {
    try {
      $this->apiClient->createLoan(
        (int) $form_state->getValue('libro_id'),
        (int) $form_state->getValue('lettore_id'),
      );
      $this->messenger()->addStatus($this->t('Prestito registrato.'));
      $form_state->setRedirect('biblioteca_portal.loans');
    }
    catch (BibliotecaApiException $exception) {
      $this->messenger()->addError($exception->getMessage());
    }
  }

}

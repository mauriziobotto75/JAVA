<?php

declare(strict_types=1);

namespace Drupal\biblioteca_portal\Form;

use Drupal\biblioteca_portal\Service\BibliotecaApiClient;
use Drupal\biblioteca_portal\Service\BibliotecaApiException;
use Drupal\Core\Form\FormBase;
use Drupal\Core\Form\FormStateInterface;
use Symfony\Component\DependencyInjection\ContainerInterface;

/**
 * Registers a library reader through the application API.
 */
final class ReaderForm extends FormBase {

  public function __construct(
    private readonly BibliotecaApiClient $apiClient,
  ) {}

  public static function create(ContainerInterface $container): self {
    return new self($container->get('biblioteca_portal.api_client'));
  }

  public function getFormId(): string {
    return 'biblioteca_portal_reader_form';
  }

  public function buildForm(array $form, FormStateInterface $form_state): array {
    $form['nominativo'] = [
      '#type' => 'textfield',
      '#title' => $this->t('Nome del lettore'),
      '#required' => TRUE,
      '#maxlength' => 120,
    ];
    $form['actions'] = ['#type' => 'actions'];
    $form['actions']['submit'] = [
      '#type' => 'submit',
      '#value' => $this->t('Registra lettore'),
    ];
    return $form;
  }

  public function submitForm(array &$form, FormStateInterface $form_state): void {
    try {
      $reader = $this->apiClient->createReader((string) $form_state->getValue('nominativo'));
      $this->messenger()->addStatus($this->t('Lettore registrato (ID @id).', ['@id' => $reader['id'] ?? '']));
      $form_state->setRedirect('biblioteca_portal.catalogue');
    }
    catch (BibliotecaApiException $exception) {
      $this->messenger()->addError($exception->getMessage());
    }
  }

}

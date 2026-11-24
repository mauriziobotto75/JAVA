# Biblioteca Web

REST API Spring Boot per un primo nucleo di catalogo e circolazione, esposto in JSON e integrato con un modulo Drupal minimale in `drupal/modules/custom/biblioteca_portal`. Il repository non contiene un progetto Drupal preesistente: il modulo è uno starter integrabile, non la ricostruzione di un portale completo.

## Funzioni e API

- `POST /api/books` e `GET /api/books`: inserimento e consultazione del catalogo. `GET /api/books?availableOnly=true` filtra i libri senza prestito attivo.
- `POST /api/readers`, `GET /api/readers` e `GET /api/readers/{id}`: inserimento e consultazione dei lettori.
- `POST /api/loans` e `GET /api/loans`: registrazione e consultazione dei prestiti. `GET /api/loans?activeOnly=true` restituisce quelli non ancora restituiti.
- `POST /api/loans/{id}/return`: registra la restituzione.
- `GET /api/reports/available-books` e `GET /api/reports/active-loans`: report dei libri disponibili e dei prestiti attivi.

Le richieste di creazione accettano JSON. Per esempio:

```json
{ "titolo": "Il nome della rosa", "autore": "Umberto Eco" }
```

Il prestito usa gli identificativi restituiti dalle API:

```json
{ "libroId": 1, "lettoreId": 1 }
```

Un libro è modellato come una singola unità catalogata: un secondo prestito attivo dello stesso libro restituisce `409 Conflict`. Le risorse inesistenti restituiscono `404`; i campi non validi `400`.

## Avvio

Requisiti: JDK 21 e Maven 3.9 o superiore.

```powershell
cd Progetto_Biblioteca_OnlIne
mvn spring-boot:run
```

L'API è disponibile su `http://localhost:8080` e usa H2 in memoria, adatto a una demo; i dati vengono persi all'arresto. Hibernate crea/aggiorna le tabelle `LIBRI`, `LETTORI` e `PRESTITI`.

## Portale Drupal

Il modulo custom fornisce una pagina catalogo (`/biblioteca`), una pagina dei prestiti attivi (`/biblioteca/prestiti`) e moduli Drupal protetti da permessi per registrare lettori, prestiti e restituzioni. Il client HTTP lato server consuma gli endpoint Spring; il browser non chiama direttamente l'API e non richiede configurazione CORS.

1. Copiare `drupal/modules/custom/biblioteca_portal` nella cartella `modules/custom` del progetto Drupal (per esempio `web/modules/custom`).
2. Abilitare il modulo con `drush en biblioteca_portal -y`.
3. Configurare l'indirizzo API raggiungibile **dal processo/container Drupal**: `drush config:set biblioteca_portal.settings api_base_url http://127.0.0.1:8080 -y`. In ambienti containerizzati sostituire `127.0.0.1` con il nome host/servizio raggiungibile da Drupal.
4. Assegnare alle persone/ruoli i permessi `Access the library catalogue`, `Manage library readers` e `Manage library loans` dalla pagina Drupal dei permessi.

`/biblioteca/lettori/nuovo` richiede il permesso di gestione lettori; `/biblioteca/prestiti/nuovo` e `/biblioteca/prestiti/restituisci` richiedono quello di gestione prestiti. Gli errori e i conflitti dell'API vengono mostrati nel portale e non simulati come operazioni riuscite.

Il test unitario del client è in `drupal/modules/custom/biblioteca_portal/tests/src/Unit/Service/BibliotecaApiClientTest.php` e richiede un'installazione di Drupal 10/11 con PHPUnit. Non è stato eseguito qui: in questo ambiente non sono disponibili PHP, Composer o un progetto Drupal. I test Spring Boot restano eseguibili con `mvn test` in questa cartella.

## Test

```powershell
cd Progetto_Biblioteca_OnlIne
mvn test
```

I test verificano il ciclo prestito/restituzione, la disponibilità aggiornata e il rifiuto di riferimenti inesistenti.

## Limiti della conversione

È disponibile uno screenshot dell'elenco COBOL/400, con etichette che descrivono programmi per inserire/recuperare lettori, gestire libri e prestiti, restituire libri e generare report; lo screenshot mostra inoltre le tabelle `LETTORI`, `LIBRI` e `PRESTITI`. I sorgenti COBOL, i tracciati dei record, i dati e un progetto Drupal esistente non sono presenti nel repository. Di conseguenza l'API e il modulo Drupal sono un nucleo iniziale basato solo su tali etichette: nomi/campi, regole dettagliate, scadenze, copie multiple e report non sono stati dedotti né dichiarati equivalenti al sistema originale. Non importa dati IBM i e non replica tutte le pagine del portale bct.comune.torino.it.

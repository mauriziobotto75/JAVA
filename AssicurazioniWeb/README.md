# AssicurazioniWeb

Applicazione web Java che traduce i flussi COBOL/400 di gestione polizze in moduli Spring MVC, Spring Data JPA e Hibernate, con interfaccia Thymeleaf.

## Funzioni migrate

| Flusso COBOL/400 | Funzione web |
| --- | --- |
| `GES_POLIZZE` / `INSPOL` | Inserimento di una polizza, con controllo del codice duplicato |
| `VARPOL` | Modifica dei dati assicurativi senza sovrascrivere lo stato del pagamento |
| `ANN-POLIZZE` | Annullamento confermato della polizza |
| `VISU_POL` / `RICPOL` | Ricerca e dettaglio per codice, oltre alla ricerca per cliente |
| `POLSTA` | Filtro delle polizze pagate e da pagare e registrazione del pagamento |

Il modello usa le colonne dello schema DB2 fornito: `COD_POLIZZA`, `NOME_CLIENTE`, `INDIRIZZO`, `IMPORTO`, `SCADENZA`, `SIGLA_AUTO`, `NUM_CAVALLI`, `STATO_PAGAMENTO` e `DATA_PAGAMENTO`. `N` indica non pagata, `P` pagata. La registrazione pagamento mantiene lo stato coerente con i campi disponibili; non viene introdotto uno storico pagamenti non previsto dal DDL originale.

Il sorgente fornito non include il corpo SQL di `ANN-POLIZZE`. L'annullamento è stato interpretato come eliminazione definitiva del record, in coerenza con il nome del modulo e il campo di conferma; verificare questa scelta prima dell'uso su dati reali.

## Tecnologie

- Java 21
- Spring Boot 4
- Spring MVC e Thymeleaf
- Spring Data JPA con Hibernate
- Bean Validation
- H2 per avvio e test locali; IBM Toolbox for Java (JTOpen) per il collegamento a Db2 for i (IBM i)

## Avvio locale

Da Windows, con Java 21 o superiore:

```powershell
.\mvnw.cmd spring-boot:run
```

Il profilo predefinito salva i dati in `./data/assicurazioni` tramite H2 e crea/aggiorna la tabella per l'ambiente locale. L'interfaccia è disponibile su `http://localhost:8080/polizze`.

## Collegamento a Db2 for i

Applicare `src/main/resources/db/db2-schema.sql` nella libreria IBM i scelta se la tabella `POLIZZE` non esiste. Avviare quindi l'applicazione con il profilo `db2` e configurare host, libreria e credenziali tramite variabili d'ambiente:

```powershell
$env:SPRING_PROFILES_ACTIVE = "db2"
$env:DB2_HOST = "nome-sistema-ibmi"
$env:DB2_SCHEMA = "MIALIB"
$env:DB2_USERNAME = "utente"
$env:DB2_PASSWORD = "password"
.\mvnw.cmd spring-boot:run
```

Il profilo Db2 for i usa `ddl-auto: validate`: non modifica lo schema e segnala eventuali differenze. JTOpen si collega tramite JDBC/DRDA; la porta IBM i predefinita è 446.

## Test

```powershell
.\mvnw.cmd test
```

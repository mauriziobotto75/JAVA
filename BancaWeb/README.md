# Banca Web

Applicazione web Java che traduce i flussi presenti nei programmi COBOL/400 allegati. Usa Spring Boot 4, Spring MVC, Thymeleaf, Spring Data JPA/Hibernate e MySQL. Il numero del movimento è generato dal database al posto della chiave numerica COBOL.

## Funzioni convertite

- Gestione clienti essenziale, necessaria per collegare i conti.
- Apertura conto con saldo iniziale zero, aggiornamento fido e chiusura logica.
- Versamenti e accrediti (`VE`, `AC`), prelievi (`PR`), bollettini (`BO`) e pagamenti (`PA`).
- Controllo del saldo disponibile (saldo + fido), stato operativo del conto e registrazione atomica di movimento e saldo.
- Consultazioni: saldi negativi, movimenti per tipo, tra date, per conto e per conto/intervallo date.

## Requisiti

- JDK 21 o superiore.
- Maven 3.9 o superiore.
- MySQL 8 o superiore.
- Docker Desktop con Docker Compose v2 (per l'avvio containerizzato).

## Avvio locale

Creare il database e un utente MySQL dedicato:

```sql
CREATE DATABASE banca CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
CREATE USER 'banca'@'localhost' IDENTIFIED BY 'scegli-una-password';
GRANT ALL PRIVILEGES ON banca.* TO 'banca'@'localhost';
```

Configurare le credenziali senza inserirle nel repository. In PowerShell:

```powershell
$env:DB_URL = "jdbc:mysql://localhost:3306/banca"
$env:DB_USERNAME = "banca"
$env:DB_PASSWORD = "scegli-una-password"
cd BancaWeb
mvn spring-boot:run
```

Aprire `http://localhost:8080`. Per impostazione predefinita l'applicazione ascolta solo su `127.0.0.1`; URL, utente e password sono sovrascrivibili con `DB_URL`, `DB_USERNAME` e `DB_PASSWORD`. Hibernate aggiorna lo schema (`ddl-auto=update`) per facilitare la demo.

## Avvio con Docker Compose

Da PowerShell, nella cartella `BancaWeb`:

```powershell
Copy-Item .env.example .env
# Modificare .env e impostare password robuste prima di avviare.
docker compose up --build -d
docker compose ps
```

L'applicazione è disponibile su `http://localhost:8080` (modificabile con `APP_PORT` in `.env`). Compose avvia MySQL 8.4 con un volume persistente e aspetta che il database superi il controllo di salute prima di avviare l'app. Le porte pubblicate sono limitate al loopback. Per arrestare i container mantenendo i dati: `docker compose down`. Per eliminare anche il database persistito: `docker compose down -v`.

Il file `.env` non è incluso nel repository. I valori di `.env.example` sono segnaposto solo per lo sviluppo: sostituirli prima dell'avvio e non riutilizzarli in produzione. Il database non espone una porta sull'host nella configurazione predefinita.

## Test

```powershell
cd BancaWeb
mvn test
```

I test usano H2 in memoria e verificano l'aggiornamento saldo/movimento, il controllo del fido e il blocco dei movimenti su conti chiusi.

## Assunzioni e limiti della conversione

- Il programma `CLI001` è richiamato dal menu ma non è incluso nel sorgente allegato. L'anagrafica minima cliente (codice, nominativo, indirizzo) è ricostruita dai campi `CLI-CODICE`, `CNT-NOME` e `CNT-INDIRIZZO` presenti nei record conto; non sostituisce il tracciato cliente originale.
- I programmi COBOL dichiarano tracciati indicizzati non perfettamente uniformi. Il modello relazionale unifica i campi conto, conserva i codici ABI/CAB e filiale, e salva le date come `DATE` e gli importi come `DECIMAL(13,2)`.
- Il sorgente non fornisce file dati o specifiche di migrazione: questa versione crea nuove tabelle MySQL e non importa i record IBM i esistenti.
- Il PDF della specializzazione ricevuto è una scansione immagine senza testo estraibile; la scelta di Spring Boot, Hibernate/JPA e Thymeleaf è basata sulle tecnologie indicate nel titolo e dalla richiesta.
- È un prototipo didattico locale, non un sistema bancario pronto per la produzione: non include login/ruoli, audit trail, gestione segreti operativa, integrazione IBM i o requisiti normativi. Prima di esporlo in rete o usarlo con dati reali servono almeno sicurezza, audit, backup, migrazione e revisione delle regole di business.

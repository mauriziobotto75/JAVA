
# Magazzino Swing + JDBC (PostgreSQL)

Applicazione desktop Swing con CRUD per la tabella `articoli` e ricerca per nome.

## Requisiti
- Java 17 (o modifica nel `pom.xml` a 11/8 a seconda dell'ambiente)
- Maven
- PostgreSQL con una tabella `public.articoli`

## Configurazione
Modifica le credenziali in `DBConnection.java`:
```java
private static final String URL = "jdbc:postgresql://localhost:5432/magazzino";
private static final String USER = "postgres";
private static final String PASSWORD = "password";
```

## Esecuzione
```bash
mvn clean package
java -jar target/magazzino-swing-jdbc-1.0.0.jar
```

## Struttura
- `db/DBConnection.java` – Connessione JDBC
- `dao/ArticoloDAO.java` – CRUD + ricerca per nome
- `model/Articolo.java` – POJO
- `ui/ArticoliFrame.java` – Interfaccia Swing con JTable
- `Main.java` – Avvio UI

## SQL di esempio
Vedi `src/main/resources/schema.sql` per creare la tabella e inserire dati demo.

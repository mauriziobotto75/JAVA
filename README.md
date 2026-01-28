# E-Commerce — Spring Boot 5 (H2) + React Starter

## Backend (Spring Boot)

Requisiti: Java 17, Maven

```bash
cd backend
mvn spring-boot:run
```

Backend: http://localhost:8080
H2 Console: http://localhost:8080/h2 (JDBC URL: jdbc:h2:mem:ecommerce)

Credenziali di esempio:
- Admin → **admin** / **admin**
- Utente → **mario** / **password**

> Password con `{noop}` (solo DEV). In produzione usa BCrypt.

## Frontend (React + Vite)

Requisiti: Node 18+

```bash
cd frontend
npm install
npm run dev
```

Frontend: http://localhost:5173

## JWT

Config in `backend/src/main/resources/application.properties`:
- `security.jwt.secret` (Base64)
- `security.jwt.expiration-ms`

## Zipping (Linux/Mac)

```bash
zip -r ecommerce_full_h2.zip backend frontend README.md
```

## Zipping (Windows PowerShell)

```powershell
Compress-Archive -Path backend,frontend,README.md -DestinationPath ecommerce_full_h2.zip
```

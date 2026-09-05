# AgroVani

Voice-first agricultural assistant for Marathi-speaking farmers in Solapur district.
Answers localised weather and crop-health questions in spoken Marathi, designed for
semi-literate and illiterate users.

## Architecture

| Module | Path | Stack | Port |
|---|---|---|---|
| Mobile app | `mobile/AgroVaniApp` | React Native 0.87 | 8081 (Metro) |
| Backend API | `backend/agrovani-backend` | Java 17, Spring Boot 4.1 | 8080 |
| AI service | `ai-service` | Python 3.11, FastAPI | 8000 |
| Database | — | MySQL 8.0 (utf8mb4) | 3306 |

## Prerequisites

- JDK 17 (Temurin)
- Node.js 22
- Python 3.11 (conda)
- MySQL 8.0
- Android SDK with build-tools 37

## Setup

### Database

```sql
CREATE DATABASE agrovani CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'agrovani'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON agrovani.* TO 'agrovani'@'localhost';
FLUSH PRIVILEGES;
```

### Backend

```bash
cd backend/agrovani-backend
cp src/main/resources/application.properties.example src/main/resources/application.properties
# edit credentials, then:
./mvnw spring-boot:run
```

### AI service

```bash
cd ai-service
conda create -n agrovani-ai python=3.11
conda activate agrovani-ai
pip install -r requirements.txt
uvicorn main:app --reload --port 8000
```

### Mobile app

```bash
cd mobile/AgroVaniApp
npm install
adb reverse tcp:8080 tcp:8080
adb reverse tcp:8000 tcp:8000
npx @react-native-community/cli run-android
```

## API endpoints

| Method | Path | Purpose |
|---|---|---|
| GET | `/api/health` | Backend health check |
| POST | `/api/farmers/register` | Register a farmer |
| GET | `/api/farmers` | List farmers |
| GET | `/api/farmers/phone/{phoneNumber}` | Look up a farmer |
| GET | `/api/crops` | Active crop master list |

## Status

Phase 1 in progress — schema and farmer registration complete.
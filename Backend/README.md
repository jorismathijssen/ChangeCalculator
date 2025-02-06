# Wisselgeld Systeem - Backend

Dit is de backend van het Wisselgeld Systeem, ontwikkeld in **Java met Spring Boot**. De backend ontvangt input van de frontend en retourneert het juiste wisselgeld in biljetten en munten.

## 📌 Vereisten
Zorg ervoor dat je de volgende software hebt geïnstalleerd voordat je de backend start:

- **Java 21**
- **Gradle**

## 🚀 Installatie en Opstarten

### 1️⃣ Build de applicatie

```sh
./gradlew build
```

Dit genereert een distributiebestand in `build/libs/`.

### 2️⃣ Backend starten

#### 🔹 In **normale modus**
```sh
./gradlew bootRun
```

Of als je direct het gegenereerde JAR-bestand wilt uitvoeren:
```sh
java -jar build/libs/*.jar
```

## ⚙️ API Endpoints
De backend API draait standaard op `http://localhost:8080`. Gebruik een tool zoals Postman of cURL om verzoeken te testen.

Zie de [Swagger-documentatie](http://localhost:8080/swagger-ui.html) zodra de backend draait.

## 🛠️ Testen
Voer unit-tests uit met:

```sh
./gradlew test
```

## 🚀 Build en Distributie
Wil je een productieversie bouwen?

```sh
./gradlew clean build
```

Dit genereert een uitvoerbaar JAR-bestand in `build/libs/`, klaar voor distributie.

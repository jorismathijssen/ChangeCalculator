# ChangeCalculatorAPI

[![Build & Deploy to GKE](https://github.com/jorismathijssen/ChangeCalculator/actions/workflows/docker-image.yml/badge.svg?branch=master)](https://github.com/jorismathijssen/ChangeCalculator/actions/workflows/docker-image.yml)

De **Change Calculator API** helpt winkelmedewerkers bij het berekenen van het wisselgeld dat zij aan klanten moeten teruggeven.

## Overzicht

Dit project bestaat uit twee hoofdcomponenten:

- **Frontend**: Een React-gebaseerde gebruikersinterface waarin de medewerker het aankoopbedrag en het contant betaalde bedrag invoert en het wisselgeld in de juiste denominaties wordt weergegeven.
- **Backend**: Een Java (Spring Boot) REST API die de berekeningen uitvoert en een gedetailleerd overzicht van het wisselgeld retourneert.

## Installatie en Documentatie

Voor installatie-instructies en details over de implementatie, raadpleeg de afzonderlijke documentatie:

- [Frontend README](./Frontend/README.md)
- [Backend README](./Backend/README.md)

### API Specificaties

De backend biedt een REST API die wisselgeld berekent op basis van een aankoopbedrag en het ontvangen contante bedrag. De API ondersteunt meerdere valuta.

- **Endpoint:** `POST /api/change`
- **Request Body (JSON):**
  ```json
  {
    "totalAmount": 10.00,
    "cashGiven": 20.00,
    "currency": "EUR"
  }
  ```
- **Response Body (JSON):**
  ```json
  {
    "changeAmount": 10.00,
    "changeBreakdown": {
      "€10": 1
    },
    "currency": "EUR"
  }
  ```

Voor volledige API-documentatie en interactie met de endpoints, bekijk de **Swagger UI** op de volgende publieke URL:  
[Swagger API Documentatie](http://34.78.94.5:8080/swagger-ui/index.html)

## Deployment en Kubernetes

De applicatie draait momenteel op **Google Kubernetes Engine (GKE)**. De deployment naar deze omgeving wordt beheerd via **GitHub Actions**. De applicatie is [hier](http://130.211.90.197/) te vinden

## Features en Uitbreidingen

### Basisfunctionaliteit
- Invoer van aankoopbedrag en contant betaald bedrag
- Berekening van het wisselgeld in optimale denominaties
- Weergave van de wisselgeldspecificaties in de frontend

### Extra Functionaliteiten (optioneel)
- **Kubernetes-compatibiliteit:** De applicatie kan draaien en [draait](http://130.211.90.197/) binnen een Kubernetes-cluster.
- **Automatische tests:** Unit- en integratietests zijn toegevoegd om de betrouwbaarheid te garanderen.
- **Meerdere valuta:** Ondersteuning voor verschillende munteenheden, zoals EUR, GBP en USD.

## Installatie en Gebruik

Voor instructies over het lokaal opstarten van de applicatie en dependencies, raadpleeg de afzonderlijke installatiehandleidingen in de frontend- en backend-mappen.

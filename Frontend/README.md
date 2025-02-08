# Wisselgeld Systeem - Frontend

Dit is de frontend van het Wisselgeld Systeem, een applicatie die berekent hoeveel wisselgeld een winkelmedewerker moet teruggeven aan een klant.

## Vereisten

Zorg ervoor dat de volgende software is geïnstalleerd voordat je de applicatie start:

- **Node.js** (versie 20 of hoger aanbevolen)
- **npm**

## Installatie en Opstarten

### 1. Installeer dependencies

Voer de volgende opdracht uit om de benodigde pakketten te installeren:

```sh
npm install
```

### 2. Maak een `.env`-bestand aan

Als je de standaard backend-URL (`http://localhost:8080`) gebruikt, kun je het voorbeeldbestand `.env.example` kopiëren:

```sh
cp .env.example .env
```

Pas indien nodig de API-URL aan in het `.env`-bestand.

### 3. Start de ontwikkelserver

Start de applicatie met:

```sh
npm run dev
```

De applicatie wordt gestart en opent automatisch een browservenster op `http://localhost:5173`.

## Configuratie

De frontend communiceert met een backend API. Raadpleeg de [backend instructies](../Backend/README.md) voor meer informatie over de configuratie.

## Builden voor productie

Om een geoptimaliseerde versie van de applicatie te genereren:

```sh
npm run build
```

Dit maakt een `dist/`-map aan met de productieversie van de applicatie.

## Code Kwaliteit

Om de code te controleren en te formatteren, voer de volgende opdrachten uit:

```sh
npm run lint
npm run format
```

## Testen

De unit tests kunnen worden uitgevoerd met:
```sh
npm test
```

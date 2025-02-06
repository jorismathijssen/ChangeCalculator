# Wisselgeld Systeem - Frontend

Dit is de frontend van het Wisselgeld Systeem, een applicatie die berekent hoeveel wisselgeld een winkelmedewerker moet teruggeven aan een klant.

## 📌 Vereisten
Zorg ervoor dat je de volgende software hebt geïnstalleerd voordat je de applicatie start:

- **Node.js** (versie 20 of hoger aanbevolen)
- **npm**

## 🚀 Installatie en Opstarten

### 1️⃣ Installeer dependencies

```sh
npm install
```

### 2️⃣ Maak een `.env` bestand aan

Indien je de standaard backend URL (`http://localhost:8080`) gebruikt, kun je het voorbeeldbestand `.env.example` kopiëren:

```sh
cp .env.example .env
```

Indien nodig, pas de API URL aan in het `.env` bestand.

### 3️⃣ Start de ontwikkelserver

```sh
npm run dev
```

Dit start de applicatie en opent een browservenster op `http://localhost:5173`
## ⚙️ Configuratie
De frontend communiceert met een backend API. Voor meer informatie zie de [backend instructies](./../Backend/README.md)

## 🛠️ Builden voor productie

```sh
npm run build
```

Dit genereert een `dist/` map met de geoptimaliseerde productieversie van de applicatie.

## ✅ Linter en Formatteren

Om de code te controleren en te formatteren:

```sh
npm run lint
npm run format
```

## 🧪 Testen

Indien je tests hebt geïmplementeerd, draai ze met:

```sh
npm test
```
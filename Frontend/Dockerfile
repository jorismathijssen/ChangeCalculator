##############################
# Stap 1: React build
##############################
FROM node:20-alpine AS build

WORKDIR /app
COPY package*.json ./
RUN npm ci --omit=dev

COPY . .
RUN npm run build

##############################
# Stap 2: Nginx
##############################
FROM nginx:alpine

# Kopieer alleen de build-output (dist) naar de webroot van Nginx
COPY --from=build /app/dist /usr/share/nginx/html

# Optioneel: Kopieer een simpele custom config voor SPA
# (zie onderaan voor minimal.conf)
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]

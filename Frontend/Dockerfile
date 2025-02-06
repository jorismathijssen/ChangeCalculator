# Use Node.js as the base image for building
FROM node:20-alpine AS build

# Set working directory
WORKDIR /app

# Copy package.json and install dependencies
COPY package.json package-lock.json ./
RUN npm install --frozen-lockfile

# Copy project files and build
COPY . .
RUN npm run build

# Debug: Ensure the `dist/` directory exists
RUN ls -la dist/

# Use Nginx to serve the Vite-built React app
FROM nginx:alpine

# Copy built files to Nginx public directory
COPY --from=build /app/dist /usr/share/nginx/html

# Expose the frontend port
EXPOSE 80

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]

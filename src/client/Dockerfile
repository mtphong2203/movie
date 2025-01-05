# Stage 1: Build Angular App
FROM node:23 AS build

# Set working directory
WORKDIR /app

# Copy package.json and package-lock.json for dependency installation
COPY package*.json ./

# Install dependencies
RUN npm install --legacy-peer-deps

# Copy source code
COPY . .

# Build the Angular app in production mode
RUN npm run build --prod

# Stage 2: Serve Angular App with Nginx
FROM nginx:1.27.3

# Copy built Angular app to Nginx HTML directory
COPY --from=build /app/dist/movie-theater/browser /usr/share/nginx/html

# Copy custom Nginx configuration (optional)
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Expose port 80
EXPOSE 80

# Start Nginx server
CMD ["nginx", "-g", "daemon off;"]

# Step 1: Build the jar file using Maven
FROM maven:3.9.9-amazoncorretto-21-debian AS build

# Declare build arguments
ARG SPRING_PROFILES_ACTIVE
ARG DB_HOST
ARG DB_PORT
ARG DB_NAME
ARG DB_USER
ARG DB_PASSWORD
ARG ACCESS_TOKEN_SECRET_KEY
ARG MAIL_HOST
ARG MAIL_PORT
ARG MAIL_USERNAME
ARG MAIL_DISPLAYNAME
ARG MAIL_PASSWORD
ARG MAIL_SMTP_AUTH
ARG MAIL_SMTP_STARTTLS_ENABLE
ARG MAIL_SMTP_TIMEOUT
ARG MAIL_SMTP_CONNECTIONTIMEOUT
ARG MAIL_SMTP_WRITETIMEOUT
ARG ANG_APP_URL

# Set environment variables from build arguments
ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}
ENV DB_HOST=${DB_HOST}
ENV DB_PORT=${DB_PORT}
ENV DB_NAME=${DB_NAME}
ENV DB_USER=${DB_USER}
ENV DB_PASSWORD=${DB_PASSWORD}
ENV ACCESS_TOKEN_SECRET_KEY=${ACCESS_TOKEN_SECRET_KEY}
ENV MAIL_HOST=${MAIL_HOST}
ENV MAIL_PORT=${MAIL_PORT}
ENV MAIL_USERNAME=${MAIL_USERNAME}
ENV MAIL_DISPLAYNAME=${MAIL_DISPLAYNAME}
ENV MAIL_PASSWORD=${MAIL_PASSWORD}
ENV MAIL_SMTP_AUTH=${MAIL_SMTP_AUTH}
ENV MAIL_SMTP_STARTTLS_ENABLE=${MAIL_SMTP_STARTTLS_ENABLE}
ENV MAIL_SMTP_TIMEOUT=${MAIL_SMTP_TIMEOUT}
ENV MAIL_SMTP_CONNECTIONTIMEOUT=${MAIL_SMTP_CONNECTIONTIMEOUT}
ENV MAIL_SMTP_WRITETIMEOUT=${MAIL_SMTP_WRITETIMEOUT}
ENV ANG_APP_URL=${ANG_APP_URL}

# Set working directory inside the container
WORKDIR /app

# Copy pom.xml and install dependencies
COPY pom.xml .
RUN mvn -B -f pom.xml dependency:go-offline

# Copy all other project files and build project
COPY . .
RUN mvn -B install

# Step 2: Create the final image using OpenJDK as base
FROM openjdk:21-jdk

# Set working directory inside the container
WORKDIR /app

# Copy the jar file from the previous build stage
COPY --from=build /app/target/movietheater-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the application port
EXPOSE 8080

# Start the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
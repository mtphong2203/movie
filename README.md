# Movie Theater Management System

## Description

This project is a movie theater management system that allows users to book tickets for movies. The system allows users to view the list of movies, view the list of theaters, view the list of showtimes, and book tickets for a showtime. The system also allows the admin to add movies, add theaters, add showtimes, and view the list of bookings.

## Features

- View the list of movies
- View the list of theaters
- View the list of showtimes
- Book tickets for a showtime
- Add movies
- Add theaters
- Add showtimes
- View the list of bookings

## Technologies

- Java
- Spring Boot
- Spring Data JPA
- SQL Server Database
- Angular
- Tailwind CSS

## How to Run

1. Clone the repository:

```bash
git clone url
```

2. Change environment variables in `example.env` file:

```env
SPRING_PROFILES_ACTIVE=local
DB_HOST=localhost
DB_PORT=1433
DB_NAME=example_db
DB_USER=example_user
DB_PASSWORD=example_password
SECRET_KEY=example_secret_key
ANGULAR_APP_API_URL=http://localhost:8080/api
```

3. Rename `example.env` file to `.env` or `local.env`.

4. Run the Spring Boot application:

- On Mac/Linux:

```bash
cd src/server
set -a; . ./src/main/resources/deployment/local.env; set +a; mvn spring-boot:run
```

- On Windows:

```bash
cd src/server
```

Create a ps1 file `load-env.ps1` with the following content:

```ps1
Get-Content PATH_TO_local.env | ForEach-Object {
    $key, $value = $_.Split('=')
    [System.Environment]::SetEnvironmentVariable($key, $value, [System.EnvironmentVariableTarget]::Process)
}
```

Run the app with ps1 file:

```bash
.\load-env.ps1; mvn spring-boot:run
```

5. Run the Angular application:

```bash
cd src/client
npm i -g @angular/cli
npm i
ng s
```

6. Open the browser and navigate:

- Angular app: http://localhost:4200
- Spring Boot app: http://localhost:8080
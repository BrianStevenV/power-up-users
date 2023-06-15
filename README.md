<br />
<div align="center">
<h3 align="center">PRAGMA POWER-UP</h3>
  <p align="center">
    In this challenge you are going to design the backend of a system that centralizes the services and orders of a restaurant chain that has different branches in the city.
  </p>
</div>

### Built With

* ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
* ![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
* ![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
* ![MySQL](https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white)


<!-- GETTING STARTED -->
## Getting Started

To get a local copy up and running follow these steps.

### Prerequisites

* JDK 17 [https://jdk.java.net/java-se-ri/17](https://jdk.java.net/java-se-ri/17)
* Gradle [https://gradle.org/install/](https://gradle.org/install/)
* MySQL [https://dev.mysql.com/downloads/installer/](https://dev.mysql.com/downloads/installer/)

### Recommended Tools
* IntelliJ Community [https://www.jetbrains.com/idea/download/](https://www.jetbrains.com/idea/download/)
* Postman [https://www.postman.com/downloads/](https://www.postman.com/downloads/)

### Installation

1. Clone the repository
2. Change directory
   ```sh
   cd power-up-arquetipo-v3
   ```
3. Create a new database in MySQL called powerup
4. Update the database connection settings
   ```yml
   # src/main/resources/application-dev.yml
   spring:
      datasource:
          url: jdbc:mysql://localhost/powerup
          username: root
          password: <your-password>
   ```
5. After the tables are created execute src/main/resources/data.sql content to populate the database
6. Open Swagger UI and search the /auth/login endpoint and login with userDni: 123, password: 1234

<!-- USAGE -->
## Usage

1. Right-click the class PowerUpApplication and choose Run
2. Open [http://localhost:8090/swagger-ui/index.html](http://localhost:8090/swagger-ui/index.html) in your web browser

<!-- ROADMAP -->
## Tests

- Right-click the test folder and choose Run tests with coverage

## User Microservice

This microservice manages all the user information, in this microservice we can create four types of users: Administrator, Owner, Employee and Customer. We also find the Login endpoint so that the user can log in and obtain the access credentials according to his role which will allow him to use them in the application.
Example of auth request:
ENDPOINT: /auth/login/
```JSON
{
  "email": "provider3@gmail.com",
  "password": "string"
}
```

Response: 

```JSON
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwcm92aWRlcjNAZ21haWwwuY29tIiwicm9sZXMiOlsiUFJPJVklERVJfUk9MRSJdLCJkbmkiOiIyNzI5MyIsImlkIjo5MCwiaWF0IjoxNjg2ODQwODMwLCJleHAiOjE2ODc0ODg4MzB9. f-0fZsLpEpqlDyxnv5ugLaqW8aCZDYVxKKDKK3E_ZZu0."
}
```

For the creation of users there are some requirements:
- To create an Owner type user it is necessary that an Administrator type user is logged in at the time of creation, if the user is different or is not logged in he/she will not be able to make the creation.
- To create a user type Employee you need to be logged in as Owner, if the user is different you will not be able to make the creation.
- 

Example of the request: 
ENDPOINT: /user/register/
```JSON
{
  "dniNumber": "0969",
  "name": "example",
  "surname": "example",
  "mail": "example@gmail.com",
  "phone": "3192621119",
  "birthdate": "2002-06-15",
  "password": "string",
  "idRole": {
    "id": 2
  }
}
```

"response":
```JSON
{
  "message": "Person created successfully"
}
```
We can also find a second endpoint which is specific for the creation of client type users, this endpoint does not need any prior authorization to be used:
Example request:
ENDPOINT: /user/registerClient/
```JSON
{
  "dniNumber": "0161",
  "name": "example",
  "surname": "example",
  "mail": "example@gmail.com",
  "phone": "3192621119",
  "birthdate": "2002-06-15",
  "password": "string",
  "idRole": {
    "id": 1
  }
}
```

"response":
```JSON
{
  "message": "Person created successfully"
}
```
We find a GET endpoint which allows us to get the user by entering its id:
ENDPOINT: /user/{dniNumber}
```JSON
104
```
Finally, we have an endpoint that obtains the roles found in the application.
ENDPOINT: /role

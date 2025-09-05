Welcome to my **sandbox**, where I mess around, break stuff, learn, and occasionally fix it. 😅

💡 **Goal**
* Revisiting basics
* Experimenting freely, without any pressure
* Trying out the latest features in the newest versions
* Keep learning by doing, failing fast, and documenting along the way

📌 **Tech Stack**
- Java 24.0.2
- Spring-Boot 3.5.5 ( ~Spring 6.2.10 )
- Angular / Angular CLI 20.2.1
- Node 24.6.0 / Npm 11.5.2
- Postgres 42.7.4
- Docker

📝 **Todo** 
- ✔ Update my envs
  - JDK https://www.oracle.com/fr/java/technologies/downloads/
  - Node https://nodejs.org/
  - Angular CLI https://www.npmjs.com/package/@angular/cli
  - Farewell Eclipse https://www.jetbrains.com/

- ✔ Init a basic spring-boot project
  - ✔ Generate starter with [Spring Initializr](https://start.spring.io/)
  - ✔ Start with some `RestController`
- ✔ Connect to a Database 
    - ✔ Use `H2` inMemoryDB (temp)
      - ✔ Play with JDBC and Repositories
      - ✔ Add some unit and integration tests
    - ✔ Change `H2` by dockerized `Postgres`
- ✔ Web Interface
    - ✔ Add `Spring-secu` + `Thymeleaf` for a simple view (temp)
- ✔ Prepare for Angular
    - ✔ Create modules ([cf structure](structure.md)) + Fix POMs
    - ✔ Create a minimal Angular app
    - ✔ Replace `Thymeleaf` views by a redirection to `Angular` SPA entrypoint
    - ✔ Configure maven with plugins for build automation
- Use JWT to secure the API 
  - Backend
    - ✔ Create tool class for JWT validation & token
    - ✔ Change to **stateless** authentication in `Spring-secu` config
    - ✔ Create a `Filter` (interceptor) to validate requests
    - ✔ Create an AuthenticationController for login call
  - Front
    - Create Interceptor to inject token in API calls



- Password encryption -> Bcrypt
- Time of Tokens
- Role based access
- CORS config
- Import and play a bit with Bootstrap/PrimeNg 
- Create the login page






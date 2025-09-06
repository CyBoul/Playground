[< Back](README.md)

### Changes

#### ✔ Init a basic spring-boot project
- Generate starter with [Spring Initializr](https://start.spring.io/)
- Start with some `RestController`

#### ✔ Connect to a database
- Use `H2` inMemoryDB (temp)
    - Play with JDBC and Repositories
    - Add some unit and integration tests
- Change `H2` by dockerized `Postgres`
```compose.yaml
services:
    postgres:
        image: 'postgres:latest'
        environment:
            - 'POSTGRES_DB=DatabaseName'
            - 'POSTGRES_PASSWORD=password'
            - 'POSTGRES_USER=user'
        ports:
            - '5432:5432'
```
All we need is this `compose.yaml` and adding `spring-boot-docker-compose` to the pom dependencies.
```pom.xml
...
<!-- <dependency>-->
<!--	<groupId>com.h2database</groupId>-->
<!--    <artifactId>h2</artifactId>-->
<!--    <scope>runtime</scope>-->
<!-- </dependency>-->

<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.7.4</version>
    <scope>runtime</scope>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-docker-compose</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
...
```

---

### ✔ Web Interface

#### ✔ Play with Thymeleaf
- Add `Spring-secu` + `Thymeleaf` for minimal login views \

#### ✔ Prepare for Angular
- Create modules + Fix POMs
```
/my-app
│
├── pom.xml                <-- Parent POM
├── compose.yml            <-- Docker Compose 
├── mvnw                   <-- Maven wrapper 
├── mvnw.cmd
├── .mvn/
│
├── backend/ 
│   ├── pom.xml
│   ├── src/
│   │   ├── main/java/...
│   │   └── main/resources/
│   │       └── static/    <-- Angular build output ends up here
│   └── ...
│
└── frontend/              <-- Angular app
    ├── pom.xml            <-- Maven config for 'frontend-maven-plugin'
    ├── angular.json
    ├── package.json
    ├── tsconfig.json
    └── src/
```
- Create a minimal Angular app
```
cd Playground
ng new frontend --minimal
```
- Replace `Thymeleaf` views by a redirection to `Angular` SPA entrypoint
- Remove `Thymeleaf` dependencies
- Configure maven with plugins for build automation
  - tell maven to install node + dependencies + build `Angular` module (frontend.pom)
  - then, copy the build output to the backend (backend.pom)
  - make sure the front build before back (parent.pom)
```frontend.pom
...
    <build>
        <plugins>
            <plugin>
                <groupId>com.github.eirslett</groupId>
                <artifactId>frontend-maven-plugin</artifactId>
                <version>1.12.0</version>
                <configuration>
                    <nodeVersion>v24.6.0</nodeVersion>
                    <workingDirectory>.</workingDirectory>
                </configuration>
                <executions>
                
                    <!-- install node & npm -->
                    <execution>
                        <id>install-npm</id>
                        <goals>
                            <goal>install-node-and-npm</goal>
                        </goals>
                    </execution>
                    
                    <!-- npm i -->
                    <execution>
                        <id>npm-install</id>
                        <goals>
                            <goal>npm</goal>
                        </goals>
                    </execution>
                    
                    <!-- npm build script = ng build -->
                    <execution>
                        <id>npm-build</id>
                        <goals>
                            <goal>npm</goal>
                        </goals>
                        <configuration>
                            <arguments>run-script build</arguments>
                        </configuration>
                    </execution>
                    
                </executions>
            </plugin>
        </plugins>
    </build>
...
```
```backend.pom
...
  <build>
        <plugins>
            ...
            <plugin>
                <artifactId>maven-resources-plugin</artifactId>
                <version>3.3.1</version>
                <executions>
                    <!-- copy angular build files to spring static folder -->
                    <execution>
                        <id>copy-angular-build</id>
                        <phase>process-resources</phase>
                        <goals>
                            <goal>copy-resources</goal>
                        </goals>
                        <configuration>
                            <outputDirectory>${project.build.outputDirectory}/static</outputDirectory>
                            <resources>
                                <resource>
                                    <directory>../frontend/dist/frontend/browser</directory>
                                    <includes>
                                        <include>**/*</include>
                                    </includes>
                                </resource>
                            </resources>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
            ...
        </plugins>
    </build>
...
```
so now ``mvnw i`` from the project folder will :
- build the frontend
- copy build output from angular to the ``static`` resource folder of spring
- build the backend

_The modules' order declared in the parent POM define the order for build executions_
```parent.pom
...
    <modules>
        <module>frontend</module>
        <module>backend</module>
    </modules>
...
```


---

#### Use JWT to secure the API
- Backend
  - ✔ Create tool class for JWT validation & token
  - ✔ Change to **stateless** authentication in `Spring-secu` config
  - ✔ Create a `Filter` (interceptor) to validate requests
  - ✔ Create an AuthenticationController for login call
- Front
  -  _next_ >> Create http-interceptor to inject token in API calls 

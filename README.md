# REST-library
This API is just an addition to REST-library API by Roman Shmandrovskyi: https://github.com/RomanShmandrovskyi/REST-library-API.
The only difference being data being ingested into DB on the start-up.

There are 3 tables: `Author`, `Genre` and `Book`.

One Author has many Books (for Genre too) and one Book must has one Author and one Genre.

## Getting started
1. Clone project:
```
git clone https://github.com/Pastornak/REST-library-API-ingested.git
```

2. Open `src/main/resources/application.properties` file and enter instead `<data_base_name>` name you want for your data base:
```
spring.datasource.dbname=<data_base_name>
```
2.1. The API will up on built-in data base H2 will be injected with data;
Queries for injected data can be found at `/src/main/resources/data.sql`;

2.2. It is also possible to up API using SQL data base. To do this, just change thre last config properties to:
```
spring.datasource.url=jdbc:mysql://localhost:3306/${spring.datasource.dbname}?serverTimezone=UTC
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL57Dialect
```

3. Load root project folder in console and run command:
```
mvn spring-boot:run
```
Or import project as Maven project and just run main method in `App` class from `src/main/java/ua/com/epam/app` package;

4. Swagger with documentation will be available on:
```
http://localhost:8080/swagger-ui.html
```

5. If you use built in H2 data base, you can reach something like SQL Workbench. To up it change `spring.h2.console.enabled` property to `true`:
```
spring.h2.console.enabled=true
```
Rebuild project. H2 console will be available on:
```
http://localhost:8080/h2-workbench
```
In opened window leave all inputs as default expect `JDBC URL`. Here you must paste `jdbc:h2:file:<absolute_path_to_db_file>`. Look step `2.1.`

## Data ingestion
It is realized random data ingestion in `DataIngestion` class by `src/main/java/ua/com/epam` path. To generate some random data run main method from this class;

It is possible to configure counts of every object:
```
private final static int authorsCount = 150;
private final static int genresCount = 30;
private final static int booksCount = 1000;
```
Note the comments on these fields.

When generation will be finished, go to `resources`. Here will be generated `data.sql` script file. Just let it be, the DB will be filled on the start-up.

After that you can fully feel all opportunities of REST Library API.

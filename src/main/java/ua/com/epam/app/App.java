package ua.com.epam.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ua.com.epam.DataIngestion;

@ComponentScan(basePackages = "ua.com.epam")
@EnableJpaRepositories("ua.com.epam.repository")
@EntityScan("ua.com.epam.entity")
@SpringBootApplication
public class App {
    public static void main(String[] args) {
        try {
            DataIngestion.main(args);
        } catch(Exception e) {
            System.err.println("Failed generating ingestion data");
            e.printStackTrace();
        }
        SpringApplication.run(App.class, args);
    }
}
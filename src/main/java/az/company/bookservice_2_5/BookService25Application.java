package az.company.bookservice_2_5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BookService25Application {

    public static void main(String[] args) {
        SpringApplication.run(BookService25Application.class, args);
    }

}

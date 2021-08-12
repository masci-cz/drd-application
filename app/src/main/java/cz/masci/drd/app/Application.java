package cz.masci.drd.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "cz.masci.drd")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

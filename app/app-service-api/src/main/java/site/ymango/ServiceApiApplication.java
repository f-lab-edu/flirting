package site.ymango;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "site.ymango")
public class ServiceApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(ServiceApiApplication.class, args);
  }

}

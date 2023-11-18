package br.com.pcvj.awss3app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AwsS3AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AwsS3AppApplication.class, args);
    }

}

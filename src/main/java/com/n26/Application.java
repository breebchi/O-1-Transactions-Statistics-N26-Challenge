package com.n26;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * main app
 *
 * @author Mahmoud Kraiem
 */
@SpringBootApplication
@EnableScheduling
@EnableAsync(proxyTargetClass=true)
public class Application {

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }

}

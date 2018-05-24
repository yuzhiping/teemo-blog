package com.github.hexsmith.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author hexsmith
 * @since 2018-05-17 22:57
 * @version v1.0
 */
@SpringBootApplication
@EnableTransactionManagement
public class TeemoBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeemoBlogApplication.class, args);
    }
}

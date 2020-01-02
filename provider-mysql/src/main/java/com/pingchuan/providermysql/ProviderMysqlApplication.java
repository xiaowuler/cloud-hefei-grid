package com.pingchuan.providermysql;

import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;

/**
 * @author xiaowuler
 */

@EnableEurekaClient
@SpringBootApplication(exclude={
        MongoAutoConfiguration.class,
        MongoDataAutoConfiguration.class
})
public class ProviderMysqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderMysqlApplication.class, args);
    }
}
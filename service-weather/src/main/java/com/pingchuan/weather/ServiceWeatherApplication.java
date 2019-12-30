package com.pingchuan.weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication(exclude={
        MongoAutoConfiguration.class,
        MongoDataAutoConfiguration.class
})
public class ServiceWeatherApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceWeatherApplication.class, args);
    }

}

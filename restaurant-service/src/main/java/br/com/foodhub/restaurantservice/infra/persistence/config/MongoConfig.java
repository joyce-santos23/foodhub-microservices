package br.com.foodhub.restaurantservice.infra.persistence.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoConfig {

    @Bean
    public MongoClient mongoClient() {
        String uri = System.getenv("SPRING_DATA_MONGODB_URI");
        System.out.println("🔥 USANDO MONGO URI: " + uri);
        return MongoClients.create(uri);
    }
}
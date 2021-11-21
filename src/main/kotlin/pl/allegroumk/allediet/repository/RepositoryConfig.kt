package pl.allegroumk.allediet.repository

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate
import pl.allegroumk.allediet.repository.mongo.MongoMealsRepository

@Configuration
class RepositoryConfig {

//    @Bean
//    fun mongoClient(): MongoClient {
//        val connectionString = ConnectionString("mongodb://localhost:27017/test")
//        val mongoClientSettings = MongoClientSettings.builder()
//            .retryWrites(true)
//            .applyConnectionString(connectionString)
//            .build()
//        return MongoClients.create(mongoClientSettings)
//    }
//
//    @Bean
//    fun mongoTemplate(mongoClient: MongoClient) = MongoTemplate(mongoClient, "test")

    @Bean
    fun mealsRepository(template: MongoTemplate) = MongoMealsRepository(template)

    @Bean
    fun feedMealsRepository(repository: MealsRepository) = FeedMealsRepository(repository)

}
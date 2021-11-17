package pl.allegroumk.allediet.repository

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.allegroumk.allediet.repository.mongo.MongoMealsRepository
import pl.allegroumk.allediet.repository.mongo.SpringMongoMealsRepository

@Configuration
class RepositoryConfig {

    @Bean
    fun mealsRepository(repository: SpringMongoMealsRepository): MealsRepository {
        return MongoMealsRepository(repository)
    }

    @Bean
    fun feedMealsRepository(repository: MealsRepository): FeedMealsRepository {
        return FeedMealsRepository(repository)
    }
}
package pl.allegroumk.allediet.repository

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.allegroumk.allediet.repository.inMemory.InMemoryMealsRepository

@Configuration
class RepositoryConfig {

    @Bean
    fun mealsRepository() = InMemoryMealsRepository()

    @Bean
    fun feedMealsRepository(repository: MealsRepository) = FeedMealsRepository(repository)

}
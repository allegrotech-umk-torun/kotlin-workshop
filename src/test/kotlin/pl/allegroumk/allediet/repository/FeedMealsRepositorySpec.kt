package pl.allegroumk.allediet.repository

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assumptions.assumeThat
import org.junit.jupiter.api.Test
import pl.allegroumk.allediet.repository.inMemory.InMemoryMealsRepository

class FeedMealsRepositorySpec {

    @Test
    fun `should feed meals repository with default meals when there are no meals available`() {
        // given
        val repository = InMemoryMealsRepository()
        val feedRepository = FeedMealsRepository(repository)

        // when
        feedRepository.feedMealsRepository()

        // then
        val meals = repository.getAllMeals()
        assertThat(meals).hasSize(4)
        assertThat(meals.map { it.name }).containsAll(listOf("breakfast", "lunch", "dinner", "supper"))
    }

    @Test
    fun `should not feed meals repository when there are already some meals available`() {
        // given
        val repository = InMemoryMealsRepository()
        val feedRepository = FeedMealsRepository(repository)
        feedRepository.feedMealsRepository()
        assumeThat(repository.getAllMeals()).hasSize(4)

        // when
        feedRepository.feedMealsRepository()

        // then
        assertThat(repository.getAllMeals()).hasSize(4)
    }

}

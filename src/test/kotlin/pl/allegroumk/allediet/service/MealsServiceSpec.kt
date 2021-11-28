package pl.allegroumk.allediet.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pl.allegroumk.allediet.repository.FeedMealsRepository
import pl.allegroumk.allediet.repository.inMemory.InMemoryMealsRepository

class MealsServiceSpec {

    private val inMemoryMealsRepository = InMemoryMealsRepository()

    @BeforeEach
    fun setup() {
        FeedMealsRepository(inMemoryMealsRepository).feedMealsRepository()
    }

    @Test
    fun `should initially feed meals repository`() {
        // given
        val service = MealsService(inMemoryMealsRepository)

        // when
        val meals = service.getAllMeals()

        // then
        assertThat(meals.count()).isEqualTo(4)
    }
}
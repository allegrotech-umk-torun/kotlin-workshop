package pl.allegro.umk.allediet

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assumptions.assumeThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate
import pl.allegroumk.allediet.AlledietApplication
import pl.allegroumk.allediet.repository.FeedMealsRepository
import pl.allegroumk.allediet.repository.mongo.MongoMeal

@SpringBootTest(
    classes = [AlledietApplication::class],
    properties = ["application.environment=integration"],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class AlledietApplicationSpec {

    @Autowired
    lateinit var feedRepository: FeedMealsRepository

    @Autowired
    lateinit var mongoTemplate: MongoTemplate

    @Test
    fun `should save default meals when application starts`() {
        // when
        val foundMeals = mongoTemplate.findAll(MongoMeal::class.java)

        // then
        assertThat(foundMeals).hasSize(4)
        assertThat(foundMeals.map { it.name }).containsAll(listOf("breakfast", "lunch", "dinner", "supper"))
    }

    @Test
    fun `should not save any default meal when there are already some meals available`() {
        // given
        assumeThat(mongoTemplate.findAll(MongoMeal::class.java)).hasSize(4)

        // when
        feedRepository.feedMealsRepository()

        // then
        assertThat(mongoTemplate.findAll(MongoMeal::class.java)).hasSize(4)
    }
}

package pl.allegro.umk.allediet.service

import org.apache.commons.lang3.RandomStringUtils
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.findAllAndRemove
import org.springframework.data.mongodb.core.query.Query
import pl.allegroumk.allediet.AlledietApplication
import pl.allegroumk.allediet.repository.mongo.MongoIngredient
import pl.allegroumk.allediet.repository.mongo.MongoMeal
import pl.allegroumk.allediet.service.MealsService
import pl.allegroumk.allediet.service.model.Ingredient
import java.time.LocalDateTime

@SpringBootTest(
    classes = [AlledietApplication::class],
    properties = ["application.environment=integration"],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class MealsServiceSpec {

    @Autowired
    lateinit var mongoTemplate: MongoTemplate

    @Autowired
    lateinit var mealsService: MealsService

    @BeforeEach
    fun beforeEach() {
        mongoTemplate.findAllAndRemove<MongoMeal>(Query())
    }

    @Test
    fun `should return meal by id`() {
        // given
        val firstMealToAdd = prepareMongoMeal("breakfast", listOf(MongoIngredient("kawa", 50)))
        mongoTemplate.save(firstMealToAdd)

        // when
        val foundMeal = mealsService.getMeal(firstMealToAdd.id)

        // then
        assertThat(foundMeal?.id).isEqualTo(firstMealToAdd.id)
        assertThat(foundMeal?.name).isEqualTo("breakfast")
        assertThat(foundMeal?.ingredients).isEqualTo(listOf(Ingredient("kawa", 50)))
        assertThat(foundMeal?.calories).isEqualTo(50)
        assertThat(foundMeal?.createdAt).isNotNull
        assertThat(foundMeal?.updatedAt).isNull()
    }

    // TODO

    private fun prepareMongoMeal(name: String, ingredients: List<MongoIngredient>) =
        MongoMeal(
            RandomStringUtils.random(8, true, true),
            name,
            ingredients.sumOf { it.calories },
            ingredients,
            LocalDateTime.now()
        )

}

package pl.allegroumk.allediet.repository.mongo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDateTime

@Document(collection = "meals")
data class MealMongo(
    @Id
    val id: String,
    val name: String,
    val calories: Int,
    @Field(name = "ingredients")
    val ingredients: List<IngredientMongo>,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime? = null
)

data class IngredientMongo(
    val name: String,
    val calories: Int
)
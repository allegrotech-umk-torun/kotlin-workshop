package pl.allegroumk.allediet.service.model

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Meal(
    val id: String,
    val name: String,
    val calories: Int,
    val ingredients: List<Ingredient>,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime? = null
) {
    constructor(
        id: String,
        name: String,
        ingredients: List<Ingredient>,
        createdAt: LocalDateTime,
        updatedAt: LocalDateTime? = null
    ) : this(id, name, ingredients.sumOf { it.calories }, ingredients, createdAt, updatedAt)
}

@JsonInclude(JsonInclude.Include.NON_NULL)
data class MealToUpdate(
    val id: String,
    val ingredients: List<Ingredient>
)

data class Ingredient(
    val name: String,
    val calories: Int
)
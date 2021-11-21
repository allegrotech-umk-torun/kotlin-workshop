package pl.allegroumk.allediet.service.model

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class MealsSummary(
    val maxIngredientsCount: String,
    val minIngredientsCount: String,
    val mealIdWithMaxIngredientsCount: Int,
    val mealIdWithMinIngredientsCount: Int
)
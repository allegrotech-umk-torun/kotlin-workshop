package pl.allegroumk.allediet.service.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

data class IngredientsList(
    val ingredientsList: List<ExternalIngredient> = emptyList()
)

data class ExternalIngredient(
    val id: Long,
    val name: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class IngredientInformation(
    val id: Long,
    val name: String,
    val amount: Int,
    val unit: String,
    val possibleUnits: List<String>,
    val calories: Float
)
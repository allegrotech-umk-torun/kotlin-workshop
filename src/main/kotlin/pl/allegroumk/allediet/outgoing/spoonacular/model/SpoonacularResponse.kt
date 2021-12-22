package pl.allegroumk.allediet.outgoing.spoonacular.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class SearchResults(
    val results: List<SearchItem>,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class SearchItem(
    val id: Long,
    val name: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class SpoonacularIngredientInformation(
    val id: Long,
    val name: String,
    val amount: Int,
    val unit: String,
    val possibleUnits: List<String>,
    val nutrition: SpoonacularNutrition
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class SpoonacularNutrition(
    val nutrients: List<SpoonacularNutrient>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class SpoonacularNutrient(
    val title: String,
    val name: String,
    val amount: Float,
    val unit: String
)
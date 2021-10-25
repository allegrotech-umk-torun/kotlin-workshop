package pl.allegroumk.allediet.api.model

data class InputMeal(
    val name: String,
    val calories: Int,
    val ingredients: List<InputIngredient>
)

data class InputIngredient(
    val name: String,
    val calories: Int
)
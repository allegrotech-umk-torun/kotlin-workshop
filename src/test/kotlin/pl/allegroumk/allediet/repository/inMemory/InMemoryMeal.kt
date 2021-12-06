package pl.allegroumk.allediet.repository.inMemory

import java.time.LocalDateTime

data class InMemoryMeal(
    val id: String,
    val name: String,
    val calories: Int,
    val ingredients: List<InMemoryIngredient>,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime? = null
)

data class InMemoryIngredient(
    val name: String,
    val calories: Int
)
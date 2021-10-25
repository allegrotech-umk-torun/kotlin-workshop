package pl.allegroumk.allediet.service.model

import java.time.LocalDateTime
import java.util.*

data class Meal(
    val id: UUID,
    val name: String,
    val calories: Int,
    val ingredients: List<Ingredient>,
    val createdAt: LocalDateTime
)

data class Ingredient(
    val name: String,
    val calories: Int
)
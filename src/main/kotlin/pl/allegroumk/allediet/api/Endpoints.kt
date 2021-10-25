package pl.allegroumk.allediet.api

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pl.allegroumk.allediet.api.model.InputMeal
import pl.allegroumk.allediet.api.model.UpdateMeal
import pl.allegroumk.allediet.service.MealsService
import pl.allegroumk.allediet.service.model.Meal

@RestController
@RequestMapping("/meals")
class Endpoints(
    private val mealsService: MealsService
) {

    @GetMapping(
        value = ["/all"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getMeals() = mealsService.getAllMeals()

    @GetMapping(
        value = ["/all"],
        produces = [V2_MEDIA_TYPE]
    )
    fun getMealsSorted() = mealsService.getAllMeals().sortedBy { it.calories }

    @GetMapping(
        value = ["/show"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getMeal(@RequestParam("id") id: String) = mealsService.getMeal(id) ?: ResponseEntity.notFound().build<Meal>()

    @PutMapping(
        value = ["/add"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun insertMeal(@RequestBody inputMeal: InputMeal) = mealsService.insertMeal(inputMeal)

    @PostMapping(
        value = ["/update"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun updateMeal(@RequestBody updateMeal: UpdateMeal) = mealsService.updateMeal(updateMeal) ?: ResponseEntity.notFound().build<Meal>()

    @DeleteMapping(
        value = ["/remove/{id}"]
    )
    fun deleteMeal(@PathVariable("id") id: String) {
        mealsService.deleteMeal(id)
    }

    companion object {
       const val V2_MEDIA_TYPE = "application/v2+json"
    }
}
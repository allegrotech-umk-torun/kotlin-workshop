package pl.allegroumk.allediet.api

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import pl.allegroumk.allediet.api.model.InputMeal
import pl.allegroumk.allediet.service.MealsService

@RestController
@RequestMapping("/meals")
class Endpoints(
    private val mealsService: MealsService
) {

    @GetMapping(
        value = ["/all"],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun showMeal() = mealsService.getAllMeals()

    @PutMapping(
        value = ["/add"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun insertMeal(@RequestBody inputMeal: InputMeal) = mealsService.insertMeal(inputMeal)
}
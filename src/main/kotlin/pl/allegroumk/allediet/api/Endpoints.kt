package pl.allegroumk.allediet.api

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
        produces = [RESPONSE_MEDIA_TYPE]
    )
    @ResponseBody
    fun getMeals(): ResponseEntity<Iterable<Meal>> {
        val allMeals = mealsService.getAllMeals()
        return ResponseEntity.ok().body(allMeals)
    }

    @GetMapping(
        value = ["/all"],
        produces = [V2_MEDIA_TYPE]
    )
    fun getMealsSortedInApp(): ResponseEntity<Iterable<Meal>> {
        val allMeals = mealsService.getAllMeals().sortedBy { it.calories }
        return ResponseEntity.ok().body(allMeals)
    }

    @GetMapping(
        value = ["/show"],
        produces = [RESPONSE_MEDIA_TYPE]
    )
    fun getMeal(@RequestParam("id") id: String): ResponseEntity<Meal> {
        return mealsService.getMeal(id)?.let { ResponseEntity.ok().body(it) } ?: ResponseEntity.notFound().build()
    }

    @PostMapping(
        value = ["/add"],
        consumes = [RESPONSE_MEDIA_TYPE],
        produces = [RESPONSE_MEDIA_TYPE]
    )
    fun insertMeal(@RequestBody inputMeal: InputMeal): ResponseEntity<Meal> {
        return ResponseEntity.ok().body(mealsService.insertMeal(inputMeal))
    }

    @PostMapping(
        value = ["/update"],
        consumes = [RESPONSE_MEDIA_TYPE],
        produces = [RESPONSE_MEDIA_TYPE]
    )
    fun updateMeal(@RequestBody updateMeal: UpdateMeal): ResponseEntity<Meal> {
        return mealsService.updateMeal(updateMeal)?.let { ResponseEntity.ok().body(it) } ?: ResponseEntity.notFound()
            .build()
    }

    @DeleteMapping(
        value = ["/remove/{id}"]
    )
    fun deleteMeal(@PathVariable("id") id: String) {
        mealsService.deleteMeal(id)
    }

    companion object {
        const val RESPONSE_MEDIA_TYPE = "application/json"
        const val V2_MEDIA_TYPE = "application/v2+json"
    }
}
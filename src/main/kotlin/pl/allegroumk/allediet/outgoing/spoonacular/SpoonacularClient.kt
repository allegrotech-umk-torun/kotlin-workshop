package pl.allegroumk.allediet.outgoing.spoonacular

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import pl.allegroumk.allediet.outgoing.spoonacular.model.SpoonacularIngredientInformation
import pl.allegroumk.allediet.outgoing.spoonacular.model.SearchResults

@Component
class SpoonacularClient(
    private val spoonacularRestTemplate: RestTemplate,
    private val spoonacularRestTemplateForRetry: RestTemplate,
    private val spoonacularClientConfiguration: SpoonacularClientConfiguration
) {
    private val logger = LoggerFactory.getLogger(SpoonacularService::class.java)

    fun findIngredientsByName(name: String): SearchResults {

        val uri = UriComponentsBuilder.fromUriString(spoonacularClientConfiguration.url!!)
            .path(spoonacularClientConfiguration.searchPath!!)
            .queryParam("query", name)
            .queryParam("number", spoonacularClientConfiguration.searchLimit ?: 10)
            .queryParam("sort", "calories")
            .queryParam("sortDirection", "desc")
            .queryParam("apiKey", spoonacularClientConfiguration.apiKey)
            .build().toUriString()

        return try {
            spoonacularRestTemplate.getForObject(uri, SearchResults::class.java) ?: SearchResults(emptyList())
        } catch (ex: RestClientException) {
            try {
                logger.warn("Retry request for ingredients by name. Exception for first request cause = ${ex.cause}")
                spoonacularRestTemplateForRetry.getForObject(uri, SearchResults::class.java)
                    ?: SearchResults(emptyList())
            } catch (ex: RestClientException) {
                logger.error("Retry request for ingredients by name failed. Cause = ${ex.cause}")
                SearchResults(emptyList())
            }
        }
    }

    fun getIngredientInformationById(
        id: Long,
        amount: Int = 100,
        unit: String = "gram"
    ): SpoonacularIngredientInformation? {
        val uri = UriComponentsBuilder.fromUriString(spoonacularClientConfiguration.url!!)
            .path(String.format(spoonacularClientConfiguration.detailsPath!!, id))
            .queryParam("amount", amount)
            .queryParam("unit", unit)
            .queryParam("apiKey", spoonacularClientConfiguration.apiKey)
            .build().toUriString()

        return try {
            spoonacularRestTemplate.getForObject(uri, SpoonacularIngredientInformation::class.java)
        } catch (ex: RestClientException) {
            return try {
                logger.warn("Retry request for ingredient information. Exception for first request cause = ${ex.cause}")
                spoonacularRestTemplateForRetry.getForObject(uri, SpoonacularIngredientInformation::class.java)
            } catch (ex: RestClientException) {
                logger.error("Retry request for ingredient information failed. Cause = ${ex.cause}")
                null
            }
        }
    }
}
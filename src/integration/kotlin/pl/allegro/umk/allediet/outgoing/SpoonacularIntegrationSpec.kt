package pl.allegro.umk.allediet.outgoing

import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.junit.ClassRule
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import pl.allegroumk.allediet.AlledietApplication
import pl.allegroumk.allediet.service.model.IngredientInformation
import pl.allegroumk.allediet.service.model.IngredientsList

@SpringBootTest(
    classes = [AlledietApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("integration")
@RunWith(SpringRunner::class)
class SpoonacularIntegrationSpec {

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    companion object {
        @ClassRule
        @JvmField
        val wireMockRule = WireMockRule(7070)
    }

    fun prepareSpoonacularStubForSearch() {
        stubFor(
            get(urlPathEqualTo("/food/ingredients/search"))
                .willReturn(
                    aResponse().withBodyFile("spoonacular/searchForEggs.json")
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(200)
                )
        )
    }

    fun prepareSpoonacularStubForInformation(id: Long) {
        stubFor(
            get(urlPathEqualTo("/food/ingredients/$id/information"))
                .willReturn(
                    aResponse().withBodyFile("spoonacular/$id.json")
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(200)
                )
        )
    }

    @Test
    fun getIngredientsListByNameTest() {
        // given:
        prepareSpoonacularStubForSearch()

        // when:
        val response = restTemplate.getForEntity("/meals/getIngredientsListByName?name=eggs", IngredientsList::class.java)

        val responseBody = response.body

        // then:
        assert(response.statusCode == HttpStatus.OK)
        assert(responseBody?.ingredientsList?.size == 5)
        assert(responseBody?.ingredientsList?.get(2)?.id == 3L)
        assert(responseBody?.ingredientsList?.get(2)?.name == "jajka chia")
    }

    @Test
    fun getIngredientInformationTest() {
        // given:
        val idToSearch = 3L
        prepareSpoonacularStubForInformation(idToSearch)

        // when:
        val response = restTemplate.getForEntity("/meals/getIngredientInformation?id=$idToSearch", IngredientInformation::class.java)

        val responseBody = response.body

        // then:
        assert(response.statusCode == HttpStatus.OK)
        assert(responseBody?.id == 3L)
        assert(responseBody?.name == "jajka chia")
        assert(responseBody?.calories == 486F)
    }
}
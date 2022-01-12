package pl.allegro.umk.allediet.outgoing

import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.junit.WireMockRule
import com.github.tomakehurst.wiremock.stubbing.Scenario
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

    private var scenario = Scenario.STARTED

    companion object {
        @ClassRule
        @JvmField
        val spoonacularServiceStub = WireMockRule(7070)
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

    fun prepareSpoonacularStubForInformation(id: Long, responseStatusCode: Int = 200, responseFile: String, state: String? = null, changeStateTo: String? = null) {
        stubFor(
            get(urlPathEqualTo("/food/ingredients/$id/information")).inScenario(scenario)
                .whenScenarioStateIs(state)
                .willReturn(
                    aResponse().withBodyFile("spoonacular/$responseFile.json")
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(responseStatusCode)
                )
                .willSetStateTo(changeStateTo))
    }

    @Test
    fun getIngredientsListByNameTest() {
        // given:
        spoonacularServiceStub.resetAll()
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
        spoonacularServiceStub.resetAll()
        val idToSearch = 3L
        prepareSpoonacularStubForInformation(idToSearch, 200, idToSearch.toString())

        // when:
        val response = restTemplate.getForEntity("/meals/getIngredientInformation?id=$idToSearch", IngredientInformation::class.java)

        val responseBody = response.body

        // then:
        assert(response.statusCode == HttpStatus.OK)
        assert(responseBody?.id == 3L)
        assert(responseBody?.name == "jajka chia")
        assert(responseBody?.calories == 486F)
    }

    @Test
    fun shouldRetryRequestToSpoonacularServiceWhenFirstResponseFailed() {
        // given:
        spoonacularServiceStub.resetAll()
        val idToSearch = 3L
        prepareSpoonacularStubForInformation(idToSearch, 503, "503", null, "serviceFailed")
        prepareSpoonacularStubForInformation(idToSearch, 200, "3", "serviceFailed", null)

        // when:
        val response = restTemplate.getForEntity("/meals/getIngredientInformation?id=$idToSearch", IngredientInformation::class.java)

        val responseBody = response.body

        // then:
        assert(response.statusCode == HttpStatus.OK)

        assert(responseBody?.name == "jajka chia")

        spoonacularServiceStub.verify(2, getRequestedFor(urlPathEqualTo("/food/ingredients/$idToSearch/information")))
    }
}
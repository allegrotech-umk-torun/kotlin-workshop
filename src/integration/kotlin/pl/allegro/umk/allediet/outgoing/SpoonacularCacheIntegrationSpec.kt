package pl.allegro.umk.allediet.outgoing

import com.github.benmanes.caffeine.cache.LoadingCache
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
import pl.allegroumk.allediet.outgoing.spoonacular.model.SpoonacularIngredientInformation
import pl.allegroumk.allediet.service.model.IngredientInformation
import pl.allegroumk.allediet.service.model.IngredientsList
import java.lang.Thread.sleep

@SpringBootTest(
    classes = [AlledietApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("integration")
@RunWith(SpringRunner::class)
class SpoonacularCacheIntegrationSpec {

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var cache: LoadingCache<Long, SpoonacularIngredientInformation>

    private var scenario = Scenario.STARTED

    companion object {
        @ClassRule
        @JvmField
        val spoonacularServiceStub = WireMockRule(7070)
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
    fun shouldReturnStaleValueAndNotUpdateCacheBeforeTimeToRefresh() {
        // given:
        spoonacularServiceStub.resetAll()
        cache.invalidateAll()
        val idToSearch = 3
        prepareSpoonacularStubForInformation(3, 200, "3")

        // when:
        val response1 = restTemplate.getForEntity("/meals/getIngredientInformation?id=$idToSearch", IngredientInformation::class.java)
        val response2 = restTemplate.getForEntity("/meals/getIngredientInformation?id=$idToSearch", IngredientInformation::class.java)

        val responseBody1 = response1.body
        val responseBody2 = response2.body

        // then:
        assert(cache.estimatedSize() == 1L)
        assert(response1.statusCode == HttpStatus.OK)
        assert(response2.statusCode == HttpStatus.OK)
        assert(responseBody1?.id == 3L)
        assert(responseBody2?.id == 3L)
        assert(responseBody1?.name == "jajka chia")
        assert(responseBody2?.name == "jajka chia")

        spoonacularServiceStub.verify(1, getRequestedFor(urlPathEqualTo("/food/ingredients/$idToSearch/information")))
    }

    @Test
    fun shouldReturnStaleValueAndUpdateCacheAfterTimeToRefresh() {
        // given:
        cache.invalidateAll()
        spoonacularServiceStub.resetAll()
        val idToSearch = 3L
        prepareSpoonacularStubForInformation(idToSearch, 200, "3", null, "after1request")
        prepareSpoonacularStubForInformation(idToSearch, 200, "3-refreshed", "after1request", null)

        // when:
        val response1 = restTemplate.getForEntity("/meals/getIngredientInformation?id=$idToSearch", IngredientInformation::class.java)
        val response2 = restTemplate.getForEntity("/meals/getIngredientInformation?id=$idToSearch", IngredientInformation::class.java)
        sleep(4000)
        val response3 = restTemplate.getForEntity("/meals/getIngredientInformation?id=$idToSearch", IngredientInformation::class.java)
        sleep(1000)
        val response4 = restTemplate.getForEntity("/meals/getIngredientInformation?id=$idToSearch", IngredientInformation::class.java)

        val responseBody1 = response1.body
        val responseBody2 = response2.body
        val responseBody3 = response3.body
        val responseBody4 = response4.body

        // then:
        assert(response1.statusCode == HttpStatus.OK)
        assert(response2.statusCode == HttpStatus.OK)
        assert(response3.statusCode == HttpStatus.OK)
        assert(response4.statusCode == HttpStatus.OK)

        assert(responseBody1?.name == "jajka chia")
        assert(responseBody2?.name == "jajka chia")
        assert(responseBody3?.name == "jajka chia")
        assert(responseBody4?.name == "jajka chia - refreshed")

        spoonacularServiceStub.verify(2, getRequestedFor(urlPathEqualTo("/food/ingredients/$idToSearch/information")))
    }

    @Test
    fun shouldReturnStaleValueAndSaveItWhenSpoonacularServiceFailed() {
        // given:
        cache.invalidateAll()
        spoonacularServiceStub.resetAll()
        val idToSearch = 3L
        prepareSpoonacularStubForInformation(idToSearch, 200, "3", null, "serviceFailed")
        prepareSpoonacularStubForInformation(idToSearch, 503, "503", "serviceFailed", "serviceFailed")

        // when:
        val response1 = restTemplate.getForEntity("/meals/getIngredientInformation?id=$idToSearch", IngredientInformation::class.java)
        sleep(4000)
        val response2 = restTemplate.getForEntity("/meals/getIngredientInformation?id=$idToSearch", IngredientInformation::class.java)
        sleep(4000)
        val response3 = restTemplate.getForEntity("/meals/getIngredientInformation?id=$idToSearch", IngredientInformation::class.java)

        val responseBody1 = response1.body
        val responseBody2 = response2.body
        val responseBody3 = response3.body

        // then:
        assert(response1.statusCode == HttpStatus.OK)
        assert(response2.statusCode == HttpStatus.OK)
        assert(response3.statusCode == HttpStatus.NOT_FOUND)

        assert(responseBody1?.name == "jajka chia")
        assert(responseBody2?.name == "jajka chia")
        assert(responseBody3 == null)

        spoonacularServiceStub.verify(5, getRequestedFor(urlPathEqualTo("/food/ingredients/$idToSearch/information")))
    }

}
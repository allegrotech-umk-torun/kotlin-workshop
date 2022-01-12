package pl.allegroumk.allediet.outgoing.spoonacular

import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.allegroumk.allediet.outgoing.spoonacular.model.SpoonacularIngredientInformation
import java.util.concurrent.TimeUnit

@Configuration
@ConfigurationProperties("spoonacular.cache")
class SpoonacularCacheConfiguration(
    var spoonacularIngredientsInformationCacheSize: Long?,
    var refreshAfterWrite: Long?,
    var expireAfterWrite: Long?,
    var spoonacularClient: SpoonacularClient
) {

    private var defaultCacheSize: Long = 500
    private var defaultRefreshDuration: Long = 600
    private var defaultExpirationDuration: Long = 3600

    @Bean
    fun ingredientsInformationCache(): LoadingCache<Long, SpoonacularIngredientInformation> {
        return Caffeine.newBuilder()
            .maximumSize(spoonacularIngredientsInformationCacheSize ?: defaultCacheSize)
            .refreshAfterWrite(refreshAfterWrite ?: defaultRefreshDuration, TimeUnit.SECONDS)
            .expireAfterWrite(expireAfterWrite ?: defaultExpirationDuration, TimeUnit.SECONDS)
            .build { spoonacularClient.getIngredientInformationById(it) }
    }
}
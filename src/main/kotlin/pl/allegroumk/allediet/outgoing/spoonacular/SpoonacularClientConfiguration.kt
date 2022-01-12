package pl.allegroumk.allediet.outgoing.spoonacular

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import java.time.Duration

@Configuration
@ConfigurationProperties("spoonacular")
class SpoonacularClientConfiguration(
    var apiKey: String?,
    var url: String?,
    var searchPath: String?,
    var searchLimit: Int?,
    var detailsPath: String?,
    var connectTimeout: Long?,
    var readTimeout: Long?,
    var retryReadTimeout: Long?,
) {

    @Bean
    fun spoonacularRestTemplate(): RestTemplate {
        return RestTemplateBuilder()
            .setConnectTimeout(Duration.ofMillis(connectTimeout ?: 500))
            .setReadTimeout(Duration.ofMillis(readTimeout ?: 1000))
            .build()
    }

    @Bean
    fun spoonacularRestTemplateForRetry(): RestTemplate {
        return RestTemplateBuilder()
            .setConnectTimeout(Duration.ofMillis(connectTimeout ?: 500))
            .setReadTimeout(Duration.ofMillis(retryReadTimeout ?: 2000))
            .build()
    }
}
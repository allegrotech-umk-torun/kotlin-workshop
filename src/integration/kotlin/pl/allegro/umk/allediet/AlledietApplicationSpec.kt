package pl.allegro.umk.allediet

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate
import pl.allegroumk.allediet.AlledietApplication
import pl.allegroumk.allediet.repository.FeedMealsRepository

@SpringBootTest(
    classes = [AlledietApplication::class],
    properties = ["application.environment=integration"],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class AlledietApplicationSpec {

    @Autowired
    lateinit var feedRepository: FeedMealsRepository

    @Autowired
    lateinit var mongoTemplate: MongoTemplate

    // TODO
}

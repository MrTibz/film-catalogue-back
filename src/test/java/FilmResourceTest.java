import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.containsString;


import static org.hamcrest.Matchers.containsString;

@QuarkusTest
public class FilmResourceTest {



    @Test
    public void testPagedCategory() {
        RestAssured.given()
                .when().get("/v1/category/1/15")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("category", equalTo("Sports"))
                .body("page", equalTo(1))
                .body("totalPages", equalTo(3))
                .body("films", hasSize(20))
                .body("films.title", hasItems(
                        "GLEAMING JAWBREAKER",
                        "GRACELAND DYNAMITE",
                        "GROOVE FICTION",
                        "GUNFIGHTER MUSSOLINI",
                        "HOLES BRANNIGAN"
                ))
                .body("films[0].description", containsString("Composer"));
    }
}


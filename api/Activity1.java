package activities;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Activity1 {
    // Set base URL
    final static String BASE_URI = "https://petstore.swagger.io/v2/pet";

    //post request
    @Test(priority=1)
    public void addNewPet() {
        // Create JSON request
        String reqBody = "{"
            + "\"id\": 77632,"
            + "\"name\": \"Tommy\","
            + " \"status\": \"alive\""
        + "}";

        Response response = 
            given().contentType(ContentType.JSON) // Set headers
            .body(reqBody) // Add request body
            .when().post(BASE_URI); // Send POST request

        // Assertion
        response.then().body("id", equalTo(77632));
        response.then().body("name", equalTo("Tommy"));
        response.then().body("status", equalTo("alive"));
    }

    @Test(priority=2)
    public void getPetInfo() {
        Response response = 
            given().contentType(ContentType.JSON) // Set headers
            .when().pathParam("petId", "77632") // Set path parameter
            .get(BASE_URI + "/{petId}"); // Send GET request

        // Assertion
        response.then().body("id", equalTo(77632));
        response.then().body("name", equalTo("Tommy"));
        response.then().body("status", equalTo("alive"));
    }
    
    @Test(priority=3)
    public void deletePet() {
        Response response = 
            given().contentType(ContentType.JSON) // Set headers
            .when().pathParam("petId", "77632") // Set path parameter
            .delete(BASE_URI + "/{petId}"); // Send DELETE request

        // Assertion
        response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("77232"));
        
        //verify that the delete request worked fine by using get request
         response = 
                given().contentType(ContentType.JSON) // Set headers
                .when().pathParam("petId", "77632") // Set path parameter
                .get(BASE_URI + "/{petId}"); // Send GET request

            // Assertion
         response.then().body("message", equalTo("Pet not found"));  
    }
}

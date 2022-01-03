package project;

import static io.restassured.RestAssured.given;

import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class TestClass {
	// Declare request specification
	RequestSpecification requestSpec;
	// Declare response specification
	ResponseSpecification responseSpec;
	// hold ssh key
	String gitHubAccessToken = "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";
	String sshKey;
	int sshId;
	String BASE_URI = "https://api.github.com";

	@BeforeClass
	public void setUp() {

		// Create request specification
		requestSpec = new RequestSpecBuilder()
				// Set content type
				.setContentType(ContentType.JSON)
				// set header as githubaccesstoken
				.addHeader("Authorization", "token " + gitHubAccessToken)
				// Set base URL
				.setBaseUri(BASE_URI)
				// Build request specification
				.build();

		responseSpec = new ResponseSpecBuilder()
				// Check response content type
				.expectContentType("application/json")
				// Check if response contains name property
				// Build response specification
				.build();
	}

	// post request
	@Test(priority = 1)
	public void addSSHKey() {
		String reqBody = "{\"title\": \"TestAPIKey\", \"key\": \"ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQC9pLmQBbQ4q/0CpFR2fcQMlfXrke9fuuomGgD2vIK2VcVpyFxg7FlsGN4WrMqPkxXTyFyOXgOHM9m4cbHYKfayqONIMxxMScPBDkbbtFuZeYVO/g6k5nuKeqKiKgWF+QBsGyu4FstZG2mSHj+I5z2k8FKaRo18E3c+TdfTthPOnhfugaJeRBq8ZEURiucUiZswAZSwSkAZOjsMi686Kh7J0LK05AmbIINoQqKlLtzqP0rH8aEbxoL9VWwYzrf6LUGHISY9QDoZKetRSESHZ3cNdUkAOQ28J2oCOH+u8mlMBVjCqp58kizdoTdBPqJwK/XSNChPRKFDs7Uf/YGmMy6IkxlXiZiRTtEPEBEaju61V04EhQHycG0I0veTbpqRrkAVjTe9uMzEg+Zmx/fbGqqRevl2YFju6AjS8SxrmnY0994vGT5KHEeummqIJcVICwAINjxP6BDgDl5SiqsLpetZP9mkb5OoTPRMUcmOsDWEHchmDm4hotqJHXeP/cAsbmk="
				+ "\"}";
		Response response = given().spec(requestSpec) // Use requestSpec
				.body(reqBody) // Send request body
				.when().post(BASE_URI + "/user/keys"); // Send POST request

		System.out.println(response.asPrettyString());
		// Additional Assertion Use responseSpec
		response.then().spec(responseSpec).statusCode(201);

		// save sshId
		sshId = response.then().extract().path("id");

	}

	// get request
	@Test(priority = 2)
	public void getSSHKeys() {
		Response response = given().spec(requestSpec) // Use requestSpec
				.when().get(BASE_URI + "/user/keys"); // Send GET request

		// Print response
		Reporter.log(response.asString());

		// Assertions
		response.then().spec(responseSpec) // Use responseSpec
				.statusCode(200); // Additional Assertion
	}

	
	  //delete request	  
	  @Test(priority=3)
	  public void deleteSSHKeys(){
		  Response response = given().spec(requestSpec) // Use requestSpec
	                .pathParam("keyId", sshId) // Add path parameter
	                .when().get(BASE_URI +"/user/keys/{keyId}"); // Send GET request
	  
			Reporter.log(response.asString());
			
	  // Assertions 
		  response.then().spec(responseSpec) // Use responseSpec
			.statusCode(200); 
		  
	  }
	  
	 
}

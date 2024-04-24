package stepdefs;

import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BookingSteps {
	
	
	@Given("User is able to get Booking using GET Request")
	public void i_create_an_account_of_type(String AccountType) throws Exception {
		//RestAssured.baseURI=("https://restful-booker.herokuapp.com");
		//RestAssured.basePath = ("/booking/1");
		
		RequestSpecBuilder builder = new RequestSpecBuilder();
		builder.setBaseUri("https://restful-booker.herokuapp.com");
		builder.setBasePath("/booking/1");
		
		RequestSpecification reqSpec = builder.build();
		
		Response res = RestAssured.given().spec(reqSpec)
		.when().get();
		
		res.then().log().all();
	
	}

}

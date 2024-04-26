package stepdefs;

import org.eclipse.jetty.http.HttpStatus;

import endpoints.BookingEndPoints;
import endpoints.Routes;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.Booking;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.rest.SerenityRest;
import utilities.Constants;

public class BookingSteps {
	
	@Steps
	BookingEndPoints bookingEndPoints;
	Booking booking;
	
	
	
	@Given("User is able to get Booking using GET Request")
	public void i_create_an_account_of_type() throws Exception {
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
	
	@Given("Booking API is active")
	public void booking_API_is_available() {
		RequestSpecification rSpec = bookingEndPoints.getCommonSpec(Routes.BOOKING_BASE_URL).basePath("ping");
		Response res = bookingEndPoints.sendRequest(rSpec, Constants.RequestType.GET_REQUEST, null);
		res.then().assertThat().statusCode(201);
		
	}
	
	@When("I POST a create booking")
	public void iCreateNewBooking() {
		booking = new Booking();
		System.out.println(booking.getFirstname());
		bookingEndPoints.addBooking( booking);
	}
	
	@Then("I see response has {int} status code")
	public void iSeeResponseStatusCode(int code) {
		//bookingEndPoints.verifyResponseStatusCode(world.getResponse(), code);
		System.out.println("Printing for Booking Steps"+this);
		System.out.println(booking.getFirstname());
	}

}

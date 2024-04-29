package stepdefs;

import endpoints.BookingEndPoints;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import model.Booking;
import net.serenitybdd.annotations.Steps;

public class BookingSteps {
	
	@Steps
	BookingEndPoints bookingEndPoints;
	Booking booking;
	Response res;
	
	/**
	 * 
	 * @param bookingID
	 * @throws Exception
	 * 
	 */
	
	@Then("User is able to get Booking with ID <{string}> using GET Request")
	public void getBooking_Using_GET(String bookingID) throws Exception {
		//RestAssured.baseURI=("https://restful-booker.herokuapp.com");
		//RestAssured.basePath = ("/booking/1");
		
		/* Straight forward Code before Framework Migration
		 * RequestSpecBuilder builder = new RequestSpecBuilder();
		 * builder.setBaseUri("https://restful-booker.herokuapp.com");
		 * builder.setBasePath("/booking/1");
		 * 
		 * RequestSpecification reqSpec = builder.build();
		 * 
		 * Response res = RestAssured.given().spec(reqSpec) .when().get();
		 * 
		 * res.then().log().all();
		 */
		res = bookingEndPoints.getBookingByID(bookingID);
		bookingEndPoints.printReponseOnConsole(res);
		bookingEndPoints.verifyResponseStatusCode(res,200);
		bookingEndPoints.validateGETByIDResponseBody("Eric", "Wilson", 736, true, "2020-05-23", "2022-10-24");
	}
	
	@Given("Booking API is active")
	public void booking_API_is_available() {
		res = bookingEndPoints.checkBookingAPIAvailable();
		bookingEndPoints.verifyResponseStatusCode(res,201);
	}
	
	@When("I POST a create booking")
	public void iCreateNewBooking() {
		booking = new Booking();
		res = bookingEndPoints.addBooking( booking);
	}
	
	@Then("I see response has {int} status code")
	public void iSeeResponseStatusCode(int code) {
		bookingEndPoints.verifyResponseStatusCode(res, code);
	}
	
	@And("I verify booking request response as per booking model")
	public void verifyBookingRequestAsPerBookingModel() {
		bookingEndPoints.verifyResponseMatchedByModel(res, booking);
		res.then().log().all();
	}

}

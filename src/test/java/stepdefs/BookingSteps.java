package stepdefs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import endpoints.BookingEndPoints;
import io.cucumber.datatable.DataTable;
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
	List<Response> responseList  = new ArrayList<Response>();
	int bookingID;
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
		bookingEndPoints.validateGETByIDResponseBody("Mary", "Smith", 144, true, "2023-12-18", "2024-04-30");
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
		
		bookingID = bookingEndPoints.getBookingIDFromResponse(res);
		System.out.println("Booking ID Created as "+bookingID);
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
	
	@When("I POST a create booking API for multiple customers")
	public void createPOSTforMultipleCustomers(DataTable dt) {
		List<Map<String,String>> dataList = dt.asMaps(String.class, String.class);
		//Reading Each Row from Data Table
		for(Map<String,String> eachRowAsDataMap : dataList) {
			Booking eachBooking = new Booking(
					eachRowAsDataMap.get("firstname"),
					eachRowAsDataMap.get("lastname"),
					Integer.parseInt(eachRowAsDataMap.get("totalprice")),
					Boolean.parseBoolean(eachRowAsDataMap.get("depositpaid")),
					null,
					eachRowAsDataMap.get("additionalneeds")
					);
		//Making Post Call for Each Data Row Since API Only Supports 1 Booking POST Request at a time
			Response res = bookingEndPoints.addBooking(eachBooking);
			res.then().log().all();
			//Saving Response to List for Each POST Request Made
			responseList.add(res);
		}//For Ends Here
	}
	
	@Then("the response for all bookings made has following response code respectively")
	public void validateResponseCodeforEachBooking(DataTable dt) {
		List<String> dataList = dt.asList(String.class);
		if(responseList.size()==dataList.size()-1) {
			//Since we need to loop through two Lists Simultaneously
			for(int i = 1; i< dataList.size(); i++) {
				bookingEndPoints.verifyResponseStatusCode(responseList.get(i-1), Integer.parseInt(dataList.get(i)));
			}
		}else {
			//Shared Less Number of Status Code to Check as Compared to Bookings Created
			Assert.assertTrue("Number of Status Codes Supplied In Step Did Not Match Number of Bookings Posted",responseList.size()==dataList.size()-1);
		}
	}
	
	@When("I DELETE a booking")
	public void deleteBooking() {
		res = bookingEndPoints.deleteBookingById(bookingID);
	}
	
	@When("getting the same booking with Id")
	public void gettingTheSameBookingWithId() {
		res = bookingEndPoints.getBookingByID(""+bookingID+"");
	}
	
	@When("I UPDATE a booking")
	public void updateBooking(DataTable dt) {
		List<List<String>> updateInfoList = dt.asLists(String.class);
		booking = new Booking();
		res = bookingEndPoints.addBooking(booking);
		bookingID = bookingEndPoints.getBookingIDFromResponse(res);
		//Creation Booking Payload for UPDATE REQUEST
		Booking updatedBookingPayload = new Booking(updateInfoList.get(0).get(0), 
				updateInfoList.get(0).get(1), Integer.parseInt(updateInfoList.get(0).get(2)),
				Boolean.parseBoolean(updateInfoList.get(0).get(3)), null, updateInfoList.get(0).get(4));
		//Make Update Booking Call
		bookingEndPoints.updateBooking(bookingID, updatedBookingPayload);
	}
	
	@When("User is able to get Booking with ID <{int}> using GET Request <{int}> times")
	public void makeGetRequestInLoop(int bookingID, int triggerCount) {
		for(int i =1; i <=triggerCount; i++) {
			System.out.println("Making Request Run#"+i);
			res =bookingEndPoints.getBookingByID(""+bookingID+"");
			System.out.println("Response CODE Found"+res.getStatusCode());
			bookingEndPoints.verifyResponseStatusCode(res,200);
		}
		
	}

}

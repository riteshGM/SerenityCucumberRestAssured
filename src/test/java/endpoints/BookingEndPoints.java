package endpoints;

import static org.hamcrest.Matchers.equalTo;

import org.json.JSONException;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.Booking;
import net.serenitybdd.rest.SerenityRest;
import utilities.Constants;

public class BookingEndPoints extends BaseEndPoints {
	public final String BOOKING_BASE_URI = Routes.BOOKING_BASE_URL;
	public final String BOOKING_BASE_PATH = "booking";
	public final String GET_BOOKING_BY_ID = BOOKING_BASE_PATH+"/{bookingID}";
	public final String DELETE_BOOKING_BY_ID = BOOKING_BASE_PATH+"/{bookingID}";
	public final String UPDATE_BOOKING_BY_ID = BOOKING_BASE_PATH+"/{bookingID}";

	public String getPath() {
		return BOOKING_BASE_PATH;
	}
	
	public String getBaseURI() {
		return BOOKING_BASE_URI;
	}
	
	public Response addBooking(Booking booking) throws JSONException {
		RequestSpecification rSpec = getCommonSpec(BOOKING_BASE_URI).basePath(BOOKING_BASE_PATH);
		Response res = sendRequest(rSpec, Constants.RequestType.POST_REQUEST, booking);
		res.then().log().all();
		return res;
	}
	
	public Response getBookingByID(String bookingID) {
		RequestSpecification rSpec = getCommonSpec(Routes.BOOKING_BASE_URL).basePath(GET_BOOKING_BY_ID);
		rSpec.pathParam("bookingID", bookingID);
		Response res = sendRequest(rSpec, Constants.RequestType.GET_REQUEST, null);
		return res;
	}
	
	public void validateGETByIDResponseBody(String firstName, String lastName, int totalPrice, boolean depositPaid, String checkDate, String checkoutDate) {
		SerenityRest.restAssuredThat(lastResponse -> lastResponse.body("'firstname'", equalTo(firstName)));
		SerenityRest.restAssuredThat(lastResponse -> lastResponse.body("'lastname'", equalTo(lastName)));
		SerenityRest.restAssuredThat(lastResponse -> lastResponse.body("'totalprice'", equalTo(totalPrice)));
		SerenityRest.restAssuredThat(lastResponse -> lastResponse.body("'depositpaid'", equalTo(depositPaid)));
		SerenityRest.restAssuredThat(lastResponse -> lastResponse.body("'bookingdates'.'checkin'", equalTo(checkDate)));
		SerenityRest.restAssuredThat(lastResponse -> lastResponse.body("'bookingdates'.'checkout'", equalTo(checkoutDate)));
	}
	
	public Response checkBookingAPIAvailable() {
		RequestSpecification rSpec = getCommonSpec(Routes.BOOKING_BASE_URL).basePath("ping");
		return sendRequest(rSpec, Constants.RequestType.GET_REQUEST, null);	
	}
	
	public Response deleteBookingById(int bookingID) {
		RequestSpecification rSpec = getCommonSpec(BOOKING_BASE_URI).cookie("token", getAuthorizationToken(BOOKING_BASE_URI)).basePath(GET_BOOKING_BY_ID);
		rSpec.pathParam("bookingID", bookingID);
		Response res = sendRequest(rSpec, Constants.RequestType.DELETE_REQUEST, null);
		res.then().log().all();
		return res;
	}
	
	public int getBookingIDFromResponse(Response res) {
		return res.jsonPath().getInt("bookingid");
	}
	
	public Response updateBooking(int bookingID, Booking booking) {
		//Prepare Your Request Part
		RequestSpecification rSpec = getCommonSpec(BOOKING_BASE_URI);
		rSpec.cookie("token", getAuthorizationToken(BOOKING_BASE_URI));
		rSpec.basePath(UPDATE_BOOKING_BY_ID);
		rSpec.pathParam("bookingID", bookingID);
		Response res = sendRequest(rSpec, Constants.RequestType.PUT_REQUEST, booking);
		res.then().log().all();
		return res;
	}
}

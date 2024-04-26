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

	public String getPath() {
		return BOOKING_BASE_PATH;
	}
	
	public String getBaseURI() {
		return BOOKING_BASE_URI;
	}
	
	public Response addBooking(Booking booking) throws JSONException {
		RequestSpecification rSpec = getCommonSpec(BOOKING_BASE_URI).basePath(BOOKING_BASE_PATH);	
		return sendRequest(rSpec, Constants.RequestType.POST_REQUEST, booking);
	}
	
	public Response getBookingByID(String bookingID) {
		RequestSpecification rSpec = getCommonSpec(Routes.BOOKING_BASE_URL).basePath(GET_BOOKING_BY_ID);
		rSpec.pathParam("bookingID", bookingID);
		return sendRequest(rSpec, Constants.RequestType.GET_REQUEST, null);
	}
	
	public void validateGETByIDResponseBody(String firstName, String lastName, int totalPrice, boolean depositPaid, String checkDate, String checkoutDate) {
		SerenityRest.restAssuredThat(lastResponse -> lastResponse.body("'firstname'", equalTo(firstName)));
		SerenityRest.restAssuredThat(lastResponse -> lastResponse.body("'lastname'", equalTo(lastName)));
		SerenityRest.restAssuredThat(lastResponse -> lastResponse.body("'totalprice'", equalTo(totalPrice)));
		SerenityRest.restAssuredThat(lastResponse -> lastResponse.body("'depositpaid'", equalTo(depositPaid)));
		SerenityRest.restAssuredThat(lastResponse -> lastResponse.body("'bookingdates'.'checkin'", equalTo(checkDate)));
		SerenityRest.restAssuredThat(lastResponse -> lastResponse.body("'bookingdates'.'checkout'", equalTo(checkoutDate)));
		
	}
	

}

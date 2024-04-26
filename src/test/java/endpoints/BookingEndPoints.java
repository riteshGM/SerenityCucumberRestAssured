package endpoints;

import org.json.JSONException;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.Booking;
import utilities.Constants;

public class BookingEndPoints extends BaseEndPoints {
	private final String BOOKING_BASE_URI = Routes.BOOKING_BASE_URL;
	private final String BOOKING_ENDPOINT_PATH = "booking";

	public String getPath() {
		return BOOKING_ENDPOINT_PATH;
	}
	
	public String getBaseURI() {
		return BOOKING_BASE_URI;
	}
	
	public Response addBooking(Booking booking) throws JSONException {
		RequestSpecification rSpec = getCommonSpec(BOOKING_BASE_URI).basePath(BOOKING_ENDPOINT_PATH);	
		return sendRequest(rSpec, Constants.RequestType.POST_REQUEST, booking);
	}
	

}

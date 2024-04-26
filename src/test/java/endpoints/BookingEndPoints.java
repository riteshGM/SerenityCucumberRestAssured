package endpoints;

import org.json.JSONException;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.Booking;
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
	

}

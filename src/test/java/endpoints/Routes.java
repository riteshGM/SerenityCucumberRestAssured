package endpoints;

public class Routes {
	
	
	/**
	 * Project BASE URL
	 */
	public static String USER_BASE_URL = "https://petstore.swagger.io/v2";
	
	/**
	 * User Module REQUEST URLs
	 */
	// Create New User
	public static String USER_POST_URL = USER_BASE_URL+"/user";
	
	//GET Existing User
	public static String USER_GET_URL = USER_BASE_URL+"/user/{username}";
	
	//Update Existing User
	public static String USER_PUT_URL = USER_BASE_URL+"/user/{username}";
	
	//Delete a User
	public static String USER_DELETE_URL = USER_BASE_URL+"/user/{username}";
	
	
	/**
	 * Booking Base URL
	 *
	 */
	
	public static String BOOKING_BASE_URL = "https://restful-booker.herokuapp.com"; 
	
	
	/**
	 * Further we can this way add as as may module URL's as needed in this class
	 */

}

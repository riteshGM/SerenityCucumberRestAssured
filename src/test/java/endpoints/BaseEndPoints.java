package endpoints;

import org.json.JSONObject;
import org.junit.Assert;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.rest.SerenityRest;
import utilities.Constants;
import utilities.PropertyLoader;

public class BaseEndPoints {
	@Steps
	PropertyLoader property; //This will Trigger Property Instance Creation - Makes Constructor call to PropertyLoader()	
	private static String authToken;

	/**
	 *  common specification for request
	 */
	public RequestSpecification getCommonSpec(String baseURI) {
		RequestSpecification rSpec = SerenityRest.given();
		rSpec.contentType(ContentType.JSON).baseUri(baseURI);
		return rSpec;
	}

	/**
	 * Convert POJO to JSON
	 */
	protected JSONObject createJSONPayload(Object pojo) {
		return new JSONObject(pojo);
	}

	/**
	 * Returns authentication token by log in with the username and password
	 * provided in applicationUnderTest.properties Authentication token is required
	 * for update and delete requests
	 */
	public String getAuthorizationToken(String baseURI) {
		if (authToken == null || authToken.length() < 1) {
			
			
			JSONObject jsonObj = new JSONObject().put("username", property.getBookingUserName())
					.put("password", property.getBookingPassword());
			
			RequestSpecification rspec = getCommonSpec(baseURI).basePath("auth")
					.body(jsonObj.toString());
		
			Response response = sendRequest(rspec, Constants.RequestType.POST_REQUEST, null);
			authToken = response.jsonPath().getString("token");
		}
		System.out.println("Auth Token Retrieved "+authToken);
		return authToken;
	}

	/**
	 * Verify that the response code is the same as expected code by comparing the
	 * provided expected code and the response code from the response received by
	 * sending the request
	 */
	public void verifyResponseStatusCode(Response response, int expectedCode) {
		Assert.assertEquals(expectedCode,response.getStatusCode());
	}

	/**
	 * Send request
	 * 
	 * @param request     details for sending the request
	 * @param requestType of the request. i.e GET, POST, PUT, DELETE, UPDATE
	 * @param url         to execute for the request
	 * @param pojo        if provided will be added to the body of request as JSON
	 *                    payload
	 * @return response received from the service by sending the request
	 */
	@Step
	public Response sendRequest(RequestSpecification request, int requestType, Object pojo) {
		Response response;

		// Add the Json to the body of the request
		if (pojo != null) {
			String payload = createJSONPayload(pojo).toString();
			request.body(payload);
		}

		// need to add a switch based on request type
		switch (requestType) {
		case Constants.RequestType.POST_REQUEST:
			if (request == null) {
				response = SerenityRest.when().post();
			} else {
				response = request.post();
			}
			break;
		case Constants.RequestType.DELETE_REQUEST:
			if (request == null) {
				response = SerenityRest.when().delete();
			} else {
				response = request.delete();
			}
			break;
		case Constants.RequestType.PUT_REQUEST:
			if (request == null) {
				response = SerenityRest.when().put();
			} else {
				response = request.put();
			}
			break;
		case Constants.RequestType.GET_REQUEST:
		default:
			if (request == null) {
				response = SerenityRest.when().get();
			} else {
				response = request.get();
			}
			break;
		}
		return response;
	}
	
	/**
	 * Print Response on Console
	 * @param res
	 */
	public void printReponseOnConsole(Response res) {
		res.then().log().all();
		
	}
	
	/**
	 * This method is used to compare Response vs Pojo/Model class Object
	 * @param <T> Used Generics Here to handle any Object Model
	 * @param res Response
	 * @param expectedModelObj
	 */
	public <T> void verifyResponseMatchedByModel(Response res, T expectedModelObj) {
		expectedModelObj.equals(res.body().asString());
	}
}

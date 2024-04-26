Feature: Booking API
  Booking API Functionalities
  
  @GET_Request
  Scenario Outline: Get Booking Request Validation
  	Given Booking API is active
    Then User is able to get Booking using GET Request
  
  @Dev  
  Scenario: Create a new booking
    Given Booking API is active
    When I POST a create booking
    Then I see response has 200 status code
    #And I verify booking request response as per booking model
    
   @Dev  
  Scenario: Create a new booking
    Given Booking API is active
    When I POST a create booking
    Then I see response has 200 status code
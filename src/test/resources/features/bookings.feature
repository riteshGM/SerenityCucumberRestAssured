Feature: Booking API
  Booking API Functionalities
  
  @GET_Request @Dev
  Scenario Outline: Get Booking Request Validation
  	Given Booking API is active
    Then User is able to get Booking with ID <"2"> using GET Request
  
   
  Scenario: Create a new booking
    Given Booking API is active
    When I POST a create booking
    Then I see response has 200 status code
    #And I verify booking request response as per booking model
    
  
  Scenario: Create a new booking
    Given Booking API is active
    When I POST a create booking
    Then I see response has 200 status code
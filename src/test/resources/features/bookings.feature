Feature: Booking API
  Booking API Functionalities
  
  @GET_Request 
  Scenario Outline: Get Booking Request Validation
  	Given Booking API is active
    Then User is able to get Booking with ID <"2"> using GET Request
  
   
  Scenario: Create a new booking
    Given Booking API is active
    When I POST a create booking
    Then I see response has 200 status code
    And I verify booking request response as per booking model
    
  Scenario: Create a new booking
    Given Booking API is active
    When I POST a create booking
    Then I see response has 200 status code
    And I verify booking request response as per booking model
    
    @Dev
   Scenario: Create a booking for multiple customers
    Given Booking API is active
    When I POST a create booking API for multiple users
      | firstname | lastname | totalprice | depositpaid | additionalneeds |
      | Test      | Customer |        100 | true        | Lunch           |
      | Test      | Customer |        250 | false       | Dinner          |
    Then the response has following response code
      | code |
      |  200 |
      |  200 |
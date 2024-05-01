Feature: Booking API
  Booking API Functionalities
  
  @GET_Request @ALL
  Scenario: Get Booking Request Validation
  	Given Booking API is active
    Then User is able to get Booking with ID <"2"> using GET Request
  
   @Create_mvBooking @ALL
  Scenario: Create a new booking
    Given Booking API is active
    When I POST a create booking
    Then I see response has 200 status code
    And I verify booking request response as per booking model
    
    @ALL
   Scenario: Create a booking for multiple customers
    Given Booking API is active
    When I POST a create booking API for multiple customers
      | firstname | lastname | totalprice | depositpaid | additionalneeds |
      | Ritesh      | Mansukhani |        100 | true        | Lunch           |
      | Pavan      | Kumar |        250 | false       | Dinner          |
    Then the response for all bookings made has following response code respectively
      | code |
      |  200 |
      | 200	 |
      
    @Dev @ALL
    Scenario: Delete a booking
    Given Booking API is active
    When I POST a create booking
    And I DELETE a booking
    Then I see response has 201 status code
    When getting the same booking with Id
    Then I see response has 404 status code
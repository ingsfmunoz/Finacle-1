Feature: Inquire account CDT for web services
  As a web user
  I want to login in Finacle
  to Consult a CDT for web services the Finacle Integration

  @InquireCDTForWebServices
  Scenario: consult account CDT in Finacle for web services
    Given that user is the page of finacle integrator
    When the User enters the next detais of the query
      | foracid | 5437784 |
    Then he should see the inquire CDT
    

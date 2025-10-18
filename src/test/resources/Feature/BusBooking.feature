Feature: validating bus bokking using abhibus websire

  @device_MAC @TRT
  Scenario: validate bus booking
  Given User navigate to the abhibus website
#  Then user logins into the application
  When User click on buses link
  When User enter details like leaving city and going city date 
  Then User click on search button
  Then User apply filters 
  Then User selects the bus and book
  Then User gets details of the bus fare
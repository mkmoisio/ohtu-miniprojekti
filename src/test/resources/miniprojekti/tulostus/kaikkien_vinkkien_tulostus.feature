Feature: As a user I am able to print out a list of all tips via CLI


  Scenario: Listing of all tips
    Given There is currenlty at least one tip in    
    When Command "tulosta lukemattomat" is entered
    Then A list of unread tips is printed
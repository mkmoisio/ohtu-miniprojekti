Feature: As a user I am able to print out a list of unread tips via CLI


  Scenario: Listing of unread tips
    Given There is currenlty at least one unread tip    
    When Command "tulosta kaikki" is entered
    Then A list of all tips is printed

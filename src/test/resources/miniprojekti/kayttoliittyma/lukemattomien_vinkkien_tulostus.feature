Feature: As a user I am able to print out a list of unread tips via CLI

  Scenario: Listing of unread tips
    Given database is reset
    Given command "lisää kirja" is entered and author "Matti Nykänen" and title "Mattihan se sopan keitti" are entered
    When print command "lukemattomat" is entered
    Then the application responds with a list containing an unread tip with author "Matti Nykänen" and title "Mattihan se sopan keitti"

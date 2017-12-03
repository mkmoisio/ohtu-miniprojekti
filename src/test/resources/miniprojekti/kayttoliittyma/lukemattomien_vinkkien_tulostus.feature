Feature: As a user I am able to print out a list of unread tips via CLI

# TOIMII
  Scenario: A single book tip is added and then unread tips are listed
    Given database is reset
    Given command "lisää kirja" is entered and author "Matti Nykänen" and title "Mattihan se sopan keitti" are entered
    When print command "lukemattomat" is entered
    Then the application responds with a list containing an unread book tip with author "Matti Nykänen" and title "Mattihan se sopan keitti"


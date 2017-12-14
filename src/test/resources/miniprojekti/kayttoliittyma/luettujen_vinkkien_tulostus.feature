Feature: As a user I am able to print out a list of read tips via CLI

# TOIMII
  Scenario: A single book tip is added, it is marked read and then read tips are listed
    Given database is reset
    Given command "lisää kirja" is entered and author "Matti Nykänen" and title "Mattihan se sopan keitti" are entered
    Given command "merkitse luetuksi" is entered and title "Mattihan se sopan keitti" is entered
    When print command "lukemattomat" is entered 
    Then the output contains "Otsikko: Mattihan se sopan keitti (lukematon)"

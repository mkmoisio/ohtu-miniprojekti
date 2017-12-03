Feature: As a user I am able to print out a list of video tips via CLI

 #
 Scenario: A single video tip is added and then video tips are listed
    Given database is reset
    Given command "lisää video" is entered and url "youtube.com" and title "Mattihan se sopan keitti" are entered
    When print command "tulosta videot" is entered
    Then the application responds with a list containing an unread tip with url "youtube.com" and title "Mattihan se sopan keitti"


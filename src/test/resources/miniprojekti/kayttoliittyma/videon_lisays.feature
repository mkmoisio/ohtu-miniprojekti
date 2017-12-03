Feature: As a user I am able to add a new video tip via CLI


Scenario: User is able to add a new video tip with valid url and valid title
Given Command "lisää video" is entered
When url "google.com" and title "Google" are entered
Then the application responds with "Video lisfätty"
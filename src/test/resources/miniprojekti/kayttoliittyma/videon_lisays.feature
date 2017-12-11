Feature: As a user I am able to add a new video tip via CLI

# KAIKKI TOIMII
Scenario: User is able to add a new video tip with valid url and valid title
Given Command "lisää video" is entered
When url "google.com" and title "Google" are entered
Then the application responds with "Video lisätty"

Scenario: User is unable to add a new video tip with valid url and empty title
Given Command "lisää video" is entered
When url "google.com" and title "" are entered
Then the application responds with "Videota ei lisätty"

Scenario: User is unable to add a new video tip with empty url and valid title
Given Command "lisää video" is entered
When url "" and title "Google" are entered
Then the application responds with "Videota ei lisätty, koska annettu url oli tyhjä."

Scenario: User is unable to add a new video tip with empty url and empty title
Given Command "lisää video" is entered
When url "" and title "" are entered
Then the application responds with "Videota ei lisätty, koska annettu url oli tyhjä."

Scenario: User is unable to add a new video tip with valid url and too long title
Given Command "lisää video" is entered
When url "Google" and title "123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901" are entered
Then the application responds with "Videota ei lisätty"

Scenario: User is unable to add a new video tip with too long url and valid title
Given Command "lisää video" is entered
When url "1234567890123456789012345678901234567890123456789012345678901234567890" and title "Google" are entered
Then the application responds with "Videota ei lisätty"

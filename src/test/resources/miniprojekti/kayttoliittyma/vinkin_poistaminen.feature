Feature: As a user I am able to delete a tip via the CLI

# 
Scenario: If user tries to delete non-existing tip the system responds with an error
Given Command "poista" is entered
When title "Non-existent tip" are entered
Then the application responds with "Vinkkiä ei poistettu"

Scenario: User is able to delete a book tip by its title
Given Command "poista" is entered
When title "Mattiahn se sopan keitti" is entered
Then the application responds with "Vinkki poistettu"

Scenario: User is able to delete a video tip by its title
Given Command "poista" is entered
When title "Google" is entered
Then the application responds with "Vinkki poistettu"

Scenario: User is able to delete a podcast tip by its title
Given Command "poista" is entered
When title "The Comedian’s Comedian" is entered
Then the application responds with "Vinkki poistettu"

Scenario: User is able to delete a blogpost tip by its title
Given Command "poista" is entered
When title "Cool blogpost" is entered
Then the application responds with "Vinkki poistettu"
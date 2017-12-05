Feature: As a user I am able to delete a tip via the CLI

# 
Scenario: If user tries to delete non-existing tip the system responds with an error
Given Command "poista" is entered
When title "Non-existent tip" are entered
Then the application responds with "Vinkkiä ei poistettu"

Scenario: User is able to delete a book tip by its title
Given System has an existing book "Mattihan se sopan keitti" with author "asd"
And Command "poista" is entered
When title "Mattihan se sopan keitti" is entered
Then the application responds with "Vinkki poistettu"

Scenario: User is able to delete a video tip by its title
Given System has an existing video "Google" with url "asd"
And Command "poista" is entered
When title "Google" is entered
Then the application responds with "Vinkki poistettu"

Scenario: User is able to delete a podcast tip by its title
Given System has an existing podcast "The Comedian’s Comedian" with name "asd" and description "asd"
And Command "poista" is entered
When title "The Comedian’s Comedian" is entered
Then the application responds with "Vinkki poistettu"

Scenario: User is able to delete a blogpost tip by its title
Given System has an existing blogpost "Cool blogpost" with url "asd" and author "asd"
And Command "poista" is entered
When title "Cool blogpost" is entered
Then the application responds with "Vinkki poistettu"
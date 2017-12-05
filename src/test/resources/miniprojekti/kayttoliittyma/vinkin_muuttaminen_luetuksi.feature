Feature: As a user I am able to delete a tip via the CLI

Scenario: User can change the status of the tip to read
Given database is reset
And System has an existing tip "ABC-kiria" with author "Agricola"
And Command "merkitse luetuksi" is entered, do not erase data
When title "ABC-kiria" is entered
Then the application responds with "Vinkki otsikolla ABC-kiria merkitty luetuksi"

Scenario: User cannot change the status of the nonexisting tip to read
Given database is reset
And Command "merkitse luetuksi" is entered
When title "ABC-kiria" is entered
Then the application responds with "Virhe: Vinkkiä ABC-kiria ei löytynyt"


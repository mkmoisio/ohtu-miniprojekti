Feature: As a user I am able to add a new book tip via CLI

# KAIKKI TOIMII
Scenario: User is able to add a new book tip with valid author and title
Given Command "lisää kirja" is entered
When author "Matti Nykänen" and title "Mattihan se sopan keitti" are entered
Then the application responds with "Kirjavinkki lisätty"

Scenario: User is unable to add a new book tip with valid author and empty title
Given Command "lisää kirja" is entered
When author "Matti Nykänen" and title "" are entered
Then the application responds with "Kirjavinkkiä ei lisätty, koska annettu otsikko oli tyhjä."

Scenario: User is unable to add a new book tip with empty author and valid title
Given Command "lisää kirja" is entered
When author "" and title "Mattihan se sopan keitti" are entered
Then the application responds with "Kirjavinkkiä ei lisätty, koska annettu kirjoittajan nimi oli tyhjä."

Scenario: User is unable to add a new book tip with too long author and a valid title
Given Command "lisää kirja" is entered
When author "1234567890123456789012345678901234567890123456789012345678901234567890" and title "Mattihan se sopan keitti" are entered
Then the application responds with "Kirjavinkkiä ei lisätty"

Scenario: User is unable to add a new book tip with valid author and a too long title
Given Command "lisää kirja" is entered
When author "Matti Nykänen" and title "123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901" are entered
Then the application responds with "Kirjavinkkiä ei lisätty, koska annettu otsikko oli pidempi kuin 100 merkkiä."

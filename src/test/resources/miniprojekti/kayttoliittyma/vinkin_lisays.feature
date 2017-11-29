Feature: As a user I am able to add a new book tip via CLI

Scenario: User is able to add a new tip with valid author and title
Given Command "lisää kirja" is entered
When author "Matti Nykänen" and title "Mattihan se sopan keitti" are entered
Then the application responds with "Kirjavinkki lisätty"

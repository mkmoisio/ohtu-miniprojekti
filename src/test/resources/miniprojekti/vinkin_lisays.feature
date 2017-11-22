Feature: As a user I am able to add a new tip via CLI

Scenario: User is able to add a new tip with valid author and title
Given Command "lisää" is entered
When author "Matti Nykänen" and title "Mattihan se sopan keitti: Nykäsellä höystetty keittokirja" are entered
Then the application responds with "Kirjavinkki lisätty"

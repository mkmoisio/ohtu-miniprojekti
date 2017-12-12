Feature: As a user I am able to add a new blogpost tip via CLI

# 
Scenario: User is able to exit from modify option
  Given database is reset
  And command "lisää kirja" is entered and author "Matti Nykänen" and title "Mattihan se sopan keitti" are entered
  And Command "muunna vinkkiä" is entered
  When command "" is entered
  Then the application responds with "Mitä muutetaan (tyhjä lopettaa): TEKIJA KUVAUS ISBN"

Scenario: User is able to modify author
  Given database is reset
  And command "lisää kirja" is entered and author "Matti Nykänen" and title "Mattihan se sopan keitti" are entered
  And Command "muunna vinkkiä" is entered
  When command "" is entered
  Then the application responds with "Mitä muutetaan (tyhjä lopettaa): TEKIJA KUVAUS ISBN"

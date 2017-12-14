Feature: As a user I am able to add a new blogpost tip via CLI

# 
Scenario: User is able to exit from modify option
  Given database is reset
  And System has an existing tip "ABC-kiria" with author "Agricola"
  And Command "muunna vinkkiä" is entered
  When command "" is entered
  Then the application responds with "Vinkkiä otsikolla:  ei löytynyt"


Scenario: User is able to modify author
  Given database is reset
  And System has an existing tip "ABC-kiria" with author "Agricola"
  And Command "muunna vinkkiä" is entered
  And Command "ABC-kiria" is entered
  And Command "TEKIJA" is entered
  And Command "Pete Petturi" is entered
  And Command "" is entered
  And Command "" is entered
  When the user checks the all the books
  Then the output contains "Kirjoittaja: Pete Petturi"


Scenario: User is able to modify description
  Given database is reset
  And command "lisää podcast" is enterered with args "Piraatti John", "Rommi tarinat" and "Etanolin huuruinen tarina"
  And Command "muunna vinkkiä" is entered
  And Command "Rommi tarinat" is entered
  And Command "KUVAUS" is entered
  And Command "Yahar!" is entered
  And Command "" is entered
  When the user checks the all the podcasts
  Then the output contains "Yahar!"


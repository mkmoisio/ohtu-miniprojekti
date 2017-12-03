Feature: As a user I am able to add a new podcast tip via CLI

Scenario: User is able to add a new podcast tip with valid title, name and description
Given Command "lisää podcast" is entered
When title "The Comedian’s Comedian" and name "The Comedian’s Comedian" and description "Comedian Stuart Goldsmith’s career" are entered
Then the application responds with "Podcast lisätty"
#
##Comedian Stuart Goldsmith’s career has been given a boost by this nerdy yet inclusive podcast. Exploring the intricacies of standup, from writing methods to internal struggles, what might come across as a specialist series turns out to be an education in creativity that even outsiders will enjoy.
#Scenario: User is able to add a new tip with valid title, name and description
#Given Command "lisää podcast" is entered
#When name "a" and title "b" and description "b" are entered
#Then the application responds with "Podcast lisätty"

Scenario: User is unable to add a new podcast tip with empty title, name and description
Given Command "lisää podcast" is entered
When title "" and name "The Comedian’s Comedian" and description "Comedian Stuart Goldsmith’s career" are entered
Then the application responds with "Podcastia ei lisätty"

Scenario: User is unable to add a new podcast tip with valid title, empty name and valid description
Given Command "lisää podcast" is entered
When title "The Comedian’s Comedian" and name "" and description "Comedian Stuart Goldsmith’s career" are entered
Then the application responds with "Podcastia ei lisätty"

Scenario: User is unable to add a new podcast tip with valid title, valid name and empty description
Given Command "lisää podcast" is entered
When title "The Comedian’s Comedian" and name "The Comedian’s Comedian" and description "" are entered
Then the application responds with "Podcastia ei lisätty"

Scenario: User is unable to add a new podcast tip with valid title, valid name and too long description
Given Command "lisää podcast" is entered
When title "The Comedian’s Comedian" and name "The Comedian’s Comedian" and description "Comedian Stuart Goldsmith’s career has been given a boost by this nerdy yet inclusive podcast. Exploring the intricacies of standup, from writing methods to internal struggles, what might come across as a specialist series turns out to be an education in creativity that even outsiders will enjoy." are entered
Then the application responds with "Podcastia ei lisätty"

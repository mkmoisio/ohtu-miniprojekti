Feature: As a user I am able to add a new podcast tip via CLI

Scenario: User is able to add a new tip with valid title, name and description
Given Command "lisää podcast" is entered
When title "The Comedian’s Comedian" and name "The Comedian’s Comedian"
and description "Comedian Stuart Goldsmith’s career has been given a boost by this nerdy yet inclusive podcast. Exploring the intricacies of standup, from writing methods to internal struggles, what might come across as a specialist series turns out to be an education in creativity that even outsiders will enjoy." are entered
Then the application responds with "Podcast lisätty"
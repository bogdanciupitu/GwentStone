# Classes description

This project contains some unused elements. Throughout the
development process, there were many challenges that led to changes in the code.
As a result, some parts of the codebase were left unused and were not removed.
Another challenge was learning the debugger in the IDE and stepping through each command.

### "Round"
The `Round` class manages the state and logic of a single round of the game.
It handles the players' decks, hands, heroes, and the game table. It also processes
the actions taken by the players during the round.

### "GameTable"
The `GameTable` class represents the game table where cards are placed during
the game.

### "Game"
The Game class manages the overall state and flow of the game. It includes
methods to start the game, manage rounds.

### "Player"
The `Player` class represents a player in the game. It manages the player's
deck, hand, hero, and the player's actions during the game.

### "Card"
The Card class represents a card in the game. It includes attributes like
mana, attack damage, health, description, colors, and name. It also has methods
to get and set these attributes, as well as methods to check if the card is frozen
or has attacked, and to execute special abilities.

The TheRipper, TheCursedOne, Miraj, and Disciple classes extend the Card
class and override the specialAbility method.

### "Minion"
The Minion class represents a minion card in the game. It extends the Card
class and includes additional attributes and methods specific to minions.

### "Tank"
The Tank class extends the Minion class and represents a special type of minion.

### "Hero"
The Hero class extends the Card class and represents a hero in the game.
It includes attributes like mana cost, description, colors, name, and health.
It also has methods to get and set these attributes, as well as methods to check
if the hero has used their ability and to execute special abilities.

The Hero class is extended by four other classes(General Kocioraw,
KingMudface, Lord Royce, Empress Thorina), each of which overrides the
specialAbility method to provide unique abilities for the respective heroes.
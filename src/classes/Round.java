package classes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Round {
    private GameTable table;
    private ArrayList<ArrayList<Card>> decks;
    private ArrayList<ArrayList<Card>> hands;
    private Hero[] heroes;
    private ArrayList<ActionsInput> actionsInputs;
    private int[] manaPlayers;
    private int seed;
    private int roundNumber = 1;
    private int startPlayer, currentPlayer;
    private int playerOneNumberOfWins = 0;
    private int playerTwoNumberOfWins = 0;
    private int numberGames = 0;

    private static final int TABLE_ROWS = 4;
    private static final int TABLE_COLUMNS = 5;
    private static final int TEN_MANA = 10;

    public Round(final ArrayList<Card> deck1, final ArrayList<Card> deck2, final Hero hero1,
                 final Hero hero2, final int seed, final int startPlayer,
                 final ArrayList<ActionsInput> actionsInputs) {
        this.decks = new ArrayList<ArrayList<Card>>();
        this.decks.add((ArrayList<Card>) deck1);
        this.decks.add((ArrayList<Card>) deck2);
        this.hands = new ArrayList<ArrayList<Card>>();
        this.hands.add(new ArrayList<Card>());
        this.hands.add(new ArrayList<Card>());
        this.heroes = new Hero[2];
        this.heroes[0] = hero1;
        this.heroes[1] = hero2;
        this.table = new GameTable(TABLE_ROWS, TABLE_COLUMNS);
        this.actionsInputs = actionsInputs;
        this.manaPlayers = new int[2];
        this.manaPlayers[0] = 1;
        this.manaPlayers[1] = 1;
        this.seed = seed;
        this.startPlayer = startPlayer;
        this.currentPlayer = startPlayer;
        Collections.shuffle(this.decks.get(0), new Random(this.seed));
        Collections.shuffle(this.decks.get(1), new Random(this.seed));
    }

    public int startRound(final int numberOfGames, final int numberOfWinsPlayer1,
                          final int numberOfWinsPlayer2, final ArrayNode output) {
        ObjectMapper mapper = new ObjectMapper();
        currentPlayer = this.startPlayer;
        this.roundNumber = 1;
        this.table = new GameTable(TABLE_ROWS, TABLE_COLUMNS);
        this.hands.get(0).add((Card) this.decks.get(0).get(0));
        this.hands.get(1).add((Card) this.decks.get(1).get(0));
        this.decks.get(0).remove(0);
        this.decks.get(1).remove(0);
        int winner = 0;
        int result = 0;

        for (ActionsInput actionsInput : actionsInputs) {
            switch (actionsInput.getCommand()) {
                case "getPlayerDeck":
                    ObjectNode command = mapper.createObjectNode();
                    command.put("command", actionsInput.getCommand());
                    command.put("playerIdx", actionsInput.getPlayerIdx());

                    ArrayNode cards = mapper.createArrayNode();
                    ArrayList<Card> cardsInDeck = this.decks.get(actionsInput.getPlayerIdx() - 1);
                    for (int i = 0;  i < cardsInDeck.size(); i++) {
                        ObjectNode cardNode = mapper.createObjectNode();
                        cardNode.put("mana", cardsInDeck.get(i).getMana());
                        cardNode.put("attackDamage", cardsInDeck.get(i).getAttackDamage());
                        cardNode.put("health", cardsInDeck.get(i).getHealth());
                        cardNode.put("description", cardsInDeck.get(i).getDescription());

                        ArrayNode colors = mapper.createArrayNode();
                        for (String color : cardsInDeck.get(i).getColors()) {
                            colors.add(color);
                        }
                        cardNode.set("colors", colors);
                        cardNode.put("name", cardsInDeck.get(i).getName());
                        cards.add(cardNode);
                    }

                    command.set("output", cards);
                    output.add(command);
                    break;
                case "getPlayerHero":
                    ObjectNode getPlayerHeroCommand = mapper.createObjectNode();
                    getPlayerHeroCommand.put("command", actionsInput.getCommand());
                    getPlayerHeroCommand.put("playerIdx", actionsInput.getPlayerIdx());
                    ObjectNode heroNode = mapper.createObjectNode();

                    heroNode.put("mana", this.heroes[actionsInput.getPlayerIdx() - 1].getMana());
                    heroNode.put("description", this.heroes[actionsInput.getPlayerIdx() - 1].
                            getDescription());
                    ArrayNode colors = mapper.createArrayNode();
                    for (String color : this.heroes[actionsInput.getPlayerIdx() - 1].getColors()) {
                        colors.add(color);
                    }
                    heroNode.set("colors", colors);
                    heroNode.put("name", this.heroes[actionsInput.getPlayerIdx() - 1]
                            .getName());
                    heroNode.put("health", this.heroes[actionsInput.getPlayerIdx() - 1]
                            .getHealth());

                    getPlayerHeroCommand.set("output", heroNode);
                    output.add(getPlayerHeroCommand);
                    break;
                case "getPlayerTurn":
                    ObjectNode getPlayerTurnCommand = mapper.createObjectNode();
                    getPlayerTurnCommand.put("command", actionsInput.getCommand());
                    getPlayerTurnCommand.put("output", currentPlayer);
                    output.add(getPlayerTurnCommand);
                    break;
                case "endPlayerTurn":
                    if (currentPlayer == 1) {
                        for (int i = 2; i < TABLE_ROWS; i++) {
                            for (int j = 0; j < TABLE_COLUMNS; j++) {
                                if (table.getTable()[i][j] != null) {
                                    table.getTable()[i][j].setFrozen(false);
                                    table.getTable()[i][j].setAttacked(false);
                                }
                            }
                        }
                    } else {
                        for (int i = 0; i < 2; i++) {
                            for (int j = 0; j < TABLE_COLUMNS; j++) {
                                if (table.getTable()[i][j] != null) {
                                    table.getTable()[i][j].setFrozen(false);
                                    table.getTable()[i][j].setAttacked(false);
                                }
                            }
                        }
                    }

                    heroes[currentPlayer - 1].setAttacked(false);

                    currentPlayer = (currentPlayer % 2) + 1;
                    if (currentPlayer == startPlayer) {
                        roundNumber++;
                        manaPlayers[0] += Math.min(TEN_MANA, roundNumber);
                        manaPlayers[1] += Math.min(TEN_MANA, roundNumber);
                    }

                    if (currentPlayer == startPlayer) {
                        if (!decks.get(0).isEmpty()) {
                            hands.get(0).add(decks.get(0).remove(0));
                        }
                        if (!decks.get(1).isEmpty()) {
                            hands.get(1).add(decks.get(1).remove(0));
                        }
                    }

                    break;
                case "placeCard":
                    ObjectNode placeCardCommand = mapper.createObjectNode();
                    placeCardCommand.put("command", actionsInput.getCommand());

                    int currentHandIdx = actionsInput.getHandIdx();
                    int currentPlayerIdx = currentPlayer - 1;

                    if (currentHandIdx >= 0 && currentHandIdx
                            < hands.get(currentPlayerIdx).size()) {
                        Card cardToPlace = hands.get(currentPlayerIdx).get(currentHandIdx);

                        if (cardToPlace.getMana() > manaPlayers[currentPlayerIdx]) {
                            placeCardCommand.put("error",
                                    "Not enough mana to place card on table.");
                            placeCardCommand.put("handIdx", actionsInput.getHandIdx());
                            output.add(placeCardCommand);
                        } else if (table.isRowFull(currentPlayerIdx)) {
                            placeCardCommand.put("error",
                                    "Cannot place card on table since row is full.");
                            placeCardCommand.put("handIdx", actionsInput.getHandIdx());
                            output.add(placeCardCommand);
                        } else {
                                if (currentPlayerIdx == 0 && (cardToPlace.isTank()
                                        || cardToPlace.getName().equals("Miraj")
                                        || cardToPlace.getName().equals("The Ripper"))) {
                                    table.placeCard(cardToPlace, 2);
                                } else if (currentPlayerIdx == 0 && !cardToPlace.isTank()) {
                                    table.placeCard(cardToPlace, 3);
                                } else if (currentPlayerIdx == 1 && (cardToPlace.isTank()
                                        || cardToPlace.getName().equals("Miraj")
                                        || cardToPlace.getName().equals("The Ripper"))) {
                                    table.placeCard(cardToPlace, 1);
                                } else if (currentPlayerIdx == 1 && !cardToPlace.isTank()) {
                                    table.placeCard(cardToPlace, 0);
                                }
                                manaPlayers[currentPlayerIdx] -= cardToPlace.getMana();
                                hands.get(currentPlayerIdx).remove(currentHandIdx);
                        }
                    }
                    break;
                case "getCardsInHand":
                    ObjectNode getCardsInHandCommand = mapper.createObjectNode();
                    getCardsInHandCommand.put("command", actionsInput.getCommand());
                    getCardsInHandCommand.put("playerIdx", actionsInput.getPlayerIdx());

                    int playerIdx = actionsInput.getPlayerIdx() - 1;
                    ArrayNode cardsInHand = mapper.createArrayNode();

                    for (Card card : hands.get(playerIdx)) {
                        ObjectNode cardNode = mapper.createObjectNode();
                        cardNode.put("mana", card.getMana());
                        cardNode.put("attackDamage", card.getAttackDamage());
                        cardNode.put("health", card.getHealth());
                        cardNode.put("description", card.getDescription());
                        ArrayNode cardColors = mapper.createArrayNode();
                        for (String color : card.getColors()) {
                            cardColors.add(color);
                        }
                        cardNode.set("colors", cardColors);
                        cardNode.put("name", card.getName());
                        cardsInHand.add(cardNode);
                    }

                    getCardsInHandCommand.set("output", cardsInHand);
                    output.add(getCardsInHandCommand);
                    break;
                case "getPlayerMana":
                    ObjectNode getPlayerManaCommand = mapper.createObjectNode();
                    getPlayerManaCommand.put("command", actionsInput.getCommand());
                    getPlayerManaCommand.put("playerIdx", actionsInput.getPlayerIdx());

                    int playerIdxMana = actionsInput.getPlayerIdx() - 1;
                    getPlayerManaCommand.put("output", manaPlayers[playerIdxMana]);

                    output.add(getPlayerManaCommand);
                    break;
                case "getCardsOnTable":
                    ObjectNode getCardsOnTableCommand = mapper.createObjectNode();
                    getCardsOnTableCommand.put("command", actionsInput.getCommand());

                    ArrayNode tableCards = mapper.createArrayNode();
                    for (int i = 0; i < TABLE_ROWS; i++) {
                        ArrayNode rowCards = mapper.createArrayNode();
                        for (int j = 0; j < TABLE_COLUMNS; j++) {
                            if (table.getTable()[i][j] != null) {
                                Card card = table.getTable()[i][j];
                                ObjectNode cardNode = mapper.createObjectNode();
                                cardNode.put("mana", card.getMana());
                                cardNode.put("attackDamage", card.getAttackDamage());
                                cardNode.put("health", card.getHealth());
                                cardNode.put("description", card.getDescription());
                                ArrayNode cardColors = mapper.createArrayNode();
                                for (String color : card.getColors()) {
                                    cardColors.add(color);
                                }
                                cardNode.set("colors", cardColors);
                                cardNode.put("name", card.getName());
                                rowCards.add(cardNode);
                            }
                        }
                        tableCards.add(rowCards);
                    }

                    getCardsOnTableCommand.set("output", tableCards);
                    output.add(getCardsOnTableCommand);
                    break;
                case "cardUsesAttack":
                    ObjectNode cardUsesAttackCommand = mapper.createObjectNode();
                    cardUsesAttackCommand.put("command", actionsInput.getCommand());

                    ObjectNode cardAttackerNode = mapper.createObjectNode();
                    cardAttackerNode.put("x", actionsInput.getCardAttacker().getX());
                    cardAttackerNode.put("y", actionsInput.getCardAttacker().getY());
                    cardUsesAttackCommand.set("cardAttacker", cardAttackerNode);

                    ObjectNode cardAttackedNode = mapper.createObjectNode();
                    cardAttackedNode.put("x", actionsInput.getCardAttacked().getX());
                    cardAttackedNode.put("y", actionsInput.getCardAttacked().getY());
                    cardUsesAttackCommand.set("cardAttacked", cardAttackedNode);

                    int attackerX = actionsInput.getCardAttacker().getX();
                    int attackerY = actionsInput.getCardAttacker().getY();
                    int attackedX = actionsInput.getCardAttacked().getX();
                    int attackedY = actionsInput.getCardAttacked().getY();

                    Card attacker = table.getCard(attackerX, attackerY);
                    Card attacked = table.getCard(attackedX, attackedY);

                    if (attacker != null && attacked != null) {
                        if (attackerX / 2 == attackedX / 2 || (attackerX == 0 && attackedX == 1)
                                || (attackerX == 3 && attackedX == 2)) {
                            cardUsesAttackCommand.put("error",
                                    "Attacked card does not belong to the enemy.");
                            output.add(cardUsesAttackCommand);
                        } else if (attacker.isFrozen()) {
                            cardUsesAttackCommand.put("error",
                                    "Attacker card is frozen.");
                            output.add(cardUsesAttackCommand);
                        } else if (attacker.isAttacked()) {
                            cardUsesAttackCommand.put("error",
                                    "Attacker card has already attacked this turn.");
                            output.add(cardUsesAttackCommand);
                        } else {
                            boolean existsTank = false;
                            for (int i = 0; i < table.getTable().length; i++) {
                                for (int j = 0; j < table.getTable()[i].length; j++) {
                                    Card card = table.getCard(i, j);
                                    if (card != null && card.isTank()
                                            && i / 2 != attackerX / 2) {
                                        existsTank = true;
                                        break;
                                    }
                                }
                                if (existsTank) {
                                    break;
                                }
                            }

                            if (existsTank && !attacked.isTank()) {
                                cardUsesAttackCommand.put("error",
                                        "Attacked card is not of type 'Tank'.");
                                output.add(cardUsesAttackCommand);
                            } else {
                                attacked.setHealth(attacked.getHealth()
                                        - attacker.getAttackDamage());
                                attacker.setAttacked(true);

                                if (attacked.getHealth() <= 0) {
                                    table.getTable()[attackedX][attackedY] = null;
                                }
                            }
                        }
                    }

                    break;
                case "cardUsesAbility":
                    ObjectNode cardUsesAbilityCommand = mapper.createObjectNode();
                    cardUsesAbilityCommand.put("command", actionsInput.getCommand());

                    ObjectNode cardAttackerAbilityNode = mapper.createObjectNode();
                    cardAttackerAbilityNode.put("x", actionsInput.getCardAttacker().getX());
                    cardAttackerAbilityNode.put("y", actionsInput.getCardAttacker().getY());
                    cardUsesAbilityCommand.set("cardAttacker", cardAttackerAbilityNode);

                    ObjectNode cardAttackedAbilityNode = mapper.createObjectNode();
                    cardAttackedAbilityNode.put("x", actionsInput.getCardAttacked().getX());
                    cardAttackedAbilityNode.put("y", actionsInput.getCardAttacked().getY());
                    cardUsesAbilityCommand.set("cardAttacked", cardAttackedAbilityNode);

                    int attackerAbilityX = actionsInput.getCardAttacker().getX();
                    int attackerAbilityY = actionsInput.getCardAttacker().getY();
                    int attackedAbilityX = actionsInput.getCardAttacked().getX();
                    int attackedAbilityY = actionsInput.getCardAttacked().getY();

                    Card attackerAbility = table.getCard(attackerAbilityX, attackerAbilityY);
                    Card attackedAbility = table.getCard(attackedAbilityX, attackedAbilityY);

                    if (attackerAbility != null &&  attackedAbility != null) {
                        if (attackerAbility.isFrozen()) {
                            cardUsesAbilityCommand.put("error",
                                    "Attacker card is frozen.");
                            output.add(cardUsesAbilityCommand);
                        } else if (attackerAbility.isAttacked()) {
                            cardUsesAbilityCommand.put("error",
                                    "Attacker card has already attacked this turn.");
                            output.add(cardUsesAbilityCommand);
                        } else if (attackerAbility.getName().equals("Disciple")) {
                            if (attackerAbilityX / 2 != attackedAbilityX / 2) {
                                cardUsesAbilityCommand.put("error",
                                        "Attacked card does not belong to the current player.");
                                output.add(cardUsesAbilityCommand);
                            } else {
                                attackerAbility.specialAbility(attackedAbility);
                                attackerAbility.setAttacked(true);
                            }
                        } else if (attackerAbility.getName().equals("The Ripper")
                                    || attackerAbility.getName().equals("Miraj")
                                    || attackerAbility.getName().equals("The Cursed One")) {
                                if (attackerAbilityX / 2 == attackedAbilityX / 2) {
                                    cardUsesAbilityCommand.put("error",
                                            "Attacked card does not belong to the enemy.");
                                    output.add(cardUsesAbilityCommand);
                                } else {
                                    boolean existsTank = false;
                                    for (int i = 0; i < table.getTable().length; i++) {
                                        for (int j = 0; j < table.getTable()[i].length; j++) {
                                            Card card = table.getCard(i, j);
                                            if (card != null && card.isTank()
                                                    && i / 2 != attackerAbilityX / 2) {
                                                existsTank = true;
                                                break;
                                            }
                                        }
                                        if (existsTank) {
                                            break;
                                        }
                                    }
                                    if (existsTank && !attackedAbility.isTank()) {
                                        cardUsesAbilityCommand.put("error",
                                                "Attacked card is not of type 'Tank'.");
                                        output.add(cardUsesAbilityCommand);
                                    } else {
                                        attackerAbility.specialAbility(attackedAbility);
                                        attackerAbility.setAttacked(true);
                                        if (attackedAbility.getHealth() <= 0) {
                                            table.getTable()[attackedAbilityX][attackedAbilityY] = null;
                                        }
                                    }
                                }
                            }
                    }

                    break;
                case "getCardAtPosition":
                    ObjectNode getCardAtPositionCommand = mapper.createObjectNode();
                    getCardAtPositionCommand.put("command", actionsInput.getCommand());

                    int xPosition = actionsInput.getX();
                    int yPosition = actionsInput.getY();
                    getCardAtPositionCommand.put("x", xPosition);
                    getCardAtPositionCommand.put("y", yPosition);

                    Card cardPosition = table.getCard(xPosition, yPosition);
                    if (cardPosition != null) {
                        ObjectNode cardNode = mapper.createObjectNode();
                        cardNode.put("mana", cardPosition.getMana());
                        cardNode.put("attackDamage", cardPosition.getAttackDamage());
                        cardNode.put("health", cardPosition.getHealth());
                        cardNode.put("description", cardPosition.getDescription());
                        ArrayNode cardColors = mapper.createArrayNode();
                        for (String color : cardPosition.getColors()) {
                            cardColors.add(color);
                        }
                        cardNode.set("colors", cardColors);
                        cardNode.put("name", cardPosition.getName());
                        getCardAtPositionCommand.set("output", cardNode);
                    } else {
                        getCardAtPositionCommand.put("output",
                                "No card available at that position.");
                    }

                    output.add(getCardAtPositionCommand);
                    break;
                case "useAttackHero":
                    ObjectNode useAttackHeroCommand = mapper.createObjectNode();
                    useAttackHeroCommand.put("command", actionsInput.getCommand());
                    ObjectNode heroAttackerNode = mapper.createObjectNode();
                    heroAttackerNode.put("x", actionsInput.getCardAttacker().getX());
                    heroAttackerNode.put("y", actionsInput.getCardAttacker().getY());
                    useAttackHeroCommand.set("cardAttacker", heroAttackerNode);

                    int heroAttackerX = actionsInput.getCardAttacker().getX();
                    int heroAttackerY = actionsInput.getCardAttacker().getY();

                    Card heroAttacker = table.getCard(heroAttackerX, heroAttackerY);

                    if (heroAttacker != null) {
                        if (heroAttacker.isFrozen()) {
                            useAttackHeroCommand.put("error",
                                    "Attacker card is frozen.");
                            output.add(useAttackHeroCommand);
                        } else if (heroAttacker.isAttacked()) {
                            useAttackHeroCommand.put("error",
                                    "Attacker card has already attacked this turn.");
                            output.add(useAttackHeroCommand);
                        } else {
                            int attackedHeroIdx = (currentPlayer + 1) % 2;
                            boolean existsTank = false;
                            for (int i = 0; i < table.getTable().length; i++) {
                                for (int j = 0; j < table.getTable()[i].length; j++) {
                                    Card currentCard = table.getCard(i, j);
                                    if (currentCard != null && currentCard.isTank()
                                            && i / 2 == attackedHeroIdx) {
                                        existsTank = true;
                                        break;
                                    }
                                }
                                if (existsTank) {
                                    break;
                                }
                            }

                            if (existsTank) {
                                useAttackHeroCommand.put("error",
                                        "Attacked card is not of type 'Tank'.");
                                output.add(useAttackHeroCommand);
                            } else {
                                attackedHeroIdx = (attackedHeroIdx == 0) ? 1 : 0;
                                Hero attackedHero = heroes[attackedHeroIdx];
                                int newHealth = attackedHero.getHealth()
                                        - heroAttacker.getAttackDamage();
                                attackedHero.setHealth(Math.max(newHealth, 0));
                                heroAttacker.setAttacked(true);

                                if (attackedHero.getHealth() <= 0) {
                                    String gameEndedMessage;

                                    if (currentPlayer == 1) {
                                        gameEndedMessage = "Player one killed the enemy hero.";
                                        playerOneNumberOfWins++;
                                        numberGames++;
                                    } else {
                                        gameEndedMessage = "Player two killed the enemy hero.";
                                        playerTwoNumberOfWins++;
                                        numberGames++;
                                    }

                                    ObjectNode gameEndedCommand = mapper.createObjectNode();
                                    gameEndedCommand.put("gameEnded", gameEndedMessage);
                                    output.add(gameEndedCommand);
//                                    result = 1;
                                }
                            }
                        }
                    }

                    break;
                case "useHeroAbility":
                    ObjectNode useHeroAbilityCommand = mapper.createObjectNode();
                    useHeroAbilityCommand.put("command", actionsInput.getCommand());
                    useHeroAbilityCommand.put("affectedRow", actionsInput.getAffectedRow());

                    int currentHeroPlayerIdx = currentPlayer - 1;
                    Hero currentHero = heroes[currentHeroPlayerIdx];

                    if (currentHero.getMana() > manaPlayers[currentHeroPlayerIdx]) {
                        useHeroAbilityCommand.put("error",
                                "Not enough mana to use hero's ability.");
                        output.add(useHeroAbilityCommand);
                        break;
                    }

                    if (currentHero.isAttacked()) {
                        useHeroAbilityCommand.put("error",
                                "Hero has already attacked this turn.");
                        output.add(useHeroAbilityCommand);
                        break;
                    }

                    int affectedRow = actionsInput.getAffectedRow();
                    boolean isEnemyRow = false;

                    if (currentHeroPlayerIdx == 1) {
                        if (affectedRow == 2 || affectedRow == 3) {
                            isEnemyRow = true;
                        }
                    } else if (currentHeroPlayerIdx == 0) {
                        if (affectedRow == 0 || affectedRow == 1) {
                            isEnemyRow = true;
                        }
                    }

                    boolean isAllyRow = !isEnemyRow;

                    if (currentHero.getName().equals("Lord Royce")
                            || currentHero.getName().equals("Empress Thorina")) {
                        if (isAllyRow) {
                            useHeroAbilityCommand.put("error",
                                    "Selected row does not belong to the enemy.");
                            output.add(useHeroAbilityCommand);
                            break;
                        }
                        for (Card card : table.getTable()[affectedRow]) {
                            if (card != null) {
                                card.setFrozen(true);
                            }
                        }
                    } else if (currentHero.getName().equals("King Mudface")
                            || currentHero.getName().equals("General Kocioraw")) {
                        if (isEnemyRow) {
                            useHeroAbilityCommand.put("error",
                                    "Selected row does not belong to the current player.");
                            output.add(useHeroAbilityCommand);
                            break;
                        }
                    }

                    currentHero.specialAbility(table.getTable()[affectedRow]);
                    currentHero.setAttacked(true);
                    manaPlayers[currentHeroPlayerIdx] -= currentHero.getMana();

                    break;
                case "getFrozenCardsOnTable":
                    ObjectNode getFrozenCardsOnTableCommand = mapper.createObjectNode();
                    getFrozenCardsOnTableCommand.put("command", actionsInput.getCommand());

                    ArrayNode frozenCards = mapper.createArrayNode();
                    for (Card[] row : table.getTable()) {
                        for (Card card : row) {
                            if (card != null && card.isFrozen()) {
                                ObjectNode cardNode = mapper.createObjectNode();
                                cardNode.put("mana", card.getMana());
                                cardNode.put("attackDamage", card.getAttackDamage());
                                cardNode.put("health", card.getHealth());
                                cardNode.put("description", card.getDescription());
                                ArrayNode cardColors = mapper.createArrayNode();
                                for (String color : card.getColors()) {
                                    cardColors.add(color);
                                }
                                cardNode.set("colors", cardColors);
                                cardNode.put("name", card.getName());
                                frozenCards.add(cardNode);
                            }
                        }
                    }

                    getFrozenCardsOnTableCommand.set("output", frozenCards);
                    output.add(getFrozenCardsOnTableCommand);
                    break;
                case "getTotalGamesPlayed":
                    ObjectNode getTotalGamesPlayedCommand = mapper.createObjectNode();
                    getTotalGamesPlayedCommand.put("command", actionsInput.getCommand());
//                    getTotalGamesPlayedCommand.put("output", numberOfGames);
                    getTotalGamesPlayedCommand.put("output", numberGames);
                    output.add(getTotalGamesPlayedCommand);
                    break;
                case "getPlayerOneWins":
                    ObjectNode getPlayerOneWinsCommand = mapper.createObjectNode();
                    getPlayerOneWinsCommand.put("command", actionsInput.getCommand());
//                    getPlayerOneWinsCommand.put("output", numberOfWinsPlayer1);
                    getPlayerOneWinsCommand.put("output", playerOneNumberOfWins);
                    output.add(getPlayerOneWinsCommand);
                    break;
                case "getPlayerTwoWins":
                    ObjectNode getPlayerTwoWinsCommand = mapper.createObjectNode();
                    getPlayerTwoWinsCommand.put("command", actionsInput.getCommand());
//                    getPlayerTwoWinsCommand.put("output", numberOfWinsPlayer2);
                    getPlayerTwoWinsCommand.put("output", playerTwoNumberOfWins);
                    output.add(getPlayerTwoWinsCommand);
                    break;
                default:
                    break;
            }
        }
        return 0;
    }

    public GameTable getTable() {
        return table;
    }

    public void setTable(final GameTable table) {
        this.table = table;
    }

    public ArrayList<ArrayList<Card>> getDecks() {
        return decks;
    }

    public void setDecks(final ArrayList<ArrayList<Card>> decks) {
        this.decks = decks;
    }

    public ArrayList<ArrayList<Card>> getHands() {
        return hands;
    }

    public void setHands(final ArrayList<ArrayList<Card>> hands) {
        this.hands = hands;
    }

    public Hero[] getHeroes() {
        return heroes;
    }

    public void setHeroes(final Hero[] heroes) {
        this.heroes = heroes;
    }

    public ArrayList<ActionsInput> getActionsInputs() {
        return actionsInputs;
    }

    public void setActionsInputs(final ArrayList<ActionsInput> actionsInputs) {
        this.actionsInputs = actionsInputs;
    }

    public int[] getManaPlayers() {
        return manaPlayers;
    }

    public void setManaPlayers(final int[] manaPlayers) {
        this.manaPlayers = manaPlayers;
    }

    public int getSeed() {
        return seed;
    }

    public void setSeed(final int seed) {
        this.seed = seed;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(final int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public int getStartPlayer() {
        return startPlayer;
    }

    public void setStartPlayer(final int startPlayer) {
        this.startPlayer = startPlayer;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(final int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
}

package classes;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.CardInput;
import fileio.DecksInput;
import fileio.GameInput;

import java.util.ArrayList;

public class Game {
    private Player playerOne, playerTwo;
    private int playerOneNumberOfWins, playerTwoNumberOfWins;
    private Round[] rounds;
    private int startPlayer;
    private ArrayList<Card> deck1, deck2;
    private DecksInput decksInput1, decksInput2;
    private static final int HERO_HEALTH = 30;

    /**
     * Gets the decks input for player one.
     * Subclasses should override this method to provide additional behavior.
     *
     * @return the decks input for player one
     */
    public DecksInput getDecksInput1() {
        return decksInput1;
    }

    /**
     * Gets the decks input for player two.
     * Subclasses should override this method to provide additional behavior.
     *
     * @return the decks input for player two
     */
    public DecksInput getDecksInput2() {
        return decksInput2;
    }

    public Game(final DecksInput decksInput1, final DecksInput decksInput2) {
        this.decksInput1 = decksInput1;
        this.decksInput2 = decksInput2;
        this.playerTwoNumberOfWins = 0;
        this.playerOneNumberOfWins = 0;
    }

    /**
     * Gets the decks input for player two.
     * Subclasses should override this method to provide additional behavior.
     *
     * @return the decks input for player two
     */
    public void startGame(final ArrayList<GameInput> game, final ArrayNode output) {
        int nrRounds = 0;
        for (GameInput gameInput : game) {
            Hero hero1 = new Hero(gameInput.getStartGame().getPlayerOneHero().getMana(),
                    gameInput.getStartGame().getPlayerOneHero().getHealth(), 0,
                    gameInput.getStartGame().getPlayerOneHero().getDescription(),
                    gameInput.getStartGame().getPlayerOneHero().getColors(),
                    gameInput.getStartGame().getPlayerOneHero().getName());
            Hero hero2 = new Hero(gameInput.getStartGame().getPlayerTwoHero().getMana(),
                    gameInput.getStartGame().getPlayerTwoHero().getHealth(), 0,
                    gameInput.getStartGame().getPlayerTwoHero().getDescription(),
                    gameInput.getStartGame().getPlayerTwoHero().getColors(),
                    gameInput.getStartGame().getPlayerTwoHero().getName());
            CardInput tmp = gameInput.getStartGame().getPlayerOneHero();
            switch (tmp.getName()) {
                case "Lord Royce":
                    hero1 = new LordRoyce(tmp.getMana(), HERO_HEALTH, 0,
                            tmp.getDescription(), tmp.getColors(), tmp.getName());
                    break;
                case "Empress Thorina":
                    hero1 = new EmpressThorina(tmp.getMana(), HERO_HEALTH, 0,
                            tmp.getDescription(), tmp.getColors(), tmp.getName());
                    break;
                case "King Mudface":
                    hero1 = new KingMudFace(tmp.getMana(), HERO_HEALTH, 0,
                            tmp.getDescription(), tmp.getColors(), tmp.getName());
                    break;
                case "General Kocioraw":
                    hero1 = new GeneralKocioraw(tmp.getMana(), HERO_HEALTH, 0,
                            tmp.getDescription(), tmp.getColors(), tmp.getName());
                    break;
                default:
                    break;
            }

            tmp = gameInput.getStartGame().getPlayerTwoHero();
            switch (tmp.getName()) {
                case "Lord Royce":
                    hero2 = new LordRoyce(tmp.getMana(), HERO_HEALTH, 0,
                            tmp.getDescription(), tmp.getColors(), tmp.getName());
                    break;
                case "Empress Thorina":
                    hero2 = new EmpressThorina(tmp.getMana(), HERO_HEALTH, 0,
                            tmp.getDescription(), tmp.getColors(), tmp.getName());
                    break;
                case "King Mudface":
                    hero2 = new KingMudFace(tmp.getMana(), HERO_HEALTH, 0,
                            tmp.getDescription(), tmp.getColors(), tmp.getName());
                    break;
                case "General Kocioraw":
                    hero2 = new GeneralKocioraw(tmp.getMana(), HERO_HEALTH, 0,
                            tmp.getDescription(), tmp.getColors(), tmp.getName());
                    break;
                default:
                    break;
            }

            this.playerOne = new Player(1, this.getDecksInput1().getNrDecks(),
                    this.getDecksInput1().getNrCardsInDeck(),  this.getDecksInput1().getDecks());
            this.playerTwo = new Player(2, this.getDecksInput2().getNrDecks(),
                    this.getDecksInput2().getNrCardsInDeck(), this.getDecksInput2().getDecks());
            this.rounds = new Round[game.size()];
            this.rounds[nrRounds] = new Round(playerOne.getDecks().get(gameInput.getStartGame()
                    .getPlayerOneDeckIdx()), playerTwo.getDecks().get(gameInput.getStartGame()
                    .getPlayerTwoDeckIdx()), hero1, hero2, gameInput.getStartGame()
                    .getShuffleSeed(), gameInput.getStartGame().getStartingPlayer(),
                    gameInput.getActions());
            int winner = this.rounds[nrRounds].startRound(playerOneNumberOfWins
                    + playerTwoNumberOfWins, playerOneNumberOfWins, playerTwoNumberOfWins, output);
            if (winner == 1) {
                playerOneNumberOfWins++;
            } else {
                playerTwoNumberOfWins++;
            }
            nrRounds++;
        }
    }
}

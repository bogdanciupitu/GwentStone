package classes;

import fileio.CardInput;

import java.util.ArrayList;

public class Player {
    private int idPlayer;
    private int numberOfCards;
    private ArrayList<ArrayList<Card>> decks;
    private int numberOfDecks;

    public Player(final int idPlayer, final int numberOfDecks,
                  final  int numberOfCards, final ArrayList<ArrayList<CardInput>> decks) {
        this.idPlayer = idPlayer;
        this.decks = new ArrayList<ArrayList<Card>>();
        this.numberOfDecks = numberOfDecks;
        this.numberOfCards = numberOfCards;
        for (int i = 0; i < numberOfDecks; i++) {
            this.decks.add(new ArrayList<Card>());
        }

        Card tmp;
        for (int i = 0; i < numberOfDecks; i++) {
            for (int j = 0; j < numberOfCards; j++) {
                CardInput cardInput = decks.get(i).get(j);
                switch (cardInput.getName()) {
                    case "Sentinel", "Berserker":
                        tmp = new Minion(cardInput.getMana(), cardInput.getHealth(),
                                cardInput.getAttackDamage(), cardInput.getDescription(),
                                cardInput.getColors(), cardInput.getName());
                        this.decks.get(i).add(tmp);
                        break;
                    case "Goliath", "Warden":
                        tmp = new Tank(cardInput.getMana(), cardInput.getHealth(),
                                cardInput.getAttackDamage(), cardInput.getDescription(),
                                cardInput.getColors(), cardInput.getName());
                        this.decks.get(i).add(tmp);
                        break;
                    case "The Ripper":
                        tmp = new TheRipper(cardInput.getMana(), cardInput.getHealth(),
                                cardInput.getAttackDamage(), cardInput.getDescription(),
                                cardInput.getColors(), cardInput.getName());
                        this.decks.get(i).add(tmp);
                        break;
                    case "Miraj":
                        tmp = new Miraj(cardInput.getMana(), cardInput.getHealth(),
                                cardInput.getAttackDamage(), cardInput.getDescription(),
                                cardInput.getColors(), cardInput.getName());
                        this.decks.get(i).add(tmp);
                        break;
                    case "The Cursed One":
                        tmp = new TheCursedOne(cardInput.getMana(), cardInput.getHealth(),
                                cardInput.getAttackDamage(), cardInput.getDescription(),
                                cardInput.getColors(), cardInput.getName());
                        this.decks.get(i).add(tmp);
                        break;
                    case "Disciple":
                        tmp = new Disciple(cardInput.getMana(), cardInput.getHealth(),
                                cardInput.getAttackDamage(), cardInput.getDescription(),
                                cardInput.getColors(), cardInput.getName());
                        this.decks.get(i).add(tmp);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * Returns the ID of the player.
     * Subclasses should override this method to provide additional behavior.
     *
     * @return the ID of the player
     */
    public int getIdPlayer() {
        return idPlayer;
    }

    /**
     * Sets the ID of the player.
     * Subclasses should override this method to provide additional behavior.
     *
     * @param idPlayer the new ID of the player
     */
    public void setIdPlayer(final int idPlayer) {
        this.idPlayer = idPlayer;
    }

    /**
     * Returns the decks of the player.
     * Subclasses should override this method to provide additional behavior.
     *
     * @return the decks of the player
     */
    public ArrayList<ArrayList<Card>> getDecks() {
        return decks;
    }

    /**
     * Sets the decks of the player.
     * Subclasses should override this method to provide additional behavior.
     *
     * @param decks the new decks of the player
     */
    public void setDecks(final ArrayList<ArrayList<Card>> decks) {
        this.decks = decks;
    }

    /**
     * Returns the number of cards.
     * Subclasses should override this method to provide additional behavior.
     *
     * @return the number of cards
     */
    public int getNumberOfCards() {
        return numberOfCards;
    }

    /**
     * Sets the number of cards.
     * Subclasses should override this method to provide additional behavior.
     *
     * @param numberOfCards the new number of cards
     */
    public void setNumberOfCards(final int numberOfCards) {
        this.numberOfCards = numberOfCards;
    }

    /**
     * Returns the number of decks.
     * Subclasses should override this method to provide additional behavior.
     *
     * @return the number of decks
     */
    public int getNumberOfDecks() {
        return numberOfDecks;
    }

    /**
     * Sets the number of decks.
     * Subclasses should override this method to provide additional behavior.
     *
     * @param numberOfDecks the new number of decks
     */
    public void setNumberOfDecks(final int numberOfDecks) {
        this.numberOfDecks = numberOfDecks;
    }
}

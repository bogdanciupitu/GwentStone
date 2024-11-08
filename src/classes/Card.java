package classes;

import java.util.ArrayList;

public class Card {
    private int mana;
    private int health;
    private int attackDamage;
    private String description;
    private ArrayList<String> colors;
    private String name;
    private boolean isFrozen;
    private boolean isTank;
    private boolean isAttacked;
    private int player;
    private int row;
    private int useOnWho;

    public Card(final int mana, final int health, final int attackDamage, final String description,
                final ArrayList<String> colors, final String name) {
        this.mana = mana;
        this.health = health;
        this.attackDamage = attackDamage;
        this.description = description;
        this.colors = colors;
        this.name = name;
        this.isFrozen = false;
        this.isTank = false;
        this.isAttacked = false;
        this.player = 0;
        this.row = 0;
        this.useOnWho = 0;
    }

    public Card(final int mana, final int health, final String description,
                final ArrayList<String> colors, final String name, final int player) {
        this.mana = mana;
        this.health = health;
        this.attackDamage = 0;
        this.description = description;
        this.colors = colors;
        this.name = name;
        this.isFrozen = false;
        this.isTank = false;
        this.isAttacked = false;
        this.player = player;
        this.row = 0;
        this.useOnWho = 0;
    }

    /**
     * Gets the mana of the card.
     * Subclasses should override this method to provide additional behavior.
     *
     * @return the mana of the card
     */
    public int getMana() {
        return mana;
    }

    /**
     * Sets the mana of the card.
     * Subclasses should override this method to provide additional behavior.
     *
     * @param mana the mana to set
     */
    public void setMana(final int mana) {
        this.mana = mana;
    }

    /**
     * Gets the health of the card.
     * Subclasses should override this method to provide additional behavior.
     *
     * @return the health of the card
     */
    public int getHealth() {
        return health;
    }

    /**
     * Sets the health of the card.
     * Subclasses should override this method to provide additional behavior.
     *
     * @param health the health to set
     */
    public void setHealth(final int health) {
        this.health = health;
    }

    /**
     * Gets the attack damage of the card.
     * Subclasses should override this method to provide additional behavior.
     *
     * @return the attack damage of the card
     */
    public int getAttackDamage() {
        return attackDamage;
    }

    /**
     * Sets the attack damage of the card.
     * Subclasses should override this method to provide additional behavior.
     *
     * @param attackDamage the attack damage to set
     */
    public void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }

    /**
     * Gets the description of the card.
     * Subclasses should override this method to provide additional behavior.
     *
     * @return the description of the card
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the card.
     * Subclasses should override this method to provide additional behavior.
     *
     * @param description the description to set
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Gets the colors of the card.
     * Subclasses should override this method to provide additional behavior.
     *
     * @return the colors of the card
     */
    public ArrayList<String> getColors() {
        return colors;
    }

    /**
     * Sets the colors of the card.
     * Subclasses should override this method to provide additional behavior.
     *
     * @param colors the colors to set
     */
    public void setColors(final ArrayList<String> colors) {
        this.colors = colors;
    }

    /**
     * Gets the name of the card.
     * Subclasses should override this method to provide additional behavior.
     *
     * @return the name of the card
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the card.
     * Subclasses should override this method to provide additional behavior.
     *
     * @param name the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Checks if the card is frozen.
     * Subclasses should override this method to provide additional behavior.
     *
     * @return true if the card is frozen, false otherwise
     */
    public boolean isFrozen() {
        return isFrozen;
    }

    /**
     * Sets the frozen status of the card.
     * Subclasses should override this method to provide additional behavior.
     *
     * @param frozen the frozen status to set
     */
    public void setFrozen(final boolean frozen) {
        this.isFrozen = frozen;
    }

    /**
     * Checks if the card is a tank.
     * Subclasses should override this method to provide additional behavior.
     *
     * @return true if the card is a tank, false otherwise
     */
    public boolean isTank() {
        return isTank;
    }

    /**
     * Sets the tank status of the card.
     * Subclasses should override this method to provide additional behavior.
     *
     * @param tank the tank status to set
     */
    public void setTank(final boolean tank) {
        this.isTank = tank;
    }

    /**
     * Checks if the card has been attacked.
     * Subclasses should override this method to provide additional behavior.
     *
     * @return true if the card has been attacked, false otherwise
     */
    public boolean isAttacked() {
        return isAttacked;
    }

    /**
     * Sets the attacked status of the card.
     * Subclasses should override this method to provide additional behavior.
     *
     * @param attacked the attacked status to set
     */
    public void setAttacked(final boolean attacked) {
        this.isAttacked = attacked;
    }

    /**
     * Gets the player associated with the card.
     * Subclasses should override this method to provide additional behavior.
     *
     * @return the player associated with the card
     */
    public int getPlayer() {
        return player;
    }

    /**
     * Sets the player associated with the card.
     * Subclasses should override this method to provide additional behavior.
     *
     * @param player the player to set
     */
    public void setPlayer(final int player) {
        this.player = player;
    }

    /**
     * Gets the row of the card.
     * Subclasses should override this method to provide additional behavior.
     *
     * @return the row of the card
     */
    public int getRow() {
        return row;
    }

    /**
     * Sets the row of the card.
     * Subclasses should override this method to provide additional behavior.
     *
     * @param row the row to set
     */
    public void setRow(final int row) {
        this.row = row;
    }

    /**
     * Gets the useOnWho attribute of the card.
     * Subclasses should override this method to provide additional behavior.
     *
     * @return the useOnWho attribute of the card
     */
    public int getUseOnWho() {
        return useOnWho;
    }

    /**
     * Sets the useOnWho attribute of the card.
     * Subclasses should override this method to provide additional behavior.
     *
     * @param useOnWho the useOnWho attribute to set
     */
    public void setUseOnWho(final int useOnWho) {
        this.useOnWho = useOnWho;
    }

    /**
     * Applies the special ability of the card to the specified card.
     * Subclasses should override this method to provide additional behavior.
     *
     * @param cards the card to apply the special ability to
     */
    public void specialAbility(final Card cards) {
        return;
    }

    /**
     * Applies the special ability of the card to the specified cards.
     * Subclasses should override this method to provide additional behavior.
     *
     * @param cards the cards to apply the special ability to
     */
    public void specialAbility(final Card[] cards) {
        return;
    }
}

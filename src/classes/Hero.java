package classes;

import java.util.ArrayList;

public class Hero extends Card {
    public Hero(final int mana, final int health, final int attackDamage,
                final String description, final ArrayList<String> colors, final String name) {
        super(mana, attackDamage, health, description, colors, name);
    }

    /**
     * Applies the special ability of the hero to the specified card.
     * Subclasses should override this method to provide additional behavior.
     *
     * @param card the card to apply the special ability to
     */
    @Override
    public void specialAbility(final Card card) {
        this.setAttacked(true);
    }
}

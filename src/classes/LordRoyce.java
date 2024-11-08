package classes;

import java.util.ArrayList;

public final class LordRoyce extends Hero {
    public LordRoyce(final int mana, final int health, final int attackDamage,
                     final String description, final ArrayList<String> colors,
                     final String name) {
        super(mana, attackDamage, health, description, colors, name);
        this.setUseOnWho(1);
    }

    @Override
    public void specialAbility(final Card[] cards) {
        this.setAttacked(true);
        for (Card card : cards) {
            if (card != null) {
                card.setFrozen(true);
            }
        }
    }
}

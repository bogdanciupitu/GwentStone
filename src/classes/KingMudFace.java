package classes;

import java.util.ArrayList;

public final class KingMudFace extends Hero {
    public KingMudFace(final int mana, final int health, final int attackDamage,
                       final String description, final ArrayList<String> colors,
                       final String name) {
        super(mana, attackDamage, health, description, colors, name);
        this.setUseOnWho(0);
    }

    @Override
    public void specialAbility(final Card[] cards) {
        this.setAttacked(true);
        for (Card card : cards) {
            if (card != null) {
                card.setHealth(card.getHealth() + 1);
            }
        }
    }
}

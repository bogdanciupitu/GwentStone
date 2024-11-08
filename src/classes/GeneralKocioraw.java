package classes;

import java.util.ArrayList;

public final class GeneralKocioraw extends Hero {
    public GeneralKocioraw(final int mana, final int health, final int attackDamage,
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
                card.setAttackDamage(card.getAttackDamage() + 1);
            }
        }
    }
}

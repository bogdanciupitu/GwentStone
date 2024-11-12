package classes;

import java.util.ArrayList;

public final class EmpressThorina extends Hero {
    public EmpressThorina(final int mana, final int health, final int attackDamage,
                          final String description, final ArrayList<String> colors,
                          final String name) {
        super(mana, attackDamage, health, description, colors, name);
        this.setUseOnWho(1);
    }

    @Override
    public void specialAbility(final Card[] cards) {
        this.setAttacked(true);
        Card highestCard = null;
        for (Card card : cards) {
            if (card != null) {
                if (highestCard == null) {
                    highestCard = card;
                } else if (card.getAttackDamage() > highestCard.getAttackDamage()) {
                    highestCard = card;
                }
            }
        }
        if (highestCard != null) {
            highestCard.setHealth(0);
        }
    }
}

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
        Card highestHealthCard = null;
        int index = -1;

        for (int i = 0; i < cards.length; i++) {
            Card card = cards[i];
            if (card != null) {
                if (highestHealthCard == null) {
                    highestHealthCard = card;
                    index = i;
                } else if (card.getHealth() > highestHealthCard.getHealth()) {
                    highestHealthCard = card;
                    index = i;
                }
            }
        }

        if (highestHealthCard != null) {
            highestHealthCard.setHealth(0);
            cards[index] = null;
        }
    }
}

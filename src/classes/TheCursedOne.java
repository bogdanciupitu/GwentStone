package classes;

import java.util.ArrayList;

class TheCursedOne extends Card {
    TheCursedOne(final int mana, final int health, final int attackDamage,
                        final String description, final ArrayList<String> colors,
                        final String name) {
        super(mana, health, 0, description, colors, name);
        this.setRow(0);
    }


    @Override
    public void specialAbility(final Card card) {
        this.setAttacked(true);
        if (card != null && !card.isFrozen()) {
            int tmp = card.getAttackDamage();
            card.setAttackDamage(card.getHealth());
            card.setHealth(tmp);

            if (card.getAttackDamage() == 0) {
                card.setHealth(0);
            }
        }
    }
}

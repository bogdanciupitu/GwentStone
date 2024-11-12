package classes;

import java.util.ArrayList;

class Miraj extends Card {
    Miraj(final int mana, final int health, final int attackDamage,
                 final String description, final ArrayList<String> colors, final String name) {
        super(mana, health, attackDamage, description, colors, name);
        this.setRow(1);
    }

    @Override
    public void specialAbility(final Card card) {
        this.setAttacked(true);
        if (card != null && !card.isFrozen() && card.getHealth() > 0) {
            int tmp = this.getHealth();
            this.setHealth(card.getHealth());
            card.setHealth(tmp);
        }
    }
}

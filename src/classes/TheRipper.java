package classes;

import java.util.ArrayList;

class TheRipper extends Card {
    TheRipper(final int mana, final int health, final int attackDamage, final String description,
              final ArrayList<String> colors, final String name) {
        super(mana, health, attackDamage, description, colors, name);
        this.setRow(1);
    }

    @Override
    public void specialAbility(final Card card) {
        this.setAttacked(true);
        if (card != null && !card.isFrozen()) {
            card.setAttackDamage(Math.max(card.getAttackDamage() - 2, 0));
        }
    }
}

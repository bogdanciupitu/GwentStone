package classes;

import java.util.ArrayList;

public class Minion  extends Card {
    public Minion(final int mana, final int health, final int attackDamage,
                  final String description, final ArrayList<String> colors, final String name) {
        super(mana, health, attackDamage, description, colors, name);
        this.setRow(0);
    }
}

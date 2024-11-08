package classes;

import java.util.ArrayList;

public class Tank extends Minion {
    public Tank(final int mana, final int health, final int attackDamage,
                final String description, final ArrayList<String> colors, final String name) {
        super(mana, health, attackDamage, description, colors, name);
        this.setTank(true);
        this.setRow(1);
    }
}

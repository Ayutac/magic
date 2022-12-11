package org.abos.fabricmc.magic.cca;

import org.abos.fabricmc.magic.Magic;

public class ManaComponent implements NatMaxComponent {

    public static final int MAX_MANA = 100;

    private int max = MAX_MANA;
    private int value = max;

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public void setValue(int value) throws IllegalArgumentException {
        if (value < 0 || value > max) {
            throw new IllegalArgumentException("Mana value is out of bounds!");
        }
        this.value = value;
    }

    @Override
    public int getMax() {
        return max;
    }

    @Override
    public void setMax(int max) {
        if (max < 0) {
            throw new IllegalArgumentException("Mana max value must be non-negative!");
        }
        this.max = max;
        if (value > max)  {
            value = max;
        }
    }

    @Override
    public String getValueKey() {
        return Magic.MANA_ID.toString();
    }

    @Override
    public String getMaxKey() {
        return Magic.MAX_MANA_ID.toString();
    }
}

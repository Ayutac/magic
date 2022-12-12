package org.abos.fabricmc.magic.cca;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import org.abos.fabricmc.magic.Magic;

import java.util.Objects;

public class ManaComponent implements NatMaxComponent, AutoSyncedComponent {

    public static final int MAX_MANA = 100;

    private int max = MAX_MANA;
    private int value = max;

    private final Object provider;

    public ManaComponent(final Object provider) {
        Objects.requireNonNull(provider, "Provider for "+ManaComponent.class.getSimpleName()+" needs to be non null!");
        this.provider = provider;
    }

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
        Magic.MANA.sync(provider);
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
        Magic.MANA.sync(provider);
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

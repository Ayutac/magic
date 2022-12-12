package org.abos.fabricmc.magic.cca;

import dev.onyxstudios.cca.api.v3.component.Component;
import net.minecraft.nbt.NbtCompound;

public interface NatComponent extends Component {

    int getValue();

    /**
     * Sets the value of this component to the specified one
     * and throws exception if out of bounds.
     */
    void setValue(int value) throws IllegalArgumentException;

    default void add(int amount) {
        setValue(getValue()+amount);
    }

    default boolean canAdd(int amount) {
        return getValue() + amount >= 0;
    }

    default void subtract(int amount) {
        add(-amount);
    }

    default boolean canSubtract(int amount) {
        return canAdd(-amount);
    }

    default void clear() {
        setValue(0);
    }

    String getValueKey();

    default void readFromNbt(NbtCompound tag) {
        setValue(tag.getInt(getValueKey()));
    }

    default void writeToNbt(NbtCompound tag) {
        tag.putInt(getValueKey(), getValue());
    }

}

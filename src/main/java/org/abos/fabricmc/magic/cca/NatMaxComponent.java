package org.abos.fabricmc.magic.cca;

import net.minecraft.nbt.NbtCompound;

public interface NatMaxComponent extends NatComponent {

    int getMax();

    /**
     * Sets the max value to the specified one. If smaller than 0,
     * throws exception. Else the value will be reduced to max if necessary.
     */
    void setMax(int max) throws IllegalArgumentException;

    String getMaxKey();

    @Override
    default boolean canAdd(int amount) {
        if (!NatComponent.super.canAdd(amount)) {
            return false;
        }
        return getValue()+amount <= getMax();
    }

    default void fill() {
        setValue(getMax());
    }

    @Override
    default void readFromNbt(NbtCompound tag) {
        NatComponent.super.readFromNbt(tag);
        setMax(tag.getInt(getMaxKey()));
    }

    @Override
    default void writeToNbt(NbtCompound tag) {
        NatComponent.super.writeToNbt(tag);
        tag.putInt(getMaxKey(), getMax());
    }
}

package org.abos.fabricmc.magic.blockentities;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.abos.fabricmc.magic.MagicContent;
import org.jetbrains.annotations.Nullable;

public class AltarBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, Inventory {
    public AltarBlockEntity(BlockPos pos, BlockState state) {
        super(MagicContent.ALTAR_BLOCK_ENTITY_TYPE, pos, state);
    }

    // NamedScreenHandlerFactory

    @Override
    public Text getDisplayName() {
        return null;
    }

    // Inventory

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getStack(int slot) {
        return null;
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return null;
    }

    @Override
    public ItemStack removeStack(int slot) {
        return null;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {

    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return false;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return null;
    }

    @Override
    public void clear() {

    }
}

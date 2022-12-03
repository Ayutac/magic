package org.abos.fabricmc.magic.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.abos.fabricmc.magic.MagicContent;
import org.abos.fabricmc.magic.entities.EarthMissileEntity;

public class WandItem extends Item {

    public WandItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        if (!world.isClient) {
            EarthMissileEntity ballEntity = new EarthMissileEntity(MagicContent.SMALL_MAGIC_MISSILE_ENTITY_TYPE, user, world);
            ballEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0f, 1f, 1f);
            world.spawnEntity(ballEntity);
            return TypedActionResult.consume(user.getStackInHand(hand));
        }
        return TypedActionResult.pass(user.getStackInHand(hand));
    }
}

package org.abos.fabricmc.magic.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.abos.fabricmc.magic.MagicContent;
import org.abos.fabricmc.magic.entities.BigEarthMissileEntity;
import org.abos.fabricmc.magic.entities.MagicMissileEntity;
import org.abos.fabricmc.magic.entities.MediumEarthMissileEntity;
import org.abos.fabricmc.magic.entities.SmallEarthMissileEntity;
import org.abos.fabricmc.magic.utils.MissileSize;

public class WandItem extends Item {

    public WandItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean isDamageable() {
        return true;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        if (!world.isClient) {
            int random = world.random.nextBetween(1,3);
            MagicMissileEntity ballEntity;
            if (random == 1) {
                ballEntity = new SmallEarthMissileEntity(MagicContent.SMALL_EARTH_MISSILE_ENTITY_TYPE, user, world);
                ballEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0f, MissileSize.SMALL.getSpeed(), MissileSize.SMALL.getDivergence());
            }
            else if (random == 2) {
                ballEntity = new MediumEarthMissileEntity(MagicContent.MEDIUM_EARTH_MISSILE_ENTITY_TYPE, user, world);
                ballEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0f, MissileSize.MEDIUM.getSpeed(), MissileSize.MEDIUM.getDivergence());
            }
            else {
                ballEntity = new BigEarthMissileEntity(MagicContent.BIG_EARTH_MISSILE_ENTITY_TYPE, user, world);
                ballEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0f, MissileSize.BIG.getSpeed(), MissileSize.BIG.getDivergence());
            }
            world.spawnEntity(ballEntity);
            return TypedActionResult.consume(user.getStackInHand(hand));
        }
        return TypedActionResult.pass(user.getStackInHand(hand));
    }
}

package org.abos.fabricmc.magic.utils;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class WorldUtils {

    private WorldUtils() {
        /* No instantiation. */
    }

    public static void raiseBlock(final World world, final BlockPos groundPos, final int height) {
        if (height < 0) {
            throw new IllegalArgumentException("Height must be non-negative!");
        }
        if (height == 0) {
            return;
        }
        BlockState groundState = world.getBlockState(groundPos);
        BlockPos targetPos = groundPos;
        int actualHeight = 0;
        for (; actualHeight < height; actualHeight++) {
            if (!isReplaceable(world, targetPos.up())) {
                break;
            }
            targetPos = targetPos.up();
            world.setBlockState(targetPos, groundState);
        }
        targetPos = targetPos.up();
        if (!targetPos.equals(groundPos.up())) {
            List<Entity> entities = world.getNonSpectatingEntities(Entity.class, new Box(0d,1d,0d,1d,2d,1d).offset(groundPos));
            for (Entity entity : entities) {
                entity.setPos(entity.getX(), entity.getY() + actualHeight, entity.getZ());
            }
        }
    }

    public static boolean isReplaceable(final World world, final BlockPos blockPos) {
        return world.getBlockState(blockPos).isAir() || !world.getFluidState(blockPos).isEmpty();
    }
}

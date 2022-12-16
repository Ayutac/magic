package org.abos.fabricmc.magic.utils;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
            if (isSolid(world, targetPos.up())) {
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

    /**
     * @return <code>false</code> if the block at the specified position is air or a fluid, <code>true</code> otherwise.
     */
    public static boolean isSolid(final World world, final BlockPos blockPos) {
        return !world.getBlockState(blockPos).isAir() && world.getFluidState(blockPos).isEmpty();
    }

    public static Set<BlockPos> circleEighth(final BlockPos center, final int radius) {
        if (radius < 0) {
            throw new IllegalArgumentException("Radius must be non-negative!");
        }
        if (radius == 0) {
            return Set.of(center);
        }
        if (radius == 1) {
            return Set.of(center.north(), center.north().east());
        }
        if (radius == 2) {
            return Set.of(center.north().north(), center.north().north().east(), center.north().east());
        }
        if (radius == 3) {
            return Set.of(center.add(3,0,0), center.add(3,0,1), center.add(2,0,1), center.add(2,0,2));
        }
        if (radius == 4) {
            return Set.of(center.add(4,0,0), center.add(4,0,1), center.add(4,0,2), center.add(3,0,2), center.add(3,0,3));
        }
        // for more special cases, use https://donatstudios.com/PixelCircleGenerator
        // for implementation of general rule, use https://en.wikipedia.org/wiki/Midpoint_circle_algorithm
        throw new IllegalArgumentException("Radius > 4 is not supported!");
    }

    public static Set<BlockPos> circle(final BlockPos center, final int radius) {
        if (radius == 0) {
            return Set.of(center);
        }
        if (radius == 1) {
            return Set.of(center.north(), center.north().east(), center.east(), center.south().east(), center.south(), center.south().west(), center.west(), center.north().west());
        }
        Set<BlockPos> circleEighth = circleEighth(center, radius);
        Set<BlockPos> circle = new HashSet<>();
        for (BlockPos pos : circleEighth) {
            //  x, z
            circle.add(pos);
            // -x, z
            circle.add(new BlockPos(-pos.getX()+2*center.getX(), pos.getY(), pos.getZ()));
            //  x,-z
            circle.add(new BlockPos(pos.getX(), pos.getY(), -pos.getZ()+2*center.getZ()));
            // -x,-z
            circle.add(new BlockPos(-pos.getX()+2*center.getX(), pos.getY(), -pos.getZ()+2*center.getZ()));
            //  z, x
            circle.add(new BlockPos(pos.getZ()-center.getZ()+center.getX(), pos.getY(), pos.getX()-center.getX()+center.getZ()));
            // -z, x
            circle.add(new BlockPos(-pos.getZ()+center.getZ()+center.getX(), pos.getY(), pos.getX()-center.getX()+center.getZ()));
            //  z,-x
            circle.add(new BlockPos(pos.getZ()-center.getZ()+center.getX(), pos.getY(), -pos.getX()+center.getX()+center.getZ()));
            // -z,-x
            circle.add(new BlockPos(-pos.getZ()+center.getZ()+center.getX(), pos.getY(), -pos.getX()+center.getX()+center.getZ()));
        }
        return circle;
    }

    public static Set<BlockPos> circleGround(final World world, final BlockPos center, final int radius) {
        Set<BlockPos> circleGround = new HashSet<>();
        for (BlockPos pos : circle(center, radius)) {
            if (isSolid(world, pos.up())) {
                if (!isSolid(world, pos.up(2))) {
                    circleGround.add(pos.up());
                    continue;
                }
                else {
                    if (!isSolid(world, pos.up(3))) {
                        circleGround.add(pos.up(2));
                        continue;
                    }
                }
            }
            else {
                if (isSolid(world, pos)) {
                    circleGround.add(pos);
                    continue;
                }
            }
            if (!isSolid(world, pos)) {
                if (isSolid(world, pos.down())) {
                    circleGround.add(pos.down());
                }
                else {
                    if (isSolid(world, pos.down(2))) {
                        circleGround.add((pos.down(2)));
                    }
                }
            }
        }
        return circleGround;
    }

    public static Set<BlockPos> filledDomeQuarter(final BlockPos center, final int radius) {
        if (radius < 0) {
            throw new IllegalArgumentException("Radius must be non-negative!");
        }
        if (radius == 0) {
            return Set.of(center);
        }
        Set<BlockPos> domeQuarter = new HashSet<>();
        final int r2 = radius*radius;
        for (int x = 0; x <= radius; x++) {
            final int x2 = x*x;
            for (int z = 0; z <= radius; z++) {
                final int z2 = z*z;
                for (int y = 0; y <= radius; y++) {
                    if (x2 + y*y + z2 <= r2) {
                        domeQuarter.add(center.add(x,y,z));
                    }
                }
            }
        }
        return domeQuarter;
    }

    public static Set<BlockPos> filledDome(final BlockPos center, final int radius) {
        if (radius == 0) {
            return Set.of(center);
        }
        Set<BlockPos> domeQuarter = filledDomeQuarter(center, radius);
        Set<BlockPos> dome = new HashSet<>();
        for (BlockPos pos : domeQuarter) {
            //  x, z
            dome.add(pos);
            // -x, z
            dome.add(new BlockPos(-pos.getX()+2*center.getX(), pos.getY(), pos.getZ()));
            //  x,-z
            dome.add(new BlockPos(pos.getX(), pos.getY(), -pos.getZ()+2*center.getZ()));
            // -x,-z
            dome.add(new BlockPos(-pos.getX()+2*center.getX(), pos.getY(), -pos.getZ()+2*center.getZ()));
        }
        return dome;
    }

    public static Set<BlockPos> filledSphere(final BlockPos center, final int radius) {
        if (radius == 0) {
            return Set.of(center);
        }
        Set<BlockPos> dome = filledDome(center, radius);
        Set<BlockPos> sphere = new HashSet<>();
        for (BlockPos pos : dome) {
            //  x, y, z
            sphere.add(pos);
            //  x,-y, z
            sphere.add(new BlockPos(pos.getX(), -pos.getY()+2*center.getY(), pos.getZ()));
        }
        return sphere;
    }
}

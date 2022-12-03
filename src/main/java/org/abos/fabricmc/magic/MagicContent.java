package org.abos.fabricmc.magic;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import org.abos.fabricmc.magic.blockentities.AltarBlockEntity;
import org.abos.fabricmc.magic.blocks.AltarBlock;
import org.abos.fabricmc.magic.entities.BigEarthMissileEntity;
import org.abos.fabricmc.magic.entities.MediumEarthMissileEntity;
import org.abos.fabricmc.magic.entities.SmallEarthMissileEntity;
import org.abos.fabricmc.magic.items.WandItem;
import org.abos.fabricmc.magic.utils.MissileSize;

public class MagicContent {

    public final static WandItem BEGINNER_WAND = new WandItem(new FabricItemSettings());

    public final static Identifier ALTAR_ID = new Identifier(Magic.MOD_ID, "altar");
    public final static Block ALTAR_BLOCK = Registry.register(Registries.BLOCK, ALTAR_ID, new AltarBlock(FabricBlockSettings.copyOf(Blocks.LECTERN)));
    public final static BlockItem ALTAR_BLOCK_ITEM =  Registry.register(Registries.ITEM, ALTAR_ID, new BlockItem(ALTAR_BLOCK, new FabricItemSettings()));
    public final static BlockEntityType<AltarBlockEntity> ALTAR_BLOCK_ENTITY_TYPE = Registry.register(Registries.BLOCK_ENTITY_TYPE, ALTAR_ID, FabricBlockEntityTypeBuilder.create(AltarBlockEntity::new, ALTAR_BLOCK).build(null));

    public final static Identifier SMALL_EARTH_MISSILE_ENTITY_TYPE_ID = new Identifier(Magic.MOD_ID, "small_earth_missile");
    public final static EntityType<SmallEarthMissileEntity> SMALL_EARTH_MISSILE_ENTITY_TYPE = registerEntityType(SMALL_EARTH_MISSILE_ENTITY_TYPE_ID, SmallEarthMissileEntity::new, MissileSize.SMALL.getWidth(), MissileSize.SMALL.getHeight(), 4, 10);
    public final static Identifier MEDIUM_EARTH_MISSILE_ENTITY_TYPE_ID = new Identifier(Magic.MOD_ID, "medium_earth_missile");
    public final static EntityType<MediumEarthMissileEntity> MEDIUM_EARTH_MISSILE_ENTITY_TYPE = registerEntityType(MEDIUM_EARTH_MISSILE_ENTITY_TYPE_ID, MediumEarthMissileEntity::new, MissileSize.MEDIUM.getWidth(), MissileSize.MEDIUM.getHeight(), 4, 10);
    public final static Identifier BIG_EARTH_MISSILE_ENTITY_TYPE_ID = new Identifier(Magic.MOD_ID, "big_earth_missile");
    public final static EntityType<BigEarthMissileEntity> BIG_EARTH_MISSILE_ENTITY_TYPE = registerEntityType(BIG_EARTH_MISSILE_ENTITY_TYPE_ID, BigEarthMissileEntity::new, MissileSize.BIG.getWidth(), MissileSize.BIG.getHeight(), 4, 10);

    public final static ScreenHandlerType<AltarScreenHandler> ALTAR_SCREEN_HANDLER_TYPE = Registry.register(Registries.SCREEN_HANDLER, ALTAR_ID, new ScreenHandlerType(AltarScreenHandler::new));

    private MagicContent() {
        /* No instantiation. */
    }

    public static void init() {
        registerItems();
    }

    private static void registerItems() {
        Registry.register(Registries.ITEM, new Identifier(Magic.MOD_ID, "beginner_wand"), BEGINNER_WAND);
    }

    private static <T extends Entity> EntityType<T> registerEntityType(Identifier id, EntityType.EntityFactory<T> factory, float width, float height, int maxTrackingRange, int trackingTickInterval) {
        return Registry.register(Registries.ENTITY_TYPE, id,
                EntityType.Builder.create(factory, SpawnGroup.MISC)
                        .setDimensions(width, height)
                        .maxTrackingRange(maxTrackingRange)
                        .trackingTickInterval(trackingTickInterval)
                        .build(id.toString()));
    }

}

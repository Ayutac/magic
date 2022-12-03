package org.abos.fabricmc.magic;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.abos.fabricmc.magic.entities.MagicMissileEntity;
import org.abos.fabricmc.magic.items.WandItem;

public class MagicContent {

    public final static WandItem BEGINNER_WAND = new WandItem(new FabricItemSettings());

    public final static EntityType<MagicMissileEntity> SMALL_MAGIC_MISSILE_ENTITY_TYPE;
    public final static Identifier SMALL_MAGIC_MISSILE_ENTITY_TYPE_ID = new Identifier(Magic.MOD_ID, "small_magic_missile");
    public final static EntityType<MagicMissileEntity> MEDIUM_MAGIC_MISSILE_ENTITY_TYPE;
    public final static Identifier MEDIUM_MAGIC_MISSILE_ENTITY_TYPE_ID = new Identifier(Magic.MOD_ID, "medium_magic_missile");
    public final static EntityType<MagicMissileEntity> BIG_MAGIC_MISSILE_ENTITY_TYPE;
    public final static Identifier BIG_MAGIC_MISSILE_ENTITY_TYPE_ID = new Identifier(Magic.MOD_ID, "big_magic_missile");

    private MagicContent() {
        /* No instantiation. */
    }

    public static void init() {
        registerItems();
    }

    private static void registerItems() {
        Registry.register(Registries.ITEM, new Identifier(Magic.MOD_ID, "beginner_wand"), BEGINNER_WAND);
    }

    static {
        SMALL_MAGIC_MISSILE_ENTITY_TYPE = Registry.register(Registries.ENTITY_TYPE, SMALL_MAGIC_MISSILE_ENTITY_TYPE_ID,
                EntityType.Builder.<MagicMissileEntity>create(MagicMissileEntity::new, SpawnGroup.MISC)
                        .setDimensions(0.5f, 0.5f)
                        .maxTrackingRange(4)
                        .trackingTickInterval(10)
                        .build(SMALL_MAGIC_MISSILE_ENTITY_TYPE_ID.toString()));
        MEDIUM_MAGIC_MISSILE_ENTITY_TYPE = Registry.register(Registries.ENTITY_TYPE, MEDIUM_MAGIC_MISSILE_ENTITY_TYPE_ID,
                EntityType.Builder.<MagicMissileEntity>create(MagicMissileEntity::new, SpawnGroup.MISC)
                        .setDimensions(1f, 1f)
                        .maxTrackingRange(4)
                        .trackingTickInterval(10)
                        .build(MEDIUM_MAGIC_MISSILE_ENTITY_TYPE_ID.toString()));
        BIG_MAGIC_MISSILE_ENTITY_TYPE = Registry.register(Registries.ENTITY_TYPE, BIG_MAGIC_MISSILE_ENTITY_TYPE_ID,
                EntityType.Builder.<MagicMissileEntity>create(MagicMissileEntity::new, SpawnGroup.MISC)
                        .setDimensions(1.5f, 1.5f)
                        .maxTrackingRange(4)
                        .trackingTickInterval(10)
                        .build(BIG_MAGIC_MISSILE_ENTITY_TYPE_ID.toString()));
    }

}

package org.abos.fabricmc.magic;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtInt;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.abos.fabricmc.magic.enchantments.WandEnchantment;
import org.abos.fabricmc.magic.entities.*;
import org.abos.fabricmc.magic.items.WandItem;
import org.abos.fabricmc.magic.utils.MissileSize;

public class MagicContent {

    public final static WandItem BEGINNER_WAND = new WandItem(ToolMaterials.IRON, new FabricItemSettings());

    public static final ItemGroup ITEM_GROUP = FabricItemGroup.builder(new Identifier(Magic.MOD_ID, "item_group"))
            .icon(() -> new ItemStack(BEGINNER_WAND))
            .build();

    public final static Identifier SMALL_AIR_MISSILE_ID = new Identifier(Magic.MOD_ID, "small_air_missile");
    public final static Identifier MEDIUM_AIR_MISSILE_ID = new Identifier(Magic.MOD_ID, "medium_air_missile");
    public final static Identifier BIG_AIR_MISSILE_ID = new Identifier(Magic.MOD_ID, "big_air_missile");
    public final static Identifier SMALL_EARTH_MISSILE_ID = new Identifier(Magic.MOD_ID, "small_earth_missile");
    public final static Identifier MEDIUM_EARTH_MISSILE_ID = new Identifier(Magic.MOD_ID, "medium_earth_missile");
    public final static Identifier BIG_EARTH_MISSILE_ID = new Identifier(Magic.MOD_ID, "big_earth_missile");
    public final static Identifier SMALL_FIRE_MISSILE_ID = new Identifier(Magic.MOD_ID, "small_fire_missile");
    public final static Identifier MEDIUM_FIRE_MISSILE_ID = new Identifier(Magic.MOD_ID, "medium_fire_missile");
    public final static Identifier BIG_FIRE_MISSILE_ID = new Identifier(Magic.MOD_ID, "big_fire_missile");
    public final static Identifier SMALL_WATER_MISSILE_ID = new Identifier(Magic.MOD_ID, "small_water_missile");
    public final static Identifier MEDIUM_WATER_MISSILE_ID = new Identifier(Magic.MOD_ID, "medium_water_missile");
    public final static Identifier BIG_WATER_MISSILE_ID = new Identifier(Magic.MOD_ID, "big_water_missile");

    public final static EntityType<SmallAirMissileEntity> SMALL_AIR_MISSILE_ENTITY_TYPE = registerEntityType(SMALL_AIR_MISSILE_ID, SmallAirMissileEntity::new, MissileSize.SMALL.getWidth(), MissileSize.SMALL.getHeight(), 4, 10);
    public final static EntityType<MediumAirMissileEntity> MEDIUM_AIR_MISSILE_ENTITY_TYPE = registerEntityType(MEDIUM_AIR_MISSILE_ID, MediumAirMissileEntity::new, MissileSize.MEDIUM.getWidth(), MissileSize.MEDIUM.getHeight(), 4, 10);
    public final static EntityType<BigAirMissileEntity> BIG_AIR_MISSILE_ENTITY_TYPE = registerEntityType(BIG_AIR_MISSILE_ID, BigAirMissileEntity::new, MissileSize.BIG.getWidth(), MissileSize.BIG.getHeight(), 4, 10);
    public final static EntityType<SmallEarthMissileEntity> SMALL_EARTH_MISSILE_ENTITY_TYPE = registerEntityType(SMALL_EARTH_MISSILE_ID, SmallEarthMissileEntity::new, MissileSize.SMALL.getWidth(), MissileSize.SMALL.getHeight(), 4, 10);
    public final static EntityType<MediumEarthMissileEntity> MEDIUM_EARTH_MISSILE_ENTITY_TYPE = registerEntityType(MEDIUM_EARTH_MISSILE_ID, MediumEarthMissileEntity::new, MissileSize.MEDIUM.getWidth(), MissileSize.MEDIUM.getHeight(), 4, 10);
    public final static EntityType<BigEarthMissileEntity> BIG_EARTH_MISSILE_ENTITY_TYPE = registerEntityType(BIG_EARTH_MISSILE_ID, BigEarthMissileEntity::new, MissileSize.BIG.getWidth(), MissileSize.BIG.getHeight(), 4, 10);
    public final static EntityType<SmallFireMissileEntity> SMALL_FIRE_MISSILE_ENTITY_TYPE = registerEntityType(SMALL_FIRE_MISSILE_ID, SmallFireMissileEntity::new, MissileSize.SMALL.getWidth(), MissileSize.SMALL.getHeight(), 4, 10);
    public final static EntityType<MediumFireMissileEntity> MEDIUM_FIRE_MISSILE_ENTITY_TYPE = registerEntityType(MEDIUM_FIRE_MISSILE_ID, MediumFireMissileEntity::new, MissileSize.MEDIUM.getWidth(), MissileSize.MEDIUM.getHeight(), 4, 10);
    public final static EntityType<BigFireMissileEntity> BIG_FIRE_MISSILE_ENTITY_TYPE = registerEntityType(BIG_FIRE_MISSILE_ID, BigFireMissileEntity::new, MissileSize.BIG.getWidth(), MissileSize.BIG.getHeight(), 4, 10);
    public final static EntityType<SmallWaterMissileEntity> SMALL_WATER_MISSILE_ENTITY_TYPE = registerEntityType(SMALL_WATER_MISSILE_ID, SmallWaterMissileEntity::new, MissileSize.SMALL.getWidth(), MissileSize.SMALL.getHeight(), 4, 10);
    public final static EntityType<MediumWaterMissileEntity> MEDIUM_WATER_MISSILE_ENTITY_TYPE = registerEntityType(MEDIUM_WATER_MISSILE_ID, MediumWaterMissileEntity::new, MissileSize.MEDIUM.getWidth(), MissileSize.MEDIUM.getHeight(), 4, 10);
    public final static EntityType<BigWaterMissileEntity> BIG_WATER_MISSILE_ENTITY_TYPE = registerEntityType(BIG_WATER_MISSILE_ID, BigWaterMissileEntity::new, MissileSize.BIG.getWidth(), MissileSize.BIG.getHeight(), 4, 10);

    public final static Enchantment SMALL_AIR_MISSILE_ENCHANTMENT = new WandEnchantment(Enchantment.Rarity.UNCOMMON);
    public final static Enchantment MEDIUM_AIR_MISSILE_ENCHANTMENT = new WandEnchantment(Enchantment.Rarity.RARE);
    public final static Enchantment BIG_AIR_MISSILE_ENCHANTMENT = new WandEnchantment(Enchantment.Rarity.VERY_RARE);
    public final static Enchantment SMALL_EARTH_MISSILE_ENCHANTMENT = new WandEnchantment(Enchantment.Rarity.UNCOMMON);
    public final static Enchantment MEDIUM_EARTH_MISSILE_ENCHANTMENT = new WandEnchantment(Enchantment.Rarity.RARE);
    public final static Enchantment BIG_EARTH_MISSILE_ENCHANTMENT = new WandEnchantment(Enchantment.Rarity.VERY_RARE);
    public final static Enchantment SMALL_FIRE_MISSILE_ENCHANTMENT = new WandEnchantment(Enchantment.Rarity.UNCOMMON);
    public final static Enchantment MEDIUM_FIRE_MISSILE_ENCHANTMENT = new WandEnchantment(Enchantment.Rarity.RARE);
    public final static Enchantment BIG_FIRE_MISSILE_ENCHANTMENT = new WandEnchantment(Enchantment.Rarity.VERY_RARE);
    public final static Enchantment SMALL_WATER_MISSILE_ENCHANTMENT = new WandEnchantment(Enchantment.Rarity.UNCOMMON);
    public final static Enchantment MEDIUM_WATER_MISSILE_ENCHANTMENT = new WandEnchantment(Enchantment.Rarity.RARE);
    public final static Enchantment BIG_WATER_MISSILE_ENCHANTMENT = new WandEnchantment(Enchantment.Rarity.VERY_RARE);

    private MagicContent() {
        /* No instantiation. */
    }

    public static void init() {
        registerItems();
        registerEnchantments();
        registerCreativeMenu();
    }

    private static void registerItems() {
        Registry.register(Registries.ITEM, new Identifier(Magic.MOD_ID, "beginner_wand"), BEGINNER_WAND);
    }

    private static void registerEnchantments() {
        Registry.register(Registries.ENCHANTMENT, SMALL_AIR_MISSILE_ID, SMALL_AIR_MISSILE_ENCHANTMENT);
        Registry.register(Registries.ENCHANTMENT, MEDIUM_AIR_MISSILE_ID, MEDIUM_AIR_MISSILE_ENCHANTMENT);
        Registry.register(Registries.ENCHANTMENT, BIG_AIR_MISSILE_ID, BIG_AIR_MISSILE_ENCHANTMENT);
        Registry.register(Registries.ENCHANTMENT, SMALL_EARTH_MISSILE_ID, SMALL_EARTH_MISSILE_ENCHANTMENT);
        Registry.register(Registries.ENCHANTMENT, MEDIUM_EARTH_MISSILE_ID, MEDIUM_EARTH_MISSILE_ENCHANTMENT);
        Registry.register(Registries.ENCHANTMENT, BIG_EARTH_MISSILE_ID, BIG_EARTH_MISSILE_ENCHANTMENT);
        Registry.register(Registries.ENCHANTMENT, SMALL_FIRE_MISSILE_ID, SMALL_FIRE_MISSILE_ENCHANTMENT);
        Registry.register(Registries.ENCHANTMENT, MEDIUM_FIRE_MISSILE_ID, MEDIUM_FIRE_MISSILE_ENCHANTMENT);
        Registry.register(Registries.ENCHANTMENT, BIG_FIRE_MISSILE_ID, BIG_FIRE_MISSILE_ENCHANTMENT);
        Registry.register(Registries.ENCHANTMENT, SMALL_WATER_MISSILE_ID, SMALL_WATER_MISSILE_ENCHANTMENT);
        Registry.register(Registries.ENCHANTMENT, MEDIUM_WATER_MISSILE_ID, MEDIUM_WATER_MISSILE_ENCHANTMENT);
        Registry.register(Registries.ENCHANTMENT, BIG_WATER_MISSILE_ID, BIG_WATER_MISSILE_ENCHANTMENT);
    }

    private static void registerCreativeMenu() {
        ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP).register(MagicContent::registerItemGroup);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(MagicContent::registerCombat);
    }

    private static <T extends Entity> EntityType<T> registerEntityType(Identifier id, EntityType.EntityFactory<T> factory, float width, float height, int maxTrackingRange, int trackingTickInterval) {
        return Registry.register(Registries.ENTITY_TYPE, id,
                FabricEntityTypeBuilder.create(SpawnGroup.MISC, factory)
                        .dimensions(new EntityDimensions(width, height, true))
                        .trackRangeBlocks(maxTrackingRange)
                        .trackedUpdateRate(trackingTickInterval)
                        .build());
    }

    private static void registerItemGroup(FabricItemGroupEntries entries) {
        entries.add(BEGINNER_WAND);

        for (Enchantment enchantment : Registries.ENCHANTMENT) {
            Identifier id = Registries.ENCHANTMENT.getId(enchantment);
            if (id.getNamespace().equals(Magic.MOD_ID)) {
                NbtCompound spell = new NbtCompound();
                spell.put("id", NbtString.of(id.toString()));
                spell.put("lvl", NbtInt.of(1));
                NbtList list = new NbtList();
                list.add(spell);
                ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
                book.setSubNbt("StoredEnchantments", list);
                entries.add(book);
            }
        }
    }

    private static void registerCombat(FabricItemGroupEntries entries) {
        entries.addBefore(Items.SHIELD,
                BEGINNER_WAND);
    }

}

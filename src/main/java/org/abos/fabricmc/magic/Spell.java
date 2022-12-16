package org.abos.fabricmc.magic;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtInt;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.abos.fabricmc.magic.enchantments.WandEnchantment;
import org.abos.fabricmc.magic.entities.EarthPillarProjectileEntity;
import org.abos.fabricmc.magic.entities.MagicProjectileEntity;
import org.abos.fabricmc.magic.utils.MagicType;
import org.abos.fabricmc.magic.utils.MissileSize;

import java.util.Locale;
import java.util.function.BiFunction;

import static net.minecraft.enchantment.Enchantment.Rarity.*;

public enum Spell {
    
    SMALL_AIR_MISSILE(UNCOMMON, MagicType.AIR, MissileSize.SMALL, MagicProjectileEntity::createSmallAirMissile),
    MEDIUM_AIR_MISSILE(RARE, MagicType.AIR, MissileSize.MEDIUM, MagicProjectileEntity::createMediumAirMissile),
    BIG_AIR_MISSILE(VERY_RARE, MagicType.AIR, MissileSize.BIG, MagicProjectileEntity::createBigAirMissile),
    SMALL_EARTH_MISSILE(UNCOMMON, MagicType.EARTH, MissileSize.SMALL, MagicProjectileEntity::createSmallEarthMissile),
    MEDIUM_EARTH_MISSILE(RARE, MagicType.EARTH, MissileSize.MEDIUM, MagicProjectileEntity::createMediumEarthMissile),
    BIG_EARTH_MISSILE(VERY_RARE, MagicType.EARTH, MissileSize.BIG, MagicProjectileEntity::createBigEarthMissile),
    SMALL_FIRE_MISSILE(UNCOMMON, MagicType.FIRE, MissileSize.SMALL, MagicProjectileEntity::createSmallFireMissile),
    MEDIUM_FIRE_MISSILE(RARE, MagicType.FIRE, MissileSize.MEDIUM, MagicProjectileEntity::createMediumFireMissile),
    BIG_FIRE_MISSILE(VERY_RARE, MagicType.FIRE, MissileSize.BIG, MagicProjectileEntity::createBigFireMissile),
    SMALL_WATER_MISSILE(UNCOMMON, MagicType.WATER, MissileSize.SMALL, MagicProjectileEntity::createSmallWaterMissile),
    MEDIUM_WATER_MISSILE(RARE, MagicType.WATER, MissileSize.MEDIUM, MagicProjectileEntity::createMediumWaterMissile),
    BIG_WATER_MISSILE(VERY_RARE, MagicType.WATER, MissileSize.BIG, MagicProjectileEntity::createBigWaterMissile),
    INSTANT_HEAL(RARE, MagicConfig.INSTANT_HEAL_COST, MagicType.LIGHT),
    SHIELD(RARE, MagicConfig.SHIELD_COST, MagicType.EARTH),
    NIGHT_VISION(COMMON, MagicConfig.NIGHT_VISION_COST, MagicType.LIGHT),
    GILLS(UNCOMMON, MagicConfig.GILLS_COST, MagicType.WATER),
    OCEANS_FRIEND(VERY_RARE, MagicConfig.OCEANS_FRIEND_COST, MagicType.WATER),
    LEVITATE(VERY_RARE, MagicConfig.LEVITATE_COST, MagicType.AIR),
    FEATHER_FALL(UNCOMMON, MagicConfig.FEATHER_FALL_COST, MagicType.AIR),
    FIRE_IMMUNITY(RARE, MagicConfig.FIRE_IMMUNITY_COST, MagicType.WATER),
    EARTH_PILLAR(UNCOMMON, MagicConfig.EARTH_PILLAR_COST, MagicType.EARTH, MissileSize.MEDIUM, EarthPillarProjectileEntity::create, EarthPillarProjectileEntity::new),
    EARTH_CIRCLE(VERY_RARE, MagicConfig.EARTH_CIRCLE_COST, MagicType.EARTH),
    EARTH_REMOVAL(VERY_RARE, MagicConfig.EARTH_REMOVAL_COST, MagicType.EARTH),
    FIRE_CIRCLE(RARE, MagicConfig.FIRE_CIRCLE_COST, MagicType.FIRE),
    WATER_DOME(RARE, MagicConfig.WATER_DOME_COST, MagicType.WATER),
    WATER_REMOVAL(UNCOMMON, MagicConfig.WATER_REMOVAL_COST, MagicType.WATER),
    CHARM(VERY_RARE, MagicConfig.CHARM_COST, MagicType.LIGHT);

    private final Identifier id = new Identifier(Magic.MOD_ID, getName());
    private final int manaCost;
    private final MagicType type;
    private final MissileSize size;
    private final BiFunction<World, PlayerEntity, MagicProjectileEntity> projectileFactory;
    private final EntityType<MagicProjectileEntity> entityType;
    private final WandEnchantment enchantment;

    Spell(Enchantment.Rarity rarity, int manaCost, MagicType type, MissileSize size, BiFunction<World, PlayerEntity, MagicProjectileEntity> projectileFactory, EntityType.EntityFactory<MagicProjectileEntity> projectileFactory2) {
        if (manaCost < 0) {
            throw new IllegalArgumentException("Mana cost cannot be negative!");
        }
        this.manaCost = manaCost;
        this.type = type;
        this.size = size;
        this.projectileFactory = projectileFactory;
        enchantment = new WandEnchantment(rarity);
        if (projectileFactory == null) {
            entityType = null;
        }
        else {
            entityType = FabricEntityTypeBuilder.create(SpawnGroup.MISC, projectileFactory2 == null ? MagicProjectileEntity::new : projectileFactory2)
                    .dimensions(new EntityDimensions(size.getWidth(), size.getHeight(), true))
                    .trackRangeBlocks(4)
                    .trackedUpdateRate(10)
                    .build();
        }
    }

    Spell(Enchantment.Rarity rarity, MagicType type, MissileSize size, BiFunction<World, PlayerEntity, MagicProjectileEntity> projectileFactory) {
        this(rarity, size.getManaCost(), type, size, projectileFactory, null);
    }

    Spell(Enchantment.Rarity rarity, int manaCost, MagicType type) {
        this(rarity, manaCost, type, null, null, null);
    }

    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }

    public Identifier getId() {
        return id;
    }

    public int getManaCost() {
        return manaCost;
    }

    public MagicType getType() {
        return type;
    }

    public MissileSize getSize() {
        return size;
    }

    public void playSound(World world, PlayerEntity user) {
        if (type == null || type.getSound() == null)
            return;
        world.playSound(null, user.getBlockPos(), type.getSound(), SoundCategory.PLAYERS);
    }

    public WandEnchantment getEnchantment() {
        return enchantment;
    }

    public ItemStack asEnchantedBook() {
        final NbtCompound spellNbt = new NbtCompound();
        spellNbt.put("id", NbtString.of(id.toString()));
        spellNbt.put("lvl", NbtInt.of(1));
        final NbtList list = new NbtList();
        list.add(spellNbt);
        final ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
        book.setSubNbt("StoredEnchantments", list);
        return book;
    }

    public boolean isProjectile() {
        return projectileFactory != null;
    }

    public MagicProjectileEntity createProjectile(World world, PlayerEntity user) {
        if (projectileFactory == null) {
            return null;
        }
        return projectileFactory.apply(world, user);
    }

    public EntityType<MagicProjectileEntity> getEntityType() {
        return entityType;
    }

    public static Spell getEnchantedSpell(final ItemStack stack) {
        for (Enchantment enchantment : EnchantmentHelper.get(stack).keySet()) {
            if (!(enchantment instanceof WandEnchantment)) {
                continue;
            }
            for (Spell spell : Spell.values()) {
                if (spell.enchantment.equals(enchantment)) {
                    return spell;
                }
            }
            // we can return early since wand spells are mutually exclusive
            return null;
        }
        return null;
    }
}

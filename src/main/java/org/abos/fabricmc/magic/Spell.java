package org.abos.fabricmc.magic;

import net.minecraft.enchantment.Enchantment;
import static net.minecraft.enchantment.Enchantment.Rarity.*;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtInt;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.abos.fabricmc.magic.enchantments.WandEnchantment;
import org.abos.fabricmc.magic.entities.*;
import org.abos.fabricmc.magic.utils.MissileSize;

import java.util.Locale;
import java.util.function.BiFunction;

public enum Spell {
    
    SMALL_AIR_MISSILE(UNCOMMON, MissileSize.SMALL.getManaCost(), MagicProjectileEntity::createSmallAirMissile),
    MEDIUM_AIR_MISSILE(RARE, MissileSize.MEDIUM.getManaCost(), MagicProjectileEntity::createMediumAirMissile),
    BIG_AIR_MISSILE(VERY_RARE, MissileSize.BIG.getManaCost(), MagicProjectileEntity::createBigAirMissile),
    SMALL_EARTH_MISSILE(UNCOMMON, MissileSize.SMALL.getManaCost(), MagicProjectileEntity::createSmallEarthMissile),
    MEDIUM_EARTH_MISSILE(RARE, MissileSize.MEDIUM.getManaCost(), MagicProjectileEntity::createMediumEarthMissile),
    BIG_EARTH_MISSILE(VERY_RARE, MissileSize.BIG.getManaCost(), MagicProjectileEntity::createBigEarthMissile),
    SMALL_FIRE_MISSILE(UNCOMMON, MissileSize.SMALL.getManaCost(), MagicProjectileEntity::createSmallFireMissile),
    MEDIUM_FIRE_MISSILE(RARE, MissileSize.MEDIUM.getManaCost(), MagicProjectileEntity::createMediumFireMissile),
    BIG_FIRE_MISSILE(VERY_RARE, MissileSize.BIG.getManaCost(), MagicProjectileEntity::createBigFireMissile),
    SMALL_WATER_MISSILE(UNCOMMON, MissileSize.SMALL.getManaCost(), MagicProjectileEntity::createSmallWaterMissile),
    MEDIUM_WATER_MISSILE(RARE, MissileSize.MEDIUM.getManaCost(), MagicProjectileEntity::createMediumWaterMissile),
    BIG_WATER_MISSILE(VERY_RARE, MissileSize.BIG.getManaCost(), MagicProjectileEntity::createBigWaterMissile),
    INSTANT_HEAL(RARE, MagicConfig.INSTANT_HEAL_COST),
    SHIELD(RARE, MagicConfig.SHIELD_COST),
    NIGHT_VISION(COMMON, MagicConfig.NIGHT_VISION_COST),
    GILLS(UNCOMMON, MagicConfig.GILLS_COST),
    OCEANS_FRIEND(VERY_RARE, MagicConfig.OCEANS_FRIEND_COST),
    LEVITATE(VERY_RARE, MagicConfig.LEVITATE_COST),
    FEATHER_FALL(UNCOMMON, MagicConfig.FEATHER_FALL_COST),
    FIRE_IMMUNITY(RARE, MagicConfig.FIRE_IMMUNITY_COST),
    EARTH_PILLAR(UNCOMMON, MagicConfig.EARTH_PILLAR_COST, EarthPillarProjectileEntity::create),
    EARTH_CIRCLE(VERY_RARE, MagicConfig.EARTH_CIRCLE_COST),
    FIRE_CIRCLE(RARE, MagicConfig.FIRE_CIRCLE_COST);

    private final Identifier id = new Identifier(Magic.MOD_ID, getName());
    private final int manaCost;
    private final WandEnchantment enchantment;
    private final BiFunction<World, PlayerEntity, MagicProjectileEntity> projectileFactory;

    Spell(Enchantment.Rarity rarity, int manaCost, BiFunction<World, PlayerEntity, MagicProjectileEntity> projectileFactory) {
        if (manaCost < 0) {
            throw new IllegalArgumentException("Mana cost cannot be negative!");
        }
        this.manaCost = manaCost;
        this.projectileFactory = projectileFactory;
        enchantment = new WandEnchantment(rarity);
    }

    Spell(Enchantment.Rarity rarity, int cost) {
        this(rarity, cost, null);
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

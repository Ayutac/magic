package org.abos.fabricmc.magic.enchantments;

import net.minecraft.enchantment.Enchantment;
import static net.minecraft.enchantment.Enchantment.Rarity.*;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtInt;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.util.Identifier;
import org.abos.fabricmc.magic.Magic;
import org.abos.fabricmc.magic.MagicConfig;
import org.abos.fabricmc.magic.utils.MissileSize;

import java.util.Locale;

public enum Spell {
    
    SMALL_AIR_MISSILE(UNCOMMON, MissileSize.SMALL.getManaCost()),
    MEDIUM_AIR_MISSILE(RARE, MissileSize.MEDIUM.getManaCost()),
    BIG_AIR_MISSILE(VERY_RARE, MissileSize.BIG.getManaCost()),
    SMALL_EARTH_MISSILE(UNCOMMON, MissileSize.SMALL.getManaCost()),
    MEDIUM_EARTH_MISSILE(RARE, MissileSize.MEDIUM.getManaCost()),
    BIG_EARTH_MISSILE(VERY_RARE, MissileSize.BIG.getManaCost()),
    SMALL_FIRE_MISSILE(UNCOMMON, MissileSize.SMALL.getManaCost()),
    MEDIUM_FIRE_MISSILE(RARE, MissileSize.MEDIUM.getManaCost()),
    BIG_FIRE_MISSILE(VERY_RARE, MissileSize.BIG.getManaCost()),
    SMALL_WATER_MISSILE(UNCOMMON, MissileSize.SMALL.getManaCost()),
    MEDIUM_WATER_MISSILE(RARE, MissileSize.MEDIUM.getManaCost()),
    BIG_WATER_MISSILE(VERY_RARE, MissileSize.BIG.getManaCost()),
    INSTANT_HEAL(RARE, MagicConfig.INSTANT_HEAL_COST),
    SHIELD(RARE, MagicConfig.SHIELD_COST),
    NIGHT_VISION(COMMON, MagicConfig.NIGHT_VISION_COST),
    GILLS(UNCOMMON, MagicConfig.GILLS_COST),
    OCEANS_FRIEND(VERY_RARE, MagicConfig.OCEANS_FRIEND_COST),
    LEVITATE(VERY_RARE, MagicConfig.LEVITATE_COST),
    FEATHER_FALL(UNCOMMON, MagicConfig.FEATHER_FALL_COST),
    FIRE_IMMUNITY(RARE, MagicConfig.FIRE_IMMUNITY_COST),
    EARTH_PILLAR(UNCOMMON, MagicConfig.EARTH_PILLAR_COST),
    EARTH_CIRCLE(VERY_RARE, MagicConfig.EARTH_CIRCLE_COST),
    FIRE_CIRCLE(RARE, MagicConfig.FIRE_CIRCLE_COST);

    private final Identifier id = new Identifier(Magic.MOD_ID, name().toLowerCase(Locale.ROOT));
    private final WandEnchantment enchantment;
    
    Spell(Enchantment.Rarity rarity, int cost) {
        if (cost < 0) {
            throw new IllegalArgumentException("Spell cost cannot be negative!");
        }
        enchantment = new WandEnchantment(rarity, cost);
    }

    public Identifier getId() {
        return id;
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

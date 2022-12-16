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
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.abos.fabricmc.magic.enchantments.WandEnchantment;
import org.abos.fabricmc.magic.entities.EarthPillarProjectileEntity;
import org.abos.fabricmc.magic.entities.MagicProjectileEntity;
import org.abos.fabricmc.magic.utils.MagicType;
import org.abos.fabricmc.magic.utils.ProjectileSettings;

import java.util.Locale;
import java.util.function.BiFunction;

import static net.minecraft.enchantment.Enchantment.Rarity.*;

public enum Spell {
    
    SMALL_AIR_MISSILE(UNCOMMON, MagicType.AIR, ProjectileSettings.small().setKnockUpSpeed(0.4f).setDamage(MagicConfig.SMALL_AIR_MISSILE_DAMAGE)),
    MEDIUM_AIR_MISSILE(RARE, MagicType.AIR, ProjectileSettings.medium().setKnockUpSpeed(0.8f).setDamage(MagicConfig.MEDIUM_AIR_MISSILE_DAMAGE)),
    BIG_AIR_MISSILE(VERY_RARE, MagicType.AIR, ProjectileSettings.big().setKnockUpSpeed(1.2f).setDamage(MagicConfig.BIG_AIR_MISSILE_DAMAGE)),
    LEVITATE(VERY_RARE, MagicConfig.LEVITATE_COST, MagicType.AIR),
    FEATHER_FALL(UNCOMMON, MagicConfig.FEATHER_FALL_COST, MagicType.AIR),
    SMALL_EARTH_MISSILE(UNCOMMON, MagicType.EARTH, ProjectileSettings.small().setDamage(MagicConfig.SMALL_EARTH_MISSILE_DAMAGE)),
    MEDIUM_EARTH_MISSILE(RARE, MagicType.EARTH, ProjectileSettings.medium().setDamage(MagicConfig.MEDIUM_EARTH_MISSILE_DAMAGE)),
    BIG_EARTH_MISSILE(VERY_RARE, MagicType.EARTH, ProjectileSettings.big().setDamage(MagicConfig.BIG_EARTH_MISSILE_DAMAGE)),
    EARTH_PILLAR(UNCOMMON, MagicConfig.EARTH_PILLAR_COST, MagicType.EARTH, ProjectileSettings.medium(), EarthPillarProjectileEntity::create, EarthPillarProjectileEntity::new),
    EARTH_CIRCLE(VERY_RARE, MagicConfig.EARTH_CIRCLE_COST, MagicType.EARTH),
    EARTH_REMOVAL(VERY_RARE, MagicConfig.EARTH_REMOVAL_COST, MagicType.EARTH),
    SHIELD(RARE, MagicConfig.SHIELD_COST, MagicType.EARTH),
    SMALL_FIRE_MISSILE(UNCOMMON, MagicType.FIRE, ProjectileSettings.small().setFireTicks(1).setDamage(MagicConfig.SMALL_FIRE_MISSILE_DAMAGE)),
    MEDIUM_FIRE_MISSILE(RARE, MagicType.FIRE, ProjectileSettings.medium().setFireTicks(5).setDamage(MagicConfig.MEDIUM_FIRE_MISSILE_DAMAGE)),
    BIG_FIRE_MISSILE(VERY_RARE, MagicType.FIRE, ProjectileSettings.big().setFireTicks(10).setDamage(MagicConfig.BIG_FIRE_MISSILE_DAMAGE)),
    FIRE_CIRCLE(RARE, MagicConfig.FIRE_CIRCLE_COST, MagicType.FIRE),
    LAVA_REMOVAL(RARE, MagicConfig.LAVA_REMOVAL_COST, MagicType.FIRE),
    INSTANT_HEAL(RARE, MagicConfig.INSTANT_HEAL_COST, MagicType.LIGHT),
    CHARM(VERY_RARE, MagicConfig.CHARM_COST, MagicType.LIGHT),
    NIGHT_VISION(COMMON, MagicConfig.NIGHT_VISION_COST, MagicType.LIGHT),
    SMALL_WATER_MISSILE(UNCOMMON, MagicType.WATER, ProjectileSettings.small().setExtinguishing(true).setDamage(MagicConfig.SMALL_WATER_MISSILE_DAMAGE)),
    MEDIUM_WATER_MISSILE(RARE, MagicType.WATER, ProjectileSettings.small().setExtinguishing(true).setDamage(MagicConfig.MEDIUM_WATER_MISSILE_DAMAGE)),
    BIG_WATER_MISSILE(VERY_RARE, MagicType.WATER, ProjectileSettings.small().setExtinguishing(true).setDamage(MagicConfig.BIG_WATER_MISSILE_DAMAGE)),
    WATER_DOME(RARE, MagicConfig.WATER_DOME_COST, MagicType.WATER),
    WATER_REMOVAL(UNCOMMON, MagicConfig.WATER_REMOVAL_COST, MagicType.WATER),
    GILLS(UNCOMMON, MagicConfig.GILLS_COST, MagicType.WATER),
    OCEANS_FRIEND(VERY_RARE, MagicConfig.OCEANS_FRIEND_COST, MagicType.WATER),
    FIRE_IMMUNITY(RARE, MagicConfig.FIRE_IMMUNITY_COST, MagicType.WATER);

    private final Identifier id = new Identifier(Magic.MOD_ID, getName());
    private final int manaCost;
    private final MagicType type;
    private final ProjectileSettings settings;
    private final BiFunction<World, PlayerEntity, MagicProjectileEntity> projectileFactory;
    private final EntityType<MagicProjectileEntity> entityType;
    private final WandEnchantment enchantment;

    Spell(Enchantment.Rarity rarity, int manaCost, MagicType type, ProjectileSettings settings, BiFunction<World, PlayerEntity, MagicProjectileEntity> projectileFactory, EntityType.EntityFactory<MagicProjectileEntity> projectileFactory2) {
        if (manaCost < 0) {
            throw new IllegalArgumentException("Mana cost cannot be negative!");
        }
        this.manaCost = manaCost;
        this.type = type;
        this.settings = settings;
        this.projectileFactory = projectileFactory;
        enchantment = new WandEnchantment(rarity);
        if (projectileFactory == null) {
            entityType = null;
        }
        else {
            EntityType.EntityFactory<MagicProjectileEntity> backup = MagicProjectileEntity::new;
            entityType = FabricEntityTypeBuilder.create(SpawnGroup.MISC, projectileFactory2 == null ? backup : projectileFactory2)
                    .dimensions(new EntityDimensions(settings.getSize().getWidth(), settings.getSize().getHeight(), true))
                    .trackRangeBlocks(4)
                    .trackedUpdateRate(10)
                    .build();
        }
    }

    Spell(Enchantment.Rarity rarity, MagicType type, ProjectileSettings settings) {
        this(rarity, settings.getSize().getManaCost(), type, settings, null, null);
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

    public ProjectileSettings getSettings() {
        return settings;
    }

    public SoundEvent getSound() {
        if (type == null) {
            return null;
        }
        return type.getSound();
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
        return settings != null;
    }

    public MagicProjectileEntity createProjectile(World world, PlayerEntity user) {
        if (!isProjectile()) {
            return null;
        }
        if (projectileFactory == null) {
            MagicProjectileEntity entity = new MagicProjectileEntity(entityType, user, world);
            entity.setVelocity(user, user.getPitch(), user.getYaw(), 0f, settings.getSize().getSpeed(), settings.getSize().getDivergence());
            if (settings.getDamage() > 0d) {
                entity.setDamage(settings.getDamage());
            }
            if (settings.getFireTicks() > 0) {
                entity.setFireTicks(settings.getFireTicks());
            } // mutually exclusive
            else if (settings.isExtinguishing()) {
                entity.setExtinguishing(settings.isExtinguishing());
            }
            if (settings.getKnockUpSpeed() > 0f) {
                entity.setKnockUpSpeed(settings.getKnockUpSpeed());
            }
            entity.setNoGravity(!type.isAffectedByGravity());
            entity.setSound(type.getSound());
            return entity;
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

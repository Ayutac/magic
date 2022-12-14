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
import org.abos.fabricmc.ayusimpleconfig.IntConfigProperty;
import org.abos.fabricmc.magic.enchantments.WandEnchantment;
import org.abos.fabricmc.magic.entities.AccelerateGrowthProjectileEntity;
import org.abos.fabricmc.magic.entities.EarthPillarProjectileEntity;
import org.abos.fabricmc.magic.entities.MagicProjectileEntity;
import org.abos.fabricmc.magic.utils.MagicType;
import org.abos.fabricmc.magic.utils.ProjectileSettings;

import java.util.Locale;
import java.util.Objects;
import java.util.function.BiFunction;

import static net.minecraft.enchantment.Enchantment.Rarity.*;

public enum Spell {
    
    SMALL_AIR_MISSILE(UNCOMMON, MagicType.AIR, ProjectileSettings.small().setKnockUpSpeed(0.4f).setDamage(Magic.CONFIG.getSmallAirMissileDamage())),
    MEDIUM_AIR_MISSILE(RARE, MagicType.AIR, ProjectileSettings.medium().setKnockUpSpeed(0.8f).setDamage(Magic.CONFIG.getMediumAirMissileDamage())),
    BIG_AIR_MISSILE(VERY_RARE, MagicType.AIR, ProjectileSettings.big().setKnockUpSpeed(1.2f).setDamage(Magic.CONFIG.getBigAirMissileDamage())),
    LEVITATE(VERY_RARE, Magic.CONFIG.getLevitateCost(), MagicType.AIR),
    FEATHER_FALL(UNCOMMON, Magic.CONFIG.getFeatherFallCost(), MagicType.AIR),
    SMALL_EARTH_MISSILE(UNCOMMON, MagicType.EARTH, ProjectileSettings.small().setDamage(Magic.CONFIG.getSmallEarthMissileDamage())),
    MEDIUM_EARTH_MISSILE(RARE, MagicType.EARTH, ProjectileSettings.medium().setDamage(Magic.CONFIG.getMediumEarthMissileDamage())),
    BIG_EARTH_MISSILE(VERY_RARE, MagicType.EARTH, ProjectileSettings.big().setDamage(Magic.CONFIG.getBigEarthMissileDamage())),
    EARTH_PILLAR(UNCOMMON, Magic.CONFIG.getEarthPillarCost(), MagicType.EARTH, ProjectileSettings.medium(), EarthPillarProjectileEntity::new, EarthPillarProjectileEntity::new),
    EARTH_CIRCLE(VERY_RARE, Magic.CONFIG.getEarthCircleCost(), MagicType.EARTH),
    EARTH_REMOVAL(VERY_RARE, Magic.CONFIG.getEarthRemovalCost(), MagicType.EARTH),
    SHIELD(RARE, Magic.CONFIG.getShieldCost(), MagicType.EARTH),
    SMALL_FIRE_MISSILE(UNCOMMON, MagicType.FIRE, ProjectileSettings.small().setFireTicks(1).setDamage(Magic.CONFIG.getSmallFireMissileDamage())),
    MEDIUM_FIRE_MISSILE(RARE, MagicType.FIRE, ProjectileSettings.medium().setFireTicks(5).setDamage(Magic.CONFIG.getMediumFireMissileDamage())),
    BIG_FIRE_MISSILE(VERY_RARE, MagicType.FIRE, ProjectileSettings.big().setFireTicks(10).setDamage(Magic.CONFIG.getBigFireMissileDamage())),
    FIRE_CIRCLE(RARE, Magic.CONFIG.getFireCircleCost(), MagicType.FIRE),
    LAVA_REMOVAL(RARE, Magic.CONFIG.getLavaRemovalCost(), MagicType.FIRE),
    ACCELERATE_GROWTH(UNCOMMON, Magic.CONFIG.getAccelerateGrowthCost(), MagicType.LIFE, ProjectileSettings.medium(), AccelerateGrowthProjectileEntity::new, AccelerateGrowthProjectileEntity::new),
    INSTANT_HEAL(RARE, Magic.CONFIG.getInstantHealCost(), MagicType.LIFE),
    CHARM(VERY_RARE, Magic.CONFIG.getCharmCost(), MagicType.LIGHT),
    NIGHT_VISION(COMMON, Magic.CONFIG.getNightVisionCost(), MagicType.LIGHT),
    SMALL_LIGHTNING_MISSILE(UNCOMMON, MagicType.LIGHTNING, ProjectileSettings.small().setParalysisTicks(10).setDamage(Magic.CONFIG.getSmallLightningMissileDamage())),
    MEDIUM_LIGHTNING_MISSILE(RARE, MagicType.LIGHTNING, ProjectileSettings.medium().setParalysisTicks(20).setDamage(Magic.CONFIG.getMediumLightningMissileDamage())),
    BIG_LIGHTNING_MISSILE(VERY_RARE, MagicType.LIGHTNING, ProjectileSettings.big().setParalysisTicks(40).setDamage(Magic.CONFIG.getBigLightningMissileDamage())),
    SMALL_WATER_MISSILE(UNCOMMON, MagicType.WATER, ProjectileSettings.small().setExtinguishing(true).setDamage(Magic.CONFIG.getSmallWaterMissileDamage())),
    MEDIUM_WATER_MISSILE(RARE, MagicType.WATER, ProjectileSettings.medium().setExtinguishing(true).setDamage(Magic.CONFIG.getMediumWaterMissileDamage())),
    BIG_WATER_MISSILE(VERY_RARE, MagicType.WATER, ProjectileSettings.big().setExtinguishing(true).setDamage(Magic.CONFIG.getBigWaterMissileDamage())),
    WATER_DOME(RARE, Magic.CONFIG.getWaterDomeCost(), MagicType.WATER),
    WATER_REMOVAL(UNCOMMON, Magic.CONFIG.getWaterRemovalCost(), MagicType.WATER),
    GILLS(UNCOMMON, Magic.CONFIG.getGillsCost(), MagicType.WATER),
    OCEANS_FRIEND(VERY_RARE, Magic.CONFIG.getOceansFriendCost(), MagicType.WATER),
    FIRE_IMMUNITY(RARE, Magic.CONFIG.getFireImmunityCost(), MagicType.WATER);

    private final Identifier id = new Identifier(Magic.MOD_ID, getName());
    private final IntConfigProperty manaCost;
    private final MagicType type;
    private final ProjectileSettings settings;
    private final BiFunction<World, PlayerEntity, MagicProjectileEntity> projectileFactory;
    private final EntityType<MagicProjectileEntity> entityType;
    private final WandEnchantment enchantment;

    Spell(Enchantment.Rarity rarity, IntConfigProperty manaCost, MagicType type, ProjectileSettings settings, BiFunction<World,PlayerEntity,MagicProjectileEntity> projectileFactory, EntityType.EntityFactory<MagicProjectileEntity> projectileFactory2) {
        this.manaCost = Objects.requireNonNull(manaCost);
        this.type = type;
        this.settings = settings;
        this.projectileFactory = projectileFactory;
        enchantment = new WandEnchantment(rarity);
        if (settings == null) {
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

    Spell(Enchantment.Rarity rarity, IntConfigProperty manaCost, MagicType type) {
        this(rarity, manaCost, type, null, null, null);
    }

    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }

    public Identifier getId() {
        return id;
    }

    public IntConfigProperty getManaCost() {
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
        final MagicProjectileEntity entity;
        if (projectileFactory != null) {
            entity = projectileFactory.apply(world, user);
        }
        else {
            entity = new MagicProjectileEntity(entityType, user, world);
        }
        entity.setVelocity(user, user.getPitch(), user.getYaw(), 0f, settings.getSize().getSpeed(), settings.getSize().getDivergence());
        entity.setSettings(settings);
        entity.setNoGravity(!type.isAffectedByGravity());
        entity.setSound(type.getSound());
        return entity;
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

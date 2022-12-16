package org.abos.fabricmc.magic;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import org.abos.fabricmc.magic.effects.InstantManaEffect;
import org.abos.fabricmc.magic.items.WandItem;

public class MagicContent {

    public final static WandItem BEGINNER_WAND = new WandItem(ToolMaterials.STONE, MagicConfig.BEGINNER_MANA_FACTOR, new FabricItemSettings());
    public final static WandItem NOVICE_WAND = new WandItem(ToolMaterials.IRON, MagicConfig.NOVICE_MANA_FACTOR, new FabricItemSettings());
    public final static WandItem EXPERT_WAND = new WandItem(ToolMaterials.DIAMOND, MagicConfig.EXPERT_MANA_FACTOR, new FabricItemSettings());
    public final static WandItem MASTER_WAND = new WandItem(ToolMaterials.NETHERITE, MagicConfig.MASTER_MANA_FACTOR, new FabricItemSettings().fireproof());

    public static final ItemGroup ITEM_GROUP = FabricItemGroup.builder(new Identifier(Magic.MOD_ID, "item_group"))
            .icon(() -> new ItemStack(BEGINNER_WAND))
            .build();

    public final static Identifier MANA_POTION_ID = new Identifier(Magic.MOD_ID, "mana");
    public final static Identifier STRONG_MANA_POTION_ID = new Identifier(Magic.MOD_ID, "strong_mana");
    public final static Identifier MANA_DRAIN_POTION_ID = new Identifier(Magic.MOD_ID, "mana_drain");
    public final static Identifier STRONG_MANA_DRAIN_POTION_ID = new Identifier(Magic.MOD_ID, "strong_mana_drain");

    public final static StatusEffect INSTANT_MANA_EFFECT = Registry.register(Registries.STATUS_EFFECT, new Identifier(Magic.MOD_ID, "instant_mana"), new InstantManaEffect(StatusEffectCategory.BENEFICIAL, 0x22aeff));
    public final static StatusEffect INSTANT_MANA_DRAIN_EFFECT = Registry.register(Registries.STATUS_EFFECT, new Identifier(Magic.MOD_ID, "instant_mana_drain"), new InstantManaEffect(StatusEffectCategory.HARMFUL, 0x8713fd));

    public final static Potion MANA_POTION = Registry.register(Registries.POTION, MANA_POTION_ID, new Potion(Magic.MOD_ID+".mana", new StatusEffectInstance(INSTANT_MANA_EFFECT, 1)));
    public final static Potion STRONG_MANA_POTION = Registry.register(Registries.POTION, STRONG_MANA_POTION_ID, new Potion(Magic.MOD_ID+".mana", new StatusEffectInstance(INSTANT_MANA_EFFECT, 1, 1)));
    public final static Potion MANA_DRAIN_POTION = Registry.register(Registries.POTION, MANA_DRAIN_POTION_ID, new Potion(Magic.MOD_ID+".mana_drain", new StatusEffectInstance(INSTANT_MANA_DRAIN_EFFECT, 1)));
    public final static Potion STRONG_MANA_DRAIN_POTION = Registry.register(Registries.POTION, STRONG_MANA_DRAIN_POTION_ID, new Potion(Magic.MOD_ID+".mana_drain", new StatusEffectInstance(INSTANT_MANA_DRAIN_EFFECT, 1, 1)));

    public static final TagKey<Block> EARTH_TAG = TagKey.of(RegistryKeys.BLOCK, new Identifier(Magic.MOD_ID, "earth"));

    private MagicContent() {
        /* No instantiation. */
    }

    public static void init() {
        registerEntityTypes();
        registerItems();
        registerBrewingRecipes();
        registerEnchantments();
        registerCreativeMenu();
    }

    private static void registerItems() {
        Registry.register(Registries.ITEM, new Identifier(Magic.MOD_ID, "beginner_wand"), BEGINNER_WAND);
        Registry.register(Registries.ITEM, new Identifier(Magic.MOD_ID, "novice_wand"), NOVICE_WAND);
        Registry.register(Registries.ITEM, new Identifier(Magic.MOD_ID, "expert_wand"), EXPERT_WAND);
        Registry.register(Registries.ITEM, new Identifier(Magic.MOD_ID, "master_wand"), MASTER_WAND);
    }

    private static void registerBrewingRecipes() {
        BrewingRecipeRegistry.registerPotionRecipe(Potions.AWKWARD, Items.LAPIS_LAZULI, MANA_POTION);
        BrewingRecipeRegistry.registerPotionRecipe(MANA_POTION, Items.GLOWSTONE_DUST, STRONG_MANA_POTION);
        BrewingRecipeRegistry.registerPotionRecipe(MANA_POTION, Items.FERMENTED_SPIDER_EYE, MANA_DRAIN_POTION);
        BrewingRecipeRegistry.registerPotionRecipe(MANA_DRAIN_POTION, Items.GLOWSTONE_DUST, STRONG_MANA_DRAIN_POTION);
        BrewingRecipeRegistry.registerPotionRecipe(STRONG_MANA_POTION, Items.FERMENTED_SPIDER_EYE, STRONG_MANA_DRAIN_POTION);
    }

    private static void registerEnchantments() {
        for (Spell spell : Spell.values()) {
            Registry.register(Registries.ENCHANTMENT, spell.getId(), spell.getEnchantment());
            Registry.register(Registries.CUSTOM_STAT, spell.getName(), spell.getId());
            Stats.CUSTOM.getOrCreateStat(spell.getId(), StatFormatter.DEFAULT);
        }
    }

    private static void registerCreativeMenu() {
        ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP).register(MagicContent::registerItemGroup);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(MagicContent::registerCombat);
    }

    private static void registerEntityTypes() {
        for (Spell spell : Spell.values()) {
            if (spell.isProjectile()) {
                Registry.register(Registries.ENTITY_TYPE, spell.getId(), spell.getEntityType());
            }
        }
    }

    private static void registerItemGroup(FabricItemGroupEntries entries) {
        entries.add(BEGINNER_WAND);
        entries.add(NOVICE_WAND);
        entries.add(EXPERT_WAND);
        entries.add(MASTER_WAND);

        ItemStack potion = new ItemStack(Items.POTION);
        PotionUtil.setPotion(potion, MANA_POTION);
        entries.add(potion);
        potion = new ItemStack(Items.POTION);
        PotionUtil.setPotion(potion, STRONG_MANA_POTION);
        entries.add(potion);
        potion = new ItemStack(Items.POTION);
        PotionUtil.setPotion(potion, MANA_DRAIN_POTION);
        entries.add(potion);
        potion = new ItemStack(Items.POTION);
        PotionUtil.setPotion(potion, STRONG_MANA_DRAIN_POTION);
        entries.add(potion);
        potion = new ItemStack(Items.SPLASH_POTION);
        PotionUtil.setPotion(potion, MANA_POTION);
        entries.add(potion);
        potion = new ItemStack(Items.SPLASH_POTION);
        PotionUtil.setPotion(potion, STRONG_MANA_POTION);
        entries.add(potion);
        potion = new ItemStack(Items.SPLASH_POTION);
        PotionUtil.setPotion(potion, MANA_DRAIN_POTION);
        entries.add(potion);
        potion = new ItemStack(Items.SPLASH_POTION);
        PotionUtil.setPotion(potion, STRONG_MANA_DRAIN_POTION);
        entries.add(potion);
        potion = new ItemStack(Items.LINGERING_POTION);
        PotionUtil.setPotion(potion, MANA_POTION);
        entries.add(potion);
        potion = new ItemStack(Items.LINGERING_POTION);
        PotionUtil.setPotion(potion, STRONG_MANA_POTION);
        entries.add(potion);
        potion = new ItemStack(Items.LINGERING_POTION);
        PotionUtil.setPotion(potion, MANA_DRAIN_POTION);
        entries.add(potion);
        potion = new ItemStack(Items.LINGERING_POTION);
        PotionUtil.setPotion(potion, STRONG_MANA_DRAIN_POTION);
        entries.add(potion);

        ItemStack arrow = new ItemStack(Items.TIPPED_ARROW);
        PotionUtil.setPotion(arrow, MANA_POTION);
        entries.add(arrow);
        arrow = new ItemStack(Items.TIPPED_ARROW);
        PotionUtil.setPotion(arrow, STRONG_MANA_POTION);
        entries.add(arrow);
        arrow = new ItemStack(Items.TIPPED_ARROW);
        PotionUtil.setPotion(arrow, MANA_DRAIN_POTION);
        entries.add(arrow);
        arrow = new ItemStack(Items.TIPPED_ARROW);
        PotionUtil.setPotion(arrow, STRONG_MANA_DRAIN_POTION);
        entries.add(arrow);

        for (Spell spell : Spell.values()) {
            entries.add(spell.asEnchantedBook());
        }
    }

    private static void registerCombat(FabricItemGroupEntries entries) {
        entries.addBefore(Items.SHIELD,
                BEGINNER_WAND,
                NOVICE_WAND,
                EXPERT_WAND,
                MASTER_WAND);
    }

}

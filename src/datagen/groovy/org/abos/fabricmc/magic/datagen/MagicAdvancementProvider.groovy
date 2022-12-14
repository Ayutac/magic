package org.abos.fabricmc.magic.datagen

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider
import net.minecraft.advancement.*
import net.minecraft.advancement.criterion.InventoryChangedCriterion
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.predicate.NumberRange
import net.minecraft.predicate.item.EnchantmentPredicate
import net.minecraft.predicate.item.ItemPredicate
import net.minecraft.registry.RegistryWrapper
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import org.abos.fabricmc.magic.Magic
import org.abos.fabricmc.magic.Spell
import org.abos.fabricmc.magic.items.Wand

import java.util.concurrent.CompletableFuture
import java.util.function.Consumer

class MagicAdvancementProvider extends FabricAdvancementProvider {

    protected MagicAdvancementProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output)
    }

    @Override
    void generateAdvancement(Consumer<Advancement> consumer) {
        Advancement.Builder builder

        var rootAdv = Advancement.Builder.create()
                .display(getDisplay(Items.LAPIS_LAZULI, "root", AdvancementFrame.TASK, new Identifier("textures/block/bookshelf.png")))
                .rewards(AdvancementRewards.NONE)
                .criterion("has_lapis", InventoryChangedCriterion.Conditions.items(Items.LAPIS_LAZULI))
                .build(consumer, Magic.MOD_ID + ":root")

        var anyWand = Advancement.Builder.create()
                .display(getDisplay(Wand.BEGINNER.asItem(), "any_wand", AdvancementFrame.TASK))
                .parent(rootAdv)
                .rewards(AdvancementRewards.NONE)
                .criterion("has_any_wand", InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().items(Wand.values()).build()))
                .criteriaMerger(CriterionMerger.OR).build(consumer, Magic.MOD_ID + ":any_wand")

        builder = Advancement.Builder.create()
                .display(getDisplay(Wand.MASTER.asItem(), "all_wands", AdvancementFrame.CHALLENGE))
                .parent(anyWand)
                .rewards(AdvancementRewards.Builder.experience(500).build())
        for (Wand wand : Wand.values()) {
            builder.criterion("has_"+wand.getName(), InventoryChangedCriterion.Conditions.items(
                    ItemPredicate.Builder.create().items(wand).build()
            ))
        }
        builder.criteriaMerger(CriterionMerger.AND)
        builder.build(consumer, Magic.MOD_ID + ":all_wands")

        builder = Advancement.Builder.create()
                .display(getDisplay(Items.ENCHANTED_BOOK, "any_spell", AdvancementFrame.TASK))
                .parent(rootAdv)
                .rewards(AdvancementRewards.NONE)
        for (Spell spell : Spell.values()) {
            builder.criterion("has_"+spell.getName(), InventoryChangedCriterion.Conditions.items(
                    ItemPredicate.Builder.create().items(Items.ENCHANTED_BOOK).storedEnchantment(new EnchantmentPredicate(spell.getEnchantment(), NumberRange.IntRange.ANY)).build()
            ))
        }
        builder.criteriaMerger(CriterionMerger.OR)
        var anySpell = builder.build(consumer, Magic.MOD_ID + ":any_spell")

        builder = Advancement.Builder.create()
                .display(getDisplay(Items.BOOKSHELF, "all_spells", AdvancementFrame.CHALLENGE))
                .parent(anySpell)
                .rewards(AdvancementRewards.Builder.experience(500).build())
        for (Spell spell : Spell.values()) {
            builder.criterion("has_"+spell.getName(), InventoryChangedCriterion.Conditions.items(
                    ItemPredicate.Builder.create().items(Items.ENCHANTED_BOOK).storedEnchantment(new EnchantmentPredicate(spell.getEnchantment(), NumberRange.IntRange.ANY)).build()
            ))
        }
        builder.criteriaMerger(CriterionMerger.AND)
        builder.build(consumer, Magic.MOD_ID + ":all_spells")
    }

    static AdvancementDisplay getDisplay(Item icon, String name, AdvancementFrame frame, Identifier background = null) {
        return new AdvancementDisplay(
                icon.getDefaultStack(),
                Text.translatable("advancements.magic."+name),
                Text.translatable("advancements.magic."+name+".desc"),
                background,
                frame,
                true, // show toast
                true, // announce to chat
                false) // hidden
    }
}

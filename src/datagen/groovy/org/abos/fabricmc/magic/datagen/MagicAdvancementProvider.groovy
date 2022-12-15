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
                .display(getDisplay(Items.BOOKSHELF, "root", AdvancementFrame.TASK, new Identifier("bookshelf")))
                .rewards(AdvancementRewards.NONE)
                .criterion("has_lapis", InventoryChangedCriterion.Conditions.items(Items.LAPIS_LAZULI))
                .build(consumer, Magic.MOD_ID + ":root")

        builder = Advancement.Builder.create()
                .display(getDisplay(Items.BOOKSHELF, "any_spell", AdvancementFrame.TASK))
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

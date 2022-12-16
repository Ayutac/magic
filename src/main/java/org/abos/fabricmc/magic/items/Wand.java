package org.abos.fabricmc.magic.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Identifier;
import org.abos.fabricmc.magic.Magic;
import org.abos.fabricmc.magic.MagicConfig;

import java.util.Locale;

public enum Wand implements ItemConvertible {

    BEGINNER(ToolMaterials.STONE, MagicConfig.BEGINNER_MANA_FACTOR),
    NOVICE(ToolMaterials.IRON, MagicConfig.NOVICE_MANA_FACTOR),
    EXPERT(ToolMaterials.DIAMOND, MagicConfig.EXPERT_MANA_FACTOR),
    MASTER(ToolMaterials.NETHERITE, MagicConfig.MASTER_MANA_FACTOR);

    private final WandItem item;
    private final Identifier id = new Identifier(Magic.MOD_ID, getName());

    Wand(ToolMaterials material, double manaFactor) {
        item = new WandItem(material, manaFactor, new FabricItemSettings());
    }

    @Override
    public Item asItem() {
        return item;
    }

    public Identifier getId() {
        return id;
    }

    public String getName() {
        return name().toLowerCase(Locale.ROOT)+"_wand";
    }
}

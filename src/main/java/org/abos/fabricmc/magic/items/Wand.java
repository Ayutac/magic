package org.abos.fabricmc.magic.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Identifier;
import org.abos.fabricmc.magic.Magic;
import org.abos.fabricmc.magic.config.Config;
import org.abos.fabricmc.magic.config.IntConfigProperty;
import org.abos.fabricmc.magic.config.PercentageConfigProperty;

import java.util.Locale;

public enum Wand implements ItemConvertible {

    BEGINNER(ToolMaterials.STONE, Magic.CONFIG.getBeginnerManaFactor()),
    NOVICE(ToolMaterials.IRON, Magic.CONFIG.getNoviceManaFactor()),
    EXPERT(ToolMaterials.DIAMOND, Magic.CONFIG.getExpertManaFactor()),
    MASTER(ToolMaterials.NETHERITE, Magic.CONFIG.getMasterManaFactor());

    private final WandItem item;
    private final Identifier id = new Identifier(Magic.MOD_ID, getName());

    Wand(ToolMaterials material, PercentageConfigProperty manaFactor) {
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

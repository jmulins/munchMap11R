package me.jmulins.munchymap11r.gui;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gg.essential.elementa.ElementaVersion;
import gg.essential.elementa.UIComponent;
import gg.essential.elementa.WindowScreen;
import gg.essential.elementa.components.UIBlock;
import gg.essential.elementa.components.UIText;
import gg.essential.elementa.components.UIWrappedText;
import gg.essential.elementa.constraints.CenterConstraint;
import gg.essential.elementa.constraints.PixelConstraint;
import gg.essential.elementa.constraints.RelativeConstraint;
import gg.essential.elementa.effects.OutlineEffect;

import java.awt.*;

public class SingleItemGuideGui extends WindowScreen {
    private JsonObject item;
    private Color borderColor = Color.WHITE;
    public SingleItemGuideGui(JsonObject item) {
        super(ElementaVersion.V10);

        this.item = item;
        System.out.println(item.get("rarity").getAsString());
        switch (item.get("rarity").getAsString()) {
            case "Consumable" -> borderColor = new Color(132, 172, 177);
            case "Semi Rare" -> borderColor = new Color(63, 63, 254);
            case "Epic Rare" -> borderColor = new Color(190, 0, 190);
            case "Ultra Rare", "Ultra Legendary Rare" -> borderColor = new Color(190, 0, 0);
            case "Legendary Rare" -> borderColor = new Color(254,254, 63);
            case "Special Rare", "Fishing Special" -> borderColor = new Color(63, 254, 254);
            case "Mythical Rare" -> borderColor = new Color(254, 63, 254);
            case "Extinct Rare" -> borderColor = Color.WHITE;
            case "Fabled" -> borderColor = new Color(0, 190, 0);
            case "Creature Rare" -> borderColor = new Color(0, 190, 190);
            case "Vote Rare" -> borderColor = Color.BLACK;
        }

        UIComponent containerBox = new UIBlock()
                .setX(new CenterConstraint())
                .setY(new CenterConstraint())
                .setWidth(new RelativeConstraint(0.9f))
                .setHeight(new RelativeConstraint(0.9f))
                .setColor(new Color(0,0,0, 200))
                .enableEffect(new OutlineEffect(borderColor, 2f))
                .setChildOf(getWindow());

        UIText itemName = (UIText) new UIText(item.get("name").getAsString())
                .setX(new CenterConstraint())
                .setY(new PixelConstraint(5f))
                .setWidth(new RelativeConstraint(0.5f))
                .setHeight(new RelativeConstraint(0.1f))
                .setChildOf(containerBox);

        UIWrappedText itemRarity = (UIWrappedText) new UIWrappedText(item.get("rarity").getAsString())
                .setX(new PixelConstraint(5f))
                .setY(new RelativeConstraint(0.15f))
                .setWidth(new RelativeConstraint(1f))
                .setHeight(new RelativeConstraint(0.049f))
                .setColor(borderColor)
                .setChildOf(containerBox);

        UIWrappedText itemUse = (UIWrappedText) new UIWrappedText(item.get("use").getAsString())
                .setX(new PixelConstraint(5f))
                .setY(new RelativeConstraint(0.2f))
                .setWidth(new RelativeConstraint(1f))
                .setHeight(new RelativeConstraint(0.049f))
                .setChildOf(containerBox);

        String itemWorldStr = item.get("world").getAsString();
        if (!itemWorldStr.isEmpty()) {
            UIWrappedText itemWorld = (UIWrappedText) new UIWrappedText("World : " + itemWorldStr)
                    .setX(new PixelConstraint(5f))
                    .setY(new RelativeConstraint(0.25f))
                    .setWidth(new RelativeConstraint(1f))
                    .setHeight(new RelativeConstraint(0.049f))
                    .setChildOf(containerBox);
        }

        JsonObject abilitiesJsonObject = item.get("abilities").getAsJsonObject();

        float minParagraphStart = 0.3f;
        float spaceBetweenAbility = (0.9f-minParagraphStart)/(float) abilitiesJsonObject.size();
        float currentSpacingAbility = spaceBetweenAbility;

        for(JsonElement abilityJson : abilitiesJsonObject.asMap().values()) {
            JsonObject abilityJsonObject = abilityJson.getAsJsonObject();
            UIWrappedText abilityText = (UIWrappedText) new UIWrappedText(abilityJsonObject.get("description").getAsString())
                    .setX(new PixelConstraint(3f))
                    .setY(new RelativeConstraint(minParagraphStart + currentSpacingAbility))
                    .setWidth(new RelativeConstraint(1f))
                    .setHeight(new RelativeConstraint(spaceBetweenAbility-0.1f))
                    .setChildOf(containerBox);
            currentSpacingAbility+= spaceBetweenAbility;
        }

    }




}

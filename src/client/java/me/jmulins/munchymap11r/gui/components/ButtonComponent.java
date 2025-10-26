package me.jmulins.munchymap11r.gui.components;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.components.UIBlock;
import gg.essential.elementa.components.UIText;
import gg.essential.elementa.constraints.CenterConstraint;
import gg.essential.elementa.constraints.PixelConstraint;
import gg.essential.elementa.effects.OutlineEffect;

import java.awt.*;

public class ButtonComponent extends UIComponent {

    private UIBlock buttonBackground;
    private UIText buttonText;
    private OutlineEffect outlineSelected;
    private OutlineEffect outlineUnselected;


    public ButtonComponent(String text) {
        super();

        outlineSelected = new OutlineEffect(Color.WHITE, 1f);
        outlineUnselected = new OutlineEffect(Color.DARK_GRAY, 1f);

        buttonBackground = (UIBlock) new UIBlock()
                .setHeight(new PixelConstraint(10f))
                .setWidth(new PixelConstraint(50f))
                .setColor(Color.GRAY)
                .enableEffect(outlineUnselected)
                .setChildOf(this);

        buttonText = (UIText) new UIText(text)
                .setX(new CenterConstraint())
                .setY(new CenterConstraint())
                .setChildOf(buttonBackground);



        buttonBackground.onMouseEnter(uiComponent -> {
            uiComponent.removeEffect(outlineUnselected);
            uiComponent.enableEffect(outlineSelected);
           return null;
        });

        buttonBackground.onMouseLeave(uiComponent -> {
            uiComponent.removeEffect(outlineSelected);
            uiComponent.enableEffect(outlineUnselected);
            return null;
        });

    }
}

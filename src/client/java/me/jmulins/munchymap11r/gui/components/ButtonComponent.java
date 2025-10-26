package me.jmulins.munchymap11r.gui.components;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.components.UIBlock;
import gg.essential.elementa.components.UIText;
import gg.essential.elementa.constraints.CenterConstraint;
import gg.essential.elementa.constraints.ChildBasedMaxSizeConstraint;
import gg.essential.elementa.constraints.PixelConstraint;
import gg.essential.elementa.constraints.RelativeConstraint;
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

        buttonText = (UIText) new UIText(text)
                .setX(new CenterConstraint())
                .setY(new CenterConstraint());

        this.setHeight(new PixelConstraint(10f))
                .setWidth(new PixelConstraint(buttonText.getWidth()+5f));
//                .enableEffect(new OutlineEffect(Color.BLUE, 2f));

        buttonBackground = (UIBlock) new UIBlock()
                .setHeight(new RelativeConstraint(1f))
                .setWidth(new RelativeConstraint(1f))
                .setColor(Color.GRAY)
                .enableEffect(outlineUnselected)
//                .enableEffect(new OutlineEffect(Color.GREEN, 2f))
                .setChildOf(this);

        buttonText.setChildOf(buttonBackground);


//        buttonBackground.setWidth(new PixelConstraint(buttonText.getWidth()+5f));


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

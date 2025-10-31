package me.jmulins.munchymap11r.gui.components;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.components.UIBlock;
import gg.essential.elementa.components.UIContainer;
import gg.essential.elementa.components.UIText;
import gg.essential.elementa.constraints.CenterConstraint;
import gg.essential.elementa.constraints.ChildBasedMaxSizeConstraint;
import gg.essential.elementa.constraints.PixelConstraint;
import gg.essential.elementa.constraints.RelativeConstraint;
import gg.essential.elementa.effects.OutlineEffect;

import javax.swing.plaf.PanelUI;
import java.awt.*;

public class ButtonComponent extends UIContainer  {

    private UIBlock buttonBackground;
    private UIText buttonText;
    private OutlineEffect outlineSelected;
    private OutlineEffect outlineUnselected;

    private static final float CHAR_WIDTH = 6f;
    private static final float PADDING = 10f;


    public ButtonComponent(String text) {
        super();

        outlineSelected = new OutlineEffect(Color.WHITE, 1f);
        outlineUnselected = new OutlineEffect(Color.DARK_GRAY, 1f);

        buttonText = (UIText) new UIText(text)
                .setX(new CenterConstraint())
                .setY(new CenterConstraint());

        this.setHeight(new PixelConstraint(10f))
                .setWidth(new PixelConstraint(calculateWidth(text)));
//                .setWidth(new PixelConstraint(buttonText.getWidth()+5f));

        buttonBackground = (UIBlock) new UIBlock()
                .setHeight(new RelativeConstraint(1f))
                .setWidth(new RelativeConstraint(1f))
                .setColor(Color.GRAY)
                .enableEffect(outlineUnselected)
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

    public void setText(String text){
        buttonText.setText(text);
        this.setWidth(new PixelConstraint(calculateWidth(text)));
    }

    private float calculateWidth(String text) {
        return text.length() * CHAR_WIDTH + PADDING;
    }
}

package me.jmulins.munchymap11r.gui;

import gg.essential.elementa.ElementaVersion;
import gg.essential.elementa.UIComponent;
import gg.essential.elementa.WindowScreen;
import gg.essential.elementa.components.ScrollComponent;
import gg.essential.elementa.components.UIBlock;
import gg.essential.elementa.constraints.*;
import gg.essential.elementa.dsl.ConstraintsKt;
import me.jmulins.munchymap11r.config.PhelperConfig;
import me.jmulins.munchymap11r.config.PhelperWaypointConfig;
import me.jmulins.munchymap11r.gui.components.ButtonComponent;
import me.jmulins.munchymap11r.gui.components.WaypointCategoryComponent;
import me.jmulins.munchymap11r.gui.components.WaypointComponent;
import me.jmulins.munchymap11r.models.WaypointCategoryModel;
import me.jmulins.munchymap11r.models.WaypointModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WaypointsGui extends WindowScreen {

    static final Color BORDERS_COLOR = Color.ORANGE;
    private List<WaypointCategoryComponent> categories = new ArrayList<>();


    ButtonComponent addCategoryButton = (ButtonComponent) new ButtonComponent("New category")
            .setX(new RelativeConstraint(0.3f))
            .setY(new RelativeConstraint(0.08f))
            .setChildOf(getWindow());


    UIComponent waypointsListBox = new UIBlock()
            .setX(new CenterConstraint())
            .setY(new CenterConstraint())
            .setWidth(new RelativeConstraint(0.75f))
            .setHeight(new RelativeConstraint(0.75f))
            .setColor(new Color(0,0,0, 100))
            .setChildOf(getWindow());

    UIComponent waypointsList = new ScrollComponent("", 4f)
            .setX(new CenterConstraint())
            .setY(new CenterConstraint())
            .setWidth(new RelativeConstraint(1f))
            .setHeight(new RelativeConstraint(1f))
            .setChildOf(waypointsListBox);



    ButtonComponent saveAndExitButton = (ButtonComponent) new ButtonComponent("Save and exit")
            .setY(new RelativeConstraint(0.9f));



    public WaypointsGui() {
        super(ElementaVersion.V10);

        saveAndExitButton.setX(ConstraintsKt.minus(new RelativeConstraint(0.5f), new PixelConstraint(saveAndExitButton.getWidth()/2)))
                .setChildOf(getWindow());



        loadWaypointsFromData();


        addCategoryButton.onMouseClick((uiComponent, uiClickEvent) -> {
            WaypointCategoryComponent categoryToAdd = new WaypointCategoryComponent("New category");
            categoryToAdd.setChildOf(waypointsList);
            categories.add(categoryToAdd);

            // Set remove callback for category
            categoryToAdd.setOnRemove(() -> {
                removeCategory(categoryToAdd);
            });

            return null;
        });





        saveAndExitButton.onMouseClick((uiComponent, uiClickEvent) -> {
            PhelperWaypointConfig.INSTANCE.waypointCategories.clear();

            for (WaypointCategoryComponent category : categories) {
                WaypointCategoryModel model = new WaypointCategoryModel(category);
//                System.out.println("Saving category: " + model.name + " with " + model.waypoints.size() + " waypoints");
                for (WaypointModel wp : model.waypoints) {
//                    System.out.println("  - " + wp.name + " @ " + wp.x + "," + wp.y + "," + wp.z);
                }
                PhelperWaypointConfig.INSTANCE.waypointCategories.add(model);
            }

            System.out.println("Total categories saved: " + PhelperWaypointConfig.INSTANCE.waypointCategories.size());
            PhelperWaypointConfig.INSTANCE.save();
            close();
            return null;
        });



    }

    private void removeCategory(WaypointCategoryComponent category) {
        categories.remove(category);
        waypointsList.removeChild(category);
//        System.out.println("Removed category. Remaining: " + categories.size());
    }


    private void loadWaypointsFromData() {
        categories.clear();

        for (UIComponent child : new ArrayList<>(waypointsList.getChildren())) {
            waypointsList.removeChild(child);
        }

//        System.out.println("Loading " + PhelperWaypointConfig.INSTANCE.waypointCategories.size() + " categories");

        for (WaypointCategoryModel categoryData : PhelperWaypointConfig.INSTANCE.waypointCategories) {
            WaypointCategoryComponent categoryUI = createCategoryUI(categoryData);
            categoryUI.setChildOf(waypointsList);
            categories.add(categoryUI);

            // Set remove callback for loaded categories
            categoryUI.setOnRemove(() -> {
                removeCategory(categoryUI);
            });

            // Handle collapsed or not
            System.out.println("collapsed : " + categoryData.collapsed);
            if (categoryData.collapsed){
                System.out.println("collapse initial category");
                categoryUI.collapseCategory();
            }

            System.out.println("Loaded category: " + categoryData.name + " with " + categoryData.waypoints.size() + " waypoints");
        }
    }

    private WaypointCategoryComponent createCategoryUI(WaypointCategoryModel data) {
        WaypointCategoryComponent category = new WaypointCategoryComponent(data.name, Color.ORANGE);

        for (WaypointModel waypointData : data.waypoints) {
            category.addWaypoint(
                    waypointData.name,
                    waypointData.x,
                    waypointData.y,
                    waypointData.z,
                    Color.GREEN
            );
        }

        category.setData(data.uuid);
        return category;
    }

}
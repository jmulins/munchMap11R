package me.jmulins.munchymap11r.gui.components;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.components.UIContainer;
import gg.essential.elementa.components.input.UITextInput;
import gg.essential.elementa.constraints.*;
import gg.essential.elementa.dsl.ConstraintsKt;
import gg.essential.elementa.effects.OutlineEffect;
import me.jmulins.munchymap11r.config.PhelperWaypointConfig;
import me.jmulins.munchymap11r.models.WaypointCategoryModel;
import me.jmulins.munchymap11r.models.WaypointModel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Reusable waypoint category component that can hold multiple waypoints
 */
public class WaypointCategoryComponent extends UIContainer {

    public String getCategoryName() {
        return currentCategoryName;
    }

    public List<WaypointComponent> getWaypoints() {
        return waypoints;
    }

    private final String categoryName;
    private final List<WaypointComponent> waypoints = new ArrayList<>();
    private UITextInput titleText;


    private boolean collapsed = false;
    private UIContainer headerContainer, waypointsContainer;
    private ButtonComponent collapseButton, removeButton, newWaypointButton;

    private Runnable onRemoveCallback;

    private String currentCategoryName;

    public UUID getUuid() {
        return uuid;
    }

    public boolean isCollapsed() {
        return collapsed;
    }


    private UUID uuid = UUID.randomUUID();

    static final float BASE_PADDING = 5f;

    public WaypointCategoryComponent(String categoryName) {
        this(categoryName, Color.ORANGE);
    }

    public WaypointCategoryComponent(String categoryName, Color borderColor) {
        super();

        this.categoryName = categoryName;
        this.currentCategoryName = categoryName;

        // Configure the container
        this.setX(new CenterConstraint());
        this.setY(new SiblingConstraint(BASE_PADDING));
        this.setWidth(new RelativeConstraint(0.9f));
        this.setHeight(new ChildBasedSizeConstraint());
        this.setColor(new ConstantColorConstraint(new Color(0, 0, 0, 100)));
        this.enableEffect(new OutlineEffect(borderColor, 2f));

        headerContainer = (UIContainer) new UIContainer()
                .setX(new PixelConstraint(0f))
                .setY(new PixelConstraint(0f))
                .setWidth(new RelativeConstraint(1f))
                .setHeight(new PixelConstraint(BASE_PADDING*4f))
                .enableEffect(new OutlineEffect(borderColor, 2f))
                .setChildOf(this);

        titleText = (UITextInput) new UITextInput(categoryName)
                .setX(new PixelConstraint(BASE_PADDING))
                .setY(new  CenterConstraint())
                .setWidth(new RelativeConstraint(0.4f))
                .setChildOf(headerContainer);


        titleText.onUpdate(newText -> {
            currentCategoryName = newText;
            return null;
        });

        collapseButton = (ButtonComponent) new ButtonComponent("Collapse")
                .setY(new CenterConstraint());

        removeButton = (ButtonComponent) new ButtonComponent("Remove")
                .setY(new CenterConstraint());

        removeButton.onMouseClick((comp, event) -> {
            if (onRemoveCallback != null) {
                onRemoveCallback.run();
            }
            return null;
        });

        newWaypointButton = (ButtonComponent) new ButtonComponent("New waypoint")
                .setY(new CenterConstraint());




        collapseButton
                .setX(ConstraintsKt.minus(new RelativeConstraint(1f),new PixelConstraint(collapseButton.getWidth()+BASE_PADDING)))
                .setChildOf(headerContainer);

        removeButton
                .setX(ConstraintsKt.minus(new RelativeConstraint(1f),new PixelConstraint(collapseButton.getWidth() + removeButton.getWidth()+BASE_PADDING*2f)))
                .setChildOf(headerContainer);

        newWaypointButton
                .setX(ConstraintsKt.minus(new RelativeConstraint(1f),new PixelConstraint(collapseButton.getWidth() + removeButton.getWidth() + newWaypointButton.getWidth()+BASE_PADDING*3f)))
                .setChildOf(headerContainer);


        waypointsContainer = (UIContainer) new UIContainer()
                .setX(new PixelConstraint(0f))
                .setY(new PixelConstraint((BASE_PADDING*4f)+4f))
                .setWidth(new RelativeConstraint(1f))
                .setHeight(new PixelConstraint(33f))
                .setChildOf(this);



        titleText.onMouseClick((comp, event) -> {
            comp.grabWindowFocus();
            return null;
        });



        newWaypointButton.onMouseClick((comp, event) -> {
            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            assert player != null;
            this.addWaypoint("New waypoint", player.getBlockX(), player.getBlockY(), player.getBlockZ(), Color.GREEN);

            return null;
        });

        collapseButton.onMouseClick((comp, event) -> {
            if (collapsed){
                uncollapseCategory();
            } else {
                collapseCategory();
            }
            return null;
        });






    }


    private void updateContainerHeight() {
        if (waypoints.isEmpty()) {
            waypointsContainer.setHeight(new PixelConstraint(33f));
        } else if (waypoints.size() == 1) {
            waypointsContainer.setHeight(new PixelConstraint(waypoints.get(0).getHeight() + 16f));
        } else {
            waypointsContainer.setHeight(new PixelConstraint((waypoints.size() * 30f) + 11f));
        }
    }

    /**
     * Set callback to be called when remove button is clicked
     */
    public void setOnRemove(Runnable callback) {
        this.onRemoveCallback = callback;
    }


    /**
     * Add a waypoint with custom color
     */
    public WaypointCategoryComponent addWaypoint(String waypointName, int x, int y, int z, Color color) {
        WaypointComponent waypoint = new WaypointComponent(waypointName, x, y, z, color);
        waypoint.setChildOf(waypointsContainer);
        waypoints.add(waypoint);

        waypoint.setOnRemove(() -> {
            removeWaypoint(waypoint);
        });

        updateContainerHeight();
        return this;


    }




    public void collapseCategory(){

        for (UIComponent waypointComp : waypointsContainer.getChildren()) {
            waypointsContainer.removeChild(waypointComp);
        }

        waypointsContainer
                .setHeight(new PixelConstraint(0f));
        collapseButton.setText("Uncollapse");
        realignHeaderButtons();
        collapsed = true;
    }

    public void uncollapseCategory(){
        List<WaypointModel> waypointModels = PhelperWaypointConfig.INSTANCE.findCategory(getUuid().toString()).waypoints;

        for (WaypointModel waypointModel : waypointModels) {
            WaypointComponent waypoint = new WaypointComponent(waypointModel.name, waypointModel.x, waypointModel.y, waypointModel.z, Color.GREEN);
            waypoint.setChildOf(waypointsContainer);

            waypoint.setOnRemove(() -> {
                removeWaypoint(waypoint);
            });

            updateContainerHeight();
        }

        collapseButton.setText("Collapse");
        realignHeaderButtons();
        collapsed = false;
    }

    public void realignHeaderButtons(){
        collapseButton
                .setX(ConstraintsKt.minus(new RelativeConstraint(1f),new PixelConstraint(collapseButton.getWidth()+BASE_PADDING)));

        removeButton
                .setX(ConstraintsKt.minus(new RelativeConstraint(1f),new PixelConstraint(collapseButton.getWidth() + removeButton.getWidth()+BASE_PADDING*2f)));

        newWaypointButton
                .setX(ConstraintsKt.minus(new RelativeConstraint(1f),new PixelConstraint(collapseButton.getWidth() + removeButton.getWidth() + newWaypointButton.getWidth()+BASE_PADDING*3f)));
    }

    private void removeWaypoint(WaypointComponent waypoint) {
        waypoints.remove(waypoint);
        waypointsContainer.removeChild(waypoint);
        updateContainerHeight();
    }


    public WaypointCategoryComponent addWaypoint(WaypointComponent waypoint) {
        addWaypoint(waypoint.getWaypointName(), waypoint.getWaypointX(), waypoint.getWaypointY(), waypoint.getWaypointZ(), Color.ORANGE);
        return this;
    }


    public void setData(String uuid){
        this.uuid = UUID.fromString(uuid);
    }
    }
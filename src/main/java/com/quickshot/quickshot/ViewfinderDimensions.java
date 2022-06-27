package com.quickshot.quickshot;

import javafx.geometry.Insets;
import javafx.scene.CacheHint;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ViewfinderDimensions extends HBox implements DisplayElement {
    private ViewfinderBoundingBox boundingBox;
    private Text text = new Text();

    public ViewfinderDimensions() {
        text.setFill(Color.WHITE);
        text.setStyle("-fx-font-weight: bold");
        setCache(true);
        setCacheHint(CacheHint.SPEED);
        getChildren().add(text);
        setStyle("-fx-background-color: black; -fx-border-radius: 3px;");
        setPadding(new Insets(1,3,3,3));
        setViewOrder(ViewfinderViewOrder.DIMENSIONS);
    }

    public ViewfinderDimensions(ViewfinderBoundingBox boundingBox) {
        this();
        setBoundingBox(boundingBox);
        updateDimensions();
    }

    private void updateDimensions() {
        text.setText(
                "(" + (int)boundingBox.getWidth() + ", " + (int)boundingBox.getHeight() + ")"
        );
    }

    private void updateLocation() {
        double Y_PADDING;
        double X_PADDING;
        int ADDITIONAL_OFFSET = 6;
        // checks if viewfinder is too close to top, then swaps the dimensions to be inside the viewfinder
        // else the dimensions sit ontop of the viewfinder
        if (boundingBox.getTopMiddle().getY() < getHeight() + ADDITIONAL_OFFSET) {
            Y_PADDING = getHeight() + 5;
            X_PADDING = 5;
        } else {
            Y_PADDING = -5;
            X_PADDING = 0;
        }
        setTranslateY(boundingBox.getTopLeft().getY() - getHeight() + Y_PADDING);
        setTranslateX(boundingBox.getTopLeft().getX() + X_PADDING);
    }

    @Override
    public void update() {
        updateLocation();
        updateDimensions();
    }

    public void setBoundingBox(ViewfinderBoundingBox boundingBox) {
        this.boundingBox = boundingBox;
    }
}

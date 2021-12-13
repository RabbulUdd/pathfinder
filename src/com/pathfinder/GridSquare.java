package com.pathfinder;

import javax.swing.*;
import java.awt.*;

// I have used the code in this link and have changed it to fit my application.
// https://stackoverflow.com/questions/14627223/how-to-change-a-jbutton-color-on-mouse-pressed

public class GridSquare extends JButton {
    private Color hoverBackgroundColor;
    private Color pressedBackgroundColor;
    private boolean isSet;
    private boolean isWall;
    private Coordinate coordinate;
    private int x;
    private int y;

    public GridSquare() {
        this(null);
    }

    public GridSquare(String text) {
        super(text);
        super.setContentAreaFilled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isPressed()) {
            g.setColor(pressedBackgroundColor);
        } else if (getModel().isRollover()) {
            g.setColor(hoverBackgroundColor);
        } else {
            g.setColor(getBackground());
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

    @Override
    public void setContentAreaFilled(boolean b) {
    }

    public void setVisited() {
        setBackground(Color.WHITE);
    }

    public Color getHoverBackgroundColor() {
        return hoverBackgroundColor;
    }

    public void setHoverBackgroundColor(Color hoverBackgroundColor) {
        this.hoverBackgroundColor = hoverBackgroundColor;
    }

    public Color getPressedBackgroundColor() {
        return pressedBackgroundColor;
    }

    public void setPressedBackgroundColor(Color pressedBackgroundColor) {
        this.pressedBackgroundColor = pressedBackgroundColor;
    }

    public boolean isWall() {
        return isWall;
    }

    public void setWall() {
        this.isWall = !this.isWall;
    }

    public boolean isSet() {
        return isSet;
    }

    public void setSet() {
        this.isSet = !this.isSet;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public String getCoordinateText() {
        return String.valueOf(coordinate.getX()) + String.valueOf(coordinate.getY());
    }

    public int getXCoordinate() {
        return this.coordinate.getX();
    }

    public int getYCoordinate() {
        return this.coordinate.getY();
    }
}
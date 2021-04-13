package com.pathfinder;

import javax.swing.*;
import java.awt.*;

// https://stackoverflow.com/questions/14627223/how-to-change-a-jbutton-color-on-mouse-pressed

public class GridButton extends JButton {
    private Color hoverBackgroundColor;
    private Color pressedBackgroundColor;
    private boolean isPressed;

    public GridButton() {
        this(null);
    }

    public GridButton(String text) {
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

    public boolean isPressed() {
        return isPressed;
    }

    public void setPressed() {
        isPressed = true;
    }
}

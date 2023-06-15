package model;

import model.exceptions.IllegalTypeException;
import org.json.JSONObject;
import persistence.Writable;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Clothing implements Writable {
    private String type;
    private String name;
    protected int xpos;
    protected int ypos;
    protected int width;
    protected int height;

    private static Color SELECTED_COLOR;

    private boolean selected;

    // EFFECTS: clothing has given type, colour, name, and conditions
    public Clothing(String type, String name) throws IllegalTypeException {
        try {
            checkArrayContains(type);
        } catch (IllegalTypeException e) {
            throw e;
        }
        this.type = type;
        this.name = name;
        this.width = 75;
        this.height = 25;
        SELECTED_COLOR = new Color(135, 206, 235);
        selected = false;
    }

    public void checkArrayContains(String type) throws IllegalTypeException {
        String[] acceptedTypes = new String[] {"shirt", "sweater", "jacket", "pants", "shoes"};
        IllegalTypeException wrongType = new IllegalTypeException("Enter a correct clothing type");
        if (!Arrays.asList(acceptedTypes).contains(type)) {
            throw wrongType;
        }
    }

    public void setPosition(int xpos, int ypos) {
        this.xpos = xpos;
        this.ypos = ypos;
    }

    // EFFECTS: return true iff the given x value is within the bounds of the Shape
    public boolean containsX(int x) {
        return (this.xpos <= x) && (x <= this.xpos + width);
    }

    // EFFECTS: return true iff the given y value is within the bounds of the Shape
    public boolean containsY(int y) {
        return (this.ypos <= y) && (y <= this.ypos + height);
    }

    // EFFECTS: return true if the given Point (x,y) is contained within the bounds of this Shape
    public boolean contains(Point point) {
        int pointX = point.x;
        int pointY = point.y;

        return containsX(pointX) && containsY(pointY);
    }

    public void draw(Graphics g) {
        Color save = g.getColor();
        if (selected) {
            g.setColor(SELECTED_COLOR);
        } else {
            g.setColor(Color.white);
        }
        fillGraphics(g);
        g.setColor(save);
        drawGraphics(g);
    }

    //EFFECTS: draws the shape
    protected void drawGraphics(Graphics g) {
        g.drawRect(xpos, ypos, width, height);
        g.drawString(this.name, xpos + 1, ypos + 15);
    }

    //EFFECTS: fills the shape
    protected void fillGraphics(Graphics g) {
        g.fillRect(xpos, ypos, width, height);
    }

    // EFFECTS: return clothing type
    public String getType() {
        return type;
    }

    // EFFECTS: return clothing name
    public String getName() {
        return name;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("type", type);
        json.put("name", name);
        return json;
    }
}

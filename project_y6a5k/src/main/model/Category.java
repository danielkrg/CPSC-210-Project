package model;

import java.util.LinkedList;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;
import java.awt.*;

public class Category implements Writable {
    private LinkedList<Clothing> category;
    private String name;
    protected int xpos;
    protected int ypos;
    protected int width;
    protected int height;

    // EFFECTS: category has given name and is an empty LinkedList of Clothing
    public Category(String name, int xpos, int ypos, int width, int height) {
        this.name = name;
        this.xpos = xpos;
        this.ypos = ypos;
        this.width = width;
        this.height = height;
        category = new LinkedList<>();
    }

    public int getXPos() {
        return xpos;
    }

    public int getYPos() {
        return ypos;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    // EFFECTS: returns category name
    public String getCategoryName() {
        return name;
    }

    // EFFECTS: returns all clothing in category
    public LinkedList<Clothing> getAllClothing() {
        return category;
    }

    // MODIFIES: this
    // EFFECTS: adds clothing to category
    public void addClothingToCategory(Clothing clothingToAdd) {
        category.add(clothingToAdd);
    }

    // MODIFIES: this
    // EFFECTS: removes given clothing from category
    public void removeClothingFromCategory(Clothing clothingToRemove) {
        for (Clothing clothing: category) {
            if (clothingToRemove.getName().equals(clothing.getName())) {
                category.remove(clothing);
            }
        }
    }

    public void draw(Graphics g) {
        Color save = g.getColor();
        g.setColor(Color.white);
        fillCategory(g);
        g.setColor(save);
        drawCategory(g);

        int currentX = 5;

        for (Clothing clothing : getAllClothing()) {
            clothing.setPosition(currentX, this.ypos + 35);
            clothing.draw(g);
            currentX += 75;
        }
    }

    //EFFECTS: draws the shape
    protected void drawCategory(Graphics g) {
        g.drawRect(xpos, ypos, width, height);
        g.drawString(this.name + ":", this.xpos + 5, this.ypos + 15);
    }

    //EFFECTS: fills the shape
    protected void fillCategory(Graphics g) {
        g.fillRect(xpos, ypos, width, height);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("xpos", xpos);
        json.put("ypos", ypos);
        json.put("width", width);
        json.put("height", height);
        json.put("clothing", clothingToJson());
        return json;
    }

    public JSONArray clothingToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Clothing c : category) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }
}

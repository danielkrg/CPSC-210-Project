package model;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

public class Wardrobe extends JPanel implements Writable {
    private LinkedList<Category> wardrobe;

    // EFFECTS: wardrobe is an empty LinkedList of Category
    public Wardrobe() {
        super();
        wardrobe = new LinkedList<>();
        setBackground(Color.white);
    }

    // MODIFIES: this
    // EFFECTS: adds given category to wardrobe
    public void addCategory(Category category) {
        wardrobe.add(category);
    }

    // EFFECTS: returns all categories in wardrobe
    public LinkedList<Category> categories() {
        return wardrobe;
    }

    // MODIFIES: this
    // EFFECTS: adds given clothing to matching category within wardrobe
    public void addClothing(Clothing clothingToAdd) {
        for (Category category : wardrobe) {
            if (category.getCategoryName().equals(clothingToAdd.getType())) {
                category.addClothingToCategory(clothingToAdd);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: removes given clothing from matching category within wardrobe
    public void removeClothing(Clothing clothingToRemove) {
        for (Category category : wardrobe) {
            if (category.getCategoryName().equals(clothingToRemove.getType())) {
                for (Clothing clothing : category.getAllClothing()) {
                    if (clothing.getName() == clothingToRemove.getName()) {
                        category.removeClothingFromCategory(clothingToRemove);
                    }
                }
            }
        }
    }

    // EFFECTS: returns the Clothing at a given Point in Drawing, if any
    public Clothing getClothingAtPoint(Point point) {
        for (Category category : categories()) {
            for (Clothing clothing : category.getAllClothing()) {
                if (clothing.contains(point)) {
                    return clothing;
                }
            }
        }
        return null;
    }

    // EFFECTS: paints grid and all categories in wardrobe
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Category category : categories()) {
            category.draw(g);
        }
        try {
            Image logo = ImageIO.read(new File("./data/logo.png"));
            Image logoResized = logo.getScaledInstance(241, 50, Image.SCALE_SMOOTH);
            g.drawImage(logoResized, 350,500,this);
        } catch (IOException e) {
            //
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("categories", categoriesToJson());
        return json;
    }

    public JSONArray categoriesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Category c : wardrobe) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }
}

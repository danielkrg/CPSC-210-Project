package persistence;

import model.Clothing;
import model.Category;
import model.Wardrobe;
import model.exceptions.IllegalTypeException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// modeled from the JsonReader class in JsonSerializationDemo
public class Reader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public Reader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Wardrobe read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWardrobe(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses wardrobe from JSON object and returns it
    private Wardrobe parseWardrobe(JSONObject jsonObject) {
        Wardrobe w = new Wardrobe();
        addCategories(w, jsonObject);
        return w;
    }

    // MODIFIES: w
    // EFFECTS: parses categories from JSON object and adds them to workroom
    private void addCategories(Wardrobe w, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("categories");
        for (Object json : jsonArray) {
            JSONObject nextCategory = (JSONObject) json;
            addCategory(w, nextCategory);
        }
    }

    // MODIFIES: w
    // EFFECTS: parses category from JSON object and adds it to wardrobe
    private void addCategory(Wardrobe w, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int xpos = jsonObject.getInt("xpos");
        int ypos = jsonObject.getInt("ypos");
        int width = jsonObject.getInt("width");
        int height = jsonObject.getInt("height");
        Category c = new Category(name, xpos, ypos, width, height);
        JSONArray jsonArray = jsonObject.getJSONArray("clothing");
        for (Object json : jsonArray) {
            JSONObject nextClothing = (JSONObject) json;
            addClothing(c, nextClothing);
        }
        w.addCategory(c);
    }

    // MODIFIES: ca
    // EFFECTS: parses clothing from JSON object and adds it to workroom
    private void addClothing(Category ca, JSONObject jsonObject) {
        String type = jsonObject.getString("type");
        String name = jsonObject.getString("name");
        try {
            Clothing c = new Clothing(type, name);
            ca.addClothingToCategory(c);
        } catch (IllegalTypeException e) {
            //
        }
    }
}

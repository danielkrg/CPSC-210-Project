package persistence;

import model.Clothing;
import model.Category;
import model.Wardrobe;
import model.exceptions.IllegalTypeException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// modeled off JsonWriterTest class in JsonSerializationDemo
public class TestWriter extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Writer writer = new Writer("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    private Wardrobe initializeEmptyWardrobe() {
        Wardrobe w = new Wardrobe();
        Category shirt = new Category("shirt",0,0,0,0);
        Category sweater = new Category("sweater",0,0,0,0);
        Category jacket = new Category("jacket",0,0,0,0);
        Category pants = new Category("pants",0,0,0,0);
        Category shoes = new Category("shoes",0,0,0,0);

        w.addCategory(shirt);
        w.addCategory(sweater);
        w.addCategory(jacket);
        w.addCategory(pants);
        w.addCategory(shoes);

        return w;
    }


    @Test
    void testWriterEmptyWardrobe() {
        try {
            Wardrobe w = initializeEmptyWardrobe();

            Writer writer = new Writer("./data/testWriterWithEmptyWardrobe.json");
            writer.open();
            writer.write(w);
            writer.close();

            Reader reader = new Reader("./data/testWriterWithEmptyWardrobe.json");
            w = reader.read();
            assertEquals(5, w.categories().size());
            assertEquals(0, w.categories().get(0).getAllClothing().size());
            assertEquals(0, w.categories().get(1).getAllClothing().size());
            assertEquals(0, w.categories().get(2).getAllClothing().size());
            assertEquals(0, w.categories().get(3).getAllClothing().size());
            assertEquals(0, w.categories().get(4).getAllClothing().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    private Wardrobe initializeGeneralWardrobe() {
        Wardrobe w = new Wardrobe();
        try {
            Category shirt = new Category("shirt", 0, 0, 0, 0);
            shirt.addClothingToCategory(new Clothing("shirt", "testShirt1"));
            shirt.addClothingToCategory(new Clothing("shirt", "testShirt2"));
            Category sweater = new Category("sweater", 0, 0, 0, 0);
            sweater.addClothingToCategory(new Clothing("sweater", "testSweater1"));
            sweater.addClothingToCategory(new Clothing("sweater", "testSweater2"));
            Category jacket = new Category("jacket", 0, 0, 0, 0);
            Category pants = new Category("pants", 0, 0, 0, 0);
            Category shoes = new Category("shoes", 0, 0, 0, 0);

            w.addCategory(shirt);
            w.addCategory(sweater);
            w.addCategory(jacket);
            w.addCategory(pants);
            w.addCategory(shoes);
            return w;
        } catch (IllegalTypeException e) {
            fail();
        }
        return w;
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            Wardrobe w = initializeGeneralWardrobe();

            Writer writer = new Writer("./data/testWriterWithGeneralWardrobe.json");
            writer.open();
            writer.write(w);
            writer.close();

            Reader reader = new Reader("./data/testWriterWithGeneralWardrobe.json");
            w = reader.read();
            List<Category> categories = w.categories();
            assertEquals(5, categories.size());
            checkCategory("shirt", categories.get(0));
            checkClothing("testShirt1", "shirt", categories.get(0).getAllClothing().get(0));
            checkClothing("testShirt2", "shirt", categories.get(0).getAllClothing().get(1));
            checkCategory("sweater", categories.get(1));
            checkClothing("testSweater1", "sweater", categories.get(1).getAllClothing().get(0));
            checkClothing("testSweater2", "sweater", categories.get(1).getAllClothing().get(1));
            assertEquals(0, w.categories().get(2).getAllClothing().size());
            assertEquals(0, w.categories().get(3).getAllClothing().size());
            assertEquals(0, w.categories().get(4).getAllClothing().size());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}

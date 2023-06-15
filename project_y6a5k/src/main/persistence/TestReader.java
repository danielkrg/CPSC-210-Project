package persistence;

import model.Category;
import model.Wardrobe;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// modeled off JsonReaderTest class in JsonSerializationDemo
public class TestReader extends JsonTest {
    @Test
    void testReaderNonExistentFile() {
        Reader reader = new Reader("./data/nonExistentFile.json");
        try {
            Wardrobe w = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWardrobe() {
        Reader reader = new Reader("./data/testReaderWithEmptyWardrobe.json");
        try {
            Wardrobe w = reader.read();
            List<Category> categories = w.categories();
            checkCategory("shirt", categories.get(0));
            checkCategory("sweater", categories.get(1));
            checkCategory("jacket", categories.get(2));
            checkCategory("pants", categories.get(3));
            checkCategory("shoes", categories.get(4));
            assertEquals(5, categories.size());
            assertEquals(0, categories.get(0).getAllClothing().size());
            assertEquals(0, categories.get(1).getAllClothing().size());
            assertEquals(0, categories.get(2).getAllClothing().size());
            assertEquals(0, categories.get(3).getAllClothing().size());
            assertEquals(0, categories.get(4).getAllClothing().size());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWardrobe() {
        Reader reader = new Reader("./data/testReaderWithGeneralWardrobe.json");
        try {
            Wardrobe w = reader.read();
            List<Category> categories = w.categories();
            assertEquals(5, categories.size());
            checkCategory("shirt", categories.get(0));
            checkClothing("testShirt1", "shirt", categories.get(0).getAllClothing().get(0));
            checkClothing("testShirt2", "shirt", categories.get(0).getAllClothing().get(1));
            checkCategory("sweater", categories.get(1));
            checkClothing("testSweater1", "sweater", categories.get(1).getAllClothing().get(0));
            checkClothing("testSweater2", "sweater", categories.get(1).getAllClothing().get(1));
            checkCategory("jacket", categories.get(2));
            assertEquals(0, w.categories().get(2).getAllClothing().size());
            checkCategory("pants", categories.get(3));
            assertEquals(0, w.categories().get(3).getAllClothing().size());
            checkCategory("shoes", categories.get(4));
            assertEquals(0, w.categories().get(4).getAllClothing().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}

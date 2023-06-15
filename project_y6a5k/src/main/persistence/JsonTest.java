package persistence;

import model.Clothing;
import model.Category;

import static org.junit.jupiter.api.Assertions.*;

// modeled off JsonTest class in JsonSerializationDemo
public class JsonTest {
    protected void checkClothing(String name, String type, Clothing clothing) {
        assertEquals(name, clothing.getName());
        assertEquals(name, clothing.getName());
    }

    protected void checkCategory(String name, Category category) {
        assertEquals(name, category.getCategoryName());
    }
}

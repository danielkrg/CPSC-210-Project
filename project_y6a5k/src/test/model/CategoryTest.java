package model;

import model.exceptions.IllegalTypeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.LinkedList;

public class CategoryTest {
    private Category shirt;

    @BeforeEach
    void runBefore() {
        shirt = new Category("shirt",0,0,0,0);
    }

    @Test
    void addClothingToCategoryTest() {
        try {
            Clothing clothing = new Clothing("shirt", "shirt1");
            shirt.addClothingToCategory(clothing);
            assertEquals(shirt.getAllClothing().size(), 1);
        } catch (IllegalTypeException e) {
            fail();
        }
    }

    @Test
    void removeClothingFromCategoryTest() {
        try {
            Clothing clothing = new Clothing("shirt", "shirt1");
            shirt.addClothingToCategory(clothing);
            Clothing clothingToRemove = new Clothing("shirt", "shirt2");
            shirt.addClothingToCategory(clothingToRemove);
            shirt.removeClothingFromCategory(clothingToRemove);
            assertEquals(shirt.getAllClothing().size(), 1);
            assertTrue(shirt.getAllClothing().contains(clothing));
        } catch (IllegalTypeException e) {
            fail();
        }
    }
}

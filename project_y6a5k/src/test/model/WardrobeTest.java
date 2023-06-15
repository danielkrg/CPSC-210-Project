package model;

import model.exceptions.IllegalTypeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class WardrobeTest {
    private Wardrobe wardrobeTest;

    @BeforeEach
    void runBefore() {
        wardrobeTest = new Wardrobe();
    }

    @Test
    void addCategoryTest() {
        Category category = new Category("shirt",0 ,0,0,0);
        wardrobeTest.addCategory(category);
        assertEquals(wardrobeTest.categories().size(), 1);
        assertTrue(wardrobeTest.categories().contains(category));
    }

    @Test
    void addClothingTest() {
        try {
            Clothing clothingToAdd = new Clothing("shirt", "shirt1");
            Category shirt = new Category("shirt", 0, 0, 0, 0);
            wardrobeTest.addCategory(shirt);
            wardrobeTest.addClothing(clothingToAdd);
            assertEquals(shirt.getAllClothing().size(), 1);
            assertTrue(shirt.getAllClothing().contains(clothingToAdd));
        } catch (IllegalTypeException e) {
            fail();
        }
    }

    @Test
    void removeClothingTest() {
        try {
            Clothing clothingToKeep = new Clothing("shirt", "shirt1");
            Clothing clothingToRemove = new Clothing("shirt", "shirt2");
            Category shirt = new Category("shirt", 0, 0, 0, 0);
            wardrobeTest.addCategory(shirt);
            wardrobeTest.addClothing(clothingToKeep);
            wardrobeTest.addClothing(clothingToRemove);

            wardrobeTest.removeClothing(clothingToRemove);

            assertEquals(shirt.getAllClothing().size(), 1);
            assertTrue(shirt.getAllClothing().contains(clothingToKeep));
        } catch (IllegalTypeException e) {
            fail();
        }
    }
}

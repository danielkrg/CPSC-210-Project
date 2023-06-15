package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import model.exceptions.IllegalTypeException;

public class ClothingTest {
    @Test
    void testCorrectType() {
        try {
            Clothing testClothing = new Clothing("shirt", "name");
        } catch (IllegalTypeException e) {
            fail();
        }
    }

    @Test
    void testIncorrectType() {
        try {
            Clothing testClothing = new Clothing("banana", "name");
            fail();
        } catch (IllegalTypeException e) {
            // pass
        }
    }
}

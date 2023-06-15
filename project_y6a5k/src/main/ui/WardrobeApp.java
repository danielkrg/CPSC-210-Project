package ui;

import model.Category;
import model.Wardrobe;
import model.Clothing;
import model.exceptions.IllegalTypeException;
import persistence.Reader;
import persistence.Writer;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.LinkedList;

public class WardrobeApp {
    private Scanner input;
    private Category shirt;
    private Category sweater;
    private Category jacket;
    private Category pants;
    private Category shoes;
    private Wardrobe userWardrobe;
    private static final String JSON_STORE = "./data/wardrobe.json";
    private Writer writer;
    private Reader reader;

    // EFFECTS: runs wardrobe app
    public WardrobeApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        userWardrobe = new Wardrobe();
        writer = new Writer(JSON_STORE);
        reader = new Reader(JSON_STORE);
        runWardrobeApp();
    }

    // EFFECTS: displays menu and runs commands
    private void runWardrobeApp() {
        Boolean run = true;
        String command = null;

        init();

        while (run) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                run = false;
            } else {
                runCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // EFFECTS: initializes wardrobe, all categories, and adds categories to wardrobe
    private void init() {
        shirt = new Category("shirt", 0, 0, 1000, 100);
        sweater = new Category("sweater",0,100,1000,100);
        jacket = new Category("jacket",0,200,1000,100);
        pants = new Category("pants", 0, 300, 1000, 100);
        shoes = new Category("shoes",0,400,1000,100);
        userWardrobe = new Wardrobe();
        input = new Scanner(System.in);

        userWardrobe.addCategory(shirt);
        userWardrobe.addCategory(sweater);
        userWardrobe.addCategory(jacket);
        userWardrobe.addCategory(pants);
        userWardrobe.addCategory(shoes);
    }

    // EFFECTS: displays menu
    private void displayMenu() {
        System.out.println("\nSelect From:");
        System.out.println("\td -> Display Wardrobe");
        System.out.println("\ta -> Add Clothing");
        System.out.println("\tr -> Remove Clothing");
        System.out.println("\ts -> save wardrobe to file");
        System.out.println("\tl -> load wardrobe from file");
        System.out.println("\tq -> Quit");
    }

    // EFFECTS: runs appropriate method based on inputted command
    private void runCommand(String command) {
        if (command.equals("d")) {
            displayWardrobe();
        } else if (command.equals("a")) {
            addClothing();
        } else if (command.equals("r")) {
            removeClothing();
        } else if (command.equals("s")) {
            saveWardrobe();
        } else if (command.equals("l")) {
            loadWardrobe();
        } else {
            System.out.println("Selection not valid");
        }
    }

    // EFFECTS: displays every piece of clothing and every category in the wardrobe
    private void displayWardrobe() {
        for (Category category: userWardrobe.categories()) {
            System.out.printf(category.getCategoryName() + ":\n");
            for (Clothing clothing: category.getAllClothing()) {
                System.out.printf("-" + clothing.getName() + "\n");
            }
        }
    }

    // EFFECTS: prints all categories in wardrobe
    private void printCategories() {
        for (Category category: userWardrobe.categories()) {
            System.out.println(category.getCategoryName());
        }
    }

    // MODIFIES: this
    // EFFECTS: creates clothing based on input and adds it to the wardrobe
    private void addClothing() {
        LinkedList<String> conditions = new LinkedList<>();
        Boolean addMore = true;

        System.out.println("What type of clothing do you want to add? Here are the options:");
        printCategories();
        String type = input.next();

        System.out.println("What is this clothing piece called?");
        String name = input.next();

        try {
            Clothing clothingToAdd = new Clothing(type, name);
            userWardrobe.addClothing(clothingToAdd);
        } catch (IllegalTypeException e) {
            System.out.println("Incorrect clothing type");
        }
    }

    // EFFECTS: finds and returns clothing based on clothing name
    private Clothing findClothing(String name) {
        for (Category category: userWardrobe.categories()) {
            for (Clothing clothing: category.getAllClothing()) {
                if (clothing.getName().equals(name)) {
                    return clothing;
                }
            }
        }
        return null;
    }

    // MODIFIES: this
    // EFFECTS: removes a clothing piece from the wardrobe
    private void removeClothing() {
        System.out.println("What is the name of the clothing you want to remove?");
        String name = input.next();
        Clothing clothingToRemove = findClothing(name);
        userWardrobe.removeClothing(clothingToRemove);
    }

    // EFFECTS: saves the wardrobe to file
    private void saveWardrobe() {
        try {
            writer.open();
            writer.write(userWardrobe);
            writer.close();
            System.out.println("Saved user wardrobe to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadWardrobe() {
        try {
            userWardrobe = reader.read();
            System.out.println("Loaded user wardrobe from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}

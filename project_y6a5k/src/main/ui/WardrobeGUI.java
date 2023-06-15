package ui;

import model.Category;
import model.Wardrobe;
import model.Clothing;
import persistence.Reader;
import persistence.Writer;
import ui.tools.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class WardrobeGUI extends JFrame {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;

    private List<Tool> tools;
    private Tool activeTool;
    private AddClothingTextField addClothingTextField;

    private Category shirt;
    private Category sweater;
    private Category jacket;
    private Category pants;
    private Category shoes;

    private Wardrobe currentWardrobe;

    private static final String JSON_STORE = "./data/wardrobe.json";
    private Writer writer;
    private Reader reader;

    public WardrobeGUI() {
        super("Wardrobe");
        initializeFields();
        initializeGraphics();
        initializeInteraction();
        init();
    }

    // MODIFIES: this
    // EFFECTS:  draws the JFrame window where this DrawingEditor will operate, and populates the tools to be used
    //           to manipulate this drawing
    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        createTools();
        addNewWardrobe();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS:  sets activeTool, currentDrawing to null, and instantiates drawings and tools with ArrayList
    //           this method is called by the DrawingEditor constructor
    private void initializeFields() {
        activeTool = null;
        currentWardrobe = null;
        tools = new ArrayList<>();
        addClothingTextField = new AddClothingTextField(currentWardrobe);
    }

    // MODIFIES: this
    // EFFECTS:  initializes a DrawingMouseListener to be used in the JFrame
    private void initializeInteraction() {
        DrawingMouseListener dml = new DrawingMouseListener();
        addMouseListener(dml);
        addMouseMotionListener(dml);
    }

    // EFFECTS: initializes wardrobe, all categories, and adds categories to wardrobe
    private void init() {
        shirt = new Category("shirt", 0, 0, 1000, 100);
        sweater = new Category("sweater",0,100,1000,100);
        jacket = new Category("jacket",0,200,1000,100);
        pants = new Category("pants", 0, 300, 1000, 100);
        shoes = new Category("shoes",0,400,1000,100);

        currentWardrobe.addCategory(shirt);
        currentWardrobe.addCategory(sweater);
        currentWardrobe.addCategory(jacket);
        currentWardrobe.addCategory(pants);
        currentWardrobe.addCategory(shoes);

        writer = new Writer(JSON_STORE);
        reader = new Reader(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS:  declares and instantiates a Drawing (newDrawing), and adds it to drawings
    private void addNewWardrobe() {
        Wardrobe newWardrobe = new Wardrobe();
        currentWardrobe = newWardrobe;
        add(newWardrobe, BorderLayout.CENTER);
        validate();
    }

    // MODIFIES: this
    // EFFECTS:  a helper method which declares and instantiates all tools
    private void createTools() {
        JPanel toolArea = new JPanel();
        toolArea.setLayout(new GridLayout(0, 1));
        toolArea.setSize(new Dimension(0, 0));
        add(toolArea, BorderLayout.SOUTH);

        SaveWardrobeTool saveWardrobe = new SaveWardrobeTool(this, toolArea);
        tools.add(saveWardrobe);

        LoadWardrobeTool loadWardrobe = new LoadWardrobeTool(this, toolArea);
        tools.add(loadWardrobe);

        AddClothingTool addClothing = new AddClothingTool(this, toolArea);
        tools.add(addClothing);

        RemoveClothingTool removeClothing = new RemoveClothingTool(this, toolArea);
        tools.add(removeClothing);

        setActiveTool(addClothing);
    }

    // MODIFIES: this
    // EFFECTS:  sets the given tool as the activeTool
    public void setActiveTool(Tool actTool) {
        if (activeTool != null) {
            activeTool.deactivate();
        }
        actTool.activate();
        activeTool = actTool;
    }

    public void addClothing() {
        addClothingTextField.main(currentWardrobe);
        //Clothing c = new Clothing("shirt", "white");
        //currentWardrobe.addClothing(c);

        repaint();
    }

    public void removeClothing(Clothing c) {
        currentWardrobe.removeClothing(c);
    }

    public void loadWardrobe() {
        try {
            Wardrobe newWardrobe = reader.read();
            for (Category category : currentWardrobe.categories()) {
                for (Clothing clothing : category.getAllClothing()) {
                    currentWardrobe.removeClothing(clothing);
                }
            }
            for (Category category : newWardrobe.categories()) {
                for (Clothing clothing : category.getAllClothing()) {
                    currentWardrobe.addClothing(clothing);
                }
            }
            repaint();
        } catch (IOException e) {
            //
        }
    }

    public void saveWardrobe() {
        try {
            writer.open();
            writer.write(currentWardrobe);
            writer.close();
        } catch (FileNotFoundException e) {
            //
        }
    }

    // EFFECTS: if activeTool != null, then mousePressedInDrawingArea is invoked on activeTool, depends on the
    //          type of the tool which is currently activeTool
    private void handleMousePressed(MouseEvent e) {
        if (activeTool != null) {
            activeTool.mousePressedInDrawingArea(e);
        }
        repaint();
    }

    // EFFECTS: if activeTool != null, then mouseClickedInDrawingArea is invoked on activeTool, depends on the
    //          type of the tool which is currently activeTool
    private void handleMouseClicked(MouseEvent e) {
        if (activeTool != null) {
            activeTool.mouseClickedInDrawingArea(e);
        }
        repaint();
    }

    // EFFECTS: return shapes at given point at the currentDrawing
    public Clothing getClothingInDrawing(Point point) {
        return currentWardrobe.getClothingAtPoint(point);
    }

    public static void main(String[] args) {
        new WardrobeGUI();
    }

    private class DrawingMouseListener extends MouseAdapter {

        // EFFECTS: Forward mouse pressed event to the active tool
        public void mousePressed(MouseEvent e) {
            handleMousePressed(translateEvent(e));
        }

        // EFFECTS:Forward mouse clicked event to the active tool
        public void mouseClicked(MouseEvent e) {
            handleMouseClicked(translateEvent(e));
        }

        // EFFECTS: translates the mouse event to current drawing's coordinate system
        private MouseEvent translateEvent(MouseEvent e) {
            return SwingUtilities.convertMouseEvent(e.getComponent(), e, currentWardrobe);
        }
    }
}

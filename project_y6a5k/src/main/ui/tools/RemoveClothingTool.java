package ui.tools;

import ui.WardrobeGUI;
import model.Clothing;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class RemoveClothingTool extends Tool {
    private Clothing clothingToRemove;

    public RemoveClothingTool(WardrobeGUI editor, JComponent parent) {
        super(editor, parent);
        clothingToRemove = null;
    }

    // MODIFIES: this
    // EFFECTS:  constructs a remove clothing button which is then added to the JComponent (parent)
    //           which is passed in as a parameter
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Remove Clothing");
        addToParent(parent);
    }

    // MODIFIES: this
    // EFFECTS:  Sets the clothing at the current mouse position as the clothing to delete and deletes it
    @Override
    public void mousePressedInDrawingArea(MouseEvent e) {
        clothingToRemove = editor.getClothingInDrawing(e.getPoint());
        if (clothingToRemove != null) {
            editor.removeClothing(clothingToRemove);
        }
    }

    // MODIFIES: this
    // EFFECTS:  constructs a new listener object which is added to the JButton
    @Override
    protected void addListener() {
        button.addActionListener(new RemoveClothingTool.RemoveClothingClickHandler());
    }

    private class RemoveClothingClickHandler implements ActionListener {

        // EFFECTS: sets active tool to the remove clothing tool
        //          called by the framework when the tool is clicked
        @Override
        public void actionPerformed(ActionEvent e) {
            editor.setActiveTool(RemoveClothingTool.this);
        }
    }
}


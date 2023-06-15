package ui.tools;

import ui.WardrobeGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddClothingTool extends Tool {
    public AddClothingTool(WardrobeGUI editor, JComponent parent) {
        super(editor, parent);
    }

    // MODIFIES: this
    // EFFECTS:  constructs an add clothing button which is then added to the JComponent (parent)
    //           which is passed in as a parameter
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Add Clothing");
        addToParent(parent);
    }

    // MODIFIES: this
    // EFFECTS:  constructs a new listener object which is added to the JButton
    @Override
    protected void addListener() {
        button.addActionListener(new AddClothingTool.AddClothingClickHandler());
    }

    private class AddClothingClickHandler implements ActionListener {

        // EFFECTS: sets active tool to the add clothing tool called by the framework when the tool is clicked and
        // runs add clothing method
        @Override
        public void actionPerformed(ActionEvent e) {
            editor.setActiveTool(AddClothingTool.this);
            editor.addClothing();
        }
    }
}

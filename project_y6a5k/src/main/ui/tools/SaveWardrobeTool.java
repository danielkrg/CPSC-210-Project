package ui.tools;

import ui.WardrobeGUI;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SaveWardrobeTool extends Tool {
    public SaveWardrobeTool(WardrobeGUI editor, JComponent parent) {
        super(editor, parent);
    }

    // MODIFIES: this
    // EFFECTS:  constructs a save wardrobe button which is then added to the JComponent (parent)
    //           which is passed in as a parameter
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Save Wardrobe");
        addToParent(parent);
    }

    // MODIFIES: this
    // EFFECTS:  constructs a new listener object which is added to the JButton
    @Override
    protected void addListener() {
        button.addActionListener(new SaveWardrobeClickHandler());
    }

    private class SaveWardrobeClickHandler implements ActionListener {

        // EFFECTS: sets active tool to the save wardrobe tool called by the framework when the tool
        // is clicked and saves wardrobe to file
        @Override
        public void actionPerformed(ActionEvent e) {
            editor.setActiveTool(SaveWardrobeTool.this);
            editor.saveWardrobe();
        }
    }
}

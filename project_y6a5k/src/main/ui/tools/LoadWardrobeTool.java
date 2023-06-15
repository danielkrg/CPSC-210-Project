package ui.tools;

import ui.WardrobeGUI;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoadWardrobeTool extends Tool {
    public LoadWardrobeTool(WardrobeGUI editor, JComponent parent) {
        super(editor, parent);
    }

    // MODIFIES: this
    // EFFECTS:  constructs a load wardrobe button which is then added to the JComponent (parent)
    //           which is passed in as a parameter
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Load Wardrobe");
        addToParent(parent);
    }

    // MODIFIES: this
    // EFFECTS:  constructs a new listener object which is added to the JButton
    @Override
    protected void addListener() {
        button.addActionListener(new LoadWardrobeClickHandler());
    }

    private class LoadWardrobeClickHandler implements ActionListener {

        // EFFECTS: sets active tool to the load wardrobe tool called by the framework when the tool is clicked
        // and loads wardrobe from file
        @Override
        public void actionPerformed(ActionEvent e) {
            editor.setActiveTool(LoadWardrobeTool.this);
            editor.loadWardrobe();
        }
    }
}

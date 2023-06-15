package ui.tools;

import model.Clothing;
import model.Wardrobe;
import model.exceptions.IllegalTypeException;

import java.awt.*;

import javax.swing.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class AddClothingTextField extends JPanel implements PropertyChangeListener {
    private String type = "";
    private String name = "";
    private Clothing newClothing;
    private Wardrobe wardrobe;

    private JLabel typeLabel;
    private JLabel nameLabel;

    private static String typeString = "Clothing type (shirt, sweater, jacket, pants, shoes): ";
    private static String nameString = "Clothing name: ";

    private JFormattedTextField typeField;
    private JFormattedTextField nameField;

    public AddClothingTextField(Wardrobe w) {
        super(new BorderLayout());
        this.wardrobe = w;

        typeLabel = new JLabel(typeString);
        nameLabel = new JLabel(nameString);

        typeField = new JFormattedTextField();
        typeField.setValue(type);
        typeField.setColumns(10);
        typeField.addPropertyChangeListener("value", this);

        nameField = new JFormattedTextField();
        nameField.setValue(name);
        nameField.setColumns(10);
        nameField.addPropertyChangeListener("value", this);

        typeLabel.setLabelFor(typeField);
        nameLabel.setLabelFor(nameField);

        JPanel labelPanel = new JPanel(new GridLayout(0,1));
        labelPanel.add(typeLabel);
        labelPanel.add(nameLabel);

        JPanel fieldPanel = new JPanel(new GridLayout(0,1));
        fieldPanel.add(typeField);
        fieldPanel.add(nameField);

        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(labelPanel, BorderLayout.CENTER);
        add(fieldPanel, BorderLayout.LINE_END);
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void propertyChange(PropertyChangeEvent e) {
//        Object source = e.getSource();
//        if (source == typeField) {
//            this.type = ((String)typeField.getValue());
//        } else if (source == nameField) {
//            this.name = ((String)nameField.getValue());
//        }
    }

    public void makeNewClothing() {
        this.type = ((String)typeField.getValue());
        this.name = ((String)nameField.getValue());
        try {
            this.newClothing = new Clothing(this.type, this.name);
            wardrobe.addClothing(this.newClothing);
        } catch (IllegalTypeException e) {
            //
        }
    }

    private void createAndShowGUI(Wardrobe w) {
        //Create and set up the window.
        JFrame frame = new JFrame("Add Clothing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JButton closeButton = new JButton("Add Clothing");
        closeButton.addActionListener(e -> {
            this.makeNewClothing();
            frame.dispose();
            System.out.println("pressed button");
        });

        //Add contents to the window.
        frame.add(this);
        frame.add(closeButton);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public void main(Wardrobe w) {
        wardrobe = w;
        System.out.println(wardrobe.categories());
        createAndShowGUI(w);
    }
}
package ui;

import model.Category;
import model.Event;
import model.EventLog;
import model.FavoriteRestaurant;
import model.RestaurantList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

// Start the app
// Method based on the ListDemo and ComboBoxDemo from
// The Java Tutorial:Using Swing Components: Example:
// https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
public class WhatToEat extends JPanel implements ListSelectionListener {
    public static final int WIDTH = 700;
    public static final int HEIGHT = 400;

    private JList list;
    private DefaultListModel restaurantListModel;
    private RestaurantList restaurantList;

    private static final String addString = "Add";
    private static final String deleteString = "Delete";
    private static final String randomString = "Random Choose";
    private static final String saveString = "Save";
    private static final String loadSting = "Load";
    private JButton addButton;
    private JButton deleteButton;
    private JButton randomButton;
    private JButton saveButton;
    private JButton loadButton;
    private JTextField restaurantName;
    private JScrollPane listScrollPane;
    private JComboBox<Category> categoryList;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/restaurantlist";

    private JLabel picture;


    // MODIFIES: this
    // EFFECTS: create a new WhatToEat
    public WhatToEat() {
        super(new BorderLayout());
        instantiateList();
        instantiateButton();
        instantiateComboBox();
        instantiatePicture();
        instantiatePanel();
    }



    // MODIFIES: this
    // EFFECTS: instantiate all the list related component
    public void instantiateList() {
        restaurantList = new RestaurantList("My List");
        restaurantListModel = new DefaultListModel();
        list = new JList(restaurantListModel);
        listScrollPane = new JScrollPane(list);

        FavoriteRestaurant restaurant = new FavoriteRestaurant("Ryuu",Category.MyFavorite);
        restaurantListModel.addElement(restaurant);
        restaurantList.addRestaurant(restaurant);

        list.setFont(new Font("Calibri",Font.PLAIN,16));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(10);
    }

    // MODIFIES: this
    // EFFECTS: instantiated all the button
    public void instantiateButton() {
        addButton = new JButton(addString);
        AddTool addTool = new AddTool(addButton);
        addButton.setActionCommand(addString);
        addButton.addActionListener(addTool);
        addButton.setEnabled(false);

        deleteButton = new JButton(deleteString);
        deleteButton.setActionCommand(deleteString);
        deleteButton.addActionListener(new DeleteTool());

        restaurantName = new JTextField(15);
        restaurantName.addActionListener(addTool);
        restaurantName.getDocument().addDocumentListener(addTool);
        //String name = restaurantListModel.getElementAt(list.getSelectedIndex()).toString();

        randomButton = new JButton(randomString);
        randomButton.setActionCommand(randomString);
        randomButton.addActionListener(new RandomChoose());

        saveButton = new JButton(saveString);
        saveButton.setActionCommand(saveString);
        saveButton.addActionListener(new Save());

        loadButton = new JButton(loadSting);
        loadButton.setActionCommand(loadSting);
        loadButton.addActionListener(new Load());

    }

    // MODIFIES: this
    // EFFECTS: instantiate ComboBox
    public void instantiateComboBox() {
        Category[] category = {Category.MyFavorite,Category.WantToTry};
        categoryList = new JComboBox(category);
        categoryList.setSelectedIndex(1);
    }

    // MODIFIES: this
    // EFFECTS: instantiate pictures used
    public void instantiatePicture() {
        try {
            File file = new File("./data/rabbit.png");
            BufferedImage bufferedImage = ImageIO.read(file);
            ImageIcon imageIcon = new ImageIcon(bufferedImage);
            picture = new JLabel();
            picture.setIcon(imageIcon);

        } catch (IOException ex) {
            //
        }
    }

    // MODIFIES: this
    // EFFECTS: instantiate the panel
    public void instantiatePanel() {
        JPanel buttonPane = new JPanel();
        add(buttonPane, BorderLayout.SOUTH);
        buttonPane.add(deleteButton);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(categoryList);
        buttonPane.add(restaurantName);
        buttonPane.add(addButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttonPane.add(randomButton);
        buttonPane.add(saveButton);
        buttonPane.add(loadButton);

        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
        add(picture, BorderLayout.EAST);
    }


    // Implement the add function in gui
    class AddTool implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        // constructor
        public AddTool(JButton button) {
            this.button = button;
        }

        // MODIFIES: this
        // EFFECTS: add the restaurant with its category on the list
        public void actionPerformed(ActionEvent e) {
            String name = restaurantName.getText();

            if (name.equals("") || restaurantListModel.contains(name)) {
                Toolkit.getDefaultToolkit().beep();
                restaurantName.requestFocusInWindow();
                restaurantName.selectAll();
                return;
            }

            int index = list.getSelectedIndex();
            if (index == -1) {
                index = 0;
            } else {
                index++;
            }

            restaurantListModel.addElement(categoryList.getSelectedItem() + ": " + restaurantName.getText());
            FavoriteRestaurant newRestaurant = new FavoriteRestaurant(name,
                    (Category) categoryList.getSelectedItem());
            restaurantList.addRestaurant(newRestaurant);

            //Reset the text field.
            restaurantName.requestFocusInWindow();
            restaurantName.setText("");

            //Select the new item and make it visible.
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }

        // Effects:Required by DocumentListener.
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        // Effects:Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        // Effects:Required by DocumentListener.
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        // Effects:if the button is not enabled, enable the button.
        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        // Effects: if the text filed is empty, do not enable the button.
        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }


    // Implement delete function in the gui
    class DeleteTool implements ActionListener {
        // MODIFIES: this
        // EFFECTS: delete the restaurant on the list
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            restaurantListModel.remove(index);
            restaurantList.removeRestaurant(restaurantList.getRestaurants().get(index));
                    //getRestaurants().remove(index);

            int size = restaurantListModel.getSize();

            if (size == 0) {
                deleteButton.setEnabled(false);
            } else {
                if (index == restaurantListModel.getSize()) {
                    index--;
                }

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
        }

    }

    // This method is required by ListSelectionListener.
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
                //No selection, disable fire button.
                deleteButton.setEnabled(false);

            } else {
                //Selection, enable the fire button.
                deleteButton.setEnabled(true);
            }
        }
    }

    // Implement the random choose function in gui
    class RandomChoose extends JFrame implements ActionListener {
        // MODIFIES: this
        // EFFECTS: set the random choose pop up window and the picture display
        public void actionPerformed(ActionEvent e) {
            JFrame frame = new JFrame("Random Choose");
            frame.setMinimumSize(new Dimension(200,200));

            JPanel panel = new JPanel();
            Random random = new Random();
            int randomIndex = random.nextInt(restaurantListModel.size());

            JLabel l = new JLabel(restaurantListModel.getElementAt(randomIndex).toString());
            panel.add(l);

            JComponent popupContentPane = panel;
            frame.setContentPane(popupContentPane);

            try {
                File file =
                        new File("./data/randomChoose.png");
                BufferedImage bufferedImage = ImageIO.read(file);
                ImageIcon imageIcon = new ImageIcon(bufferedImage);
                picture = new JLabel();
                picture.setIcon(imageIcon);
                picture.setSize(new Dimension(100,100));

            } catch (IOException ex) {
                //
            }

            frame.add(picture);
            frame.pack();
            frame.setVisible(true);
        }
    }

    // Implement the load function in gui
    class Load implements ActionListener {
        // MODIFIES: this
        // EFFECTS: load the restaurants to file;
        public void actionPerformed(ActionEvent e) {
            jsonReader = new JsonReader(JSON_STORE);
            try {
                RestaurantList loadRestaurantList = jsonReader.read();
                for (FavoriteRestaurant next: loadRestaurantList.getRestaurants()) {
                    restaurantList.addRestaurant(next);
                    restaurantListModel.addElement(next.getCategory() + ": " + next.getName());
                }
            } catch (IOException ex) {
                //
            }
        }
    }

    // Implement the save function in gui
    class Save implements ActionListener {
        // MODIFIES: this
        // EFFECTS: save the restaurants to file;
        public void actionPerformed(ActionEvent e) {
            jsonWriter = new JsonWriter(JSON_STORE);
            try {
                jsonWriter.open();
                jsonWriter.write(restaurantList);
                jsonWriter.close();

            } catch (FileNotFoundException ex) {
                //
            }
        }
    }


    // MODIFIES: this
    // EFFECTS: create the window, content pane, and display the window.
    //          print the eventLog when window close
    public static void createAndShowGUI() {
        JFrame frame = new JFrame("What To Eat");
        frame.setMinimumSize(new Dimension(WIDTH,HEIGHT));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JComponent newContentPane = new WhatToEat();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        frame.pack();
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                for (Event event: EventLog.getInstance()) {
                    System.out.println(event.toString());
                }
            }
        });
    }



    // EFFECTS: run the application
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
            });
    }


}

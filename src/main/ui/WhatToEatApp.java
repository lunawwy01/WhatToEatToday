package ui;

import model.Category;
import model.FavoriteRestaurant;
import model.RestaurantList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class WhatToEatApp {
    private static final String JSON_STORE = "./data/restaurantlist";
    private Scanner input;
    private RestaurantList list;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the What to eat application
    public WhatToEatApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        list = new RestaurantList("Restaurant List");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runWhatToEat();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runWhatToEat() {
        System.out.println("Welcome to WhatToEat!");
        boolean keepGoing = true;
        String command = null;

        while (keepGoing) {
            displayMenu();
            command = input.next();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                goToList(command);
            }
        }
        System.out.println("\nBye!");
    }

    //MODIFIES: this
    //EFFECTS: processes user command
    private void goToList(String command) {
        if (command.equals("l")) {
            boolean keepGoing = true;
            viewList();

            while (keepGoing) {
                displayListMenu();
                command = input.next();

                if (command.equals("q")) {
                    keepGoing = false;
                } else {
                    processListCommand(command);
                }
            }
        } else {
            System.out.println("Selection not valid...");
        }
    }

    //EFFECTS: displays menu of lists to user
    private void displayMenu() {
        System.out.println("\nSelect from");
        System.out.println("\tl -> list");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: displays the list menu to user
    private void displayListMenu() {
        System.out.println("\nSelect from");
        System.out.println("\t1 -> Add Restaurant");
        System.out.println("\t2 -> Delete Restaurant");
        System.out.println("\t3 -> Random Choose");
        System.out.println("\t4 -> View the list");
        System.out.println("\t5 -> Save restaurant list to file");
        System.out.println("\t6 -> Load restaurant list from file");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command in list
    private void processListCommand(String command) {
        if (command.equals("1")) {
            addNewRestaurant();
        } else if (command.equals("2")) {
            deleteRestaurant();
        } else if (command.equals("3")) {
            randomChoose();
        } else if (command.equals("4")) {
            viewList();
        } else if (command.equals("5")) {
            saveList();
        } else if (command.equals("6")) {
            loadList();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: let user choose name and category of the restaurant and adds to list.
    //          if the restaurant is not on the list, add the restaurant to the list,
    //          otherwise produce the warning.
    private void addNewRestaurant() {
        Category category = readCategory();
        System.out.print("Enter Restaurant name you want to add: ");
        String str = input.next();
        boolean keepAdding = true;

        for (FavoriteRestaurant restaurant : list.getRestaurants()) {
            if ((restaurant.getName()).equals(str)) {
                keepAdding = false;
                System.out.println("The restaurant is already on the list.");
            }
        }

        if (keepAdding) {
            list.addRestaurant(new FavoriteRestaurant(str, category));
        }

        viewList();

    }

    // EFFECTS: let user select the category
    private Category readCategory() {
        System.out.println("Select the category for the restaurant");

        int index = 1;
        for (Category c : Category.values()) {
            System.out.println(index + ": " + c);
            index++;

        }

        int selection = input.nextInt();
        return  Category.values()[selection - 1];
    }

    // MODIFIES: this
    // EFFECTS: if the restaurant exists on the list, delete a restaurant in the list.
    //          otherwise, produce the warning.
    private void deleteRestaurant() {
        viewList();
        System.out.println("Enter Restaurant name you want to delete: ");
        String str = input.next();
        int index = 0;
        boolean keepDeleting = false;

        for (FavoriteRestaurant restaurant : list.getRestaurants()) {
            if ((restaurant.getName()).equals(str)) {
                index = list.getRestaurants().indexOf(restaurant);
                keepDeleting = true;
            }
        }

        if (keepDeleting) {
            list.removeRestaurant(list.getRestaurants().get(index));
        } else {
            System.out.println("The restaurant you enter is not on the list.");
        }

        viewList();
    }

    // EFFECTS: saves the restaurant to file
    private void saveList() {
        try {
            jsonWriter.open();
            jsonWriter.write(list);
            jsonWriter.close();
            System.out.println("Saved " + list.getTitle() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Fail to save the file " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads restaurant list from file
    private void loadList() {
        try {
            list = jsonReader.read();
            System.out.println("Loaded " + list.getTitle() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Fail to save the file " + JSON_STORE);
        }
    }

    // EFFECTS: randomly choose a restaurant from the list
    private void randomChoose() {
        Random r = new Random();
        int randomIndex = r.nextInt(list.getRestaurants().size());
        FavoriteRestaurant pickedRestaurant = list.getRestaurants().get(randomIndex);
        System.out.println(pickedRestaurant.getName());

    }

    // EFFECTS: view the restaurant list
    public void viewList() {
        for (FavoriteRestaurant restaurant: list.getRestaurants()) {
            System.out.println(restaurant.getName());
        }
    }

}

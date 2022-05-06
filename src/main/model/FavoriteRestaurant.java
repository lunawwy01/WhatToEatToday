package model;


import org.json.JSONObject;
import persistence.Writable;

// Represents a restaurant with name and category

public class FavoriteRestaurant implements Writable {
    private String name;
    private Category category;

    //EFFECTS: constructs a restaurant with given name
    public FavoriteRestaurant(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    // EFFECTS: returns string representation of this restaurant
    public String toString() {
        return category + ": " + name;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("category", category);
        return json;
    }

}

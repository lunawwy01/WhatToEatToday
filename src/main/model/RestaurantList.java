package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a restaurant list with given title and contains
// different favorite restaurant

public class RestaurantList implements Writable {
    private String title;
    private List<FavoriteRestaurant> restaurants;

    //EFFECTS: construct restaurant list with given title.
    // Initially, restaurant list have an empty list of
    // favorite restaurant
    public RestaurantList(String title) {
        this.title = title;
        this.restaurants = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: Add a favorite restaurant to the list
    public void addRestaurant(FavoriteRestaurant restaurant) {
        if (!(restaurants.contains(restaurant))) {
            this.restaurants.add(restaurant);
            EventLog.getInstance().logEvent(new Event("Added restaurant: " + restaurant.getName()));
        }
    }

    // MODIFIES: this
    // EFFECTS: Delete a favorite restaurant on the list
    public void removeRestaurant(FavoriteRestaurant restaurant) {
        this.restaurants.remove(restaurant);
        EventLog.getInstance().logEvent(new Event("Removed restaurant: " + restaurant.getName()));
    }

    // EFFECTSL returns an unmodifiable list of restuarnt in this

    public String getTitle() {
        return title;
    }

    public List<FavoriteRestaurant> getRestaurants() {
        return restaurants;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title",title);
        json.put("restaurants", restaurantsToJson());
        return json;
    }

    // EFFECTS: returns things in the restaurant list as a JSON array
    private JSONArray restaurantsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (FavoriteRestaurant r : restaurants) {
            jsonArray.put(r.toJson());
        }

        return jsonArray;
    }

}

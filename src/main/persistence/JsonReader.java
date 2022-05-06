package persistence;

import model.Category;
import model.FavoriteRestaurant;
import model.RestaurantList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads restaurant list from JSON data stored in file
// Method taken from JsonReader class in JsonSerializationDEmo:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads restaurant list from file and returns it;
    // throws IOException if an error occurs reading data from file

    public RestaurantList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseRestaurantList(jsonObject);
    }

    // EFFECTS: read source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses restaurant list from JSON object and returns it
    private RestaurantList parseRestaurantList(JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        RestaurantList rl = new RestaurantList(title);
        addRestaurants(rl, jsonObject);
        return rl;
    }

    // MODIFIES: rl
    // EFFECTS: parses restaurants from Json object and adds them to restaurant list
    private void addRestaurants(RestaurantList rl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("restaurants");
        for (Object json : jsonArray) {
            JSONObject nextRestaurant = (JSONObject) json;
            addRestaurant(rl, nextRestaurant);
        }
    }

    // MODIFIES: rl
    // EFFECTS: parses restaurant from JSON object and add it to restaurant list
    private void addRestaurant(RestaurantList rl, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Category category = Category.valueOf(jsonObject.getString("category"));
        FavoriteRestaurant restaurant = new FavoriteRestaurant(name,category);
        rl.addRestaurant(restaurant);
    }
}

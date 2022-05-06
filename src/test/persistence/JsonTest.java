package persistence;

import model.Category;
import model.FavoriteRestaurant;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Reference to the JsonSerializationDEmo:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonTest {
    protected void checkRestaurant(String name, Category category, FavoriteRestaurant restaurant) {
        assertEquals(name, restaurant.getName());
        assertEquals(category, restaurant.getCategory());
    }
}

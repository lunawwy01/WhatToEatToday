package persistence;

import model.Category;
import model.FavoriteRestaurant;
import model.RestaurantList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Method taken from JsonReaderTest class in JsonSerializationDEmo:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonReaderTest extends JsonTest{

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            RestaurantList rl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            //
        }
    }

    @Test
    void testReaderEmptyRestaurantList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyRestaurantList");
        try {
            RestaurantList rl = reader.read();
            assertEquals("My Restaurant List", rl.getTitle());
            List<FavoriteRestaurant> restaurants = rl.getRestaurants();
            assertEquals(0, restaurants.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralRestaurantList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralRestaurantList");
        try {
            RestaurantList rl = reader.read();
            assertEquals("My Restaurant List", rl.getTitle());
            List<FavoriteRestaurant> restaurants = rl.getRestaurants();
            assertEquals(2, restaurants.size());
            checkRestaurant("Nori", Category.MyFavorite, restaurants.get(0));
            checkRestaurant("Kinto", Category.WantToTry, restaurants.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }

    }
}

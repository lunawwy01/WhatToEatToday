package persistence;

import model.Category;
import model.FavoriteRestaurant;
import model.RestaurantList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Method taken from JsonWriterTest class in JsonSerializationDEmo:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonWriterTest extends JsonTest{

    @Test
    void testWriterInvalidFile() {
        try {
            RestaurantList rl = new RestaurantList("My Restaurant List");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyRestaurantList() {
        try {
            RestaurantList rl = new RestaurantList("My Restaurant List");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyRestaurantList");
            writer.open();
            writer.write(rl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testReaderEmptyRestaurantList");
            rl = reader.read();
            assertEquals("My Restaurant List", rl.getTitle());
            List<FavoriteRestaurant> restaurants = rl.getRestaurants();
            assertEquals(0, restaurants.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralRestaurantList() {
        try {
            RestaurantList rl = new RestaurantList("My Restaurant List");
            rl.addRestaurant(new FavoriteRestaurant("Nori", Category.MyFavorite));
            rl.addRestaurant(new FavoriteRestaurant("Kinto", Category.WantToTry));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralRestaurantList");
            writer.open();
            writer.write(rl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testReaderGeneralRestaurantList");
            rl = reader.read();
            assertEquals("My Restaurant List", rl.getTitle());
            List<FavoriteRestaurant> restaurants = rl.getRestaurants();
            assertEquals(2, restaurants.size());
            checkRestaurant("Nori",Category.MyFavorite,restaurants.get(0));
            checkRestaurant("Kinto",Category.WantToTry,restaurants.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}

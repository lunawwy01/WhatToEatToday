package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestaurantListTest {

    private RestaurantList ubcRestaurants;
    private RestaurantList vancouverRestaurant;

    private FavoriteRestaurant nori;
    private FavoriteRestaurant ryuu;
    private FavoriteRestaurant kinto;

    @BeforeEach
    void runBefore() {
        ubcRestaurants = new RestaurantList("UBC Restaurant");
        vancouverRestaurant = new RestaurantList("Vancouver Restaurant");
        nori = new FavoriteRestaurant("Nori", Category.MyFavorite);
        ryuu = new FavoriteRestaurant("Ryuu", Category.WantToTry);
        kinto = new FavoriteRestaurant("Kinto",Category.WantToTry);
    }

    @Test
    void testConstructor() {
        assertEquals("UBC Restaurant",ubcRestaurants.getTitle());
        assertEquals(0,ubcRestaurants.getRestaurants().size());
    }

    @Test
    void testConstructorSecondCase() {
        assertEquals("Vancouver Restaurant",vancouverRestaurant.getTitle());
        assertEquals(0, vancouverRestaurant.getRestaurants().size());
    }

    @Test
    void testAddRestaurant () {
        ubcRestaurants.addRestaurant(nori);
        assertEquals(1,ubcRestaurants.getRestaurants().size());
        assertEquals(nori,ubcRestaurants.getRestaurants().get(0));

        ubcRestaurants.addRestaurant(ryuu);
        assertEquals(2,ubcRestaurants.getRestaurants().size());
        assertEquals(ryuu, ubcRestaurants.getRestaurants().get(1));
    }

    @Test
    void testMultipleAddRestaurant() {
        vancouverRestaurant.addRestaurant(ryuu);
        vancouverRestaurant.addRestaurant(kinto);
        vancouverRestaurant.addRestaurant(nori);
        assertEquals(3,vancouverRestaurant.getRestaurants().size());
        assertEquals(nori,vancouverRestaurant.getRestaurants().get(2));
    }

    @Test
    void testAddRestaurantAlreadyExist () {
        ubcRestaurants.addRestaurant(kinto);
        ubcRestaurants.addRestaurant(ryuu);
        ubcRestaurants.addRestaurant(ryuu);
        assertEquals(2,ubcRestaurants.getRestaurants().size());
        assertEquals(ryuu,ubcRestaurants.getRestaurants().get(1));
    }

    @Test
    void testRemoveRestaurant() {
        ubcRestaurants.removeRestaurant(nori);
        assertEquals(0,ubcRestaurants.getRestaurants().size());

        ubcRestaurants.addRestaurant(nori);
        ubcRestaurants.removeRestaurant(nori);
        assertEquals(0,ubcRestaurants.getRestaurants().size());

        ubcRestaurants.addRestaurant(nori);
        ubcRestaurants.addRestaurant(ryuu);
        ubcRestaurants.removeRestaurant(nori);
        assertEquals(1,ubcRestaurants.getRestaurants().size());
        assertEquals(ryuu, ubcRestaurants.getRestaurants().get(0));

        ubcRestaurants.addRestaurant(kinto);
        assertEquals(2,ubcRestaurants.getRestaurants().size());
        assertEquals(kinto,ubcRestaurants.getRestaurants().get(1));
    }

    @Test
    void testMultipleRemoveRestaurant() {
        vancouverRestaurant.addRestaurant(nori);
        vancouverRestaurant.addRestaurant(ryuu);
        vancouverRestaurant.addRestaurant(kinto);
        vancouverRestaurant.removeRestaurant(ryuu);
        vancouverRestaurant.removeRestaurant(nori);
        assertEquals(1,vancouverRestaurant.getRestaurants().size());
        assertEquals(kinto,vancouverRestaurant.getRestaurants().get(0));
    }

}

package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FavoriteRestaurantTest {
    private FavoriteRestaurant testRestaurant;

    @BeforeEach
    void runBefore() {
        testRestaurant = new FavoriteRestaurant("Nori", Category.MyFavorite);
    }
    @Test
    void testConstructor() {
        assertEquals("Nori",testRestaurant.getName());
    }

    @Test
    void testConstructorSecondCase() {
        testRestaurant = new FavoriteRestaurant("Ryuu",Category.WantToTry);
        assertEquals("Ryuu",testRestaurant.getName());
    }

    @Test
    void testToString() {
        assertEquals("MyFavorite: Nori",testRestaurant.toString());
    }
}

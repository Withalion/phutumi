package sk.fiit.phutumi.models;

import javax.persistence.*;
@Entity
@Table(name = "restaurants_foods")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "restaurant_id")
    private Long restaurantId;

    @Column(name = "food_id")
    private Long foodId;

    public Menu() {

    }

    public Menu(Long restaurantId, Long foodId) {
        this.restaurantId = restaurantId;
        this.foodId = foodId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Long getFoodId() {
        return foodId;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }

    public long getId() {
        return id;
    }
}
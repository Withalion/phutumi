package sk.fiit.phutumi.models;

import javax.persistence.*;

@Entity
@Table(name = "orders_foods")
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "food_id")
    private Long foodId;

    @Column(name = "order_id")
    private Long orderId;

    public ShoppingCart(Long foodId, Long orderId) {
        this.foodId = foodId;
        this.orderId = orderId;
    }

    public ShoppingCart() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFoodId() {
        return foodId;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}

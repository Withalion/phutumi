package sk.fiit.phutumi.OrderService.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Entity
@Table(name = "orders_foods")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShoppingCart {

    @jakarta.persistence.Id
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
}

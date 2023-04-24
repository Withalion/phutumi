package sk.fiit.phutumi.OfferService.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Entity
@Table(name = "restaurants_foods")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Menu {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "restaurant_id")
    private Long restaurantId;

    @Column(name = "food_id")
    private Long foodId;

    public Menu(Long restaurantId, Long foodId) {
        this.restaurantId = restaurantId;
        this.foodId = foodId;
    }
}

package sk.fiit.phutumi.OrderService.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddToShoppingCartEvent {

    private Long foodId;
    private Long orderId;
}

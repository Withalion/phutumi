package sk.fiit.phutumi.WebService.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AddToShoppingCartEvent {

    private Long foodId;
    private Long orderId;
}

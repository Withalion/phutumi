package sk.fiit.phutumi.PaymentService.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.fiit.phutumi.PaymentService.Models.ShoppingCart;

import java.util.List;

public interface ShoppingCardRepository extends JpaRepository<ShoppingCart, Long> {

    List<ShoppingCart> findOnlyFoodIdsByOrderId(Long id);
}

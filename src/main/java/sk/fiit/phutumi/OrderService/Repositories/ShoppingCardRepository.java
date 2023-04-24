package sk.fiit.phutumi.OrderService.Repositories;

import sk.fiit.phutumi.OrderService.Models.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoppingCardRepository extends JpaRepository<ShoppingCart, Long> {

    List<ShoppingCart> findOnlyFoodIdsByOrderId(Long id);
}

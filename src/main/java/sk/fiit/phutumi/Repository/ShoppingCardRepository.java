package sk.fiit.phutumi.Repository;

import sk.fiit.phutumi.models.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoppingCardRepository extends JpaRepository<ShoppingCart, Long> {
    List<ShoppingCart> findOnlyFoodIdsByOrderId(Long id);

}

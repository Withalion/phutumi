package sk.fiit.phutumi.Repository;

import sk.fiit.phutumi.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {


}

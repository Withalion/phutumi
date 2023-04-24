package sk.fiit.phutumi.OrderService.Repositories;

import sk.fiit.phutumi.OrderService.Models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {


}

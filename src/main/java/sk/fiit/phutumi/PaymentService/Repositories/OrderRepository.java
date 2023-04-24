package sk.fiit.phutumi.PaymentService.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.fiit.phutumi.PaymentService.Models.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {


}

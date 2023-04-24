package sk.fiit.phutumi.PaymentService.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.fiit.phutumi.PaymentService.Models.Food;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {

    List<Food> findFoodsByIdIn(List<Long> ids);

}

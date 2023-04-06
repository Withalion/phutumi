package sk.fiit.phutumi.Repository;

import sk.fiit.phutumi.models.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {

    List<Food> findFoodsByIdIn(List<Long> ids);

}

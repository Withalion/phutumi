package sk.fiit.phutumi.Repository;

import sk.fiit.phutumi.models.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long>{

}

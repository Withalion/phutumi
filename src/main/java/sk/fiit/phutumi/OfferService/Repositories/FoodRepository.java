package sk.fiit.phutumi.OfferService.Repositories;

import sk.fiit.phutumi.OfferService.Models.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {

    List<Food> findFoodsByIdIn(List<Long> ids);

}

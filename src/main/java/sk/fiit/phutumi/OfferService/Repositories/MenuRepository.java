package sk.fiit.phutumi.OfferService.Repositories;

import sk.fiit.phutumi.OfferService.Models.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findOnlyFoodIdsByRestaurantId(Long id);

}

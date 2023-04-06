package sk.fiit.phutumi.Repository;

import sk.fiit.phutumi.models.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {


}

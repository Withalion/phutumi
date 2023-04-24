package sk.fiit.phutumi.OfferService.Repositories;

import sk.fiit.phutumi.OfferService.Models.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {


}

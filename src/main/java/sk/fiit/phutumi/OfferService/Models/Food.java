package sk.fiit.phutumi.OfferService.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Entity
@Table(name = "foods")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Food {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    public Food(String name) {
        this.name = name;
    }

}
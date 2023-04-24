package sk.fiit.phutumi.WebService.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.tuple.GenerationTiming;
import org.springframework.data.annotation.Id;

import java.sql.Date;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CurrentTimestamp(timing = GenerationTiming.INSERT)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Column(name = "processed")
    private boolean processed;

    @Column(name = "paid")
    private boolean paid;
}

package sk.fiit.phutumi.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.tuple.GenerationTiming;
import org.springframework.data.annotation.Id;

import java.sql.Date;

@Entity
@Table(name = "orders")
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

    public Order() {

    }

    public Order(Long id, boolean processed) {
        this.id = id;
        this.processed = processed;
        this.paid = paid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public boolean getPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}

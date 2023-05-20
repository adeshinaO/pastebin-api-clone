package adeshinaogunmodede.textbin.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.ZonedDateTime;
import java.util.UUID;

import lombok.Data;

@Entity
@Table(name = "texts")
@Data
public class Text {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "content", updatable = false, nullable = false)
    private String content;

    @Column(name = "reference", unique = true, nullable = false, updatable = false)
    private String reference;

    @Column(name = "has_expiry_date", updatable = false, nullable = false)
    private boolean hasExpiryDate;

    @Column(name = "expiry_date", updatable = false)
    private ZonedDateTime expiryDate;
}

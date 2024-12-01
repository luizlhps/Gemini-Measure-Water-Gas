package com.measure.measure_water_gas.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "measure")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Measure {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    private Instant measureDatetime;
    private String measureType;
    private boolean hasConfirmed;

    @Column(columnDefinition = "TEXT")
    private String imageUrl;
    private boolean isDeleted;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    @ManyToOne()
    @JoinColumn(name = "customer_code", referencedColumnName = "customer_code", foreignKey = @ForeignKey(name = "FK_measure_customer"))
    private Customer customer;

}

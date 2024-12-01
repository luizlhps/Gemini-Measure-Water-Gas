package com.measure.measure_water_gas.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.checker.signature.qual.Identifier;
import org.checkerframework.common.aliasing.qual.Unique;

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
    private Instant createdAt;
    private Instant updatedAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_code", referencedColumnName = "customer_code")
    private Customer customer;

}

package com.measure.measure_water_gas.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private Instant measureDatetime;

    @NotNull
    private String measureType;

    @NotNull
    private boolean hasConfirmed;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String imageUrl;

    @JsonIgnore
    @NotNull
    private boolean isDeleted;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    @ManyToOne()
    @JoinColumn(name = "customer_code", referencedColumnName = "customer_code", foreignKey = @ForeignKey(name = "FK_measure_customer"))
    @JsonBackReference
    @NotNull
    private Customer customer;

}

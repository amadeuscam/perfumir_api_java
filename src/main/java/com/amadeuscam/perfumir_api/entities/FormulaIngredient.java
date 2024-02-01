package com.amadeuscam.perfumir_api.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Setter
@Getter
@Table(name = "formulaIngredient")
public class FormulaIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String ingredientName;
    private String pyramidLevel;
    @Column(precision = 8, scale = 3)
    private BigDecimal amount;
    private Integer dilutionOfIngredient;
    private Integer alcohol;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false, updatable = false)
    private Date created;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "formula_id")
    private Formula formula;


}

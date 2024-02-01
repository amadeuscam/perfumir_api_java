package com.amadeuscam.perfumir_api.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Setter
@Getter
@Table(name = "ingredients")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ingredient_id")
    private Long id;
    @Column(unique=true)
    private String name;
    @Column(unique=true)
    private String casNumber;
    private String pyramidLevel;
    private String odorDescription;
    private String type;
    public Integer odorImpact;
    public Integer odorLife;
    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Dilution> dilutions;

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OlfactiveFamilies> olfactiveFamilies;


}


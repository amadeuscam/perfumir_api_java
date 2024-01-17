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
    private Long id;
    private String name;
    private String casNumber;
    private String pyramidLevel;
    private String odorDescription;
    private String type;
    public Integer odorImpact;
    public Integer odorLife;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Dilution> dilutions;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<OlfactiveFamilies> olfactiveFamilies;

}


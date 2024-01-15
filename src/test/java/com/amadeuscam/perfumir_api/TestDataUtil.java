package com.amadeuscam.perfumir_api;

import com.amadeuscam.perfumir_api.dto.DilutionDto;
import com.amadeuscam.perfumir_api.dto.IngredientDto;
import com.amadeuscam.perfumir_api.dto.OlfactiveFamiliesDto;
import com.amadeuscam.perfumir_api.entities.Dilution;
import com.amadeuscam.perfumir_api.entities.Ingredient;
import com.amadeuscam.perfumir_api.entities.OlfactiveFamilies;

import java.util.Set;

public class TestDataUtil {

    public TestDataUtil() {
    }

    public static IngredientDto testIngredientDto(Set<DilutionDto> dilution, Set<OlfactiveFamiliesDto> olfactiveFamilies) {
        return IngredientDto.builder()
                .name("Ambroxan")
                .casNumber("3738-00-9")
                .pyramidLevel("base")
                .odorDescription("Ambroxan, nota amaderada, ámbar, dulce, terrosa y almizclada con una delicada tonalidad anima")
                .type("synthetic")
                .odorImpact(400)
                .odorLife(750)
                .dilutions(dilution)
                .olfactiveFamilies(olfactiveFamilies)
                .build();
    }

    public static Ingredient createTestIngredient(Long id,final Set<Dilution> dilution, final Set<OlfactiveFamilies> olfactiveFamilies) {
        return Ingredient.builder()
                .id(id)
                .name("Ambroxan")
                .casNumber("3738-00-9")
                .pyramidLevel("base")
                .odorDescription("Ambroxan, nota amaderada, ámbar, dulce, terrosa y almizclada con una delicada tonalidad anima")
                .type("synthetic")
                .odorImpact(400)
                .odorLife(750)
                .dilutions(dilution)
                .olfactiveFamilies(olfactiveFamilies)
                .build();
    }

    public static Dilution createDilution() {
        return Dilution.builder()
                .id(1L)
                .quantity(10)
                .build();
    }
    public static OlfactiveFamilies createOlfactiveFamilies(String family) {
        return OlfactiveFamilies.builder()
                .id(1L)
                .name(family)
                .build();
    }
}

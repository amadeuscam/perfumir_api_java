package com.amadeuscam.perfumir_api;

import java.util.Set;

import com.amadeuscam.perfumir_api.dto.DilutionDto;
import com.amadeuscam.perfumir_api.dto.IngredientDto;
import com.amadeuscam.perfumir_api.dto.OlfactiveFamiliesDto;
import com.amadeuscam.perfumir_api.entities.Dilution;
import com.amadeuscam.perfumir_api.entities.Formula;
import com.amadeuscam.perfumir_api.entities.Ingredient;
import com.amadeuscam.perfumir_api.entities.OlfactiveFamilies;
import com.amadeuscam.perfumir_api.entities.Project;

public class TestDataUtil {

    public TestDataUtil() {
    }

    public static IngredientDto testIngredientDto(Set<DilutionDto> dilution,
            Set<OlfactiveFamiliesDto> olfactiveFamilies) {
        return IngredientDto.builder()
                .name("Ambroxan")
                .casNumber("3738-00-9")
                .pyramidLevel("base")
                .odorDescription(
                        "Ambroxan, nota amaderada, ámbar, dulce, terrosa y almizclada con una delicada tonalidad anima")
                .type("synthetic")
                .odorImpact(400)
                .odorLife(750)
                .dilutions(dilution)
                .olfactiveFamilies(olfactiveFamilies)
                .build();
    }

    public static Ingredient createTestIngredient(Long id, final Set<Dilution> dilution,
            final Set<OlfactiveFamilies> olfactiveFamilies) {
        return Ingredient.builder()
                .id(id)
                .name("Ambroxan")
                .casNumber("3738-00-9")
                .pyramidLevel("base")
                .odorDescription(
                        "Ambroxan, nota amaderada, ámbar, dulce, terrosa y almizclada con una delicada tonalidad anima")
                .type("synthetic")
                .odorImpact(400)
                .odorLife(750)
                .dilutions(dilution)
                .olfactiveFamilies(olfactiveFamilies)
                .build();
    }

    public static Dilution createDilution(Long id) {
        return Dilution.builder()
                .id(id)
                .quantity(10)
                .build();
    }

    public static DilutionDto createDilutionDto(Long id) {
        return DilutionDto.builder()
                .id(id)
                .quantity(10)
                .build();
    }

    public static OlfactiveFamilies createOlfactiveFamilies(Long id, String family) {
        return OlfactiveFamilies.builder()
                .id(id)
                .name(family)
                .build();
    }

    public static Project createProject(final Set<Formula> formulas) {
        return Project.builder()
                .id(1L)
                .Name("floral")
                .formulas(formulas)
                .build();
    }

    public static Formula createFormula() {
        return Formula.builder()
                .id(1L)
                .name("jane luc")
                .status("working")
                .version("1.1")
                .formulaIngredients(null)
                .build();
    }
}

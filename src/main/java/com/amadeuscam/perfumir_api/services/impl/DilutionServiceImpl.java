package com.amadeuscam.perfumir_api.services.impl;

import com.amadeuscam.perfumir_api.dto.DilutionCountDto;
import com.amadeuscam.perfumir_api.entities.Dilution;
import com.amadeuscam.perfumir_api.entities.Ingredient;
import com.amadeuscam.perfumir_api.repository.DilutionRepository;
import com.amadeuscam.perfumir_api.repository.IngredientRepository;
import com.amadeuscam.perfumir_api.services.DilutionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class DilutionServiceImpl implements DilutionService {
    private   DilutionRepository dilutionRepository;
    private   IngredientRepository ingredientRepository;

    @Autowired
    public DilutionServiceImpl(DilutionRepository dilutionRepository, IngredientRepository ingredientRepository) {
        this.dilutionRepository = dilutionRepository;
        this.ingredientRepository = ingredientRepository;
    }


    @Override
    public Dilution createDilution(Dilution dilution, Long ingredientId) {
        Optional<Ingredient> ingredientOptional = ingredientRepository.findById(ingredientId);
        return ingredientOptional.map(ingredient -> {
            dilution.setIngredient(ingredient);
            return dilutionRepository.save(dilution);
        }).orElseThrow(() -> new RuntimeException("Ingredient does not exist"));
    }

    @Override
    public Dilution updateDilution(Dilution dilution, Long ingredientId) {
        Optional<Ingredient> ingredientOptional = ingredientRepository.findById(ingredientId);
        return ingredientOptional.map(ingredient -> {
            dilution.setIngredient(ingredient);
            return dilutionRepository.save(dilution);
        }).orElseThrow(() -> new RuntimeException("Ingredient does not exist"));
    }

    @Override
    public Ingredient deleteDilution(Long id, Long ingredientId) {
        Optional<Ingredient> ingredientOptional = ingredientRepository.findById(ingredientId);
        return ingredientOptional.map(ingredient -> {
            ingredient.getDilutions().removeIf(d-> Objects.equals(d.getId(), id));
            return  ingredientRepository.save(ingredient);
        }).orElseThrow(() -> new RuntimeException("Ingredient does not exist"));
    }

    @Override
    public Optional<Dilution> getDilution(Long id, Long ingredientId) {
        Optional<Ingredient> ingredientOptional = ingredientRepository.findById(ingredientId);
        return  ingredientOptional.map(ingredient -> {
               return ingredient.getDilutions().stream().filter(d-> Objects.equals(d.getId(), id)).findFirst();
        }).orElseThrow(() -> new RuntimeException("Ingredient does not exist"));
    }

    @Override
    public Set<Dilution> getDilutions(Long ingredientId) {
        Optional<Ingredient> ingredientOptional = ingredientRepository.findById(ingredientId);
        return ingredientOptional.map(Ingredient::getDilutions).orElseThrow(() -> new RuntimeException("Ingredient does not exist"));
    }

    @Override
    public List<DilutionCountDto> getDilutionsQuantities() {
        return dilutionRepository.getCountByQuantity();
    }

    @Override
    public boolean isDilutionExists(Long id, Long ingredientId) {
        Optional<Ingredient> ingredientOptional = ingredientRepository.findById(ingredientId);
        return ingredientOptional.map(ingredient -> ingredient.getDilutions().stream().anyMatch(d -> Objects.equals(d.getId(), id))).orElseThrow(() -> new RuntimeException("Ingredient does not exist"));
    }


}

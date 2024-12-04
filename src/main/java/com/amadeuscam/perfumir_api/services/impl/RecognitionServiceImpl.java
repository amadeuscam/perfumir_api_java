package com.amadeuscam.perfumir_api.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amadeuscam.perfumir_api.entities.Recognition;
import com.amadeuscam.perfumir_api.repository.RecognitionRepository;
import com.amadeuscam.perfumir_api.services.RecognitionService;

@Service
public class RecognitionServiceImpl implements RecognitionService {

    @Autowired
    private RecognitionRepository recognitionRepository;

    @Override
    public List<Recognition> getAllRecognitions() {
        return recognitionRepository.findAll();
    }

    @Override
    public List<Recognition> getAllRecognitionsByDate(LocalDate date) {
        return recognitionRepository.findDistinctByCreated(date);
    }

    @Override
    public void deleteRecognitions(Long id) {
        recognitionRepository.deleteById(id);
    }

    @Override
    public Recognition addRecognition(Recognition recognition) {
        return recognitionRepository.save(recognition);
    }

    // Método para obtener todos los registros y agrupar por fecha
    public Map<LocalDate, List<Recognition>> getRecognitionsGroupedByDate() {
        // Obtener todos los registros de la base de datos
        List<Recognition> recognitions = recognitionRepository.findAll();

        // Agrupar los registros por la columna 'date'
        return recognitions.stream()
                .collect(Collectors.groupingBy(Recognition::getCreated));
    }

}

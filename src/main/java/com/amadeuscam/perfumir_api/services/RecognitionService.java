package com.amadeuscam.perfumir_api.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.amadeuscam.perfumir_api.entities.Recognition;

public interface RecognitionService {

    List<Recognition> getAllRecognitions();

    List<Recognition> getAllRecognitionsByDate(LocalDate date);

    void deleteRecognitions(Long id);

    Recognition addRecognition(Recognition recognition);

    Map<LocalDate, List<Recognition>> getRecognitionsGroupedByDate();

}

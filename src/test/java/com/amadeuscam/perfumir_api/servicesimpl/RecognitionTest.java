package com.amadeuscam.perfumir_api.servicesimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.amadeuscam.perfumir_api.entities.Recognition;
import com.amadeuscam.perfumir_api.repository.RecognitionRepository;

@ExtendWith(MockitoExtension.class)
public class RecognitionTest {

    @Mock
    private RecognitionRepository recognitionRepository;

    private Recognition recognition1;
    private Recognition recognition2;
    private Recognition recognition3;

    @BeforeEach
    public void setUp() {
        // Limpiar la base de datos antes de cada prueba
        recognitionRepository.deleteAll();

        // Crear y guardar algunos registros de prueba
        recognition1 = new Recognition();
        recognition1.setCreated(LocalDate.of(2023, 9, 15));
        recognitionRepository.save(recognition1);

        recognition2 = new Recognition();
        recognition2.setCreated(LocalDate.of(2023, 9, 15));
        recognitionRepository.save(recognition2);

        recognition3 = new Recognition();
        recognition3.setCreated(LocalDate.of(2024, 1, 1));
        recognitionRepository.save(recognition3);

        List<Recognition> recognitions = recognitionRepository.findAll();
        System.out.println(recognitions.size());

    }

    @Test
    public void testFindDistinctByDate() {
        // Llamar al método de prueba
        List<Recognition> recognitions = recognitionRepository.findDistinctByCreated(LocalDate.of(2023, 9, 15));

        // Validar el resultado
        assertEquals(2, recognitions.size());
        assertTrue(recognitions.contains(recognition1));
        assertTrue(recognitions.contains(recognition2));

        // Verificar que no se incluya otro registro con fecha diferente
        List<Recognition> differentDateRecognitions = recognitionRepository
                .findDistinctByCreated(LocalDate.of(2024, 1, 1));
        assertEquals(1, differentDateRecognitions.size());
        assertTrue(differentDateRecognitions.contains(recognition3));
    }
}

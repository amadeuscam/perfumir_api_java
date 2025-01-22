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

    /**
     * Obtener todos los registros de reconocimiento
     * 
     * @return una lista con todos los objetos Recognition registrados en la base de datos
     */
    @Override
    public List<Recognition> getAllRecognitions() {
        return recognitionRepository.findAll();
    }

    /**
     * Obtener todos los registros de reconocimiento cuyo valor de la fecha sea el dado por parametro
     * 
     * @param date la fecha a buscar, puede ser null
     * @return una lista con todos los objetos Recognition registrados cuya fecha coincida con la fecha
     *         dada por parametro
     */
    @Override
    public List<Recognition> getAllRecognitionsByDate(LocalDate date) {
        return recognitionRepository.findDistinctByCreated(date);
    }

    /**
     * Eliminar un registro de reconocimiento a partir del id dado por parametro
     * 
     * @param id el id del objeto Recognition que se desea eliminar
     */
    @Override
    public void deleteRecognitions(Long id) {
        recognitionRepository.deleteById(id);
    }

    /**
     * Agregar un registro de reconocimiento a la base de datos
     * 
     * @param recognition el objeto Recognition a guardar en la base de datos
     * @return un objeto Recognition con los valores recogidos del parámetro (dado por parametro)
     */
    @Override
    public void addRecognition(List<Recognition> recognitions) {

        for (Recognition recognition : recognitions) {
            recognitionRepository.save(recognition);
        }

    }

    /**
     * Método para obtener todos los registros y agruparlos por fecha
     * 
     * @return un mapa con las fechas de los records como clave y una lista de objetos Recognition como
     *         valores
     */
    public Map<LocalDate, List<Recognition>> getRecognitionsGroupedByDate() {
        // Obtener todos los registros de la base de datos
        List<Recognition> recognitions = recognitionRepository.findAll();

        // Agrupar los registrosn por la columna 'date'
        return recognitions.stream()
                .collect(Collectors.groupingBy(Recognition::getCreated));
    }
}

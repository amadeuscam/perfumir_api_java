package com.amadeuscam.perfumir_api.controllers;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.amadeuscam.perfumir_api.entities.Recognition;
import com.amadeuscam.perfumir_api.services.impl.RecognitionServiceImpl;

@RestController
@RequestMapping("/api/v1/recognitions")
public class RecognitionController {

    @Autowired // This signals to Spring Boot that you want it to wire up the
               // AuthenticationServiceImpl when it
               // creates an instance of AuthController.
    private RecognitionServiceImpl recognitionService;

    /**
     * Get all records of recognitions
     */
    @GetMapping("/")
    public ResponseEntity<List<Recognition>> getAll() {

        return ResponseEntity.ok(recognitionService.getAllRecognitions());
    }

    /**
     * Get all records of recognitions by date
     */
    @GetMapping("/{date}")
    public ResponseEntity<List<Recognition>> getByDate(@PathVariable LocalDate date) {
        return ResponseEntity.ok(recognitionService.getAllRecognitionsByDate(date));
    }

    /**
     * Creates a new recognition record
     */
    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody List<Recognition> recognitions) {
        recognitionService.addRecognition(recognitions);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Deletes a recognition by id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        recognitionService.deleteRecognitions(id);
        return ResponseEntity.noContent().build();
    }

}

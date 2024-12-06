package com.amadeuscam.perfumir_api.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecognitionDto {

    private Long id;

    private Date created;

    private Date updated;

    private String ingredientName;

    private String notes;

    private boolean recognized;
}

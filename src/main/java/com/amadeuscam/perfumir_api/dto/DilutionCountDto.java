package com.amadeuscam.perfumir_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DilutionCountDto {
    private long count;
    private Integer quantity;

    public DilutionCountDto(Integer quantity, long count) {
        this.count = count;
        this.quantity = quantity;
    }
}

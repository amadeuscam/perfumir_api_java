package com.amadeuscam.perfumir_api.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentDto {

    private Long id;

    private Date created;

    private Date updated;

    private String body;

}

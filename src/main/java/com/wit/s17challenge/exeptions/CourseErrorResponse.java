package com.wit.s17challenge.exeptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CourseErrorResponse {
    private Integer status;
    private String message;
    private Long timestamp;

}

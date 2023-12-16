package com.wit.s17challenge.dto;

import com.wit.s17challenge.model.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//datayı transfer eden basit paketler dto, lombok dışında anotation yok
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CourseResponse {
    private Course course;
    private double totalGpa;
}

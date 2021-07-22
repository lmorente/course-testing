package com.demo.courses.testing.examples.classExam.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Exam {

    private Long id;

    private String name;

    private List<String> questions;

    public Exam() {
        this.questions = new ArrayList<>();
    }
}

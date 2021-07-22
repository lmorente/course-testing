package com.demo.courses.testing.examples.classExam.persistence.repository.impl;

import com.demo.courses.testing.examples.classExam.models.Exam;
import com.demo.courses.testing.examples.classExam.persistence.repository.ExamRepositoryMock;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ExamRepositoryMockImpl implements ExamRepositoryMock {

    @Override
    public List<Exam> findAll() {
        return Arrays.asList(new Exam(1L, "Math", Arrays.asList("Q1", "Q2", "Q3")),
                new Exam(2L, "History", Arrays.asList("Q1", "Q2", "Q3", "Q4", "Q5")),
                new Exam(3L, "Literature", Arrays.asList("Q1", "Q2", "Q3", "Q4", "Q5")));
    }
}

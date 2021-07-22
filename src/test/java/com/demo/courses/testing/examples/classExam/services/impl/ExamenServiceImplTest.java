package com.demo.courses.testing.examples.classExam.services.impl;

import com.demo.courses.testing.examples.classExam.models.Exam;
import com.demo.courses.testing.examples.classExam.persistence.repository.ExamRepositoryMock;
import com.demo.courses.testing.examples.classExam.services.ExamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ExamenServiceImplTest {

    private ExamRepositoryMock repository;

    private  ExamService service;
    @BeforeEach
    void setup(){
        repository = mock(ExamRepositoryMock.class);
        service = new ExamenServiceImpl(repository);
    }

    @Test
    void findExamByName() {
        //Given
        List<Exam> data = Arrays.asList(new Exam(1L, "Math", Arrays.asList("Q1", "Q2", "Q3")),
                new Exam(2L, "History", Arrays.asList("Q1", "Q2", "Q3", "Q4", "Q5")),
                new Exam(3L, "Literature", Arrays.asList("Q1", "Q2", "Q3", "Q4", "Q5")));
        when(repository.findAll()).thenReturn(data);

        //When
        Exam exam = service.findExamByName("Math");

        //Then
        assertNotNull(exam);
        assertEquals(1L, exam.getId());
    }

    @Test
    void findExamWithEmptyData() {
        //Given
        List<Exam> data = Collections.emptyList();
        when(repository.findAll()).thenReturn(data);

        //When
        Exam exam = service.findExamByName("Math");

        //Then
        assertNull(exam);
    }
}
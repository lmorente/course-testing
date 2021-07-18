package com.demo.courses.testing.examples.classExam.persistence.repository;

import com.demo.courses.testing.examples.classExam.models.Exam;

import java.util.List;

public interface ExamRepositoryMock {

    List<Exam> findAll();

}

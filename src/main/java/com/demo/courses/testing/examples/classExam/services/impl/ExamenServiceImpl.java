package com.demo.courses.testing.examples.classExam.services.impl;

import com.demo.courses.testing.examples.classExam.models.Exam;
import com.demo.courses.testing.examples.classExam.persistence.repository.ExamRepositoryMock;
import com.demo.courses.testing.examples.classExam.services.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExamenServiceImpl implements ExamService {

    private ExamRepositoryMock repository;

    public ExamenServiceImpl(ExamRepositoryMock repository) {
        this.repository = repository;
    }

    @Override
    public Exam findExamByName(String name) {
        Exam exam = null;
        List<Exam> exams = repository.findAll();
        Optional<Exam> examOpt = exams.stream().filter(e -> name.equals(e.getName())).findFirst();
        if(examOpt.isPresent()){
            exam = examOpt.get();
        }
        return exam;
    }
}

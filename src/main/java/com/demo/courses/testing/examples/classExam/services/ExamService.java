package com.demo.courses.testing.examples.classExam.services;

import com.demo.courses.testing.examples.classExam.models.Exam;

public interface ExamService {

    Exam findExamByName(String name);
}

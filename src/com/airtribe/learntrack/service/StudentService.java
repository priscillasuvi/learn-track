package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.repository.StudentRepository;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;

import java.util.ArrayList;
import java.util.List;

public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(String firstName, String lastName, String email, String batch) {
        InputValidator.isRequired(firstName, "First name");
        InputValidator.isRequired(lastName, "Last name");
        InputValidator.isRequired(batch, "Batch");
        InputValidator.validateEmail(email);
        Student student = new Student(IdGenerator.getNextStudentId(),
                firstName, lastName, email == null ? "" : email, batch);
        studentRepository.getStudents().add(student);
        return student;
    }

    // Overloaded method: student without an email address.
    public Student addStudent(String firstName, String lastName, String batch) {
        return addStudent(firstName, lastName, "", batch);
    }

    // Find Student by id
    public Student findStudentById(int id) {
        return studentRepository.getStudents().stream()
                .filter(s -> s.getId() == id).findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Student with ID " + id + " was not found."));
    }

    public List<Student> listStudents() {
        return studentRepository.getStudents();
    }

    public Student updateStudent(int id, String firstName, String lastName, String email, String batch) {
        Student student = findStudentById(id);
        InputValidator.isRequired(firstName, "First name");
        InputValidator.isRequired(lastName, "Last name");
        InputValidator.isRequired(batch, "Batch");
        InputValidator.validateEmail(email);
        student.setFirstName(firstName.trim());
        student.setLastName(lastName.trim());
        student.setEmail(email == null ? "" : email.trim());
        student.setBatch(batch.trim());
        return student;
    }

    // Delete a student by Id
    public void removeStudent(int id) {
        findStudentById(id).setActive(false);
    }
}

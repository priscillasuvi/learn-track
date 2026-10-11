package com.airtribe.learntrack.service;

import com.airtribe.learntrack.constants.enums.Status;
import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.EnrollmentRepository;
import com.airtribe.learntrack.repository.StudentRepository;
import com.airtribe.learntrack.util.IdGenerator;

import java.util.ArrayList;
import java.util.List;

public class EnrollmentService {

    private final StudentService studentService;
    private final CourseService courseService;
    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentService(StudentService studentService, CourseService courseService, EnrollmentRepository enrollmentRepository) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.enrollmentRepository = enrollmentRepository;
    }

    // Add Student
    public Enrollment enrollStudent(int studentId, int courseId) {
        Student student = studentService.findStudentById(studentId);
        Course course = courseService.findCourseById(courseId);

        if (!student.isActive()) {
            throw new InvalidInputException("Cannot enroll an inactive student.");
        }
        if (!course.isActive()) {
            throw new InvalidInputException("Cannot enroll in an inactive course.");
        }

        boolean alreadyActive = enrollmentRepository.getEnrollments().stream()
                .anyMatch(e -> e.getStudentId() == studentId && e.getCourseId() == courseId
                        && e.getStatus() == Status.ACTIVE);

        if (alreadyActive) {
            throw new InvalidInputException("This student already has an active enrollment in that course.");
        }

        Enrollment enrollment = new Enrollment(
                IdGenerator.getNextEnrollmentId(), studentId, courseId);
        enrollmentRepository.getEnrollments().add(enrollment);
        return enrollment;
    }

    // List enrollments for Student By id
    public List<Enrollment> getEnrollmentsForStudent(int studentId) {
        studentService.findStudentById(studentId);
        List<Enrollment> result = new ArrayList<>();
        List<Enrollment> enrollments = enrollmentRepository.getEnrollments();
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudentId() == studentId) {
                result.add(enrollment);
            }
        }
        return result;
    }

    public List<Enrollment> listEnrollments() {
        return enrollmentRepository.getEnrollments();
    }

    // Update status of the enrollment
    public void updateStatus(int enrollmentId, Status status) {
        Enrollment enrollment = enrollmentRepository.getEnrollments().stream()
                .filter(e -> e.getId() == enrollmentId)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        "Enrollment with ID " + enrollmentId + " was not found."));
        if (status == null) {
            throw new InvalidInputException("Enrollment status is required.");
        }
        enrollment.setStatus(status);
    }
}

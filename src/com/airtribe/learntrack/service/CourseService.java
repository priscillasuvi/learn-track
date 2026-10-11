package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;

import java.util.ArrayList;
import java.util.List;

public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    // Enroll Course - with default Active status
    public Course addCourse(String name, String description, int durationInWeeks) {
        InputValidator.isRequired(name, "Course Name");
        InputValidator.isRequired(description, "Course Description");
        InputValidator.validateInput(durationInWeeks, "Course Duration");
        Course course = new Course(IdGenerator.getNextCourseId(), name, description, durationInWeeks);
        courseRepository.getCourses().add(course);
        return course;
    }

    // Find Course By id - if not present throw EntityNotFoundException
    public Course findCourseById(int id) {
        return courseRepository.getCourses().stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Course with ID " + id + " was not found."));
    }

    // List All Courses
    public List<Course> listCourses() {
        return new ArrayList<>(courseRepository.getCourses());
    }

    public void setCourseActive(int id, boolean active) {
        Course course = findCourseById(id);
        course.setActive(active);
    }

    // DeActivate the course
    public void deactivateCourse(int id) {
        setCourseActive(id, false);
    }

    // Activate the course
    public void activateCourse(int id) {
        setCourseActive(id, true);
    }
}

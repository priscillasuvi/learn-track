package com.airtribe.learntrack;

import com.airtribe.learntrack.constants.enums.Status;
import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.repository.EnrollmentRepository;
import com.airtribe.learntrack.repository.StudentRepository;
import com.airtribe.learntrack.service.CourseService;
import com.airtribe.learntrack.service.EnrollmentService;
import com.airtribe.learntrack.service.StudentService;

import java.util.List;
import java.util.Scanner;

public class LearnTrackApplication {
    private final StudentRepository studentRepository = new StudentRepository();
    private final EnrollmentRepository enrollmentRepository = new EnrollmentRepository();
    private final CourseRepository courseRepository = new CourseRepository();
    private final Scanner scanner = new Scanner(System.in);
    private final StudentService studentService = new StudentService(studentRepository);
    private final CourseService courseService = new CourseService(courseRepository);
    private final EnrollmentService enrollmentService =
            new EnrollmentService(studentService, courseService, enrollmentRepository);

    public static void main(String[] args) {
        new LearnTrackApplication().run();
    }

        private void run() {
            boolean running = true;
            System.out.println("Student & Course Management System");
            while (running) {
                printMenu();
                try {
                    int choice = readInt("Choose an option: ");
                    switch (choice) {
                        case 1 -> addStudent();
                        case 2 -> listStudents();
                        case 3 -> searchStudent();
                        case 4 -> deactivateStudent();
                        case 5 -> updateStudent();
                        case 6 -> addCourse();
                        case 7 -> listCourses();
                        case 8 -> changeCourseStatus();
                        case 9 -> enrollStudent();
                        case 10 -> viewStudentEnrollments();
                        case 11 -> updateEnrollmentStatus();
                        case 12 -> listAllEnrollments();
                        case 0 -> {
                            running = false;
                            System.out.println("Thank you.");
                        }
                        default -> System.out.println("Invalid menu option. Please choose 0-12.");
                    }
                } catch (EntityNotFoundException | InvalidInputException ex) {
                    System.out.println("Error: " + ex.getMessage());
                } catch (IllegalArgumentException ex) {
                    System.out.println("Invalid input: " + ex.getMessage());
                }
                System.out.println();
            }
            scanner.close();
        }

        private void printMenu() {
            System.out.println("""
                ---------------- MENU ----------------
                STUDENT MANAGEMENT
                1. Add student
                2. View all students
                3. Search student by ID
                4. Deactivate student
                5. Update student
                COURSE MANAGEMENT
                6. Add course
                7. View all courses
                8. Activate / deactivate course
                ENROLLMENT MANAGEMENT
                9. Enroll student in course
                10. View enrollments for student
                11. Complete / cancel enrollment
                12. View all enrollments
                0. Exit
                --------------------------------------
                """);
        }

        private void addStudent() {
            String firstName = readText("First name: ");
            String lastName = readText("Last name: ");
            String email = readText("Email (optional; press Enter to skip): ");
            String batch = readText("Batch: ");
            Student student = studentService.addStudent(firstName, lastName, email, batch);
            System.out.println("Student created: " + student);
        }

        private void listStudents() {
            List<Student> students = studentService.listStudents();
            if (students.isEmpty()) {
                System.out.println("No students found.");
                return;
            }
            students.forEach(System.out::println);
        }

        private void searchStudent() {
            int id = readInt("Student ID: ");
            System.out.println(studentService.findStudentById(id));
        }

        private void deactivateStudent() {
            int id = readInt("Student ID to deactivate: ");
            studentService.removeStudent(id);
            System.out.println("Student deactivated.");
        }

        private void updateStudent() {
            int id = readInt("Student ID to update: ");
            String firstName = readText("New first name: ");
            String lastName = readText("New last name: ");
            String email = readText("New email (optional): ");
            String batch = readText("New batch: ");
            System.out.println("Updated: " +
                    studentService.updateStudent(id, firstName, lastName, email, batch));
        }

        private void addCourse() {
            String name = readText("Course name: ");
            String description = readText("Description: ");
            int duration = readInt("Duration in weeks: ");
            Course course = courseService.addCourse(name, description, duration);
            System.out.println("Course created: " + course);
        }

        private void listCourses() {
            List<Course> courses = courseService.listCourses();
            if (courses.isEmpty()) {
                System.out.println("No courses found.");
                return;
            }
            courses.forEach(System.out::println);
        }

        private void changeCourseStatus() {
            int id = readInt("Course ID: ");
            String action = readText("Enter A to activate or D to deactivate: ");
            if (action.equalsIgnoreCase("A")) {
                courseService.activateCourse(id);
                System.out.println("Course activated.");
            } else if (action.equalsIgnoreCase("D")) {
                courseService.deactivateCourse(id);
                System.out.println("Course deactivated.");
            } else {
                throw new InvalidInputException("Enter A or D.");
            }
        }

        private void enrollStudent() {
            int studentId = readInt("Student ID: ");
            int courseId = readInt("Course ID: ");
            System.out.println("Enrollment created: " +
                    enrollmentService.enrollStudent(studentId, courseId));
        }

        private void viewStudentEnrollments() {
            int studentId = readInt("Student ID: ");
            List<Enrollment> enrollments =
                    enrollmentService.getEnrollmentsForStudent(studentId);
            if (enrollments.isEmpty()) {
                System.out.println("No enrollments found for this student.");
                return;
            }
            enrollments.forEach(System.out::println);
        }

        private void updateEnrollmentStatus() {
            int id = readInt("Enrollment ID: ");
            String status = readText("Enter ACTIVE, COMPLETED, or CANCELLED: ");
            try {
                Status parsedStatus = Status.valueOf(status.toUpperCase());
                enrollmentService.updateStatus(id, parsedStatus);
                System.out.println("Enrollment status updated.");
            } catch (IllegalArgumentException ex) {
                throw new InvalidInputException("Status must be ACTIVE, COMPLETED, or CANCELLED.");
            }
        }

        private void listAllEnrollments() {
            List<Enrollment> enrollments = enrollmentService.listEnrollments();
            if (enrollments.isEmpty()) {
                System.out.println("No enrollments found.");
                return;
            }
            enrollments.forEach(System.out::println);
        }

        private String readText(String prompt) {
            System.out.print(prompt);
            return scanner.nextLine().trim();
        }

        private int readInt(String prompt) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException ex) {
                throw new InvalidInputException("Please enter a valid whole number.");
            }
        }
    }
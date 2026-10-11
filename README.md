# Student and Course Management System

A Java 21 console application for managing students, courses, and enrollments. It demonstrates OOP, packages, encapsulation, inheritance, polymorphism, static utility methods, collections, and custom exception handling.

## Features
- Add, list, search, update, and deactivate students
- Add and list courses; activate or deactivate courses
- Enroll active students in active courses
- View enrollments and change enrollment status
- Validate user input and show friendly error messages
- Store data in memory using `ArrayList` (data resets when the application exits)

## Requirements
- JDK 21 (JDK 17+ may work, but Java 21 is the documented version)
- IntelliJ IDEA or a terminal

## Run in IntelliJ IDEA
1. Open this folder as a project.
2. Set Project SDK to JDK 21 in **File > Project Structure > Project**.
3. Mark `src` as **Sources Root** if IntelliJ has not done so.
4. Open `src/com/airtribe/learntrack/ui/LearnTrackApplication.java`.
5. Click the green Run icon next to `main`.

## Compile and run from a terminal
From the project root:

```bash
javac -d out $(find src -name "*.java")
java -cp out com.airtribe.learntrack.ui.Main
```

On Windows Command Prompt, compile all Java files with:

```bat
dir /s /b src\*.java > sources.txt
javac -d out @sources.txt
java -cp out com.airtribe.learntrack.ui.LearnTrackApplication
```

## Package layout
- `entity`: domain objects
- `service`: business logic and in-memory collections
- `ui`: menu-driven console UI
- `exception`: custom exceptions
- `util`: ID generation and validation
- `docs`: setup and design documentation

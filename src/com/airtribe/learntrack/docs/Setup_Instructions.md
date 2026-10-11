# Setup Instructions

## JDK version
This project targets **JDK 21**. If another compatible JDK is used, update this note to match the installed version.

## Install and configure Java
1. Install a JDK 21 distribution from a trusted provider, such as Eclipse Temurin or Oracle.
2. In IntelliJ IDEA, open **File > Project Structure > Project**.
3. Set **SDK** to the installed JDK 21. If it is missing, choose **Add SDK > JDK** and select the JDK installation directory.
4. Set the language level to 21 (or use the SDK default).
5. Mark the `src` directory as **Sources Root**.

Verify the installation in a terminal:

```bash
java -version
javac -version
```

Both commands should report version 21 if JDK 21 is installed.

## Run Hello World
Create `HelloWorld.java`:

```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

Run it using the green Run icon in IntelliJ. The Run window should display:

```text
Hello, World!
```

Alternatively, from the directory containing the file:

```bash
javac HelloWorld.java
java HelloWorld
```

### Screenshot evidence
A screenshot can be captured after running the program in IntelliJ: include the editor with `HelloWorld.java` and the Run window showing `Hello, World!`. A real screenshot should be added by the student after running the program on their own machine.

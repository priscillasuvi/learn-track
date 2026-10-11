# JVM Basics

## JDK, JRE, and JVM
The **JVM (Java Virtual Machine)** runs Java bytecode. It manages runtime tasks such as loading classes and executing code. The **JRE (Java Runtime Environment)** is the runtime components needed to run Java applications, including the JVM and supporting libraries. The **JDK (Java Development Kit)** is used to develop Java applications; it includes tools such as the Java compiler (`javac`) as well as the runtime components. For development, install a JDK.

## What is bytecode?
When a Java source file (`.java`) is compiled with `javac`, the compiler produces bytecode in a `.class` file. Bytecode is an intermediate instruction format understood by the JVM, rather than directly being the native machine instructions of one specific operating system.

## What does “write once, run anywhere” mean?
Java source code can be compiled into bytecode once, then run on any platform that has a compatible JVM. This helps the same application run on Windows, macOS, and Linux without compiling a separate native program for each operating system. Platform-specific dependencies or differences can still affect portability, so the phrase describes Java's portability goal rather than an absolute guarantee.

# Employee Leave Management System

A console-based Core Java project built for **HCL Training – Assessment 1: Core Java Checkpoint**.

## Features

1. Add employee (Permanent or Contract)
2. Display all employees using `Iterator`
3. Search employee by ID
4. Apply for leave
5. Approve leave and deduct balance
6. Reject leave without changing balance
7. Display all leave requests
8. Display an employee's leave balance
9. Exit

## Core Java concepts

- Encapsulation, inheritance, method overloading and overriding
- Runtime polymorphism, constructors, `this` and `super`
- `ArrayList`, `HashSet`, `HashMap`, and `Iterator`
- Enums and custom exceptions
- Input validation and exception handling

## Requirements

- Java JDK 8 or later
- No external dependencies

## Run from the project folder

Compile:

```bash
javac *.java
```

Run:

```bash
java LeaveManagementSystem
```

## Example workflow

1. Select `1` and add employee `EMP101` with leave balance `18`.
2. Select `4` and create a `CASUAL` leave request for `3` days.
3. Select `5` and approve the generated request ID (for example, `LR1001`).
4. The leave balance should become `15`.

## Notes

- Data is stored in memory and is reset when the program exits.
- This is an educational console application, not a production HR system.
- Review and understand the code, then test the required edge cases before submitting.

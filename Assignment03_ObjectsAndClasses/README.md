# Assignment 3 – Objects, Classes & Arrays of Objects (CS9053, NYU)

This assignment focuses on designing custom classes in Java, working with object methods, and storing objects in arrays. It includes implementing a `ComplexNumber` class, a `Student` class, and a `Gradebook` class. :contentReference[oaicite:1]{index=1}

---

## Part I – Complex Numbers

I implemented a `ComplexNumber` class based on the provided UML. It includes:

- Private fields: `real`, `imaginary`
- Public getters and setters
- Mathematical operations:
  - `add(ComplexNumber other)`
  - `subtract(ComplexNumber other)`
  - `multiply(ComplexNumber other)`
  - *Extra credit:* `divide(ComplexNumber other)`
- `magnitude()`: computes √(a² + b²)
- `toString()`: returns `<real> + i<imaginary>`

The main method creates:
- `7.5 + i4.2`
- `8.2 + i9.4`

and performs addition, subtraction, and multiplication.

---

## Part II – Student Class

The `Student` class stores a student’s name, ID, and an array of 5 grades. Features include:

- Automatic ID generation
- `setGrade(index, value)`:
  - Only valid if index is 0–4 and grade is 0–100
- `getGrades()` returns a *copy* of the grades array
- `getAverage()` ignores `-1` entries (unsubmitted assignments)

All fields and methods follow the UML from the instructions.

---

## Part III – Gradebook Class

The `Gradebook` stores an array of `Student` objects and provides:

- `addStudent(Student s)`: fills the first `null` slot
- `findById(int id)`
- `getTopStudent()`: highest average grade
- `printAll()`: prints all students with their info

The main method:

1. Creates a Gradebook for 5 students  
2. Instantiates 5 Student objects  
3. Assigns random grades (0–100)  
4. Prints all students  
5. Prints the top student

---

## Skills Demonstrated

- Object-oriented design (encapsulation, classes, fields)
- Methods that operate on objects
- Arrays of objects (`Student[]`)
- Copying arrays to preserve encapsulation
- Computing averages while ignoring missing values
- Implementing mathematical operations using object fields

This assignment strengthens OOP fundamentals, preparing for more complex designs later.

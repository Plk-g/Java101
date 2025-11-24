# Assignment 5 – Abstract Classes, Interfaces & Generics (CS9053, NYU)

This assignment focuses on three major Java concepts:  
**abstract classes**, **interfaces (Comparable)**, and **generics with type constraints**.  
It builds a task-processing simulation involving different developers and a vector-magnitude sorting system. :contentReference[oaicite:1]{index=1}

---

# Part I – Abstract Classes & Polymorphism

I implemented an abstract class **Task**, which contains:

- `String module`
- `int time`
- abstract method `performTask()`

Then I created three subclasses:

- **DevelopModule**
- **DocumentModule**
- **TestModule**

Each class overrides `performTask()` to print messages like:

- “Developing module X”
- “Documenting module X”
- “Testing module X”

A parameterized `ArrayList<Task>` named `jobQueue` stores all tasks.

---

## Person Hierarchy

I implemented an abstract class **Person** with:

- A field: `ArrayList<Task> jobQueue`
- Abstract method: `startTask(String moduleName, int time)`

Then three concrete subclasses:

- **Developer**
- **Tester**
- **TechWriter**

Each implements `startTask()` by creating the correct Task subclass and adding it to `jobQueue`.

### Task Generation

- Created one Person of each type
- Generated **5 random tasks per person**
  - Random 4-digit module name
  - Random time from 1–10
- Added all tasks to `jobQueue`
- Iterated through the queue:
  - Called `performTask()`
  - Computed the total processing time

---

# Part II – Interfaces (Comparable)

`Task` implements `Comparable<Task>` so tasks can be sorted by their `time` field.

Sorting:  
```java
Collections.sort(jobQueue);

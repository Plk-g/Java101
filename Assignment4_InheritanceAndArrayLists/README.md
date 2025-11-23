# Assignment 4 – Inheritance & ArrayLists (CS9053, NYU)

This assignment focuses on object-oriented programming with inheritance, method overriding, polymorphism, and using `ArrayList` with custom objects. It includes designing an employee class hierarchy and simulating workplace behavior. :contentReference[oaicite:1]{index=1}

---

## Part I – Employee Inheritance Hierarchy

I implemented the following class structure:

- **Employee** (base class)  
- **Manager** (subclass)  
- **Intern** (subclass)

### Features Implemented

- Automatic ID assignment using a static `numberOfEmployees` counter  
- Private fields such as:
  - `name`, `age`, `salary`, `id`
  - `teamSize` in Manager
  - `school` or `duration` in Intern
- Getters and setters for all fields
- `toString()` overridden in each class
- `displayInfo()` prints the class-specific details directly
- `equals()` compares:
  - class type
  - field values
  - **id** (as required) :contentReference[oaicite:2]{index=2}
- `work()` method:
  - Employee → “doing work”
  - Manager → “managing team of <teamSize>”
  - Intern → Something like “interning for <duration> months”

### Test Objects

Created:
- 1 Manager
- 2 Employees
- 2 Interns  
(as required by Part I) :contentReference[oaicite:3]{index=3}

---

## Part II – Workplace with ArrayList

I implemented a `Workplace` class that contains an `ArrayList<Employee>`.

### Included Methods

- `addEmployee(Employee e)` → adds Employee or subclass  
- `listEmployees()` → prints all employees
- `processWorkday()` → loops through all employees and calls `work()`  
  This demonstrates **polymorphism**, since each subclass has its own version of `work()`.

### Workflow Implemented

1. Instantiate a `Workplace`  
2. Add the Manager, Employees, and Interns  
3. Print all employees  
4. Run `processWorkday()` to call each object's `work()` method  
   (e.g., “managing team…”, “doing work”, “interning…”)  
   :contentReference[oaicite:4]{index=4}

---

## Skills Demonstrated

- Java inheritance (`extends`)
- Method overriding and polymorphism
- Using `ArrayList<Employee>` to store mixed subclasses
- Static fields for ID generation
- Designing classes based on UML diagrams
- Clean separation between model classes and the driver program

This assignment reinforces real-world OOP design patterns used in software engineering.

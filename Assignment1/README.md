# Assignment 1 – Java Basics (CS9053, NYU)

This assignment practices the core skills I'll use in more complex Java programs:
- Setting up a project and running code in Eclipse
- Printing output with `System.out.println`
- Doing basic arithmetic and storing results in variables
- Reading input from the console and via command-line arguments
- Implementing a mathematical formula in Java using `double` and scientific notation

## Structure

- `Part0_HelloWorld_Debug/`  
  - Simple `HelloWorld` program run in Eclipse and from the terminal.
  - Practiced:
    - Using breakpoints and stepping through code in the debugger
    - Compiling manually with `javac` and running with `java`

- `Part1_Output_Math/`  
  - `MyInitials.java`: prints my initials using "big letters" made of characters.  
  - `PrintResults.java`: prints the results of a series of arithmetic expressions in the format  
    `the value of a is [value]`.

- `Part2_Input_Name/`  
  - `GetNameConsole.java`: reads a name from the console and prints `Hello, [name]!`.  
  - `GetNameArgument.java`: reads a name from `args[0]` (command-line argument) and prints `Hello, [name]!`.
  - Configured a Run Configuration in Eclipse to pass command-line arguments.

- `Part3_BlackbodyRadiation/`  
  - `BlackbodyRadiation.java`: implements the `calculateEnergy` method to compute spectral radiance for a star
    given frequency and temperature using the blackbody radiation formula.
  - Uses:
    - `double` precision arithmetic
    - scientific notation literals in Java
    - constants (Planck's constant, speed of light, Boltzmann constant)
    - examples for Betelgeuse and the Sun.

## Concepts Practiced

- Java project setup in Eclipse
- Debugging with breakpoints and stepping through code
- Printing formatted results
- Reading input from the console (`Scanner`) and via command-line arguments (`String[] args`)
- Translating a physics formula into Java code
- Working with `double` and scientific notation

## How to Run

From Eclipse:
- Import the project and run each class as a "Java Application".
- For `GetNameArgument`, set the program arguments in **Run Configurations → Arguments**.

From the terminal (example for Part 0):

```bash
cd Part0_HelloWorld_Debug
javac -d bin/ src/HelloWorld.java
cd bin
java HelloWorld

# Assignment 2 – Loops, Arrays, and Strings

This assignment focuses on three core Java concepts:
- Using loops to approximate π numerically
- Working with 2D arrays and traversing them in different patterns
- Manipulating strings to build acronyms and mirrored strings :contentReference[oaicite:1]{index=1}

---

## Part I – Approximating π with the Leibniz Formula

I implemented a method that uses the Leibniz series to estimate π:

\[
\frac{\pi}{4} = 1 - \frac{1}{3} + \frac{1}{5} - \frac{1}{7} + \dots
\]

Using a loop, the program keeps adding terms until the estimate of π is within `0.00001` of `Math.PI`.  
It then prints:

`pi is estimated as <value> after <iterations> iterations`

This part practices:
- While/for loops
- Alternating signs in a series
- Using a stopping condition based on numeric error

---

## Part II – 2D Arrays and Spiral Matrix

The provided class `SpiralMatrix` contains a 5×4 matrix (5 rows, 4 columns). :contentReference[oaicite:2]{index=2}

I did two things:

1. **Print the matrix row by row** using nested loops.
2. **Return a "spiral order" traversal** of the matrix, starting from the top-left and moving:
   - right → down → left → up, and repeating this pattern until all elements are visited.

Example of spiral order for:

\[
\begin{bmatrix}
1 & 2 & 3 \\
4 & 5 & 6 \\
7 & 8 & 9
\end{bmatrix}
\]

is:

`[1, 2, 3, 6, 9, 8, 7, 4, 5]`

Concepts practiced:
- 2D arrays (`int[][]`)
- Nested loops
- Careful index management (top, bottom, left, right bounds)
- Building and returning a 1D array from a 2D structure

---

## Part III – String Manipulation

### 1. Acronym Generator

Given a sentence like:

`"As Soon As Possible"`

the program returns the acronym:

`"ASAP"` :contentReference[oaicite:3]{index=3}

Techniques used:
- `split(" ")` to break a sentence into words
- `charAt(0)` to get the first letter of each word
- `toUpperCase()` to normalize the acronym
- String concatenation using `+=` or `concat()`

### 2. Mirror String Generator

Given a string, the program appends its reverse to create a "mirrored" version.

Examples:
- `"hello"` → `"helloolleh"`
- `"java"` → `"javaavaj"`
- `"a"` → `"aa"` :contentReference[oaicite:4]{index=4}

This part practices:
- Iterating over a string from end to beginning
- Building a reversed version manually
- Combining the original and reversed strings

---

## Skills Demonstrated

- Loop control and numeric approximation
- 2D arrays and custom traversal patterns
- Console output and clean formatting
- String processing: splitting, indexing, transforming, and concatenation

This assignment builds on basic Java syntax and reinforces problem-solving with loops, arrays, and strings.

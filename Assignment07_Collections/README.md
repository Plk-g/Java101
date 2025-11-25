# Assignment 7 – Collections: Sets, Maps, Queues & Custom Linked List

This assignment focuses on several Java Collection Framework concepts:
- Sets (unique vs duplicate words)
- Maps (tracking user activity)
- Queues (per-user recent URLs)
- Implementing a custom parameterized linked list  
based on the Queue interface. :contentReference[oaicite:1]{index=1}

---

# Part I – Sets: Unique & Duplicate Words

Given the text file **little_women.txt**, I implemented logic to:

1. Read the file line by line  
2. Split each line into individual words  
3. Clean punctuation using `cleanString()`  
4. Build two sets:
   - A set of **words appearing once**
   - A set of **words appearing more than once**

At the end, the program prints how many words appear in each set.

Skills demonstrated:
- `HashSet`
- Text cleanup and normalization
- File reading with `BufferedReader`
- Counting logic using sets

---

# Part II – Maps & URL Tracker

Using the log file **server_log.csv**, which contains:

timestamp, userId, url

I built:

### ✔ A Map **user → Queue of last 5 URLs**
- For each user:
  - Append URLs to their queue
  - If the queue exceeds 5 items, remove the oldest (FIFO behavior)
- Implemented with:
  - `Map<String, Queue<String>>`
  - `LinkedList<String>` as the queue implementation

### ✔ A Map **URL → number of total visits**
- Counts how many times each URL appears across all users.

The output matches the required format, e.g.:

u001 -> [/products, /checkout, /orders, /logout, /home]
...
URL Visit Counts:
/home : 4
/products : 4
/checkout : 3
...

Skills demonstrated:
- `HashMap`
- `Queue` behavior using `LinkedList`
- CSV parsing
- Maintaining fixed-size queues
- Frequency counting maps

---

# Part III – Custom Linked List (Generics + Queue Interface)

I implemented a **parameterized CustomLinkedList<T>** class in the `linkedlist` package that:

- Contains internal `Node<T>` objects (`val`, `next`)
- Implements the `Queue<T>` interface
- Only implements the methods required by the assignment (others marked “// Ignore”)

In the main method:

1. Created both:
   - `CustomLinkedList<Integer>`
   - `LinkedList<Integer>`
2. Added **100,000 random integers** to each
3. Measured and printed the time taken

The purpose is to compare performance between:
- Java’s built-in `LinkedList`
- A manually implemented linked list

Skills demonstrated:
- Custom data structures
- Queue interface methods (`add`, `poll`, `peek`, etc.)
- Performance benchmarking
- Generics with nodes & linked structures

---

# Technologies & Concepts Used
- `HashSet`, `HashMap`, `Queue`, `LinkedList`
- File reading (`BufferedReader`, CSV parsing)
- Custom generic data structure design
- Time benchmarking with `System.nanoTime()`
- Logic for unique/duplicate detection
- URL tracking and sliding-window queues

This assignment ties together core Collection Framework concepts and introduces real-world data processing patterns.

# Assignment 8 – Multithreaded Pipeline System (CS9053, NYU)

This assignment implements a fully multithreaded data-processing pipeline.  
Four modules—**Reader**, **Validator**, **ErrorWriter**, and **Aggregator**—run in separate threads and communicate using bounded blocking queues. :contentReference[oaicite:1]{index=1}

The goal is to process transaction records from `transactions.csv`, validate them, write errors to `errors.csv`, and aggregate valid transaction totals per user per day.

---

## 🧵 System Overview: 4-Stage Pipeline

### 1. **Reader Thread**
- Reads lines from `transactions.csv`
- Parses each line into a `Tx` (transaction) object
- Wraps it in a `MsgTx` message object
- Places `MsgTx` into the **validator queue**
- When the file ends, pushes one or more **Stop messages** into the validator queue

### 2. **Validator Threads (2 threads)**
- Two parallel validator threads read from the shared validator queue
- Each:
  - Validates the Tx object using the provided `validate()` method
  - If missing or invalid data → push message to **error queue**
  - If valid → push message to **aggregator queue**
- When a Stop message is received:
  - Each validator forwards **Stop** to both:
    - ErrorWriter queue
    - Aggregator queue
  - Then terminates

### 3. **ErrorWriter Thread**
- Reads from the error queue
- Writes invalid transactions to `errors.csv`, including the reason for failure
- Stops when it receives a Stop message

### 4. **Aggregator Thread**
- Reads from the aggregator queue
- Groups valid transactions by **AggKey (userId + date)**  
- Aggregates the total price (in cents) for each key
- After receiving Stop:
  - Outputs the **Top 10 aggregates**, e.g.:

a5 @ 2024-11-03 -> 5000 cents
a3 @ 2024-11-03 -> 4700 cents
a1 @ 2024-11-02 -> 3799 cents
...
---

## 📦 Queues

All communication uses **bounded blocking queues**:

* Capacity: **256**
* Threads must block when queues are full (no busy waiting)
* `BlockingQueue` or a custom blocking queue implementation was used

Queues used:

* Reader → Validator queue
* Validator → ErrorWriter queue
* Validator → Aggregator queue

This design prevents race conditions and manages backpressure naturally.

---

## 🛑 Stop Message Protocol (Shutdown Process)

To cleanly end all threads:

* Reader sends **Stop** to Validator queue
* Validators:

  * On receiving Stop:

    * Forward Stop to ErrorWriter queue
    * Forward Stop to Aggregator queue
    * Stop themselves
* ErrorWriter & Aggregator stop once they receive Stop
* Main thread waits using `join()` for:

  * Reader
  * 2 Validators
  * ErrorWriter
  * Aggregator

After all join successfully, final aggregated results and errors are printed.

---

## 🔒 Synchronization & Threads

Special care was taken to avoid **race conditions**, especially since:

* Two Validator threads share:

  * The same input queue
  * The same output aggregator queue
  * The same error queue

Synchronization techniques used:

* BlockingQueue internal locking
* Avoiding shared mutable state except through messages
* Ensuring each stage only mutates its local data
* Using thread-safe maps or synchronized regions for aggregation

---

## 🧮 Data Processing Summary

* Input: `transactions.csv`

  * transaction id
  * account id
  * timestamp
  * price in cents
* Output:

  * `errors.csv` (invalid records + reasons)
  * Printed **Top 10 aggregated totals per user per day**

---

## 🧠 Skills Demonstrated

* Multithreading and thread lifecycle management
* Producer/consumer design
* Bounded blocking queues
* Safe shutdown signaling using Stop messages
* File I/O (reading CSV, writing error logs)
* Aggregation using Maps keyed by user+date
* Performance-aware pipeline design
* Thread synchronization & race condition avoidance

This assignment models real-world distributed data pipelines and processing systems, similar to log processors or ETL (Extract-Transform-Load) jobs.

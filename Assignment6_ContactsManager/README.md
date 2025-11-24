# Assignment 6 – Contacts Manager UI Application (CS9053, NYU)

This assignment focuses on building a full Java Swing UI application.  
The goal is to create a **Contacts Manager** that allows users to add, remove, clear, save, and load contact entries.  
The assignment also practices working with **JTable**, **DefaultTableModel**, file dialogs, and layouts. :contentReference[oaicite:1]{index=1}

---

## 📌 Features Implemented

### ✔ Add Contact  
Users can enter contact information (name, phone, email, etc.) into input fields and click **Add** to insert it into the table.

### ✔ Remove Contact  
Deletes the currently selected row in the table.

### ✔ Clear All  
Removes **all** contact entries from the table.

### ✔ Scrollable Table  
Contacts are displayed in a `JTable` wrapped in a `JScrollPane`, enabling scrolling for large datasets.

---

## 📁 File Menu Options

The application contains a **File** menu with:

### ✔ Open  
Loads contacts from a CSV file into the table.

### ✔ Save  
Saves the current table contents into a CSV file.

### ✔ Exit  
Closes the program.

These features use a `JFileChooser`, as required.  
Skeleton code provided by the instructor guided implementation. :contentReference[oaicite:2]{index=2}

---

## 🖥 Swing UI Components Used

- `JFrame`
- `JPanel`
- `BorderLayout`
- `JTextField` (for data entry)
- `JTable`
- `DefaultTableModel`
- `JScrollPane`
- `JButton`
- `JMenuBar`, `JMenu`, `JMenuItem`
- `JFileChooser`

Hints in the assignment recommended using **BorderLayout** and the **WEST** region for left-aligned controls.  
Extra credit (2 pts): replicating the example layout exactly. :contentReference[oaicite:3]{index=3}

---

## 📝 Program Behavior

- Adding contacts updates the table immediately.  
- Removing contacts only works when a row is selected.  
- Clearing resets the table to empty.  
- CSV load/save formats rows using comma-separated values.  
- Closing the window exits the application.

---

## 🎯 Skills Demonstrated

- Java Swing GUI development  
- Using `DefaultTableModel` for dynamic tables  
- Handling UI events with ActionListeners  
- Reading and writing CSV files  
- File dialog handling with `JFileChooser`  
- Application layout and component organization  

This assignment builds practical desktop UI development skills and introduces real-world Java patterns for managing visual components and persistent data.

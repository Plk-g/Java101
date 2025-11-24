import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ContactBookManager extends JFrame {
    //table & model
    private final JTable contactTable;
    private final DefaultTableModel tableModel;

    // left style
    private final JTextField nameField   = new JTextField(16);
    private final JTextField streetField = new JTextField(16);
    private final JTextField cityField   = new JTextField(16);
    private final JTextField stateField  = new JTextField(8);
    private final JTextField phoneField  = new JTextField(12);
    private final JTextField emailField  = new JTextField(16);

    private static final String DEFAULT_DIR = System.getProperty("user.dir");

    public ContactBookManager() {
        super("Contact Book Manager");

        // window basics
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // close window exits app
        setLayout(new BorderLayout(8, 8));
        setMinimumSize(new Dimension(930, 560));
        setJMenuBar(buildMenuBar());

        // model + table
        String[] columnNames = {"Name", "Street", "City", "State", "Phone", "Email"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        contactTable = new JTable(tableModel);
        contactTable.setFillsViewportHeight(true);
        contactTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        contactTable.setRowHeight(22);
        contactTable.setShowGrid(true);
        contactTable.setGridColor(new Color(220, 220, 220));
        contactTable.setIntercellSpacing(new Dimension(1, 1));

        Font h = contactTable.getTableHeader().getFont();
        contactTable.getTableHeader().setFont(h.deriveFont(h.getStyle() | Font.BOLD, h.getSize() + 1f));

        contactTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        int[] widths = {140, 180, 120, 60, 120, 180}; // Name, Street, City, State, Phone, Email
        for (int i = 0; i < widths.length; i++) {
            contactTable.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        }

        // layout regions
        add(buildLeftPanel(), BorderLayout.WEST);

        JScrollPane sp = new JScrollPane(contactTable);
        sp.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6)); 
        add(sp, BorderLayout.CENTER);

        add(buildBottomBar(), BorderLayout.SOUTH);

        pack();
        setLocationByPlatform(true);
    }

    // UI Stuff
    private JMenuBar buildMenuBar() {
        JMenuBar mb = new JMenuBar();
        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);

        // Java 8 compatible shortcut mask:
        int cmdMask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

        JMenuItem open = new JMenuItem("Open…");
        open.setMnemonic(KeyEvent.VK_O);
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, cmdMask));
        open.addActionListener(e -> doOpen());

        JMenuItem save = new JMenuItem("Save…");
        save.setMnemonic(KeyEvent.VK_S);
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, cmdMask));
        save.addActionListener(e -> doSave());

        JMenuItem exit = new JMenuItem("Exit");
        exit.setMnemonic(KeyEvent.VK_X);
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, cmdMask));
        exit.addActionListener(e -> System.exit(0));

        file.add(open);
        file.add(save);
        file.addSeparator();
        file.add(exit);
        mb.add(file);
        return mb;
    }

    private JPanel buildLeftPanel() {
        JPanel left = new JPanel(new BorderLayout());

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(3, 6, 3, 6);           // slightly tighter gaps
        gc.anchor = GridBagConstraints.LINE_START;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1.0;

        int row = 0;
        row = addRow(form, gc, row, "Name:",   nameField);
        row = addRow(form, gc, row, "Street:", streetField);
        row = addRow(form, gc, row, "City:",   cityField);
        row = addRow(form, gc, row, "State:",  stateField);
        row = addRow(form, gc, row, "Phone:",  phoneField);
        row = addRow(form, gc, row, "Email:",  emailField);

        JButton addBtn = new JButton("Add");
        addBtn.setMargin(new Insets(3, 12, 3, 12));   
        addBtn.addActionListener(e -> addContact());
        gc.gridx = 1; gc.gridy = row; gc.weightx = 0;
        form.add(addBtn, gc);

        JPanel wrap = new JPanel(new BorderLayout());
        wrap.add(form, BorderLayout.NORTH);
        wrap.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(8, 8, 8, 8),
                BorderFactory.createTitledBorder("Add Contact") 
        ));

        left.add(wrap, BorderLayout.WEST); 
        return left;
    }

    private int addRow(JPanel p, GridBagConstraints gc, int row, String label, JComponent field) {
        gc.gridx = 0; gc.gridy = row; gc.weightx = 0;
        p.add(new JLabel(label), gc);
        gc.gridx = 1; gc.gridy = row; gc.weightx = 1.0;
        p.add(field, gc);
        return row + 1;
    }

    private JPanel buildBottomBar() {
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottom.setBorder(BorderFactory.createEmptyBorder(6, 8, 10, 8));

        JButton remove = new JButton("Remove Selected");
        JButton clear  = new JButton("Clear All");
        remove.setMargin(new Insets(3, 10, 3, 10));
        clear.setMargin(new Insets(3, 10, 3, 10));

        remove.addActionListener(e -> removeSelected());
        clear.addActionListener(e -> clearAll());

        bottom.add(remove);
        bottom.add(clear);
        return bottom;
    }

    // actions 
    private void addContact() {
        String name   = nameField.getText().trim();
        String street = streetField.getText().trim();
        String city   = cityField.getText().trim();
        String state  = stateField.getText().trim();
        String phone  = phoneField.getText().trim();
        String email  = emailField.getText().trim();

        if (name.isEmpty() && street.isEmpty() && city.isEmpty()
                && state.isEmpty() && phone.isEmpty() && email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter at least one field.",
                    "Nothing to add", JOptionPane.WARNING_MESSAGE);
            return;
        }
        tableModel.addRow(new Object[]{name, street, city, state, phone, email});

        nameField.setText(""); streetField.setText(""); cityField.setText("");
        stateField.setText(""); phoneField.setText(""); emailField.setText("");
        nameField.requestFocusInWindow();
    }

    private void removeSelected() {
        int row = contactTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a contact to remove.",
                    "No selection", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        tableModel.removeRow(row);
    }

    private void clearAll() {
        if (tableModel.getRowCount() == 0) return;
        int confirm = JOptionPane.showConfirmDialog(this,
                "Clear all contacts?", "Confirm", JOptionPane.OK_CANCEL_OPTION);
        if (confirm == JOptionPane.OK_OPTION) {
            tableModel.setRowCount(0);
        }
    }

    // wrappers
    private void saveContacts() { doSave(); }
    private void loadContacts() { doOpen(); }

    private void doSave() {
        JFileChooser chooser = new JFileChooser(DEFAULT_DIR);
        chooser.setDialogTitle("Save Contacts (CSV)");
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            if (!f.getName().toLowerCase().endsWith(".csv")) {
                f = new File(f.getParentFile(), f.getName() + ".csv");
            }
            try (BufferedWriter out = new BufferedWriter(new FileWriter(f))) {
                out.write("Name,Street,City,State,Phone,Email");
                out.newLine();

                for (int r = 0; r < tableModel.getRowCount(); r++) {
                    List<String> fields = new ArrayList<>();
                    for (int c = 0; c < tableModel.getColumnCount(); c++) {
                        String cell = String.valueOf(tableModel.getValueAt(r, c));
                        fields.add(csvEscape(cell));
                    }
                    out.write(String.join(",", fields));
                    out.newLine();
                }
                JOptionPane.showMessageDialog(this, "Saved to:\n" + f.getAbsolutePath(),
                        "Saved", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Save failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void doOpen() {
        JFileChooser chooser = new JFileChooser(DEFAULT_DIR);
        chooser.setDialogTitle("Open Contacts (CSV)");
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            if (f == null || !f.exists()) {
                JOptionPane.showMessageDialog(this, "File not found.",
                        "Open failed", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try (BufferedReader in = new BufferedReader(new FileReader(f))) {
                String line;
                List<String[]> rows = new ArrayList<>();
                boolean first = true;

                while ((line = in.readLine()) != null) {
                    if (first) {
                        first = false;
                        String header = line.toLowerCase();
                        if (header.contains("name") && header.contains("email")) {
                            continue; // skip header if present
                        }
                    }
                    rows.add(csvParse(line, 6));
                }

                tableModel.setRowCount(0);
                for (String[] r : rows) {
                    Object[] row = new Object[6];
                    for (int i = 0; i < 6; i++) row[i] = (i < r.length ? r[i] : "");
                    tableModel.addRow(row);
                }

                JOptionPane.showMessageDialog(this, "Loaded " + rows.size() + " contact(s).",
                        "Opened", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Open failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // CSV Helpers
    private static String csvEscape(String field) {
        if (field == null) field = "";
        boolean needsQuotes = field.contains(",") || field.contains("\"")
                || field.contains("\n") || field.contains("\r");
        String val = field.replace("\"", "\"\"");
        return needsQuotes ? "\"" + val + "\"" : val;
    }

    private static String[] csvParse(String line, int expectedCols) {
        if (line == null) return new String[expectedCols];
        List<String> out = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            if (inQuotes) {
                if (ch == '"') {
                    if (i + 1 < line.length() && line.charAt(i + 1) == '"') {
                        cur.append('"'); i++; // escaped quote
                    } else {
                        inQuotes = false;
                    }
                } else {
                    cur.append(ch);
                }
            } else {
                if (ch == '"') {
                    inQuotes = true;
                } else if (ch == ',') {
                    out.add(cur.toString());
                    cur.setLength(0);
                } else {
                    cur.append(ch);
                }
            }
        }
        out.add(cur.toString());

        String[] arr = new String[expectedCols];
        for (int i2 = 0; i2 < expectedCols; i2++) {
            arr[i2] = i2 < out.size() ? out.get(i2) : "";
        }
        return arr;
    }

    private void showADialog() {
        JOptionPane.showMessageDialog(this, "This is a dialog you can use for messages!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ContactBookManager app = new ContactBookManager();
            app.setVisible(true);
        });
    }
}

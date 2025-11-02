import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import com.toedter.calendar.JDateChooser;

public class AddItemDialog extends JDialog {
    private JTextField txtItemName;
    private JComboBox<String> cmbItemType;
    private JDateChooser dateChooser;
    private JComboBox<String> cmbStatus;
    private boolean itemAdded = false;
    private ItemDAO itemDAO;

    public AddItemDialog(JFrame parent) {
        super(parent, "Add New Item", true);
        itemDAO = new ItemDAO();
        initComponents();
    }

    private void initComponents() {
        setSize(400, 350);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(43, 141, 200));
        JLabel lblTitle = new JLabel("Add New Item");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);
        titlePanel.add(lblTitle);
        add(titlePanel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Item Name
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 0;
        JLabel lblName = new JLabel("Item Name:");
        lblName.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(lblName, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        gbc.weightx = 1.0;
        txtItemName = new JTextField(20);
        txtItemName.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(txtItemName, gbc);

        // Item Type
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.weightx = 0;
        JLabel lblType = new JLabel("Item Type:");
        lblType.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(lblType, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        gbc.weightx = 1.0;
        String[] itemTypes = {"Personal Items", "Electronics", "Bags", "Accessories", "Others"};
        cmbItemType = new JComboBox<>(itemTypes);
        cmbItemType.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(cmbItemType, gbc);

        // Date Found
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.weightx = 0;
        JLabel lblDate = new JLabel("Date Found:");
        lblDate.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(lblDate, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        gbc.weightx = 1.0;
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        dateChooser.setDate(new java.util.Date());
        dateChooser.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(dateChooser, gbc);

        // Status
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.weightx = 0;
        JLabel lblStatus = new JLabel("Status:");
        lblStatus.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(lblStatus, gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        gbc.weightx = 1.0;
        String[] statuses = {"Found", "Lost"};
        cmbStatus = new JComboBox<>(statuses);
        cmbStatus.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(cmbStatus, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));

        JButton btnSave = new JButton("Save Item");
        btnSave.setBackground(new Color(169, 223, 191));
        btnSave.setForeground(Color.BLACK);
        btnSave.setFont(new Font("Arial", Font.BOLD, 14));
        btnSave.setFocusPainted(false);
        btnSave.setPreferredSize(new Dimension(120, 35));

        JButton btnCancel = new JButton("Cancel");
        btnCancel.setBackground(new Color(210, 218, 226));
        btnCancel.setForeground(Color.BLACK);
        btnCancel.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancel.setFocusPainted(false);
        btnCancel.setPreferredSize(new Dimension(120, 35));

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        add(buttonPanel, BorderLayout.SOUTH);

        btnSave.addActionListener(e -> saveItem());
        btnCancel.addActionListener(e -> dispose());

        getRootPane().setDefaultButton(btnSave);
    }

    private void saveItem() {
        if (validateForm()) {
            String itemName = txtItemName.getText().trim();
            String itemType = (String) cmbItemType.getSelectedItem();
            java.util.Date utilDate = dateChooser.getDate();
            Date dateFound = new Date(utilDate.getTime());
            String status = (String) cmbStatus.getSelectedItem();

            Item item = new Item(itemName, itemType, dateFound, status);

            if (itemDAO.addItem(item)) {
                JOptionPane.showMessageDialog(this,
                        "Item added successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                itemAdded = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Failed to add item!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean validateForm() {
        if (txtItemName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter item name!",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            txtItemName.requestFocus();
            return false;
        }

        if (dateChooser.getDate() == null) {
            JOptionPane.showMessageDialog(this,
                    "Please select a date!",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    public boolean isItemAdded() {
        return itemAdded;
    }
}
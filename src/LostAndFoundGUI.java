import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.util.List;

import com.toedter.calendar.JDateChooser;

public class LostAndFoundGUI extends JFrame {
    private final ItemDAO itemDAO;
    private JTable itemTable;
    private DefaultTableModel tableModel;

    // Form components
    private JTextField txtItemName;
    private JComboBox<String> cmbItemType;
    private JDateChooser dateChooser;
    private JComboBox<String> cmbStatus;
    private JTextField txtSearch;

    // Buttons
    private JButton btnAdd, btnUpdate, btnDelete, btnArchive, btnViewArchive, btnSearch;
    private JButton btnShowFound, btnShowLost, btnShowAll;

    private int selectedItemId = -1;

    public LostAndFoundGUI() {
        itemDAO = new ItemDAO();
        initComponents();
        loadAllItems();
    }

    private void initComponents() {
        setTitle("Lost and Found Item Management System");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Top Panel - Title and Archive Button
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(38, 128, 186));

        JLabel lblTitle = new JLabel("Lost and Found Management System");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        topPanel.add(lblTitle, BorderLayout.WEST);

        // View Archive Button on top right
        btnViewArchive = new JButton("View Archive (" + itemDAO.getTotalArchivedCount() + ")");
        btnViewArchive.setBackground(new Color(149, 165, 166));
        btnViewArchive.setForeground(Color.BLACK);
        btnViewArchive.setFont(new Font("Arial", Font.BOLD, 13));
        btnViewArchive.setFocusPainted(false);
        btnViewArchive.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        topPanel.add(btnViewArchive, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // Center Panel - Table
        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Search by Item Name:"));
        txtSearch = new JTextField(20);
        searchPanel.add(txtSearch);
        btnSearch = new JButton("Search");
        btnSearch.setBackground(new Color(174, 214, 241));
        btnSearch.setForeground(Color.BLACK);
        btnSearch.setFont(new Font("Arial", Font.BOLD, 12));
        searchPanel.add(btnSearch);

        // Filter Panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.add(new JLabel("Filter by Status:"));

        btnShowAll = new JButton("Show All");
        btnShowAll.setBackground(new Color(189, 195, 199));
        btnShowAll.setForeground(Color.BLACK);
        btnShowAll.setFont(new Font("Arial", Font.BOLD, 12));
        filterPanel.add(btnShowAll);

        btnShowFound = new JButton("Show Found");
        btnShowFound.setBackground(new Color(169, 223, 191));
        btnShowFound.setForeground(Color.BLACK);
        btnShowFound.setFont(new Font("Arial", Font.BOLD, 12));
        filterPanel.add(btnShowFound);

        btnShowLost = new JButton("Show Lost");
        btnShowLost.setBackground(new Color(245, 183, 177));
        btnShowLost.setForeground(Color.BLACK);
        btnShowLost.setFont(new Font("Arial", Font.BOLD, 12));
        filterPanel.add(btnShowLost);

        // Combined search and filter panel
        JPanel topSearchPanel = new JPanel(new BorderLayout());
        topSearchPanel.add(searchPanel, BorderLayout.NORTH);
        topSearchPanel.add(filterPanel, BorderLayout.CENTER);

        centerPanel.add(topSearchPanel, BorderLayout.NORTH);

        // Table
        String[] columns = {"Item ID", "Item Name", "Item Type", "Date Found", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        itemTable = new JTable(tableModel);
        itemTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemTable.setRowHeight(25);
        itemTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        itemTable.getTableHeader().setBackground(new Color(52, 73, 94));
        itemTable.getTableHeader().setForeground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(itemTable);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // Bottom Panel - Form for Update/Delete
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(52, 73, 94), 2),
                "Selected Item Details (Click on table to edit/delete)",
                0, 0,
                new Font("Arial", Font.BOLD, 14)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Item Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Item Name:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        txtItemName = new JTextField(20);
        formPanel.add(txtItemName, gbc);

        // Item Type
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Item Type:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        String[] itemTypes = {"Personal Items", "Electronics", "Bags", "Accessories", "Others"};
        cmbItemType = new JComboBox<>(itemTypes);
        formPanel.add(cmbItemType, gbc);

        // Date Found
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Date Found:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        dateChooser.setDate(new java.util.Date());
        formPanel.add(dateChooser, gbc);

        // Status
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Status:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        String[] statuses = {"Found", "Lost"};
        cmbStatus = new JComboBox<>(statuses);
        formPanel.add(cmbStatus, gbc);

        bottomPanel.add(formPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        btnAdd = new JButton("Add New Item");
        btnAdd.setBackground(new Color(169, 223, 191));
        btnAdd.setForeground(Color.BLACK);
        btnAdd.setFont(new Font("Arial", Font.BOLD, 14));
        btnAdd.setFocusPainted(false);

        btnUpdate = new JButton("Update Item");
        btnUpdate.setBackground(new Color(250, 215, 160));
        btnUpdate.setForeground(Color.BLACK);
        btnUpdate.setFont(new Font("Arial", Font.BOLD, 14));
        btnUpdate.setEnabled(false);
        btnUpdate.setFocusPainted(false);

        btnArchive = new JButton("Archive Item");
        btnArchive.setBackground(new Color(149, 165, 166));
        btnArchive.setForeground(Color.BLACK);
        btnArchive.setFont(new Font("Arial", Font.BOLD, 14));
        btnArchive.setEnabled(false);
        btnArchive.setFocusPainted(false);

        btnDelete = new JButton("Delete Item");
        btnDelete.setBackground(new Color(245, 183, 177));
        btnDelete.setForeground(Color.BLACK);
        btnDelete.setFont(new Font("Arial", Font.BOLD, 14));
        btnDelete.setEnabled(false);
        btnDelete.setFocusPainted(false);

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnArchive);
        buttonPanel.add(btnDelete);

        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        addActionListeners();
    }

    private void addActionListeners() {
        // Table selection listener
        itemTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = itemTable.getSelectedRow();
                if (selectedRow != -1) {
                    loadSelectedItem(selectedRow);
                }
            }
        });

        // Add button - popup dialog
        btnAdd.addActionListener(e -> {
            AddItemDialog dialog = new AddItemDialog(this);
            dialog.setVisible(true);
            if (dialog.isItemAdded()) {
                loadAllItems();
                updateArchiveCount();
            }
        });

        // Update button
        btnUpdate.addActionListener(e -> updateItem());

        // Archive button
        btnArchive.addActionListener(e -> archiveItem());

        // Delete button
        btnDelete.addActionListener(e -> deleteItem());

        // View Archive button
        btnViewArchive.addActionListener(e -> {
            ArchiveViewer viewer = new ArchiveViewer();
            viewer.setVisible(true);
            viewer.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    loadAllItems();
                    updateArchiveCount();
                }
            });
        });


        btnSearch.addActionListener(e -> searchItems());
        txtSearch.addActionListener(e -> searchItems());

        btnShowAll.addActionListener(e -> loadAllItems());
        btnShowFound.addActionListener(e -> filterByStatus("Found"));
        btnShowLost.addActionListener(e -> filterByStatus("Lost"));
    }


    private void loadAllItems() {
        tableModel.setRowCount(0);
        List<Item> items = itemDAO.getAllItems();
        for (Item item : items) {
            Object[] row = {
                    item.getItemId(),
                    item.getItemName(),
                    item.getItemType(),
                    item.getDateFound(),
                    item.getStatus()
            };
            tableModel.addRow(row);
        }
        txtSearch.setText("");
    }

    private void loadSelectedItem(int row) {
        selectedItemId = (int) tableModel.getValueAt(row, 0);
        String itemName = (String) tableModel.getValueAt(row, 1);
        String itemType = (String) tableModel.getValueAt(row, 2);
        Date dateFound = (Date) tableModel.getValueAt(row, 3);
        String status = (String) tableModel.getValueAt(row, 4);

        txtItemName.setText(itemName);
        cmbItemType.setSelectedItem(itemType);
        dateChooser.setDate(new java.util.Date(dateFound.getTime()));
        cmbStatus.setSelectedItem(status);

        btnUpdate.setEnabled(true);
        btnArchive.setEnabled(true);
        btnDelete.setEnabled(true);
    }

    private void updateItem() {
        if (validateForm() && selectedItemId != -1) {
            String itemName = txtItemName.getText().trim();
            String itemType = (String) cmbItemType.getSelectedItem();
            java.util.Date utilDate = dateChooser.getDate();
            Date dateFound = new Date(utilDate.getTime());
            String status = (String) cmbStatus.getSelectedItem();

            Item item = new Item(selectedItemId, itemName, itemType, dateFound, status);

            if (itemDAO.updateItem(item)) {
                JOptionPane.showMessageDialog(this, "Item updated successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                loadAllItems();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update item!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void archiveItem() {
        if (selectedItemId != -1) {
            String[] options = {"Claimed", "Returned to Owner", "Resolved", "Other"};
            String reason = (String) JOptionPane.showInputDialog(
                    this,
                    "Select reason for archiving:",
                    "Archive Item",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            if (reason != null) {
                if (itemDAO.archiveItem(selectedItemId, reason)) {
                    JOptionPane.showMessageDialog(this,
                            "Item archived successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    loadAllItems();
                    clearForm();
                    updateArchiveCount();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Failed to archive item!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void deleteItem() {
        if (selectedItemId != -1) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete this item?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                if (itemDAO.deleteItem(selectedItemId)) {
                    JOptionPane.showMessageDialog(this, "Item deleted successfully!",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadAllItems();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete item!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void searchItems() {
        String searchTerm = txtSearch.getText().trim();
        if (!searchTerm.isEmpty()) {
            tableModel.setRowCount(0);
            List<Item> items = itemDAO.searchItemsByName(searchTerm);

            if (items.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No items found!",
                        "Search Result", JOptionPane.INFORMATION_MESSAGE);
            } else {
                for (Item item : items) {
                    Object[] row = {
                            item.getItemId(),
                            item.getItemName(),
                            item.getItemType(),
                            item.getDateFound(),
                            item.getStatus()
                    };
                    tableModel.addRow(row);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a search term!",
                    "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void filterByStatus(String status) {
        tableModel.setRowCount(0);
        List<Item> items = itemDAO.getItemsByStatus(status);

        if (items.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No " + status.toLowerCase() + " items!",
                    "Filter Result",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (Item item : items) {
                Object[] row = {
                        item.getItemId(),
                        item.getItemName(),
                        item.getItemType(),
                        item.getDateFound(),
                        item.getStatus()
                };
                tableModel.addRow(row);
            }
        }
        txtSearch.setText("");
    }

    private void clearForm() {
        txtItemName.setText("");
        cmbItemType.setSelectedIndex(0);
        dateChooser.setDate(new java.util.Date());
        cmbStatus.setSelectedIndex(0);
        selectedItemId = -1;

        btnUpdate.setEnabled(false);
        btnArchive.setEnabled(false);
        btnDelete.setEnabled(false);

        itemTable.clearSelection();
    }

    private void updateArchiveCount() {
        btnViewArchive.setText("View Archive (" + itemDAO.getTotalArchivedCount() + ")");
    }

    private boolean validateForm() {
        if (txtItemName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter item name!",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (dateChooser.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please select a date!",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            LostAndFoundGUI gui = new LostAndFoundGUI();
            gui.setVisible(true);
        });
    }
}
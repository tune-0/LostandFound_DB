import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ArchiveViewer extends JFrame {
    private ItemDAO itemDAO;
    private JTable archiveTable;
    private DefaultTableModel tableModel;
    private JButton btnRestore, btnDelete, btnClose, btnRefresh;
    private int selectedArchiveId = -1;

    public ArchiveViewer() {
        itemDAO = new ItemDAO();
        initComponents();
        loadArchivedItems();
    }

    private void initComponents() {
        setTitle("Archive - Lost and Found Items");
        setSize(1000, 600);  // Increased width to accommodate new column
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Top Panel - Title
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(43, 141, 200));
        JLabel lblTitle = new JLabel("Archived Items");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setForeground(Color.WHITE);
        topPanel.add(lblTitle);
        add(topPanel, BorderLayout.NORTH);

        // Center Panel - Table
        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Info Panel
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblInfo = new JLabel("These items have been resolved and archived. You can restore or permanently delete them.");
        lblInfo.setFont(new Font("Arial", Font.ITALIC, 12));
        lblInfo.setForeground(new Color(127, 140, 141));
        infoPanel.add(lblInfo);
        centerPanel.add(infoPanel, BorderLayout.NORTH);

        // Table - UPDATED with Location Found column
        String[] columns = {"Archive ID", "Item ID", "Item Name", "Item Type", "Date Found", "Location Found", "Status", "Archived Date", "Reason"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        archiveTable = new JTable(tableModel);
        archiveTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        archiveTable.setRowHeight(25);
        archiveTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));
        archiveTable.getTableHeader().setBackground(new Color(52, 73, 94));
        archiveTable.getTableHeader().setForeground(Color.BLACK);

        // Column widths - UPDATED
        archiveTable.getColumnModel().getColumn(0).setPreferredWidth(80);   // Archive ID
        archiveTable.getColumnModel().getColumn(1).setPreferredWidth(60);   // Item ID
        archiveTable.getColumnModel().getColumn(2).setPreferredWidth(150);  // Item Name
        archiveTable.getColumnModel().getColumn(3).setPreferredWidth(120);  // Item Type
        archiveTable.getColumnModel().getColumn(4).setPreferredWidth(100);  // Date Found
        archiveTable.getColumnModel().getColumn(5).setPreferredWidth(130);  // Location Found - NEW
        archiveTable.getColumnModel().getColumn(6).setPreferredWidth(80);   // Status
        archiveTable.getColumnModel().getColumn(7).setPreferredWidth(150);  // Archived Date
        archiveTable.getColumnModel().getColumn(8).setPreferredWidth(120);  // Reason

        JScrollPane scrollPane = new JScrollPane(archiveTable);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // Bottom Panel - Buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        btnRestore = new JButton("Restore to Active");
        btnRestore.setBackground(new Color(52, 152, 219));
        btnRestore.setForeground(Color.BLACK);
        btnRestore.setFont(new Font("Arial", Font.BOLD, 13));
        btnRestore.setFocusPainted(false);
        btnRestore.setEnabled(false);

        btnDelete = new JButton("Delete Permanently");
        btnDelete.setBackground(new Color(231, 76, 60));
        btnDelete.setForeground(Color.BLACK);
        btnDelete.setFont(new Font("Arial", Font.BOLD, 13));
        btnDelete.setFocusPainted(false);
        btnDelete.setEnabled(false);

        btnRefresh = new JButton("Refresh");
        btnRefresh.setBackground(new Color(52, 152, 219));
        btnRefresh.setForeground(Color.BLACK);
        btnRefresh.setFont(new Font("Arial", Font.BOLD, 13));
        btnRefresh.setFocusPainted(false);

        btnClose = new JButton("Close");
        btnClose.setBackground(new Color(149, 165, 166));
        btnClose.setForeground(Color.BLACK);
        btnClose.setFont(new Font("Arial", Font.BOLD, 13));
        btnClose.setFocusPainted(false);

        bottomPanel.add(btnRestore);
        bottomPanel.add(btnDelete);
        bottomPanel.add(btnRefresh);
        bottomPanel.add(btnClose);

        add(bottomPanel, BorderLayout.SOUTH);

        addActionListeners();
    }

    private void addActionListeners() {
        // Table selection listener
        archiveTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = archiveTable.getSelectedRow();
                if (selectedRow != -1) {
                    selectedArchiveId = (int) tableModel.getValueAt(selectedRow, 0);
                    btnRestore.setEnabled(true);
                    btnDelete.setEnabled(true);
                } else {
                    selectedArchiveId = -1;
                    btnRestore.setEnabled(false);
                    btnDelete.setEnabled(false);
                }
            }
        });

        // Restore button
        btnRestore.addActionListener(e -> restoreItem());

        // Delete button
        btnDelete.addActionListener(e -> deleteItem());

        // Refresh button
        btnRefresh.addActionListener(e -> loadArchivedItems());

        // Close button
        btnClose.addActionListener(e -> dispose());
    }

    private void loadArchivedItems() {
        tableModel.setRowCount(0);
        List<ArchivedItem> items = itemDAO.getAllArchivedItems();

        if (items.isEmpty()) {
            JLabel lblEmpty = new JLabel("No archived items");
            lblEmpty.setHorizontalAlignment(SwingConstants.CENTER);
            lblEmpty.setFont(new Font("Arial", Font.ITALIC, 14));
            lblEmpty.setForeground(Color.GRAY);
        }

        for (ArchivedItem item : items) {
            Object[] row = {
                    item.getArchiveId(),
                    item.getItemId(),
                    item.getItemName(),
                    item.getItemType(),
                    item.getDateFound(),
                    item.getLocationFound(),  // NEW - Added location
                    item.getStatus(),
                    item.getArchivedDate(),
                    item.getArchivedReason()
            };
            tableModel.addRow(row);
        }

        selectedArchiveId = -1;
        btnRestore.setEnabled(false);
        btnDelete.setEnabled(false);
    }

    private void restoreItem() {
        if (selectedArchiveId != -1) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Restore this item back to active items?",
                    "Confirm Restore",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                if (itemDAO.restoreArchivedItem(selectedArchiveId)) {
                    JOptionPane.showMessageDialog(this,
                            "Item restored successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    loadArchivedItems();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Failed to restore item!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void deleteItem() {
        if (selectedArchiveId != -1) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Permanently delete this archived item?\nThis action cannot be undone!",
                    "Confirm Permanent Delete",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                if (itemDAO.deleteArchivedItem(selectedArchiveId)) {
                    JOptionPane.showMessageDialog(this,
                            "Archived item deleted permanently!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    loadArchivedItems();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Failed to delete archived item!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {

    public boolean addItem(Item item) {
        String sql = "INSERT INTO items (item_name, item_type, date_found, location_found, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, item.getItemName());
            pstmt.setString(2, item.getItemType());
            pstmt.setDate(3, item.getDateFound());
            pstmt.setString(4, item.getLocationFound());  // NEW
            pstmt.setString(5, item.getStatus());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error adding item: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Get all items
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items ORDER BY date_found DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Item item = new Item();
                item.setItemId(rs.getInt("item_id"));
                item.setItemName(rs.getString("item_name"));
                item.setItemType(rs.getString("item_type"));
                item.setDateFound(rs.getDate("date_found"));
                item.setLocationFound(rs.getString("location_found"));  // NEW
                item.setStatus(rs.getString("status"));
                item.setCreatedAt(rs.getTimestamp("created_at"));
                item.setUpdatedAt(rs.getTimestamp("updated_at"));

                items.add(item);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving items: " + e.getMessage());
            e.printStackTrace();
        }

        return items;
    }

    // Get item by ID
    public Item getItemById(int itemId) {
        String sql = "SELECT * FROM items WHERE item_id = ?";
        Item item = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, itemId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                item = new Item();
                item.setItemId(rs.getInt("item_id"));
                item.setItemName(rs.getString("item_name"));
                item.setItemType(rs.getString("item_type"));
                item.setDateFound(rs.getDate("date_found"));
                item.setLocationFound(rs.getString("location_found"));  // NEW
                item.setStatus(rs.getString("status"));
                item.setCreatedAt(rs.getTimestamp("created_at"));
                item.setUpdatedAt(rs.getTimestamp("updated_at"));
            }

            rs.close();

        } catch (SQLException e) {
            System.out.println("Error retrieving item: " + e.getMessage());
            e.printStackTrace();
        }

        return item;
    }

    // Search items by name
    public List<Item> searchItemsByName(String searchTerm) {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items WHERE item_name LIKE ? ORDER BY date_found DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + searchTerm + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Item item = new Item();
                item.setItemId(rs.getInt("item_id"));
                item.setItemName(rs.getString("item_name"));
                item.setItemType(rs.getString("item_type"));
                item.setDateFound(rs.getDate("date_found"));
                item.setLocationFound(rs.getString("location_found"));  // NEW
                item.setStatus(rs.getString("status"));
                item.setCreatedAt(rs.getTimestamp("created_at"));
                item.setUpdatedAt(rs.getTimestamp("updated_at"));

                items.add(item);
            }

            rs.close();

        } catch (SQLException e) {
            System.out.println("Error searching items: " + e.getMessage());
            e.printStackTrace();
        }

        return items;
    }

    // Get items by status
    public List<Item> getItemsByStatus(String status) {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items WHERE status = ? ORDER BY date_found DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Item item = new Item();
                item.setItemId(rs.getInt("item_id"));
                item.setItemName(rs.getString("item_name"));
                item.setItemType(rs.getString("item_type"));
                item.setDateFound(rs.getDate("date_found"));
                item.setLocationFound(rs.getString("location_found"));  // NEW
                item.setStatus(rs.getString("status"));
                item.setCreatedAt(rs.getTimestamp("created_at"));
                item.setUpdatedAt(rs.getTimestamp("updated_at"));

                items.add(item);
            }

            rs.close();

        } catch (SQLException e) {
            System.out.println("Error retrieving items by status: " + e.getMessage());
            e.printStackTrace();
        }

        return items;
    }

    // Update
    public boolean updateItem(Item item) {
        String sql = "UPDATE items SET item_name = ?, item_type = ?, date_found = ?, location_found = ?, status = ? WHERE item_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, item.getItemName());
            pstmt.setString(2, item.getItemType());
            pstmt.setDate(3, item.getDateFound());
            pstmt.setString(4, item.getLocationFound());  // NEW
            pstmt.setString(5, item.getStatus());
            pstmt.setInt(6, item.getItemId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error updating item: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Delete
    public boolean deleteItem(int itemId) {
        String sql = "DELETE FROM items WHERE item_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, itemId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                resetAutoIncrement(conn);
                return true;
            }
            return false;

        } catch (SQLException e) {
            System.out.println("Error deleting item: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private void resetAutoIncrement(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX(item_id) as max_id FROM items");
            int maxId = 0;
            if (rs.next()) {
                maxId = rs.getInt("max_id");
            }

            stmt.executeUpdate("ALTER TABLE items AUTO_INCREMENT = " + (maxId + 1));
            stmt.close();
            System.out.println("AUTO_INCREMENT reset to: " + (maxId + 1));
        } catch (SQLException e) {
            System.out.println("Error resetting auto increment: " + e.getMessage());
        }
    }

    // Get total count of items
    public int getTotalItemCount() {
        String sql = "SELECT COUNT(*) as total FROM items";
        int count = 0;

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                count = rs.getInt("total");
            }

        } catch (SQLException e) {
            System.out.println("Error getting item count: " + e.getMessage());
            e.printStackTrace();
        }

        return count;
    }


    // Archive
    public boolean archiveItem(int itemId, String reason) {
        Item item = getItemById(itemId);
        if (item == null) {
            System.out.println("Item not found!");
            return false;
        }

        String insertSql = "INSERT INTO archived_items (item_id, item_name, item_type, date_found, location_found, status, archived_reason) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String deleteSql = "DELETE FROM items WHERE item_id = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                 PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {

                insertStmt.setInt(1, item.getItemId());
                insertStmt.setString(2, item.getItemName());
                insertStmt.setString(3, item.getItemType());
                insertStmt.setDate(4, item.getDateFound());
                insertStmt.setString(5, item.getLocationFound());  // NEW
                insertStmt.setString(6, item.getStatus());
                insertStmt.setString(7, reason);
                insertStmt.executeUpdate();

                deleteStmt.setInt(1, itemId);
                deleteStmt.executeUpdate();

                conn.commit();
                System.out.println("Item archived successfully!");
                return true;

            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Error archiving item: " + e.getMessage());
                e.printStackTrace();
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Get all archived items
    public List<ArchivedItem> getAllArchivedItems() {
        List<ArchivedItem> items = new ArrayList<>();
        String sql = "SELECT * FROM archived_items ORDER BY archived_date DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ArchivedItem item = new ArchivedItem();
                item.setArchiveId(rs.getInt("archive_id"));
                item.setItemId(rs.getInt("item_id"));
                item.setItemName(rs.getString("item_name"));
                item.setItemType(rs.getString("item_type"));
                item.setDateFound(rs.getDate("date_found"));
                item.setLocationFound(rs.getString("location_found"));  // NEW
                item.setStatus(rs.getString("status"));
                item.setArchivedDate(rs.getTimestamp("archived_date"));
                item.setArchivedReason(rs.getString("archived_reason"));

                items.add(item);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving archived items: " + e.getMessage());
            e.printStackTrace();
        }

        return items;
    }

    // Restore archived item back to active items
    public boolean restoreArchivedItem(int archiveId) {
        String selectSql = "SELECT * FROM archived_items WHERE archive_id = ?";
        String insertSql = "INSERT INTO items (item_name, item_type, date_found, location_found, status) VALUES (?, ?, ?, ?, ?)";
        String deleteSql = "DELETE FROM archived_items WHERE archive_id = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement selectStmt = conn.prepareStatement(selectSql);
                 PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                 PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {

                // Get archived item
                selectStmt.setInt(1, archiveId);
                ResultSet rs = selectStmt.executeQuery();

                if (rs.next()) {

                    insertStmt.setString(1, rs.getString("item_name"));
                    insertStmt.setString(2, rs.getString("item_type"));
                    insertStmt.setDate(3, rs.getDate("date_found"));
                    insertStmt.setString(4, rs.getString("location_found"));  // NEW
                    insertStmt.setString(5, rs.getString("status"));
                    insertStmt.executeUpdate();


                    deleteStmt.setInt(1, archiveId);
                    deleteStmt.executeUpdate();

                    conn.commit();
                    System.out.println("Item restored successfully!");
                    return true;
                } else {
                    conn.rollback();
                    System.out.println("Archived item not found!");
                    return false;
                }

            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Error restoring item: " + e.getMessage());
                e.printStackTrace();
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Delete archived item permanently
    public boolean deleteArchivedItem(int archiveId) {
        String sql = "DELETE FROM archived_items WHERE archive_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, archiveId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting archived item: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Get total count of archived items
    public int getTotalArchivedCount() {
        String sql = "SELECT COUNT(*) as total FROM archived_items";
        int count = 0;

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                count = rs.getInt("total");
            }

        } catch (SQLException e) {
            System.out.println("Error getting archived count: " + e.getMessage());
            e.printStackTrace();
        }

        return count;
    }
}
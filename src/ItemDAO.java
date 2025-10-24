import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {

    // Create - Add new item
    public boolean addItem(Item item) {
        String sql = "INSERT INTO items (item_name, item_type, date_found, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, item.getItemName());
            pstmt.setString(2, item.getItemType());
            pstmt.setDate(3, item.getDateFound());
            pstmt.setString(4, item.getStatus());

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

    // Update - Update existing item
    public boolean updateItem(Item item) {
        String sql = "UPDATE items SET item_name = ?, item_type = ?, date_found = ?, status = ? WHERE item_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, item.getItemName());
            pstmt.setString(2, item.getItemType());
            pstmt.setDate(3, item.getDateFound());
            pstmt.setString(4, item.getStatus());
            pstmt.setInt(5, item.getItemId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error updating item: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Delete - Delete item by ID
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
            // Get the maximum ID currently in the table
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
}
import java.sql.Date;
import java.sql.Timestamp;

public class Item {
    private int itemId;
    private String itemName;
    private String itemType;
    private Date dateFound;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Item() {
    }

    public Item(String itemName, String itemType, Date dateFound, String status) {
        this.itemName = itemName;
        this.itemType = itemType;
        this.dateFound = dateFound;
        this.status = status;
    }

    public Item(int itemId, String itemName, String itemType, Date dateFound, String status) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemType = itemType;
        this.dateFound = dateFound;
        this.status = status;
    }

    // Getters and Setters
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public Date getDateFound() {
        return dateFound;
    }

    public void setDateFound(Date dateFound) {
        this.dateFound = dateFound;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", itemType='" + itemType + '\'' +
                ", dateFound=" + dateFound +
                ", status='" + status + '\'' +
                '}';
    }
}
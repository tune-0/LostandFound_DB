import java.sql.Date;
import java.sql.Timestamp;

public class ArchivedItem {
    private int archiveId;
    private int itemId;
    private String itemName;
    private String itemType;
    private Date dateFound;
    private String status;
    private Timestamp archivedDate;
    private String archivedReason;

    // Constructors
    public ArchivedItem() {
    }

    public ArchivedItem(int itemId, String itemName, String itemType, Date dateFound, String status, String archivedReason) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemType = itemType;
        this.dateFound = dateFound;
        this.status = status;
        this.archivedReason = archivedReason;
    }

    // Getters and Setters
    public int getArchiveId() {
        return archiveId;
    }

    public void setArchiveId(int archiveId) {
        this.archiveId = archiveId;
    }

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

    public Timestamp getArchivedDate() {
        return archivedDate;
    }

    public void setArchivedDate(Timestamp archivedDate) {
        this.archivedDate = archivedDate;
    }

    public String getArchivedReason() {
        return archivedReason;
    }

    public void setArchivedReason(String archivedReason) {
        this.archivedReason = archivedReason;
    }

    @Override
    public String toString() {
        return "ArchivedItem{" +
                "archiveId=" + archiveId +
                ", itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", itemType='" + itemType + '\'' +
                ", dateFound=" + dateFound +
                ", status='" + status + '\'' +
                ", archivedDate=" + archivedDate +
                ", archivedReason='" + archivedReason + '\'' +
                '}';
    }
}
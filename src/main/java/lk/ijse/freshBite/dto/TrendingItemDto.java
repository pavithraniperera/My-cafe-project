package lk.ijse.freshBite.dto;

public class TrendingItemDto {
    private String itemId;
    private String itemName;
    private int quantity;
    private String imagePath;

    public TrendingItemDto() {
    }

    public TrendingItemDto(String itemId, String itemName, int quantity, String imagePath) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.imagePath = imagePath;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "TrendingItemDto{" +
                "itemId='" + itemId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", quantity=" + quantity +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}

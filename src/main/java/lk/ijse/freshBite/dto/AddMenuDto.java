package lk.ijse.freshBite.dto;

import javafx.scene.image.Image;

public class AddMenuDto {
    private String itemId;
    private String name;
    private String  type;

    private int qtyOnhand;
    private double sellPrice;
    private String status;
    private String stockId;
    private String imagePath ;

    public AddMenuDto() {
    }

    public AddMenuDto(String itemId, String name, String type, int qtyOnhand, double sellPrice, String status, String stockId, String imagePath) {
        this.itemId = itemId;
        this.name = name;
        this.type = type;
        this.qtyOnhand = qtyOnhand;
        this.sellPrice = sellPrice;
        this.status = status;
        this.stockId = stockId;
        this.imagePath = imagePath;
    }


    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQtyOnhand() {
        return qtyOnhand;
    }

    public void setQtyOnhand(int qtyOnhand) {
        this.qtyOnhand = qtyOnhand;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "AddMenuDto{" +
                "itemId='" + itemId + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", qtyOnhand=" + qtyOnhand +
                ", sellPrice=" + sellPrice +
                ", status='" + status + '\'' +
                ", stockId='" + stockId + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }


}

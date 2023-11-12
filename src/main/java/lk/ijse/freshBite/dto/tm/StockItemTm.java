package lk.ijse.freshBite.dto.tm;

import lombok.Getter;

import java.sql.Date;

@Getter
public class StockItemTm {
    private String stockId;
    private String name;
    private  int quantity;
    private double price;
    private String sup_id;
    private  java.sql.Date date;

    public StockItemTm(String stockId, String name, int quantity, double price, String supId, Date date) {
        this.stockId = stockId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.sup_id = supId;
        this.date = date;
    }

    public StockItemTm() {
    }


    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setSup_id(String sup_id) {
        this.sup_id = sup_id;
    }

    @Override
    public String toString() {
        return "StockItemTm{" +
                "stockId='" + stockId + '\'' +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", sup_id='" + sup_id + '\'' +
                '}';
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

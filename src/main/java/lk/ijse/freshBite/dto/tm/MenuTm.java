package lk.ijse.freshBite.dto.tm;

public class MenuTm {
 private String item_id;
 private String name;
 private String type;
 private int qty;

 private double price;
 private  String status;
 private  String stock_id;



    public MenuTm(String item_id, String name, String type, int qty, double price, String status, String stock_id) {
        this.item_id = item_id;
        this.name = name;
        this.type = type;
        this.qty = qty;
        this.price = price;
        this.status = status;
        this.stock_id = stock_id;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
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

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStock_id() {
        return stock_id;
    }

    public void setStock_id(String stock_id) {
        this.stock_id = stock_id;
    }

    @Override
    public String toString() {
        return "menuTm{" +
                "item_id='" + item_id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", qty=" + qty +
                ", price=" + price +
                ", status='" + status + '\'' +
                ", stock_id='" + stock_id + '\'' +
                '}';
    }
}

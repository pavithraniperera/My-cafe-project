package lk.ijse.freshBite.dto.tm;

import com.jfoenix.controls.JFXButton;

public class ItemCardTm {
    private String itemName;
    private  int qty;
    private double price;
    private JFXButton button;

    public ItemCardTm() {
    }

    public ItemCardTm(String itemName, int qty, double price, JFXButton button) {
        this.itemName = itemName;
        this.qty = qty;
        this.price = price;
        this.button = button;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
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

    @Override
    public String toString() {
        return "ItemCardTm{" +
                "itemName='" + itemName + '\'' +
                ", qty=" + qty +
                ", price=" + price +
                '}';
    }

    public JFXButton getButton() {
        return button;
    }

    public void setButton(JFXButton button) {
        this.button = button;
    }
}

package lk.ijse.freshBite.dto.tm;

import javafx.scene.image.ImageView;

public class TrendingItemTm {
   private String itemName;
   private ImageView imageView;
   private int itemQuantity;

    public TrendingItemTm() {
    }

    public TrendingItemTm(String itemName, ImageView imageView, int itemQuantity) {
        this.itemName = itemName;
        this.imageView = imageView;
        this.itemQuantity = itemQuantity;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    @Override
    public String toString() {
        return "TrendingItemTm{" +
                "itemName='" + itemName + '\'' +
                ", imageView=" + imageView +
                ", itemQuantity=" + itemQuantity +
                '}';
    }
}

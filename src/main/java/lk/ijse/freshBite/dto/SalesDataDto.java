package lk.ijse.freshBite.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesDataDto {
    private String itemName;
    private double unitPrice;
    private int totalQuantity;
    private double totalPrice;
    private String customerId;
    private String transactionDate;

    public SalesDataDto() {
    }

    public SalesDataDto(String itemName, double unitPrice, int totalQuantity, double totalPrice, String customerId, String transactionDate) {
        this.itemName = itemName;
        this.unitPrice = unitPrice;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
        this.customerId = customerId;
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        return "SalesDataDto{" +
                "itemName='" + itemName + '\'' +
                ", unitPrice=" + unitPrice +
                ", totalQuantity=" + totalQuantity +
                ", totalPrice=" + totalPrice +
                ", customerId='" + customerId + '\'' +
                ", transactionDate='" + transactionDate + '\'' +
                '}';
    }
}

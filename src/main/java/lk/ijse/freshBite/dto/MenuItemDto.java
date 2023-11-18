package lk.ijse.freshBite.dto;

import lk.ijse.freshBite.dto.tm.ItemCardTm;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class MenuItemDto {
    private String orderId;
    private LocalDate date;
    private String customerId;
    private double total;
    private List<ItemCardTm> cartTmList = new ArrayList<>();

    public MenuItemDto() {
    }


    public MenuItemDto(String orderId, LocalDate date, String customerId, double total, List<ItemCardTm> cartTmList) {
        this.orderId = orderId;
        this.date = date;
        this.customerId = customerId;
        this.total = total;
        this.cartTmList = cartTmList;
    }

    @Override
    public String toString() {
        return "MenuItemDto{" +
                "orderId='" + orderId + '\'' +
                ", date=" + date +
                ", customerId='" + customerId + '\'' +
                ", total=" + total +
                ", cartTmList=" + cartTmList +
                '}';
    }

}

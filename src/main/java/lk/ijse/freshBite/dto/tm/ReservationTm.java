package lk.ijse.freshBite.dto.tm;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.CheckBox;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter


public class ReservationTm {
private  String reservationId;
private String time;
private String custId;
private String name;
private int tableNo;
private int size;
private String telephone;
private JFXButton deleteBtn;
private CheckBox checkBox;

    public ReservationTm(String reservationId, String time, String custId, String name, int tableNo, int size, String telephone, JFXButton deleteBtn, CheckBox checkBox) {
        this.reservationId = reservationId;
        this.time = time;
        this.custId = custId;
        this.name = name;
        this.tableNo = tableNo;
        this.size = size;
        this.telephone = telephone;
        this.deleteBtn = deleteBtn;
        this.checkBox = checkBox;
    }

    @Override
    public String toString() {
        return "ReservationTm{" +
                "reservationId='" + reservationId + '\'' +
                ", time='" + time + '\'' +
                ", custId='" + custId + '\'' +
                ", name='" + name + '\'' +
                ", tableNo=" + tableNo +
                ", size=" + size +
                ", telephone='" + telephone + '\'' +
                ", deleteBtn=" + deleteBtn +
                ", checkBox=" + checkBox +
                '}';
    }
}

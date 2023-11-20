package lk.ijse.freshBite.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter

@NoArgsConstructor
public class ReservationDto {
    private LocalDate date;
    private String time ;
    private int tableNo;
    private String custId;
    private String telephone;
    private int size;
    private String id;
    private String name;

    @Override
    public String toString() {
        return "ReservationDto{" +
                "date=" + date +
                ", time='" + time + '\'' +
                ", tableNo=" + tableNo +
                ", custId='" + custId + '\'' +
                ", telephone='" + telephone + '\'' +
                ", size=" + size +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public ReservationDto(String id, LocalDate date, String time, int tableNo, String custId, String telephone, int size, String name) {
       this. id = id ;
        this.date = date;
        this.time = time;
        this.tableNo = tableNo;
        this.custId = custId;
        this.telephone = telephone;
        this.size = size;
        this.name = name;
    }

}

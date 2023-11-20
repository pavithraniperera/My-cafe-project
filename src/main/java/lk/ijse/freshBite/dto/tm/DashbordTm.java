package lk.ijse.freshBite.dto.tm;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DashbordTm {
    private String time;
    private int table_no;
    private String custId;

    public DashbordTm(String time, int table_no, String custId) {
        this.time = time;
        this.table_no = table_no;
        this.custId = custId;
    }
}


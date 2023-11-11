package lk.ijse.freshBite.dto.tm;

import com.jfoenix.controls.JFXButton;
import lombok.Getter;

@Getter
public class SupplierTm {
 private String sup_id;
 private String name;
 private String address;
 private  String  telephone;
 private String email;
 private JFXButton btn1;
 private JFXButton btn2;

    public SupplierTm() {
    }

    public SupplierTm(String sup_id, String name, String address, String telephone, String email) {
        this.sup_id = sup_id;
        this.name = name;
        this.address = address;
        this.telephone = telephone;
        this.email = email;
    }


    public void setSup_id(String sup_id) {
        this.sup_id = sup_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String toString() {
        return "SupplierTm{" +
                "sup_id='" + sup_id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

package lk.ijse.freshBite.dto;

public class NotificationDto {
    private String itemName;
    private String telephone;
    private String sup_name;

    public NotificationDto() {
    }

    public NotificationDto(String itemName, String telephone, String sup_name) {
        this.itemName = itemName;
        this.telephone = telephone;
        this.sup_name = sup_name;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getSup_name() {
        return sup_name;
    }

    public void setSup_name(String sup_name) {
        this.sup_name = sup_name;
    }

    @Override
    public String toString() {
        return "NotificationDto{" +
                "itemName='" + itemName + '\'' +
                ", telephone='" + telephone + '\'' +
                ", sup_name='" + sup_name + '\'' +
                '}';
    }
}

package lk.ijse.freshBite.dto;

public class ItemCardDto {
private AddMenuDto item;
private int qty;

    public ItemCardDto() {
    }

    public ItemCardDto(AddMenuDto item, int qty) {
        this.item = item;
        this.qty = qty;
    }

    public AddMenuDto getItem() {
        return item;
    }

    public void setItem(AddMenuDto item) {
        this.item = item;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}

package lk.ijse.freshBite.dto;

public class MembershipLevelDto {
    private String name;
    private double discountPercentage;

    public MembershipLevelDto() {
    }

    public MembershipLevelDto(String name, double discountPercentage) {
        this.name = name;
        this.discountPercentage = discountPercentage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    @Override
    public String toString() {
        return "MembershipLevelDto{" +
                "name='" + name + '\'' +
                ", discountPercentage=" + discountPercentage +
                '}';
    }
}

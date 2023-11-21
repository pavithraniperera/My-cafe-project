package lk.ijse.freshBite.dto;

public class IncomeDto {
    private String orderDate;
    private double dailyIncome;

    public IncomeDto() {
    }

    @Override
    public String toString() {
        return "IncomeDto{" +
                "orderDate='" + orderDate + '\'' +
                ", dailyIncome=" + dailyIncome +
                '}';
    }

    public IncomeDto(String orderDate, double dailyIncome) {
        this.orderDate = orderDate;
        this.dailyIncome = dailyIncome;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public double getDailyIncome() {
        return dailyIncome;
    }

}

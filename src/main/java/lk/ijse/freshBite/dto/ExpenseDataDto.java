package lk.ijse.freshBite.dto;

public class ExpenseDataDto {
    private String date;  // Date of the expense
    private double expenseAmount;  // Amount spent for the expense

    // Constructors, getters, and setters...


    public ExpenseDataDto() {
    }

    public ExpenseDataDto(String date, double expenseAmount) {
        this.date = date;
        this.expenseAmount = expenseAmount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(double expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    @Override
    public String toString() {
        return "ExpenseDataDto{" +
                "date='" + date + '\'' +
                ", expenseAmount=" + expenseAmount +
                '}';
    }
}

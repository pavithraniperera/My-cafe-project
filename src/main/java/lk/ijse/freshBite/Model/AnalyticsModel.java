package lk.ijse.freshBite.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.freshBite.db.DbConnection;
import lk.ijse.freshBite.dto.ExpenseDataDto;
import lk.ijse.freshBite.dto.SalesDataDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class AnalyticsModel {
    public ObservableList<ExpenseDataDto> getDailyChartData() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT \n" +
                "DATE(date) AS expense_date,\n" +
                "  SUM(stock_item.cost_price * stock_item.quantity) AS daily_expense\n" +
                "FROM stock_item\n" +
                "JOIN item_supply_details  \n" +
                "ON stock_item.stock_id = item_supply_details .stock_id\n" +
                "GROUP BY DATE(date)\n" +
                "ORDER BY expense_date;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        ObservableList<ExpenseDataDto> observableList = FXCollections.observableArrayList();
        while (resultSet.next()){
            observableList.add(new ExpenseDataDto(resultSet.getString(1),resultSet.getDouble(2)));
        }
        return observableList;
    }

    public ObservableList<ExpenseDataDto> getWeeklyChartData() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT\n" +
                "  WEEK(date) AS expense_week,\n" +
                "  SUM(stock_item.cost_price * stock_item.quantity) AS weekly_expense\n" +
                "FROM stock_item\n" +
                "JOIN item_supply_details \n" +
                "ON item_supply_details .stock_id = stock_item.stock_id\n" +
                "GROUP BY WEEK(date)\n" +
                "ORDER BY expense_week;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        ObservableList<ExpenseDataDto> observableList = FXCollections.observableArrayList();
        while (resultSet.next()){
            observableList.add(new ExpenseDataDto(resultSet.getString(1),resultSet.getDouble(2)));
        }
        return observableList;
    }

    public ObservableList<ExpenseDataDto> getMonthluChartData() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT\n" +
                "  MONTHNAME(date) AS expense_month,\n" +
                "  SUM(stock_item.cost_price * stock_item.quantity) AS monthly_expense\n" +
                "FROM stock_item\n" +
                "JOIN item_supply_details\n" +
                "ON item_supply_details.stock_id = stock_item.stock_id\n" +
                "GROUP BY MONTHNAME(date)\n" +
                "ORDER BY expense_month;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        ObservableList<ExpenseDataDto> observableList = FXCollections.observableArrayList();
        while (resultSet.next()){
            observableList.add(new ExpenseDataDto(resultSet.getString(1),resultSet.getDouble(2)));
        }
        return observableList;
    }

    public List<SalesDataDto> getSalesData() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT\n" +
                "        i.name AS item_name,\n" +
                "        i.unit_price,\n" +
                "         oi.quantity AS quantity_sold,\n" +
                "         oi.item_price AS total_price,\n" +
                "        o.customer_id,\n" +
                "        o.date AS transaction_date\n" +
                "     FROM\n" +
                "         order_item oi\n" +
                "     JOIN\n" +
                "         orders o ON oi.order_id = o.order_id\n" +
                "       JOIN\n" +
                "        menu_item i ON oi.item_id = i.item_id\n" +
                "      ORDER BY o.date DESC";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<SalesDataDto> list = new ArrayList<>();
        while (resultSet.next()){
            list.add(new SalesDataDto(resultSet.getString(1),
                    resultSet.getDouble(2),
                    resultSet.getInt(3),
                    resultSet.getDouble(4),
                    resultSet.getString(5),
                    resultSet.getString(6) ));
        }
        return list;
    }
}

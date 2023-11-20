package lk.ijse.freshBite.Model;

import lk.ijse.freshBite.db.DbConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;

public class OrderModel {

    public  boolean saveOrder(String orderId, String customerId, LocalDate date, double total) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO orders VALUES (?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,orderId);
        preparedStatement.setString(2,customerId);
        preparedStatement.setDate(3, Date.valueOf(date));
        preparedStatement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
        preparedStatement.setDouble(5,total);
        return preparedStatement.executeUpdate()>0;
    }

    public int getTotalOrderCount() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT count(*) AS totalOrders FROM orders WHERE date = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, String.valueOf(LocalDate.now()));
        ResultSet resultSet = preparedStatement.executeQuery();
        int totalOrders = -1;
        while (resultSet.next()){
            totalOrders = resultSet.getInt(1);
        }
        return  totalOrders;
    }

    public double getTotalPriceOfToday() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT total_price FROM orders WHERE date = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, String.valueOf(LocalDate.now()));
        ResultSet resultSet = preparedStatement.executeQuery();
        double price = -1;
        while (resultSet.next()){
            price+= resultSet.getDouble(1);
        }
        return  price;
    }
}

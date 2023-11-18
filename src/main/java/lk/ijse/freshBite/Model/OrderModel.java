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
}

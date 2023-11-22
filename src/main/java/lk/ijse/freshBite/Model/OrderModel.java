package lk.ijse.freshBite.Model;

import lk.ijse.freshBite.db.DbConnection;
import lk.ijse.freshBite.dto.IncomeDto;
import lk.ijse.freshBite.dto.TrendingItemDto;
import lk.ijse.freshBite.dto.tm.ItemCardTm;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OrderModel {

    public static List<TrendingItemDto> getTrendingItems() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql= "WITH total_ordered_items AS (\n" +
                "    SELECT\n" +
                "        order_item.item_id,\n" +
                "        SUM(order_item.quantity) AS total_quantity,\n" +
                "        menu_item.name AS item_name,\n" +
                "        menu_item.image_path\n" +
                "    FROM order_item\n" +
                "    JOIN menu_item\n" +
                "    ON order_item.item_id = menu_item.item_id\n" +
                "    GROUP BY order_item.item_id, menu_item.name, menu_item.image_path\n" +
                ")\n" +
                "\n" +
                "SELECT\n" +
                "    total_ordered_items.item_id,\n" +
                "    total_ordered_items.total_quantity,\n" +
                "    total_ordered_items.item_name,\n" +
                "    total_ordered_items.image_path\n" +
                "FROM total_ordered_items\n" +
                "ORDER BY total_ordered_items.total_quantity DESC\n" +
                "LIMIT 5";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<TrendingItemDto> list = new ArrayList<>();
        while (resultSet.next()){
            list.add(new TrendingItemDto(resultSet.getString(1), resultSet.getString(3),resultSet.getInt(2),resultSet.getString(4) ));
        }
        return list;
    }

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

    public List<IncomeDto> getIncomeDetails() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT DATE(date) AS order_date, SUM(total_price) AS daily_income FROM orders GROUP BY DATE(date) ORDER BY TIMESTAMP(order_date)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<IncomeDto> list = new ArrayList<>();
        while (resultSet.next()){
            list.add(new IncomeDto(resultSet.getString(1),resultSet.getDouble(2)));
        }
        return list;
    }

    public Double getTotalRevenue() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT\n" +
                "    DATE(order_time) AS order_date,\n" +
                "    SUM(total_price) AS total_price_day\n" +
                "FROM orders\n" +
                "GROUP BY DATE(order_time)\n" +
                "ORDER BY order_date;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ResultSet resultSet = preparedStatement.executeQuery();
        double price = -1;
        while (resultSet.next()){
            price = resultSet.getDouble(2);
        }
        return  price;
    }
}

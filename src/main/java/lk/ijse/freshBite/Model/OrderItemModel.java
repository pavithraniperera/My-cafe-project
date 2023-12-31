package lk.ijse.freshBite.Model;

import lk.ijse.freshBite.db.DbConnection;
import lk.ijse.freshBite.dto.tm.ItemCardTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class OrderItemModel {

    public boolean seveOrederDetails(String orderId, List<ItemCardTm> cartTmList) throws SQLException {
        for (ItemCardTm item : cartTmList){
            if (!saveOrderDetails(orderId,item)){
                return  false;
            }
        }
        return  true;
    }

    private boolean saveOrderDetails(String orderId, ItemCardTm item) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String itemId = getItemId(item.getItemName());

        String sql = "INSERT INTO order_item (order_id,item_id,item_price,quantity) VALUES (?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,orderId);
        preparedStatement.setString(2,itemId);
        preparedStatement.setDouble(3,item.getPrice());
        preparedStatement.setInt(4,item.getQty());
        return  preparedStatement.executeUpdate()>0;
    }

    private String getItemId(String itemName) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT item_id FROM menu_item WHERE name=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,itemName);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            return resultSet.getString(1);
        }
        return  null;
    }

    public int getSoldUnits() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT\n" +
                "    DATE(order_time) AS order_date,\n" +
                "    SUM(quantity) AS total_quantity_day\n" +
                "FROM order_item\n" +
                "JOIN orders\n" +
                "ON order_item.order_id = orders.order_id\n" +
                "GROUP BY DATE(order_time)\n" +
                "ORDER BY order_date;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ResultSet resultSet = preparedStatement.executeQuery();
        int units = -1;
        while (resultSet.next()){
            units = resultSet.getInt(2);
        }
        return  units;
    }
}

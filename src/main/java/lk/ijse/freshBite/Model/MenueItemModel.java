package lk.ijse.freshBite.Model;

import lk.ijse.freshBite.db.DbConnection;
import lk.ijse.freshBite.dto.AddMenuDto;
import lk.ijse.freshBite.dto.MenuItemDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenueItemModel {
    private  OrderModel orderModel = new OrderModel();
    private  AddMenuModel menuModel = new AddMenuModel();
    private  OrderItemModel orderItemModel = new OrderItemModel();


    public List<AddMenuDto> getAllMenuItems() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM menu_item";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<AddMenuDto> dtoList = new ArrayList<>();
        while(resultSet.next()){

            dtoList.add(new AddMenuDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(4),
                    resultSet.getInt(5),
                    resultSet.getDouble(3),
                    resultSet.getString(7),
                    resultSet.getString(6),
                    resultSet.getString(8)));
        }
        return dtoList;
    }

    public List<String> getAllCustId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT customer_id FROM customers";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<String> idList = new ArrayList<>();
        while (resultSet.next()){
            idList.add(resultSet.getString(1));
        }
        return  idList;
    }

    public String getName(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT name FROM customers WHERE customer_id =?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        String name = null;
        while (resultSet.next()){
            name= resultSet.getString(1);
        }
        return  name;
    }

    public String getMembership(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT membership_level FROM customers WHERE customer_id =?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        String membership = null;
        while (resultSet.next()){
            membership= resultSet.getString(1);
        }
        return  membership;
    }

    public String genarateId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
          return getNextId(  resultSet.getString(1));
        }
        return getNextId(null);

    }

    private String getNextId(String currentId) {

        if (currentId!= null){
            String numericPart = currentId.substring(1);
            int numericValue = Integer.parseInt(numericPart);
            numericValue++;
            String nextStockId = String.format("O%03d", numericValue);
            return nextStockId;
        }
        else {
            return "O001";
        }
    }

    public boolean placeOrder(MenuItemDto dto) throws SQLException {
        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            boolean isOrderSaved = orderModel.saveOrder(dto.getOrderId(),dto.getCustomerId(),dto.getDate(),dto.getTotal());
            if (isOrderSaved){
                boolean isUpdateItem =menuModel.updateItem(dto.getCartTmList());
                if (isUpdateItem){
                    boolean saveOrderDetails =orderItemModel.seveOrederDetails(dto.getOrderId(),dto.getCartTmList());
                    if (saveOrderDetails){
                        connection.commit();
                    }
                    else {
                        connection.rollback();;
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            connection.setAutoCommit(true);
        }
        return  true;
    }
}

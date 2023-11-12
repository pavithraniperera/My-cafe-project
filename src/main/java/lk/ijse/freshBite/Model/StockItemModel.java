package lk.ijse.freshBite.Model;

import lk.ijse.freshBite.db.DbConnection;
import lk.ijse.freshBite.dto.StockItemDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockItemModel {
    public boolean addItems(StockItemDto dto) throws SQLException {
        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            String sql = "INSERT INTO stock_item VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, dto.getStockId());
            preparedStatement.setString(2, dto.getName());
            preparedStatement.setString(3, String.valueOf(dto.getPrice()));
            preparedStatement.setString(4, String.valueOf(dto.getQuantity()));
            boolean isSaved = preparedStatement.executeUpdate()>0;
            if (isSaved){
                String sql1 = "INSERT INTO item_supply_details(supplier_id,stock_id,date,quantity) VALUES (?,?,?,?)";
                PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
                preparedStatement1.setString(1, dto.getSup_id());
                preparedStatement1.setString(2, dto.getStockId());
                preparedStatement1.setDate(3, new java.sql.Date(new java.util.Date().getTime()));
                preparedStatement1.setInt(4,dto.getQuantity());
                boolean isInsert = preparedStatement1.executeUpdate()>0;
                if (isInsert){
                    connection.commit();
                }
                else {
                    connection.rollback();
                }
            }

        } catch (SQLException e) {
           connection.rollback();
        }
        finally {
            connection.setAutoCommit(true);
        }

         return  true;

    }

    public List<String> getSupIdList() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT sup_id FROM supplier";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<String> list = new ArrayList<>();
        while (resultSet.next()){
            list.add(resultSet.getString(1));
        }
        return list;
    }

    public boolean updateItems( StockItemDto dto) throws SQLException {
        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            String sql = "UPDATE stock_item SET name=?,cost_price=?,quantity =? WHERE stock_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, dto.getName());
            preparedStatement.setString(2, String.valueOf(dto.getPrice()));
            preparedStatement.setString(3, String.valueOf(dto.getQuantity()));
            preparedStatement.setString(4, dto.getStockId());
            boolean isSaved = preparedStatement.executeUpdate()>0;
            if (isSaved){
               String sql1 = "UPDATE item_supply_details SET supplier_id=?,quantity = ?,date=? WHERE stock_id=?" ;
               PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
               preparedStatement1.setString(1, dto.getSup_id());
               preparedStatement1.setInt(2,dto.getQuantity());
                preparedStatement1.setDate(3, new java.sql.Date(new java.util.Date().getTime()));
                preparedStatement1.setString(4, dto.getStockId());
                boolean updated = preparedStatement1.executeUpdate()>0;
                if (updated){
                    connection.commit();
                }
                else {
                    connection.rollback();
                }
            }

        } catch (SQLException e) {
           connection.rollback();
        }
        finally {
            connection.setAutoCommit(true);
        }
        return true;
    }

    public boolean deleteItem(String stockId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "DELETE FROM stock_item WHERE stock_id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,stockId);
        return  preparedStatement.executeUpdate()>0;
    }

    public List<StockItemDto> loadItems() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT si.stock_id,si.name,si.cost_price,si.quantity,isd.date,isd.supplier_id  FROM stock_item si JOIN item_supply_details isd ON si.stock_id = isd.stock_id";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<StockItemDto> stockItems = new ArrayList<>();
        while (resultSet.next()){
            stockItems.add(new StockItemDto(resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(4),
                    resultSet.getDouble(3),
                    resultSet.getString(6),
                    resultSet.getDate(5)));
        }
        return stockItems;

    }
}

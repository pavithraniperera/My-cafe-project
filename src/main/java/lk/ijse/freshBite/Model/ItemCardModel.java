package lk.ijse.freshBite.Model;

import lk.ijse.freshBite.db.DbConnection;
import lk.ijse.freshBite.dto.AddMenuDto;
import lk.ijse.freshBite.dto.StockItemDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemCardModel {

    public String getStatus(String name) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT availability FROM menu_item WHERE name = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,name);
        String menuName = null;
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            menuName =resultSet.getString(1);
        }
        return  menuName;

    }

    public AddMenuDto getItemDetails(String name) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM menu_item WHERE name=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,name);
        ResultSet resultSet=  preparedStatement.executeQuery();
        AddMenuDto dto = null;
        while (resultSet.next()){
            dto = new AddMenuDto(resultSet.getString(1),resultSet.getString(2),resultSet.getString(4),resultSet.getInt(5),resultSet.getDouble(3),resultSet.getString(7),resultSet.getString(6),resultSet.getString(8));
        }
        return  dto;
    }
}

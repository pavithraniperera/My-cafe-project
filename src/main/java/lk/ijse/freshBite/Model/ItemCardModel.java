package lk.ijse.freshBite.Model;

import lk.ijse.freshBite.db.DbConnection;

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
}

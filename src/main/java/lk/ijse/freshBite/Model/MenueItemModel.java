package lk.ijse.freshBite.Model;

import lk.ijse.freshBite.db.DbConnection;
import lk.ijse.freshBite.dto.AddMenuDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenueItemModel {

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
}

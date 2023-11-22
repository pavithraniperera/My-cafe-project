package lk.ijse.freshBite.Model;

import lk.ijse.freshBite.db.DbConnection;
import lk.ijse.freshBite.dto.ProfileDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileModel {
    public Object getUserData() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM users";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        ProfileDto dto = null;
        while (resultSet.next()){
            dto = new ProfileDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6));
        }
        return  dto;
    }

    public boolean updateUser(ProfileDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "UPDATE users SET username=?,password=?,email=?,phone_no=?,address=? WHERE user_id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, dto.getUserName());
        preparedStatement.setString(2, dto.getPassword());
        preparedStatement.setString(3, dto.getEmail());
        preparedStatement.setString(4, dto.getPhone());
        preparedStatement.setString(5, dto.getAddress());
        preparedStatement.setString(6, "U001");
        return  preparedStatement.executeUpdate()>0;


    }
}

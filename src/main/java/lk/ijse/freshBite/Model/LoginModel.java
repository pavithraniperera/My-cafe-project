package lk.ijse.freshBite.Model;

import lk.ijse.freshBite.db.DbConnection;
import lk.ijse.freshBite.dto.LoginDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {

    public LoginModel() {

    }

    public LoginDto CheckUserNamePassword() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        LoginDto dto = null;
        String sql = "SELECT username,password FROM users";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            String userName = resultSet.getString(1);
            String pw = resultSet.getString(2);

            dto = new LoginDto(userName,pw);


        }
        return dto;

    }
}

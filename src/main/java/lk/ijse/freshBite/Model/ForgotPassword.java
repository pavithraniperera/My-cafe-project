package lk.ijse.freshBite.Model;

import lk.ijse.freshBite.db.DbConnection;
import lk.ijse.freshBite.dto.ForgotPassworddto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ForgotPassword {


    public ForgotPassworddto checkUserName() throws SQLException {
        ForgotPassworddto dto = null;
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT username FROM users";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while ((resultSet.next())){
          String userName =  resultSet.getString(1);
            dto = new ForgotPassworddto(userName);


        }
        return  dto;
    }

    public boolean setPassword(ForgotPassworddto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "UPDATE users SET password = ? WHERE username=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, dto.getPwd());
        preparedStatement.setString(2, dto.getUserName());
       return  preparedStatement.executeUpdate()>0;
    }
}

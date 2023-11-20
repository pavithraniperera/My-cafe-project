package lk.ijse.freshBite.Model;

import lk.ijse.freshBite.db.DbConnection;
import lk.ijse.freshBite.dto.ReservationDto;

import javax.naming.ContextNotEmptyException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationModel {

    public boolean saveReservation(ReservationDto dto) throws SQLException {
        String userId = getUserId();
        Connection connection  = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO reservations (reservation_id,user_id,customer_id,date,time,table_number,number_of_people)VALUES (?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, dto.getId());
        preparedStatement.setString(2,userId);
        preparedStatement.setString(3,dto.getCustId());
        preparedStatement.setDate(4, Date.valueOf(dto.getDate()));
        preparedStatement.setString(5, dto.getTime());
        preparedStatement.setInt(6,dto.getTableNo());
        preparedStatement.setInt(7,dto.getSize());
        return preparedStatement.executeUpdate()>0;

    }

    private String getUserId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT user_id FROM users";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            return  resultSet.getString(1);
        }
        return  null;
    }

    public boolean updateReservation(ReservationDto dto) throws SQLException {
        String userId = getUserId();
        Connection connection  = DbConnection.getInstance().getConnection();
        String sql = "UPDATE reservations SET user_id=?,customer_id=?,date=?,time=?,table_number=?,number_of_people=? WHERE reservation_id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1,userId);
        preparedStatement.setString(2,dto.getCustId());
        preparedStatement.setDate(3,Date.valueOf(dto.getDate()));
        preparedStatement.setString(4, dto.getTime());
        preparedStatement.setInt(5,dto.getTableNo());
        preparedStatement.setInt(6,dto.getSize());
        preparedStatement.setString(7, dto.getId());
        return preparedStatement.executeUpdate()>0;
    }

    public List<ReservationDto> getAllReservations() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "select * from reservations r inner join customers c on r.customer_id= c.customer_id";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<ReservationDto> dtoList = new ArrayList<>();
        while (resultSet.next()){

               dtoList.add(new ReservationDto(resultSet.getString(1),
                       resultSet.getDate(4).toLocalDate(),
                       resultSet.getString(5),
                       resultSet.getInt(6),
                       resultSet.getString(9),
                       resultSet.getString(13),
                       resultSet.getInt(7),
                       resultSet.getString(10)
               ));
        }

        return dtoList;
    }

    public List<ReservationDto> getAllReservationListOnDate(LocalDate date) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "select * from reservations r inner join customers c on r.customer_id= c.customer_id WHERE date=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setDate(1, Date.valueOf(date));
        ResultSet resultSet = preparedStatement.executeQuery();
        List<ReservationDto> dtoList = new ArrayList<>();
        while (resultSet.next()){
            dtoList.add(new ReservationDto(resultSet.getString(1),
                    resultSet.getDate(4).toLocalDate(),
                    resultSet.getString(5),
                    resultSet.getInt(6),
                    resultSet.getString(3),
                    resultSet.getString(13),
                    resultSet.getInt(7),
                    resultSet.getString(10)
            ));
        }
        return dtoList;

    }

    public List<ReservationDto> getAllReservationListtodayDate() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "select * from reservations r inner join customers c on r.customer_id= c.customer_id WHERE date=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setDate(1, Date.valueOf(LocalDate.now()));
        ResultSet resultSet = preparedStatement.executeQuery();
        List<ReservationDto> dtoList = new ArrayList<>();
        while (resultSet.next()){
            dtoList.add(new ReservationDto(resultSet.getString(1),
                    resultSet.getDate(4).toLocalDate(),
                    resultSet.getString(5),
                    resultSet.getInt(6),
                    resultSet.getString(3),
                    resultSet.getString(13),
                    resultSet.getInt(7),
                    resultSet.getString(10)
            ));
        }
        return dtoList;
    }

    public String generateNextId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT reservation_id FROM reservations ORDER BY reservation_id DESC LIMIT 1";
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
            String Id = String.format("R%03d", numericValue);
            return Id;
        }
        else {
            return "R001";
        }
    }

    public boolean deleteReservation(String reservationId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "DELETE FROM  reservations WHERE  reservation_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,reservationId);
        return  preparedStatement.executeUpdate()>0;
    }

    public boolean completeReservation(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "UPDATE reservations SET IsCompleted=1 WHERE reservation_id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,id);
        return preparedStatement.executeUpdate()>0;
    }

    public boolean unCompleteReservation(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "UPDATE reservations SET IsCompleted=0 WHERE reservation_id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,id);
        return preparedStatement.executeUpdate()>0;
    }

    public boolean checkComplete(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT IsCompleted FROM reservations WHERE reservation_id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        int complete =-1;
        while (resultSet.next()){
            complete = resultSet.getInt(1);
        }
        if (complete==1){
            return  true;
        }
        else {
            return  false;
        }

    }

    public List<ReservationDto> getTodayReservations() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "select * from reservations r inner join customers c on r.customer_id= c.customer_id WHERE date=? ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, String.valueOf(LocalDate.now()));
        ResultSet resultSet = preparedStatement.executeQuery();
        List<ReservationDto> dtoList = new ArrayList<>();
        while (resultSet.next()){
            dtoList.add(new ReservationDto(resultSet.getString(1),
                    resultSet.getDate(4).toLocalDate(),
                    resultSet.getString(5),
                    resultSet.getInt(6),
                    resultSet.getString(3),
                    resultSet.getString(13),
                    resultSet.getInt(7),
                    resultSet.getString(10)
            ));
        }
        return dtoList;
    }
}

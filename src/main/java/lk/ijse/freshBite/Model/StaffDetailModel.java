package lk.ijse.freshBite.Model;

import lk.ijse.freshBite.db.DbConnection;
import lk.ijse.freshBite.dto.StaffDetailDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StaffDetailModel {
    public boolean addEmployee(StaffDetailDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO Employee VALUES (?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, dto.getEmpId());
        preparedStatement.setString(2, dto.getName());
        preparedStatement.setString(3, dto.getNic());
        preparedStatement.setString(4, dto.getTelephone());
        preparedStatement.setString(5,dto.getAddress());
        preparedStatement.setString(6,dto.getEmail());
        preparedStatement.setString(7, dto.getJobRole());
        preparedStatement.setString(8, String.valueOf(dto.getChargePerHour()));
        preparedStatement.setString(9, dto.getQualification());
        preparedStatement.setString(10, dto.getBarCode());
       return preparedStatement.executeUpdate()>0;
    }

    public boolean updateEmployee(StaffDetailDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "UPDATE Employee SET name=?,NIC=?,phone_number=?,address=?,email=?,job_role=?,charge_per_hour=?,qualification=? WHERE Emp_id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, dto.getName());
        preparedStatement.setString(2, dto.getNic());
        preparedStatement.setString(3, dto.getTelephone());
        preparedStatement.setString(4,dto.getAddress());
        preparedStatement.setString(5,dto.getEmail());
        preparedStatement.setString(6, dto.getJobRole());
        preparedStatement.setString(7, String.valueOf(dto.getChargePerHour()));
        preparedStatement.setString(8, dto.getQualification());
        preparedStatement.setString(9, dto.getEmpId());
        return  preparedStatement.executeUpdate()>0;


    }

    public boolean deleteEmployee(String empId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "DELETE FROM Employee WHERE Emp_id =?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,empId);
        return preparedStatement.executeUpdate()>0;
    }

    public List<StaffDetailDto> loadEmployees() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Employee";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<StaffDetailDto> dtoList = new ArrayList<>();
        while (resultSet.next()){
            dtoList.add(new StaffDetailDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(3),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getDouble(8),
                    resultSet.getString(9)
            ));

        }
        return dtoList;
    }

    public String generateEmpId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT Emp_id FROM Employee ORDER BY Emp_id DESC LIMIT 1";
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
            String nextStockId = String.format("E%03d", numericValue);
            return nextStockId;
        }
        else {
            return "E001";
        }
    }

    public String getBarcodePath(String text) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT barcode_data FROM Employee WHERE Emp_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,text);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }
}

package lk.ijse.freshBite.Model;

import lk.ijse.freshBite.db.DbConnection;
import lk.ijse.freshBite.dto.SupplierDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierModel {
    public boolean addSupplier(SupplierDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO supplier VALUES (?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, dto.getSup_id());
        preparedStatement.setString(2, dto.getName());
        preparedStatement.setString(3, dto.getAddress());
        preparedStatement.setString(4,dto.getTelephone());
        preparedStatement.setString(5, dto.getEMail());
        boolean isSaved = preparedStatement.executeUpdate()>0;
        return isSaved;
    }

    public List<SupplierDto> loadSupplier() throws SQLException {
        Connection connection =DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM supplier";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
       ResultSet resultSet = preparedStatement.executeQuery();
       List<SupplierDto> dtoList = new ArrayList<>();
       while (resultSet.next()){
           dtoList.add(new SupplierDto(resultSet.getString(1),
                   resultSet.getString(2),
                   resultSet.getString(3),
                   resultSet.getString(5),
                   resultSet.getString(4)));
       }
       return  dtoList;
    }

    public boolean updateSupplier(SupplierDto supplierDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "UPDATE supplier SET name=?,address = ?,phone_number=?,email = ? WHERE sup_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, supplierDto.getName());
        preparedStatement.setString(2,supplierDto.getAddress());
        preparedStatement.setString(3, supplierDto.getTelephone());
        preparedStatement.setString(4, supplierDto.getEMail());
        preparedStatement.setString(5, supplierDto.getSup_id());
        boolean isUpdated = preparedStatement.executeUpdate()>0;
        return isUpdated;
    }

    public boolean deleteSupplier(String supId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "DELETE FROM supplier WHERE sup_id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,supId);
        return  preparedStatement.executeUpdate()>0;
    }
}

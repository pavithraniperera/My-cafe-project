package lk.ijse.freshBite.Model;

import lk.ijse.freshBite.db.DbConnection;
import lk.ijse.freshBite.dto.AnalyticsDto;
import lk.ijse.freshBite.dto.CustomerDetailDto;
import lk.ijse.freshBite.dto.tm.StaffTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomerDetailModel {
    public boolean saveCustomer(CustomerDetailDto customerDetailDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO customers VALUES (?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, customerDetailDto.getCustId());
        preparedStatement.setString(2, customerDetailDto.getName());
        preparedStatement.setString(3, customerDetailDto.getAddress());
        preparedStatement.setString(4, customerDetailDto.getEmail());
        preparedStatement.setString(5, customerDetailDto.getTelephone());
        preparedStatement.setString(6, customerDetailDto.getGender());
        preparedStatement.setString(7, customerDetailDto.getMembership());
        return preparedStatement.executeUpdate()>0;
    }

    public boolean updateCustomer(CustomerDetailDto customerDetailDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "UPDATE customers SET name=?,address =?,email=?,phone_no=?,gender=?,membership_level=? WHERE  customer_id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, customerDetailDto.getName());
        preparedStatement.setString(2, customerDetailDto.getAddress());
        preparedStatement.setString(3, customerDetailDto.getEmail());
        preparedStatement.setString(4, customerDetailDto.getTelephone());
        preparedStatement.setString(5, customerDetailDto.getGender());
        preparedStatement.setString(6, customerDetailDto.getMembership());
        preparedStatement.setString(7, customerDetailDto.getCustId());
        return preparedStatement.executeUpdate()>0;
    }

    public boolean deleteCustomer(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "DELETE customers WHERE customer_id =?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,id);
        return preparedStatement.executeUpdate()>0;
    }

    public List<CustomerDetailDto> loadCustomers() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM customers ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        List<CustomerDetailDto> dtoList = new ArrayList<>();
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()){
          dtoList.add(new CustomerDetailDto(
                  resultSet.getString(1),
                  resultSet.getString(2),
                  resultSet.getString(3),
                  resultSet.getString(5),
                  resultSet.getString(4),
                  resultSet.getString(6),
                  resultSet.getString(7)
          ));
      }
      return  dtoList;
    }

    public String generateCustomerId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT customer_id FROM customers ORDER BY customer_id DESC LIMIT 1";
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
            String nextStockId = String.format("C%03d", numericValue);
            return nextStockId;
        }
        else {
            return "C001";
        }
    }

    public List<String> loadCustomerId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT customer_id FROM customers";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<String> list = new ArrayList<>();
        while (resultSet.next()){
            list.add(resultSet.getString(1));
        }
        return list;
    }

    public String getTelephone(String custId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT phone_no FROM customers WHERE customer_id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql) ;
        preparedStatement.setString(1,custId);
        ResultSet resultSet = preparedStatement.executeQuery();
        String tele = null;
        while(resultSet.next()){
            tele = resultSet.getString(1);
        }
        return  tele;
    }

    public String getName(String custId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT name FROM customers WHERE customer_id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql) ;
        preparedStatement.setString(1,custId);
        ResultSet resultSet = preparedStatement.executeQuery();
        String name = null;
        while(resultSet.next()){
            name = resultSet.getString(1);
        }
        return  name;
    }

    public int getCustomerCount() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT count(*) AS avgCustomers FROM customers ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        int totalCustomers = -1;
        while (resultSet.next()){
            totalCustomers = resultSet.getInt(1);
        }
        return  totalCustomers;
    }

    public List<AnalyticsDto> getCustomerLocationData() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT address, COUNT(DISTINCT customer_id) AS customer_count\n" +
                "FROM customers\n" +
                "GROUP BY address\n" +
                "ORDER BY customer_count DESC";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<AnalyticsDto> list = new ArrayList<>();
        while (resultSet.next()){
            list.add(new AnalyticsDto(resultSet.getString(1),resultSet.getInt(2)));
        }
        return list;
    }
}

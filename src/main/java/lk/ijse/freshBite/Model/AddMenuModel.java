package lk.ijse.freshBite.Model;

import javafx.scene.image.Image;
import lk.ijse.freshBite.db.DbConnection;
import lk.ijse.freshBite.dto.AddMenuDto;
import lk.ijse.freshBite.dto.NotificationDto;
import lk.ijse.freshBite.dto.StockItemDto;
import lk.ijse.freshBite.dto.tm.ItemCardTm;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddMenuModel {
    public boolean addMenu(AddMenuDto dto) throws SQLException, IOException {
        Connection connection = DbConnection.getInstance().getConnection();


        String sql = "INSERT INTO menu_item (item_id, name, unit_price, menu_type, qty_on_hand, stock_id, availability, image_path) VALUES (?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, dto.getItemId());
        preparedStatement.setString(2, dto.getName());
        preparedStatement.setDouble(3,dto.getSellPrice());
        preparedStatement.setString(4, dto.getType());
        preparedStatement.setInt(5,dto.getQtyOnhand());
        preparedStatement.setString(6, dto.getStockId());
        preparedStatement.setString(7, dto.getStatus());
        preparedStatement.setString(8, dto.getImagePath());
        return preparedStatement.executeUpdate()>0;

    }



    public List<String> getStockId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT stock_id FROM stock_item";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<String> idList = new ArrayList<>();
        while (resultSet.next()){
           idList.add(resultSet.getString(1));
        }
        return idList;
    }

    public boolean updateMenu(AddMenuDto dto) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();


        String sql = "UPDATE menu_item SET name=?,unit_price =?,menu_type=?,qty_on_hand=?,stock_id=?,availability=?,image_path=? WHERE item_id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, dto.getName());
        preparedStatement.setDouble(2,dto.getSellPrice());
        preparedStatement.setString(3, dto.getType());
        preparedStatement.setInt(4,dto.getQtyOnhand());
        preparedStatement.setString(5, dto.getStockId());
        preparedStatement.setString(6, dto.getStatus());
        preparedStatement.setString(7, dto.getImagePath());
        preparedStatement.setString(8, dto.getItemId());
        return preparedStatement.executeUpdate()>0;

    }

    public boolean deleteItem(String menuId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "DELETE FROM menu_item WHERE item_id=? ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,menuId);
        return preparedStatement.executeUpdate()>0;
    }

    public List<AddMenuDto> loadMenuItems() throws SQLException {
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

    public Image getImgPath(String itemId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT image_path FROM menu_item WHERE item_id=? ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,itemId);
        ResultSet resultSet = preparedStatement.executeQuery();
        String path = null;

        while (resultSet.next()){
            path=resultSet.getString(1);
        }
        return new Image(path,239,232,false,true);
    }
 public StockItemDto getItemDetail(String id) throws SQLException {
     Connection connection = DbConnection.getInstance().getConnection();
     String sql = "SELECT * FROM stock_item WHERE stock_id=?";
     PreparedStatement preparedStatement = connection.prepareStatement(sql);
     preparedStatement.setString(1,id);
     ResultSet resultSet=  preparedStatement.executeQuery();
     StockItemDto dto = null;
     while (resultSet.next()){
         dto = new StockItemDto(resultSet.getString(1),resultSet.getString(2),resultSet.getInt(4));
     }
      return  dto;
    }

    public boolean updateItem(List<ItemCardTm> cartTmList) throws SQLException {
        for (ItemCardTm item : cartTmList){
            if (!updateQty(item.getItemName(),item.getQty())){
                return  false;
            }
        }
        return  true;
    }

    private boolean updateQty(String itemName, int qty) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql ="UPDATE menu_item SET qty_on_hand =qty_on_hand -? WHERE name=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,qty);
        preparedStatement.setString(2,itemName);
        return preparedStatement.executeUpdate()>0;

    }

    public List<NotificationDto> getOutOfStockData() throws SQLException {
        Connection  connection = DbConnection.getInstance().getConnection();
        String sql ="SELECT\n" +
                "  menu_item.name AS item_name,\n" +
                "  supplier.name AS supplier_name,\n" +
                "  supplier.phone_number\n" +
                "FROM menu_item\n" +
                "JOIN item_supply_details\n" +
                "ON menu_item.stock_id = item_supply_details.stock_id\n" +
                "JOIN supplier\n" +
                "ON item_supply_details.supplier_id = supplier.sup_id\n" +
                "WHERE menu_item.qty_on_hand = 0;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<NotificationDto> dtoList = new ArrayList<>();
        while (resultSet.next()){
            dtoList.add(new NotificationDto(resultSet.getString(1),resultSet.getString(3),resultSet.getString(2)));
        }
        return  dtoList;
    }

    public boolean updateStatus(String itemId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql ="UPDATE menu_item SET availability =? WHERE item_id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,"Unavailable");
        preparedStatement.setString(2,itemId);
        return preparedStatement.executeUpdate()>0;
    }

    public boolean updateStatusAvailable(String itemId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql ="UPDATE menu_item SET availability =? WHERE item_id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,"Available");
        preparedStatement.setString(2,itemId);
        return preparedStatement.executeUpdate()>0;
    }
}

package lk.ijse.freshBite.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.freshBite.Model.StockItemModel;
import lk.ijse.freshBite.dto.StockItemDto;
import lk.ijse.freshBite.dto.tm.StockItemTm;
import lk.ijse.freshBite.regex.RegexPattern;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class StockItemFormController {

    public AnchorPane pane2;
    public JFXButton btnSeeSupplier;
    public TableColumn dateCol;
    @FXML
    private JFXButton btnAddItem;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;


    @FXML
    private JFXButton btnUpdate;

    @FXML
    private ComboBox<String> combSupId;

    @FXML
    private TableColumn<?, ?> nameCol;
    @FXML
    private TableColumn<?, ?> priceCol;

    @FXML
    private TableColumn<?, ?> quantityCol;

    @FXML
    private TableColumn<?, ?> stockIdCol;

    @FXML
    private TableColumn<?, ?> supIdCol;

    @FXML
    private TableView<StockItemTm> tableStockItem;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtStockId;
    private StockItemModel model = new StockItemModel();
    public void  initialize()  {
        ObservableList<String> supIdList = FXCollections.observableArrayList();
        List<String> list = null;
        try {
            list = model.getSupIdList();
            for (String id : list){
                supIdList.add(id);
            }
            combSupId.setItems(supIdList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setCellValueFactory();
        loadAllItems();
        tableListener();
        generateNextStockId();


    }

    private void tableListener() {
        tableStockItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
//            System.out.println(newValue);
            setData(newValue);
        });
    }

    private void setData(StockItemTm newValue) {
        txtStockId.setText(newValue.getStockId());
        txtName.setText(newValue.getName());
        combSupId.setValue(newValue.getSup_id());
        txtQuantity.setText(String.valueOf(newValue.getQuantity()));
        txtPrice.setText(String.valueOf(newValue.getPrice()));
    }

    private void loadAllItems() {
        ObservableList<StockItemTm> oblist = FXCollections.observableArrayList();
        try {
            List<StockItemDto> dtoList = model.loadItems();
            for (StockItemDto dto : dtoList){
                oblist.add(new StockItemTm(dto.getStockId() ,
                        dto.getName(),
                        dto.getQuantity(),
                        dto.getPrice(),
                        dto.getSup_id(),
                        dto.getDate()));
            }
            tableStockItem.setItems(oblist);
            oblist.addListener((ListChangeListener<StockItemTm>) change -> {
                while (change.next()) {
                    if (change.wasAdded() || change.wasRemoved()) {
                        tableStockItem.refresh();
                    }
                }
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
       stockIdCol.setCellValueFactory(new PropertyValueFactory<>("stockId"));
       nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
       quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
       priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
       supIdCol.setCellValueFactory(new PropertyValueFactory<>("sup_id"));
       dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

    }

    @FXML
    void btnAddItemOnAction(ActionEvent event) {
        if (validation()) {
            String stockId = txtStockId.getText();
            String name = txtName.getText();
            int quantity = Integer.parseInt(txtQuantity.getText());
            double price = Double.parseDouble(txtPrice.getText());
            String sup_id = (String) combSupId.getValue();
            var dto = new StockItemDto(stockId, name, quantity, price, sup_id);
            try {
                boolean isAdd = model.addItems(dto);
                if (isAdd) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Item add Successful");
                    tableStockItem.refresh();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage());
            }
            clearFields();
        }

    }
    public void generateNextStockId(){
        try {
            String nextId = model.getNextStockId();
            txtStockId.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String stock_id = txtStockId.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Do you want to Delete this item?");
        alert.setContentText("Choose your option.");

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");

        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == yesButton) {
            try {
                boolean isDeleted = model.deleteItem(stock_id);
                if (isDeleted){
                    new Alert(Alert.AlertType.CONFIRMATION,"Item Deleted Successfully!!");
                    tableStockItem.refresh();
                }
            } catch (SQLException e) {
                  new Alert(Alert.AlertType.ERROR,e.getMessage());
            }


        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if (validation()) {
            String stockId = txtStockId.getText();
            String name = txtName.getText();
            int quantity = Integer.parseInt(txtQuantity.getText());
            double price = Double.parseDouble(txtPrice.getText());
            String sup_id = (String) combSupId.getValue();
            var dto = new StockItemDto(stockId, name, quantity, price, sup_id);
            boolean isUpdated = false;
            try {
                isUpdated = model.updateItems(dto);

                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Item Information Updated").show();
                    tableStockItem.refresh();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
            clearFields();

        }

    }

    @FXML
    void comSupIdOnAction(ActionEvent event) {


    }


    public void btnSeeSupplierOnAction(ActionEvent actionEvent) throws IOException {

        Parent fxml = FXMLLoader.load(getClass().getResource("/view/Supplier_form.fxml"));
        pane2.getChildren().removeAll();

        pane2.getChildren().setAll(fxml);
    }
    private void clearFields(){
        txtName.setText("");
        txtPrice.setText("");
        txtQuantity.setText("");
        txtStockId.setText("");
        combSupId.setValue(null);

    }
    public  boolean validation(){

        if (!(Pattern.matches("[I][0-9]{3,}",txtStockId.getText()))){
            new Alert(Alert.AlertType.ERROR,"Invalid Id").show();
            return false;
        }

        if (!(Pattern.matches(String.valueOf(RegexPattern.getIntPattern()),txtQuantity.getText()))){
            new Alert(Alert.AlertType.ERROR,"Invalid Quantity").show();
            return false;
        }

        return  true;
    }

}

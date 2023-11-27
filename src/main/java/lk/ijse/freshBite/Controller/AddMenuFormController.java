package lk.ijse.freshBite.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import lk.ijse.freshBite.Model.AddMenuModel;
import lk.ijse.freshBite.dto.AddMenuDto;
import lk.ijse.freshBite.dto.StockItemDto;
import lk.ijse.freshBite.dto.tm.MenuTm;
import lk.ijse.freshBite.regex.RegexPattern;
import javafx.scene.control.TableCell;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class AddMenuFormController {

    public AnchorPane pane3;
    @FXML
    private TableColumn<?, ?> ItemNameCol;

    @FXML
    private TableColumn<?, ?> QuantityCol;

    @FXML
    private TextField TxtType;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnImport;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private ComboBox<String> cmbId;

    @FXML
    private ComboBox<String> cmbStatus;

    @FXML
    private ImageView img;

    @FXML
    private TableColumn<?, ?> itemIdCol;

    @FXML
    private AnchorPane paneImg;

    @FXML
    private TableColumn<?, ?> priceCol;

    @FXML
    private TableColumn<?, ?> statusCol;

    @FXML
    private TableColumn<?, ?> stockIdCol;

    @FXML
    private TableView<MenuTm> tableMenueItem;

    @FXML
    private TextField txtItemId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtStock;
    private  String imagePath;

    @FXML
    private TableColumn<?, ?> typeCol;
    private AddMenuModel model = new AddMenuModel();
  private   ObservableList<MenuTm> oblist = FXCollections.observableArrayList();
    public void initialize() throws SQLException {
        ObservableList<String> statusList = FXCollections.observableArrayList("Available","Unavailable");
        cmbStatus.setItems(statusList);
        ObservableList<String> idList = FXCollections.observableArrayList();
        List<String> id = model.getStockId();
        for (String id1 :id ){
            idList.add(id1);
        }
        cmbId.setItems(idList);
        setCellValueFactory();
        loadAllMenues();
        tableListener();
        updateStatusIfQuantityZero();

    }

    private void tableListener() {
        tableMenueItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
//            System.out.println(newValue);
            try {
                setData(newValue);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void setData(MenuTm newValue) throws SQLException {
        txtItemId.setText(newValue.getItem_id());
        txtName.setText(newValue.getName());
        txtStock.setText(String.valueOf(newValue.getQty()));
        txtPrice.setText(String.valueOf(newValue.getPrice()));
        cmbId.setValue(newValue.getStock_id());
        cmbStatus.setValue(newValue.getStatus());
        TxtType.setText(newValue.getType());
        Image image = model.getImgPath(newValue.getItem_id());
        if (image != null) {
            img.setImage(image);
        } else {
            // Handle the case where the image is not found
            new Alert(Alert.AlertType.ERROR, "Image not found.").show();
        }

    }

    private void loadAllMenues() {

        try {
            List<AddMenuDto> dtoList = model.loadMenuItems();
            for (AddMenuDto dto : dtoList){
                oblist.add(new MenuTm(dto.getItemId(), dto.getName(), dto.getType(), dto.getQtyOnhand(), dto.getSellPrice(), dto.getStatus(), dto.getStockId()));
            }
            tableMenueItem.setItems(oblist);
            tableMenueItem.refresh();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        itemIdCol.setCellValueFactory(new PropertyValueFactory<>("item_id"));
        ItemNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        QuantityCol.setCellValueFactory(new PropertyValueFactory<>("qty"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        stockIdCol.setCellValueFactory(new PropertyValueFactory<>("stock_id"));


    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        if (validation()) {
            String stockId = String.valueOf(cmbId.getValue());
            String itemId = txtItemId.getText();
            String name = txtName.getText();
            String type = TxtType.getText();
            int qtyOnhand = Integer.parseInt(txtStock.getText());
            double price = Double.parseDouble(txtPrice.getText());
            String status = String.valueOf(cmbStatus.getValue());


            var dto = new AddMenuDto(itemId, name, type, qtyOnhand, price, status, stockId, imagePath);
            try {
                boolean isAdd = model.addMenu(dto);
                if (isAdd) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Menu Item Add Successful!!").show();
                    loadAllMenues();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            clearFields();
        }

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String menu_id = txtItemId.getText();

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
                boolean isDeleted = model.deleteItem(menu_id);
                if (isDeleted){
                    new Alert(Alert.AlertType.CONFIRMATION,"Item Deleted Successfully!!");
                    loadAllMenues();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage());
            }


        }

    }

    @FXML
    void btnImportOnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(pane3.getScene().getWindow());
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString(),239,232,false,true);

            img.setImage(image);
            imagePath = selectedFile.toURI().toString();
        }


    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if (validation()) {
            String itemId = txtItemId.getText();
            String name = txtName.getText();
            String type = TxtType.getText();
            int qtyOnhand = Integer.parseInt(txtStock.getText());
            double price = Double.parseDouble(txtPrice.getText());
            String status = String.valueOf(cmbStatus.getValue());
            String stockId = String.valueOf(cmbId.getValue());
            Image image = img.getImage();
            String path = null;
            if (image != null) {
                path = image.getUrl();
            }


            var dto = new AddMenuDto(itemId, name, type, qtyOnhand, price, status, stockId, path);
            try {
                boolean isUpdate = model.updateMenu(dto);
                if (isUpdate) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Menu Updated!!").show();
                    loadAllMenues();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
            clearFields();
        }

    }
    private void clearFields(){
        txtItemId.setText("");
        txtName.setText("");
        txtPrice.setText("");
        txtStock.setText("");
        cmbStatus.setValue(null);
        cmbId.setValue(null);
        imagePath = "";
        img.setImage(null);
        TxtType.setText("");
    }

    public void setOnAction(ActionEvent actionEvent) {
        String stockId = String.valueOf(cmbId.getValue());
        try {
            StockItemDto itemDto = model.getItemDetail(stockId);
           // txtStock.setText(String.valueOf(itemDto.getQuantity()));
            txtName.setText(itemDto.getName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean validation(){
        if (!(Pattern.matches("[M][0-9]{3,}",txtItemId.getText()))){
            new Alert(Alert.AlertType.ERROR,"Invalid Id").show();
            return false;

        }
        if (!(Pattern.matches(String.valueOf(RegexPattern.getNamePattern()),txtName.getText()))){
            new Alert(Alert.AlertType.ERROR,"Invalid name").show();
            return  false;
        }
        if (!(Pattern.matches("[0-9]{1,13}(\\.[0-9]*)?",txtPrice.getText()))){
            new Alert(Alert.AlertType.ERROR,"Invalid price ").show();
            return  false;
        }
       /* if (!(Pattern.matches("\"[1-9]\\\\d*\"",txtStock.getText()))){
            new Alert(Alert.AlertType.ERROR,"Invalid Quantity ").show();
            return false;
        }*/
        return true;
    }
    private void updateStatusIfQuantityZero() {
        try {
            List<MenuTm> itemsToUpdate = new ArrayList<>();

            for (MenuTm item : oblist) {
                if (item.getQty() == 0) {
                    boolean isUpdate = model.updateStatus(item.getItem_id());
                    if (isUpdate) {
                        itemsToUpdate.add(item);
                    }
                } else if (item.getQty() > 0 && item.getStatus().equals("Unavailable")) {
                    boolean isUpdate = model.updateStatusAvailable(item.getItem_id());
                    if (isUpdate) {
                        itemsToUpdate.add(item);
                    }
                }
            }

            // Update the statuses in the current table data
            for (MenuTm item : itemsToUpdate) {
                if (item.getQty() == 0) {
                    item.setStatus("Unavailable");
                } else if (item.getQty() > 0 && item.getStatus().equals("Unavailable")) {
                    item.setStatus("Available");
                }
            }

            // Optionally, you can call loadAllMenues() here if needed.
            // loadAllMenues();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}

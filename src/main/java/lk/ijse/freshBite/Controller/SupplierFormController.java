package lk.ijse.freshBite.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.freshBite.Model.SupplierModel;
import lk.ijse.freshBite.dto.SupplierDto;
import lk.ijse.freshBite.dto.tm.SupplierTm;
import lk.ijse.freshBite.regex.RegexPattern;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class SupplierFormController {

    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public JFXButton btnClear;

    @FXML
    private TableColumn<?, ?> addressCol;

    @FXML
    private TableColumn<?, ?> emailCol;

    @FXML
    private TableColumn<?, ?> nameCol;

    @FXML
    private TableColumn<?, ?> phoneCol;

    @FXML
    private TableColumn<?, ?> supIdCol;

    @FXML
    private TableView<SupplierTm> tableSupplier;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSupplierId;

    @FXML
    private TextField txtTelephone;
    private SupplierModel supplierModel = new SupplierModel();

    public void initialize(){
      setCellValueFactory();
      loadAllSuppliers();
      tableListener();

    }
    private void tableListener() {
        tableSupplier.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
//            System.out.println(newValue);
            setData(newValue);
        });
    }

    private void setData(SupplierTm row) {
        txtSupplierId.setText(row.getSup_id());
        txtName.setText(row.getName());
        txtAddress.setText(row.getAddress());
        txtTelephone.setText(row.getTelephone());
        txtEmail.setText(row.getEmail());

    }

    private void loadAllSuppliers() {
        ObservableList<SupplierTm> obList= FXCollections.observableArrayList();
        try {
            List <SupplierDto> dtoList = supplierModel.loadSupplier();
            for(SupplierDto dto : dtoList){
                obList.add(new SupplierTm(dto.getSup_id(),
                        dto.getName(),
                        dto.getAddress(),
                        dto.getTelephone(),
                        dto.getEMail()));
            }
            tableSupplier.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    private void setCellValueFactory() {
        supIdCol.setCellValueFactory(new PropertyValueFactory<>("Sup_id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("telephone"));

    }

    @FXML
    void btnAddSupplierOnAction(ActionEvent event) {
        if (validation()) {
            SupplierDto dto = null;
            String sup_id = txtSupplierId.getText();
            String name = txtName.getText();
            String address = txtAddress.getText();
            String Email = txtEmail.getText();
            String telephone = txtTelephone.getText();
            if (sup_id != null) {
                dto = new SupplierDto(sup_id, name, address, Email, telephone);
            } else {
                new Alert(Alert.AlertType.ERROR, "Please enter the required information");
            }
            try {
                if (dto != null) {
                    boolean isSave = supplierModel.addSupplier(dto);
                    if (isSave) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Supplier Saved!!").show();
                        clear();
                    }
                }
            } catch (SQLException e) {

                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }


    }
    public void  clear(){
        txtSupplierId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtTelephone.setText("");
        txtEmail.setText("");
    }

    public void btnUpdateSupplierOnAction(ActionEvent actionEvent) {
        if (validation()) {
            String sup_id = txtSupplierId.getText();
            String name = txtName.getText();
            String Address = txtAddress.getText();
            String telephone = txtTelephone.getText();
            String email = txtEmail.getText();
            try {
                boolean isUpdated = supplierModel.updateSupplier(new SupplierDto(sup_id, name, Address, telephone, email));
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Supplier data updated!!").show();
                }

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }

    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String sup_id = txtSupplierId.getText();
        try {
            boolean isDeleted = supplierModel.deleteSupplier(sup_id);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Supplier Deleted !!").show();
            }
        } catch (SQLException e) {
           new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clear();

    }
    public boolean validation(){
        if (!(Pattern.matches("[S][0-9]{3,}",txtSupplierId.getText()))){
            new Alert(Alert.AlertType.ERROR,"Invalid Id").show();
            return false;
        }
        if (!(Pattern.matches("[A-Za-z]{2,}[^!@%* .]",txtName.getText()))){
            new Alert(Alert.AlertType.ERROR,"Invalid name").show();
            return false;
        }
        if (!(Pattern.matches(String.valueOf(RegexPattern.getEmailPattern()),txtEmail.getText()))){
            new Alert(Alert.AlertType.ERROR,"Invalid Email").show();
            return false;
        }
        if (!(Pattern.matches(String.valueOf(RegexPattern.getCityPattern()),txtAddress.getText()))){
            new Alert(Alert.AlertType.ERROR,"Invalid address").show();
            return false;
        }
        if (!(Pattern.matches(String.valueOf(RegexPattern.getMobilePattern()),txtTelephone.getText()))){
            new Alert(Alert.AlertType.ERROR,"Invalid Phone_no").show();
            return false;
        }
        return  true;

    }
}

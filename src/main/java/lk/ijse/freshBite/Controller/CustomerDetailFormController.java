package lk.ijse.freshBite.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lk.ijse.freshBite.Model.CustomerDetailModel;
import lk.ijse.freshBite.dto.CustomerDetailDto;
import lk.ijse.freshBite.dto.tm.CustomerTm;

import java.sql.SQLException;

public class CustomerDetailFormController {
    public Label lblMembership;

    public  void initialize(){
        genderGrp = new ToggleGroup();
        radioBtnFemale.setToggleGroup(genderGrp);
        radioBtnMale.setToggleGroup(genderGrp);

        ObservableList<String> oblist = FXCollections.observableArrayList(
                "VIP",
                "Gold",
                "Silver",
                "Bronze"
        );
        ComBoxMemebership.setItems(oblist);




    }
    private ToggleGroup genderGrp ;
    @FXML
    private JFXComboBox<String> ComBoxMemebership;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private RadioButton radioBtnFemale;

    @FXML
    private RadioButton radioBtnMale;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtCustId;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtTelephone;
    private CustomerDetailModel model = new CustomerDetailModel();

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtCustId.getText();
        try {
            boolean isUpdated = model.deleteCustomer(id);
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION,"Customer Deleted!!");
            }
        } catch (SQLException e) {
          new Alert(Alert.AlertType.ERROR,e.getMessage());
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtCustId.getText();
        String  name = txtName.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        String tele = txtTelephone.getText();
        String gender = handleRadioBtn();
        String membership = handleComboBox();
       // lblMembership.setText(membership);
        var customerDetailDto  = new CustomerDetailDto(id,name,address,tele,email,gender,membership);
        try {
            boolean isSaved = model.saveCustomer(customerDetailDto);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Customer Saved Successfully").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }


    }

    @FXML
    void btnUpdateONAction(ActionEvent event) {
        String id = txtCustId.getText();
        String  name = txtName.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        String tele = txtTelephone.getText();
        String gender = handleRadioBtn();
        String membership = handleComboBox();
        var customerDetailDto  = new CustomerDetailDto(id,name,address,tele,email,gender,membership);
        try {
            boolean isUpdated = model.updateCustomer(customerDetailDto);
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION,"Customer updated!!").show();
            }
        } catch (SQLException e) {
           new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }
    private String handleRadioBtn (){
        RadioButton selectedBtn = (RadioButton) genderGrp.getSelectedToggle();
        String gender = null;
        if (selectedBtn != null){
             gender = selectedBtn.getText();
        }
        return  gender;
    }
    private void clearFields(){
        txtAddress.setText("");
        txtCustId.setText("");
        txtEmail.setText("");
        txtName.setText("");
        txtTelephone.setText("");
        ComBoxMemebership.setValue(null);
        genderGrp.selectToggle(null);

    }
    private String handleComboBox(){
        String membership = ComBoxMemebership.getValue();
        lblMembership.setText(ComBoxMemebership.getValue());
        return membership;
    }

    public void handleSelectedData(CustomerTm selectedData) {
        txtName.setText(selectedData.getName());
        txtTelephone.setText(selectedData.getTelephone());
        txtCustId.setText(selectedData.getCust_id());
        txtEmail.setText(selectedData.getEmail());
        txtAddress.setText(selectedData.getAddress());
        ComBoxMemebership.setValue(selectedData.getMembership());
        lblMembership.setText(selectedData.getMembership());
        String gender = selectedData.getGender();
        if(gender!=null){
            RadioButton selectedBtn = findRadioBtn(gender);
            if (selectedBtn!=null){
                selectedBtn.setSelected(true);
            }
        }

    }

    private RadioButton findRadioBtn(String gender) {
        for (Toggle toggle : genderGrp.getToggles()) {
            if (toggle instanceof RadioButton) {
                RadioButton btn = (RadioButton) toggle;
                // Assuming the gender information is directly stored in the table column
                if (gender.equalsIgnoreCase(btn.getText())) {
                    return btn;
                }
            }
        }
        return null;
    }


}

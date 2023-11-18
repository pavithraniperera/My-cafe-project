package lk.ijse.freshBite.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.freshBite.Model.StaffDetailModel;
import lk.ijse.freshBite.dto.StaffDetailDto;
import lk.ijse.freshBite.dto.tm.StaffTm;
import lk.ijse.freshBite.regex.RegexPattern;

import java.sql.SQLException;
import java.util.regex.Pattern;

public class StaffDetailFormController {

    public TextField txtJobroll;
    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    public TextField txtAddress;

    @FXML
    public TextField txtCharge;

    @FXML
    public TextField txtEmail;

    @FXML
    public TextField txtEmpId;

    @FXML
    public TextField txtName;

    @FXML
    public TextField txtNic;

    @FXML
    public TextField txtQualification;

    @FXML
    public TextField txtTelephone;
    private StaffDetailModel model = new StaffDetailModel();
    public void initialize(){
        generateNextId();
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String emp_id = txtEmpId.getText();
        try {
            boolean isDeleted = model.deleteEmployee(emp_id);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Employee Deleted").show();
            }
        } catch (SQLException e) {
              new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        if (validation()) {
            String emp_id = txtEmpId.getText();
            String name = txtName.getText();
            String address = txtAddress.getText();
            String telephone = txtTelephone.getText();
            String email = txtEmail.getText();
            String nic = txtNic.getText();
            String jobRll = txtJobroll.getText();
            double chargePerHour = Double.parseDouble(txtCharge.getText());
            String qualification = txtQualification.getText();
            var dto = new StaffDetailDto(emp_id, name, address, nic, telephone, email, jobRll, chargePerHour, qualification);
            try {
                boolean isSaved = model.addEmployee(dto);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Employee saved !!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
            clearFields();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if (validation()) {
            String emp_id = txtEmpId.getText();
            String name = txtName.getText();
            String address = txtAddress.getText();
            String telephone = txtTelephone.getText();
            String email = txtEmail.getText();
            String nic = txtNic.getText();
            String jobRll = txtJobroll.getText();
            double chargePerHour = Double.parseDouble(txtCharge.getText());
            String qualification = txtQualification.getText();
            var dto = new StaffDetailDto(emp_id, name, address, nic, telephone, email, jobRll, chargePerHour, qualification);
            try {
                boolean isUpdated = model.updateEmployee(dto);
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Employee Data Updated !!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
            clearFields();
        }

    }
    public void clearFields(){
        txtEmpId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtCharge.setText("");
        txtEmail.setText("");
        txtJobroll.setText("");
        txtNic.setText("");
        txtQualification.setText("");
        txtTelephone.setText("");
    }


    public void handleSelectedData(StaffTm selectedData) {
        txtEmpId.setText(selectedData.getEmp_id());
        txtName.setText(selectedData.getName());
        txtAddress.setText(selectedData.getAddress());
        txtCharge.setText(String.valueOf(selectedData.getCharge()));
        txtEmail.setText(selectedData.getEmail());
        txtJobroll.setText(selectedData.getJobRole());
        txtNic.setText(selectedData.getNic());
        txtQualification.setText(selectedData.getQualification());
        txtTelephone.setText(selectedData.getTelephone());
    }
    public boolean validation(){
        if (!(Pattern.matches("[E][0-9]{3,}",txtEmpId.getText()))){
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
    public  void generateNextId(){
        try {
            String nextId = model.generateEmpId();
            txtEmpId.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

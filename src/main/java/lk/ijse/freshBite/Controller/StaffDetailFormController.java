package lk.ijse.freshBite.Controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lk.ijse.freshBite.Model.StaffDetailModel;
import lk.ijse.freshBite.dto.StaffDetailDto;
import lk.ijse.freshBite.dto.tm.StaffTm;
import lk.ijse.freshBite.regex.RegexPattern;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class StaffDetailFormController {

    public TextField txtJobroll;
    public ImageView imgBarcode;
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
            String barcodeData = generateBarcode(txtEmpId.getText());
           // System.out.println(barcodeData);
            double chargePerHour = Double.parseDouble(txtCharge.getText());
            String qualification = txtQualification.getText();
            var dto = new StaffDetailDto(emp_id, name, address, nic, telephone, email, jobRll, chargePerHour, qualification,barcodeData);
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

    private String generateBarcode(String employeeId) {
        try {
            // Create a Code128 barcode writer
            Writer writer = new Code128Writer();
            // Encode the employee ID as a barcode
            BitMatrix bitMatrix = writer.encode(employeeId, BarcodeFormat.CODE_128, 200, 80);

            // Allow the user to choose the file path to save the barcode image
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Barcode Image");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Files", "*.png"));
            File selectedFile = fileChooser.showSaveDialog(new Stage());

            if (selectedFile != null) {
                String imagePath = selectedFile.getAbsolutePath();
                MatrixToImageWriter.writeToPath(bitMatrix, "PNG", selectedFile.toPath());
                return imagePath; // Return the image path
            } else {
                return null; // User canceled the file selection
            }

        } catch (WriterException | IOException e) {
            // Handle exceptions more gracefully, e.g., log the exception or throw a custom exception
            e.printStackTrace();
            return null; // Return null if an error occurs
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
        imgBarcode.setImage(null);
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
        try {
            String path = model.getBarcodePath(txtEmpId.getText());
            if (path != null) {
                File file = new File(path);
                String uriString = file.toURI().toString();

                Image image = new Image(uriString);
                imgBarcode.setImage(image);
            } else {
                System.out.println("Path is null");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

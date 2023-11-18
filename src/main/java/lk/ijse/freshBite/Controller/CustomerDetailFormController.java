package lk.ijse.freshBite.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import lk.ijse.freshBite.Model.CustomerDetailModel;
import lk.ijse.freshBite.dto.CustomerDetailDto;
import lk.ijse.freshBite.dto.tm.CustomerTm;
import lk.ijse.freshBite.regex.RegexPattern;

import java.awt.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

public class CustomerDetailFormController {
    public Label lblMembership;
    public Label lblChangeDiscount;

    public  void initialize(){
        genderGrp = new ToggleGroup();
        radioBtnFemale.setToggleGroup(genderGrp);
        radioBtnMale.setToggleGroup(genderGrp);

        ObservableList<String> oblist = FXCollections.observableArrayList(
                "VIP",
                "Gold",
                "Silver",
                "Bronze",
                "None"
        );
        ComBoxMemebership.setItems(oblist);
        generateNextCustomerId();



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
    Map<String, Double> newDiscounts = new HashMap<>();


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
        if (validation()) {
            String id = txtCustId.getText();
            String name = txtName.getText();
            String address = txtAddress.getText();
            String email = txtEmail.getText();
            String tele = txtTelephone.getText();
            String gender = handleRadioBtn();
            String membership = handleComboBox();
            // lblMembership.setText(membership);
            var customerDetailDto = new CustomerDetailDto(id, name, address, tele, email, gender, membership);
            try {
                boolean isSaved = model.saveCustomer(customerDetailDto);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Customer Saved Successfully").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
            clearFields();
        }

    }

    @FXML
    void btnUpdateONAction(ActionEvent event) {
        if (validation()) {
            String id = txtCustId.getText();
            String name = txtName.getText();
            String address = txtAddress.getText();
            String email = txtEmail.getText();
            String tele = txtTelephone.getText();
            String gender = handleRadioBtn();
            String membership = handleComboBox();
            var customerDetailDto = new CustomerDetailDto(id, name, address, tele, email, gender, membership);
            try {
                boolean isUpdated = model.updateCustomer(customerDetailDto);
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Customer updated!!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
            clearFields();
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


    public void membershipOnAction(ActionEvent actionEvent) {
        String memberShip = ComBoxMemebership.getValue();
        lblMembership.setText(memberShip);
    }

    public void lblClickedOnAction(MouseEvent mouseEvent) {
        showChangedDiscountDialog();
    }

    private void showChangedDiscountDialog() {
        Dialog<Map<String, Double>> dialog = new Dialog<>();
        dialog.setTitle("Change Discount Percentages");
        dialog.setHeaderText("Set new discount percentages for membership levels");

        // Create save button and add it to the dialog pane
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        MembershipLevelController.getMembershipLevels().forEach((membership, level) -> {
            Label label = new Label(membership + " Discount:");
            TextField textField = new TextField(String.valueOf(level.getDiscountPercentage()));
            textField.setPromptText("Enter discount percentage");

            // Create a local final variable to capture the current membership in the loop
            final String currentMembership = membership;

            grid.add(label, 0, grid.getChildren().size() / 2);
            grid.add(textField, 1, grid.getChildren().size() / 2);

            // Bind the text property to the newDiscounts map using the local variable
            textField.textProperty().addListener((observable, oldValue, newValue) -> {
                // Save the new value directly to the map using the local variable
                newDiscounts.put(currentMembership, Double.parseDouble(newValue));
            });
        });

        dialog.getDialogPane().setContent(grid);


        // Convert the result to a map when the save button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                return newDiscounts;
            }
            return null;
        });

        // Show the dialog and wait for the user's response
        Optional<Map<String, Double>> result = dialog.showAndWait();
        result.ifPresent(newDiscountsMap -> {
            // Handle the result if needed
        });

    }
    public boolean validation(){
        if (!(Pattern.matches("[C][0-9]{3,}",txtCustId.getText()))){
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
    public void generateNextCustomerId(){
        try {
            String nextId = model.generateCustomerId();
            txtCustId.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

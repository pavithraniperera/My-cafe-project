package lk.ijse.freshBite.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.freshBite.Model.ProfileModel;
import lk.ijse.freshBite.dto.ProfileDto;

import java.sql.SQLException;

public class ProfileFormController {

    @FXML
    private JFXButton btnChangePassword;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private AnchorPane pane2;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtName;

    @FXML
    private PasswordField txtPw;

    @FXML
    private TextField txtUserName;

    @FXML
    private TextField txtmail;

    @FXML
    private TextField txtphone;
    private ProfileModel profileModel = new ProfileModel();
    public void initialize(){
        loadUserData();
    }

    private void loadUserData() {
        try {
            ProfileDto dto = (ProfileDto) profileModel.getUserData();
            if (dto!=null){
                setData(dto);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void setData(ProfileDto dto) {
        txtUserName.setText(dto.getUserName());
        txtPw.setText(dto.getPassword());
        txtphone.setText(dto.getPhone());
        txtmail.setText(dto.getEmail());
        txtAddress.setText(dto.getAddress());

    }

    @FXML
    void btnChangePasswordOnAction(ActionEvent event) {


    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
       String userName = txtUserName.getText();
       String pw = txtPw.getText();
       String mail = txtmail.getText();
       String address = txtAddress.getText();
       String  phoneNo = txtphone.getText();
       var dto = new  ProfileDto(userName,pw,mail,phoneNo,address);
        try {
            boolean isUpdate = profileModel.updateUser(dto);
            if (isUpdate){
                new Alert(Alert.AlertType.INFORMATION,"User Data Updated");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

}

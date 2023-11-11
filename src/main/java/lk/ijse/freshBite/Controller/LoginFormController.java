package lk.ijse.freshBite.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.freshBite.Model.LoginModel;
import lk.ijse.freshBite.dto.LoginDto;

import java.io.IOException;
import java.sql.SQLException;

public class LoginFormController {

    public Label lblAlert;
    public AnchorPane root;
    public TextField txtPassword;

    @FXML
    private JFXButton btnForgotPwd;

    @FXML
    private JFXButton btnLogin;

    @FXML
    private JFXCheckBox pwdCheckBox;

    @FXML
    private PasswordField txtPwd;

    @FXML
    private TextField txtUserName;
    private LoginModel model = new LoginModel();

    @FXML
    void btnForgotPwdONAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Forgot_password_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Forget Password form");
        stage.centerOnScreen();
    }

    @FXML
    void btnLoginOnAction(ActionEvent event) {
        String userName = txtUserName.getText();
        String pwd = txtPwd.getText();
        String pwd1 = txtPassword.getText();
        System.out.println(userName);
        System.out.println(pwd);
        try {
           var dto =  model.CheckUserNamePassword ();
            System.out.println(dto);
           if(dto.getUserName().equals(userName) && dto.getPwd().equals(pwd) || dto.getPwd().equals(pwd1)){
               AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"));
               Scene scene = new Scene(anchorPane);
               Stage stage = (Stage) root.getScene().getWindow();
               stage.setScene(scene);
               stage.centerOnScreen();
           }
           else {
               lblAlert.setVisible(true);
           }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void cbShowPasswordOnAcction(ActionEvent event) {
        if (pwdCheckBox.isSelected()){
            txtPassword.setText(txtPwd.getText());
            txtPassword.setVisible(true);
            txtPwd.setVisible(false);
        }
        else{
            txtPwd.setText(txtPassword.getText());
            txtPwd.setVisible(true);
            txtPassword.setVisible(false);
        }

    }

}


package lk.ijse.freshBite.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.freshBite.Model.ForgotPassword;
import lk.ijse.freshBite.dto.ForgotPassworddto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotPasswordFormController {

    public TextField txtPwd;
    public JFXButton btnReset;
    public TextField txtUserName;
    public Label lblAlert1;
    public Label lblAlert2;
    public AnchorPane root;

    private ForgotPassword model = new ForgotPassword();


    public void btnResetPasswordOnAction(ActionEvent actionEvent) {
        lblAlert1.setVisible(false);
        lblAlert2.setVisible(false);
      String userName = txtUserName.getText();
        try {
            var dto = model.checkUserName();
            if (dto.getUserName().equals(userName)){
               String pw = txtPwd.getText();

               if(pw.length()==8 && validation(pw)) {
                   var dto1 = new ForgotPassworddto(userName, pw);
                   boolean isSet = model.setPassword(dto1);
                   if (isSet) {
                       new Alert(Alert.AlertType.CONFIRMATION, "Password Reset successful!!").show();

                       AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Login_form.fxml"));
                       Scene scene = new Scene(anchorPane);
                       Stage stage = (Stage) root.getScene().getWindow();
                       stage.setScene(scene);
                       stage.setTitle("Login form");
                       stage.centerOnScreen();


                   }
               }
               else {
                   lblAlert2.setVisible(true);
               }
            }
            else{
                lblAlert1.setVisible(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private boolean validation(String pw) {
        if (!(Pattern.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",pw))){
            return  false;
        }
        return  true;
    }

}

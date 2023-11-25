package lk.ijse.freshBite.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lk.ijse.freshBite.Model.CustomerDetailModel;
import lk.ijse.freshBite.email.SendEmail;

import javax.mail.MessagingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MailFormController {



    @FXML
    private JFXButton btnSend;

    @FXML
    private TextField lblSubject;

    @FXML
    private TextField lblText;
    private CustomerDetailModel customerDetailModel = new CustomerDetailModel();


    @FXML
    void btnSendOnAction(ActionEvent event) throws MessagingException {
        try {
            List<String> emailList = customerDetailModel.getCustomerEmails();
           // System.out.println(emailList);
            String subject = lblSubject.getText();
            String text = lblText.getText();
            SendEmail.outMail(text,emailList,subject);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }


}

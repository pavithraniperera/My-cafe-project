package lk.ijse.freshBite.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lk.ijse.freshBite.email.SendEmail;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

public class MailFormController {



    @FXML
    private JFXButton btnSend;

    @FXML
    private TextField lblSubject;

    @FXML
    private TextField lblText;


    @FXML
    void btnSendOnAction(ActionEvent event) throws MessagingException {
        List<String> emailList = new ArrayList<>();
       emailList.add("pererapavithrani02@gmail.com");
       // String email = "pvlo98715@gmail.com";
        String subject = lblSubject.getText();
        String text = lblText.getText();
        SendEmail.outMail(text,emailList,subject);


    }

}

package lk.ijse.freshBite.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lk.ijse.freshBite.Model.CustomerDetailModel;
import lk.ijse.freshBite.email.SendEmail;

import javax.mail.MessagingException;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MailFormController {


    public TextArea txtAreaText;
    public JFXButton btnTextDelete;
    public JFXButton btnAttachFile;
    @FXML
    private JFXButton btnSend;

    @FXML
    private TextField lblSubject;

    @FXML
    private TextField lblText;

    private List<File> attachedFiles = new ArrayList<>();
    private CustomerDetailModel customerDetailModel = new CustomerDetailModel();
    public void initialize(){
       // btnSend.setDisable(true);
        setImageToBtn();
        setImageToFileBtn();
    }


    @FXML
    void btnSendOnAction(ActionEvent event) throws MessagingException {
        try {
            List<String> emailList = customerDetailModel.getCustomerEmails();
           // System.out.println(emailList);
            String subject = lblSubject.getText();
            String text = txtAreaText.getText();
            SendEmail.outMail(text,emailList,subject,attachedFiles);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }


    public void txtAreaOnKeyTyped(KeyEvent keyEvent) {
        if (txtAreaText.getText().equals("")){
           btnSend.setDisable(true);
        }
        else {
            btnSend.setDisable(false);
        }
    }

    public void btnTextDeleteOnAction(ActionEvent actionEvent) {
        txtAreaText.setText("");
    }
    public  void setImageToBtn(){
        Image image = new Image("/image/icons8-delete-80.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(40);
        imageView.setFitHeight(40);
        btnTextDelete.setGraphic(imageView);
    }

    public void btnAttachFileOnAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Attach File");
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {

            attachedFiles.add(selectedFile);

            // Get the existing text from the text area
            String existingText = txtAreaText.getText();
            String fileName = selectedFile.getName();
            String modifiedText = fileName.replace("[FILE_NAME_PLACEHOLDER]", "Your_File_Name.pdf");


            // Append the file name to the existing text
            String newText = existingText + "\nAttached File: " + selectedFile.getName();

            // Set the updated text back to the text area
            txtAreaText.setText(newText);
        }
    }
    public void setImageToFileBtn(){
        Image image = new Image("/image/icons8-attach-file-25.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(40);
        imageView.setFitHeight(40);
        btnAttachFile.setGraphic(imageView);
    }
}

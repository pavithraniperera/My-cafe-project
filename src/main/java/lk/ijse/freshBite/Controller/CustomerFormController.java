package lk.ijse.freshBite.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CustomerFormController {

    public JFXButton btnAddCustomer;
    public AnchorPane pane2;

    public void btnAddCustomerOnAction(ActionEvent actionEvent) throws IOException {

        Parent fxml = FXMLLoader.load(getClass().getResource("/view/Customer_detail_form.fxml"));
        pane2.getChildren().removeAll();

        pane2.getChildren().setAll(fxml);

    }
}

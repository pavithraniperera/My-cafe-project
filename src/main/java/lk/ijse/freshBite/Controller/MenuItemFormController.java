package lk.ijse.freshBite.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MenuItemFormController {

    public JFXButton btnNewMenu;
    public JFXButton btnShowTable;
    public AnchorPane pane2;

    public void btnNewMenuOnAction(ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/view/add-menu-form.fxml"));
        pane2.getChildren().removeAll();

        pane2.getChildren().setAll(fxml);

    }

    public void btnShowTableOnAction(ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/view/add-menu-form.fxml"));
        pane2.getChildren().removeAll();

        pane2.getChildren().setAll(fxml);

    }
}

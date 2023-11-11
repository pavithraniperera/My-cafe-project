package lk.ijse.freshBite.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.ResourceBundle;

public class DashboardController  implements Initializable {

    public AnchorPane pane1;
    public AnchorPane pane2;
    public ImageView menuAfterClicked;
    public ImageView imgMenuItem;
    public AnchorPane root;
    @FXML
    private JFXButton btnAnalytics;

    @FXML
    private JFXButton btnCustomer;

    @FXML
    private JFXButton btnDashboard;

    @FXML
    private JFXButton btnLogOUt;

    @FXML
    private JFXButton btnMenueItem;

    @FXML
    private JFXButton btnProfile;

    @FXML
    private JFXButton btnReservation;

    @FXML
    private JFXButton btnStaff;

    @FXML
    private JFXButton btnStock;

    @FXML
    private ImageView imgAnalytics;

    @FXML
    private ImageView imgCustomer;

    @FXML
    private ImageView imgDasboard;

    @FXML
    private ImageView imgExit;

    @FXML
    private ImageView imgLogOut;

    @FXML
    private ImageView imgMenu;

    @FXML
    private ImageView imgMenueItem;

    @FXML
    private ImageView imgProfile;

    @FXML
    private ImageView imgReservation;

    @FXML
    private ImageView imgStaff;

    @FXML
    private ImageView imgStock;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onExit(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void onNenuClicked(MouseEvent mouseEvent) {

        if (pane1.isVisible()) {
            pane1.setVisible(false);
            TranslateTransition transition = new TranslateTransition(Duration.seconds(1), pane1);

// Set the initial and final positions (fromX, fromY, toX, toY)
            transition.setFromY(-pane1.getHeight()); // Start off the screen
            transition.setToY(0); // End at the desired position
            transition.play();
            menuAfterClicked.setVisible(false);
            imgMenu.setVisible(true);

        }
        else {
            pane1.setVisible(true);

            TranslateTransition transition = new TranslateTransition(Duration.seconds(1), pane1);

// Set the initial and final positions (fromX, fromY, toX, toY)
            transition.setFromY(-pane1.getHeight()); // Start off the screen
            transition.setToY(0); // End at the desired position
            transition.play();
            imgMenu.setVisible(false);
            menuAfterClicked.setVisible(true);
        }
    }

    public void onAfterMenueClicked(MouseEvent mouseEvent) {

        if (pane1.isVisible()) {
            pane1.setVisible(false);
            TranslateTransition transition = new TranslateTransition(Duration.seconds(1), pane1);

// Set the initial and final positions (fromX, fromY, toX, toY)
            transition.setFromY(-pane1.getHeight()); // Start off the screen
            transition.setToY(0); // End at the desired position
            transition.play();
            menuAfterClicked.setVisible(false);
            imgMenu.setVisible(true);

        }
        else {
            pane1.setVisible(true);

            TranslateTransition transition = new TranslateTransition(Duration.seconds(1), pane1);

// Set the initial and final positions (fromX, fromY, toX, toY)
            transition.setFromY(-pane1.getHeight()); // Start off the screen
            transition.setToY(0); // End at the desired position
            transition.play();
            imgMenu.setVisible(false);
            menuAfterClicked.setVisible(true);
        }

    }

    public void onbtnmenu(ActionEvent actionEvent) throws IOException {
        //AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/MenuItem_form.fxml"));
        Parent fxml = FXMLLoader.load(getClass().getResource("/view/MenuItem_form.fxml"));
        pane2.getChildren().removeAll();

        pane2.getChildren().setAll(fxml);
        pane2.getChildren().add(pane1);


    }

    public void onimgMenu(MouseEvent mouseEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/view/MenuItem_form.fxml"));
        pane2.getChildren().removeAll();

        pane2.getChildren().setAll(fxml);
        pane2.getChildren().add(pane1);
    }

    public void btnDashBoardOnAction(ActionEvent actionEvent) throws IOException {


        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();

    }

    public void imgDashboardClick(MouseEvent mouseEvent) throws IOException {

        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    public void CustomerMouseClicked(MouseEvent mouseEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/view/Customer_form.fxml"));
        pane2.getChildren().removeAll();

        pane2.getChildren().setAll(fxml);
        pane2.getChildren().add(pane1);

    }

    public void btnCustomerOnAction(ActionEvent actionEvent) throws IOException {

        Parent fxml = FXMLLoader.load(getClass().getResource("/view/Customer_form.fxml"));
        pane2.getChildren().removeAll();

        pane2.getChildren().setAll(fxml);
        pane2.getChildren().add(pane1);
    }

    public void ReservationOnClicked(MouseEvent mouseEvent) throws IOException {

        Parent fxml = FXMLLoader.load(getClass().getResource("/view/Reservation_form.fxml"));
        pane2.getChildren().removeAll();

        pane2.getChildren().setAll(fxml);
        pane2.getChildren().add(pane1);
    }

    public void btnReservationOnAction(ActionEvent actionEvent) throws IOException {

        Parent fxml = FXMLLoader.load(getClass().getResource("/view/Reservation_form.fxml"));
        pane2.getChildren().removeAll();

        pane2.getChildren().setAll(fxml);
        pane2.getChildren().add(pane1);
    }
    public void stockClicked(MouseEvent mouseEvent) throws IOException {

        Parent fxml = FXMLLoader.load(getClass().getResource("/view/StockItem_form.fxml"));
        pane2.getChildren().removeAll();

        pane2.getChildren().setAll(fxml);
        pane2.getChildren().add(pane1);
    }

    public void btnStockOnAction(ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/view/StockItem_form.fxml"));
        pane2.getChildren().removeAll();

        pane2.getChildren().setAll(fxml);
        pane2.getChildren().add(pane1);
    }

    public void analyticsClicked(MouseEvent mouseEvent) throws IOException {

        Parent fxml = FXMLLoader.load(getClass().getResource("/view/Analytics_form.fxml"));
        pane2.getChildren().removeAll();

        pane2.getChildren().setAll(fxml);
        pane2.getChildren().add(pane1);
    }

    public void btnAnalyticsOnAction(ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/view/Analytics_form.fxml"));
        pane2.getChildren().removeAll();

        pane2.getChildren().setAll(fxml);
        pane2.getChildren().add(pane1);
    }

    public void staffClicked(MouseEvent mouseEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/view/Staff_form.fxml"));
        pane2.getChildren().removeAll();

        pane2.getChildren().setAll(fxml);
        pane2.getChildren().add(pane1);
    }

    public void btnStaffOnAction(ActionEvent actionEvent) throws IOException {

        Parent fxml = FXMLLoader.load(getClass().getResource("/view/Staff_form.fxml"));
        pane2.getChildren().removeAll();

        pane2.getChildren().setAll(fxml);
        pane2.getChildren().add(pane1);
    }

    public void profileClicked(MouseEvent mouseEvent) throws IOException {

        Parent fxml = FXMLLoader.load(getClass().getResource("/view/Profile_form.fxml"));
        pane2.getChildren().removeAll();

        pane2.getChildren().setAll(fxml);
        pane2.getChildren().add(pane1);
    }

    public void btnProfileOnAction(ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/view/Profile_form.fxml"));
        pane2.getChildren().removeAll();

        pane2.getChildren().setAll(fxml);
        pane2.getChildren().add(pane1);
    }


    public void btnLogOutOnAction(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Do you want to log out?");
        alert.setContentText("Choose your option.");

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");

        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == yesButton) {
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Login_form.fxml"));
            Scene scene = new Scene(anchorPane);
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Login form");
            stage.centerOnScreen();


        }



    }

    public void logOutClicked(MouseEvent mouseEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Do you want to log out?");
        alert.setContentText("Choose your option.");

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");

        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == yesButton) {
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Login_form.fxml"));
            Scene scene = new Scene(anchorPane);
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Login form");
            stage.centerOnScreen();


        }

    }

}


package lk.ijse.freshBite.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.freshBite.Model.CustomerDetailModel;
import lk.ijse.freshBite.Model.OrderModel;
import lk.ijse.freshBite.Model.ReservationModel;
import lk.ijse.freshBite.dto.ReservationDto;
import lk.ijse.freshBite.dto.tm.DashbordTm;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class DashboardController  implements Initializable {

    public AnchorPane pane1;
    public AnchorPane pane2;
    public ImageView menuAfterClicked;
    public ImageView imgMenuItem;
    public AnchorPane root;
    public Label lblRevenue;
    public Label lblorders;
    public Label lblCustomer;
    public TableView tableReservationList;
    public TableColumn colTime;
    public TableColumn colCustId;
    public TableColumn colTablNo;
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
    private OrderModel orderModel = new OrderModel();
    private CustomerDetailModel customerDetailModel = new CustomerDetailModel();
    private ReservationModel reservationModel = new ReservationModel();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setOrder();
        setRevenue();
        setCustomers();
        setCellValueFactory();
        loadTableReservations();

    }

    private void setCellValueFactory() {
       colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
       colTablNo.setCellValueFactory(new PropertyValueFactory<>("table_no"));
       colCustId.setCellValueFactory(new PropertyValueFactory<>("custId"));
    }

    private void loadTableReservations() {
        ObservableList<DashbordTm> obList = FXCollections.observableArrayList();
        try {
            List<ReservationDto> dtoList = reservationModel.getTodayReservations();
            System.out.println(dtoList);
            for (ReservationDto dto :dtoList){
                obList.add(new DashbordTm(dto.getTime(), dto.getTableNo(), dto.getCustId()));
            }
            tableReservationList.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void setCustomers() {
        try {
            int AvgCustomers = customerDetailModel.getCustomerCount();
            if (AvgCustomers!=(-1)){
                lblCustomer.setText(String.valueOf(AvgCustomers));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setRevenue() {
        try {
            double price = orderModel.getTotalPriceOfToday();
            if (price!=(-1)){
                lblRevenue.setText(String.valueOf(price));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    private void setOrder() {
        try {
            int totalOrders = orderModel.getTotalOrderCount();
            if (totalOrders!=(-1)){
                lblorders.setText(String.valueOf(totalOrders));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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


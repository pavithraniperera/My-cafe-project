package lk.ijse.freshBite.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import lk.ijse.freshBite.Model.CustomerDetailModel;
import lk.ijse.freshBite.Model.OrderModel;
import lk.ijse.freshBite.Model.ReservationModel;
import lk.ijse.freshBite.dto.IncomeDto;
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
    public JFXButton btnCalculater;
    public JFXButton btnNotification;
    public JFXButton btnSend;
    @FXML
    private LineChart<?, ?> chartRevenue;
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
        loadIncome();
        setImageToBtn();
        SetNotificationBtn();
        setSendButton();

    }

    private void setSendButton() {
        Image image = new Image("/image/icons8-send-mail-53.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        btnSend.setGraphic(imageView);
    }

    private void SetNotificationBtn() {
        Image image = new Image("/image/icons8-notification-48 (1).png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        btnNotification.setGraphic(imageView);
    }

    private void setImageToBtn() {
        Image image = new Image("/image/icons8-calculator-24.png"); // Replace with the actual path

        // Create an ImageView with the Image
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);

        btnCalculater.setGraphic(imageView);
    }

    private void loadIncome() {
        try {
            List<IncomeDto> list = orderModel.getIncomeDetails();
            if (!list.isEmpty()){
                setupLineChart(list);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void setupLineChart(List<IncomeDto> list) {
        chartRevenue.getData().clear();
        // Create a new series for the line chart
        XYChart.Series series = new XYChart.Series();
        // Populate the series with data from the incomeList
        for (IncomeDto incomeDto : list) {
            series.getData().add(new XYChart.Data<>(incomeDto.getOrderDate(), incomeDto.getDailyIncome()));

        }
        // Add the series to the line chart
        chartRevenue.getData().add(series);

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
            tableReservationList.refresh();
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

    public void btnCalculaterOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Calculater_form.fxml"));
        Parent root = loader.load();
        Stage calculatorStage = new Stage();
        calculatorStage.setScene(new Scene(root));
        calculatorStage.initStyle(StageStyle.TRANSPARENT);
        calculatorStage.setResizable(false);
        calculatorStage.setTitle("calculater");
        calculatorStage.show();

    }

    public void btnNotificationOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Notification_form.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        double screenHeight = Screen.getPrimary().getBounds().getHeight();
        double finalY = (screenHeight - stage.getHeight()) / 2; // Centered on the screen

        double initialY = -stage.getHeight(); // Initial Y position above the screen

        // Set the initial Y position
        stage.setY(initialY);
        Timeline timeline = new Timeline();
        // Create a timeline for the animation
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.5)// Animate to the final Y position
                )
        );

        timeline.play();

        // Show the stage after the animation starts
        stage.show();;
    }

    public void btnSendOnAction(ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/view/Mail_form.fxml"));
        pane2.getChildren().removeAll();

        pane2.getChildren().setAll(fxml);
    }
}


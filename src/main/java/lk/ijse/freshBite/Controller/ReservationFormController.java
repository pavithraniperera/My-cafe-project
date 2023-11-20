package lk.ijse.freshBite.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.freshBite.Model.CustomerDetailModel;
import lk.ijse.freshBite.Model.ReservationModel;
import lk.ijse.freshBite.dto.ReservationDto;
import lk.ijse.freshBite.dto.tm.ReservationTm;
import lk.ijse.freshBite.regex.RegexPattern;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class ReservationFormController {

    public TextField txtReservationId;
    public TableColumn colComplete;
    public Label lblReservationList;
    @FXML
    private ComboBox<String> comboTime;
    public JFXButton btnUpdate;
    @FXML
    private ComboBox<String> CombCustId;

    @FXML
    private JFXButton btnReservation;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colCustId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colSize;

    @FXML
    private TableColumn<?, ?> colTableNo;

    @FXML
    private TableColumn<?, ?> colTelephone;

    @FXML
    private TableColumn<?, ?> colTime;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private DatePicker datePickerCalender1;

    @FXML
    private DatePicker datePickerCalender2;

    @FXML
    private Label lblDate;

    @FXML
    private TableView<ReservationTm> tableReservation;

    @FXML
    private TextField txtSize;

    @FXML
    private TextField txtTableNo;

    @FXML
    private TextField txtTelephone;

    @FXML
    private TextField txtTime;
    private CustomerDetailModel customerDetailModel = new CustomerDetailModel();
    private ReservationModel model = new ReservationModel();
    public void initialize(){
        setCellValueFactory();
         generateNextId();
         allReservation();
       loadComboTime();
       loadCustId();
       tableListener();

    }
    private void tableListener() {
        tableReservation.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
//            System.out.println(newValue);
            setData(newValue);
        });
    }

    private void setData(ReservationTm newValue) {
        txtTelephone.setText(newValue.getTelephone());
        txtSize.setText(String.valueOf(newValue.getSize()));
        String time = newValue.getTime();
        String[] parts = time.split(" ");
        if (parts.length == 2) {
            String string1 = parts[0]; // "9.00"
            String string2 = parts[1];//PM
            txtTime.setText(string1);
            comboTime.setValue(string2);
        }
      //  datePickerCalender2.setValue(LocalDate.parse(lblDate.getText()));
        txtReservationId.setText(newValue.getReservationId());
        txtTableNo.setText(String.valueOf(newValue.getTableNo()));
        CombCustId.setValue(newValue.getCustId());



    }

    private void loadAllReservations() {
        ObservableList<ReservationTm> oblist = FXCollections.observableArrayList();
        try {
            List<ReservationDto> dtoList = model.getAllReservations();
            lblDate.setText("");
            for (ReservationDto dto : dtoList){
                JFXButton button = createRemoveButton();
                setRemoveBtnOnAction(button);
                CheckBox checkBox = new CheckBox();
                boolean isComplete = model.checkComplete(dto.getId());
                checkBox.setSelected(isComplete);
                // Set a listener for the CheckBox
                checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue) {
                        // The CheckBox is selected, mark the reservation as completed
                        markReservationAsCompleted(dto.getId());
                    } else {
                        // The CheckBox is deselected, you may want to handle this case
                        unmarkReservationAsCompleted(dto.getId());
                    }
                });
                oblist.add(new ReservationTm(dto.getId(),
                        dto.getTime(),
                        dto.getCustId(),
                        dto.getName(),
                        dto.getTableNo(),
                        dto.getSize(),
                        dto.getTelephone(),
                        button,
                        new CheckBox()
                        ));
            }
            tableReservation.setItems(oblist);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private JFXButton createRemoveButton() {
        JFXButton button = new JFXButton();
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/image/icons8-delete-80.png")));
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        button.setGraphic(imageView);
        button.setCursor(Cursor.HAND);
        return button;

    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("reservationId"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colCustId.setCellValueFactory(new PropertyValueFactory<>("custId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colTableNo.setCellValueFactory(new PropertyValueFactory<>("tableNo"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        colComplete.setCellValueFactory(new PropertyValueFactory<>("checkBox"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("deleteBtn"));

    }

    private void loadCustId() {
        ObservableList<String> custIdList = FXCollections.observableArrayList();
        try {
            List<String> list = customerDetailModel.loadCustomerId();
            custIdList.addAll(list);
            CombCustId.setItems(custIdList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadComboTime() {
        ObservableList<String> timePeriods = FXCollections.observableArrayList("AM", "PM");
        comboTime.setItems(timePeriods);

    }

    @FXML
    void btnReservationOnAction(ActionEvent event) {
        if (validation()) {
            String id = txtReservationId.getText();
            LocalDate date = datePickerCalender2.getValue();
            String timeTxt = txtTime.getText();
            String period = comboTime.getValue();
            String time = timeTxt + " " + period;
            int tableNo = Integer.parseInt(txtTableNo.getText());
            String custId = CombCustId.getValue();
            String name = null;
            try {
                name = customerDetailModel.getName(custId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            String telephone = txtTelephone.getText();
            int size = Integer.parseInt(txtSize.getText());
            var dto = new ReservationDto(id,date, time, tableNo, custId, telephone, size,name);
            try {
                boolean isSaved =model.saveReservation(dto);
                if (isSaved){
                    new Alert(Alert.AlertType.INFORMATION,"Reservation saved").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }


        }

    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        if (validation()) {
            String id = txtReservationId.getText();
            LocalDate date = datePickerCalender2.getValue();
            String timeTxt = txtTime.getText();
            String period = comboTime.getValue();
            String time = timeTxt + " " + period;
            int tableNo = Integer.parseInt(txtTableNo.getText());
            String custId = CombCustId.getValue();
            String name = null;
            try {
                name = customerDetailModel.getName(custId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            String telephone = txtTelephone.getText();
            int size = Integer.parseInt(txtSize.getText());
            var dto = new ReservationDto(id,date, time, tableNo, custId, telephone, size,name);
            try {
                boolean isUpdated =model.updateReservation(dto);
                if (isUpdated){
                    new Alert(Alert.AlertType.INFORMATION,"Reservation Update").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }


        }
    }

    public void datePickerCalender1OnAction(KeyEvent keyEvent) {
        LocalDate date = datePickerCalender1.getValue();
        lblDate.setText(String.valueOf(date));
        ObservableList<ReservationTm> observableList = FXCollections.observableArrayList();
        try {
            List<ReservationDto> dtoList = model.getAllReservationListOnDate(date);
            for (ReservationDto dto : dtoList){
                JFXButton button = createRemoveButton();
                setRemoveBtnOnAction(button);
                CheckBox checkBox = new CheckBox();
                boolean isComplete = model.checkComplete(dto.getId());
                checkBox.setSelected(isComplete);
                // Set a listener for the CheckBox
                checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue) {
                        // The CheckBox is selected, mark the reservation as completed
                        markReservationAsCompleted(dto.getId());
                    } else {
                        // The CheckBox is deselected, you may want to handle this case
                        unmarkReservationAsCompleted(dto.getId());
                    }
                });
                observableList.add(new ReservationTm(dto.getId(),
                        dto.getTime(),
                        dto.getCustId(),
                        dto.getName(),
                        dto.getTableNo(),
                        dto.getSize(),
                        dto.getTelephone(),
                        button,
                        checkBox
                ));
            }
            tableReservation.getItems().clear();
            tableReservation.setItems(observableList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    public void allReservation(){
        ObservableList<ReservationTm> observableList = FXCollections.observableArrayList();
        lblDate.setText(String.valueOf(LocalDate.now()));
        try {
            List<ReservationDto> dtoList = model.getAllReservationListtodayDate();
            for (ReservationDto dto : dtoList){
                JFXButton button = createRemoveButton();
                setRemoveBtnOnAction(button);
                CheckBox checkBox = new CheckBox();
                boolean isComplete = model.checkComplete(dto.getId());
                checkBox.setSelected(isComplete);

                // Set a listener for the CheckBox
                checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue) {
                        // The CheckBox is selected, mark the reservation as completed
                        markReservationAsCompleted(dto.getId());
                    } else {
                        // The CheckBox is deselected, you may want to handle this case
                        unmarkReservationAsCompleted(dto.getId());
                    }
                });


                observableList.add(new ReservationTm(dto.getId(),
                        dto.getTime(),
                        dto.getCustId(),
                        dto.getName(),
                        dto.getTableNo(),
                        dto.getSize(),
                        dto.getTelephone(),
                        button,
                        checkBox
                ));
            }
            tableReservation.getItems().clear();
            tableReservation.setItems(observableList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void unmarkReservationAsCompleted(String id) {
        try {
            boolean isUpdated = model.unCompleteReservation(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void markReservationAsCompleted(String id) {
        try {
            boolean isUpdated = model.completeReservation(id);
            if (isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"reservation Is Completed").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage());
        }

    }

    public void comboIdOnAction(ActionEvent actionEvent) {
        String custId = CombCustId.getValue();
        try {
            String telephone = customerDetailModel.getTelephone(custId);
            if (telephone!= null){
                txtTelephone.setText(telephone);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private  boolean validation(){
        if (!(Pattern.matches(String.valueOf(RegexPattern.getIntPattern()),txtSize.getText()))){
            new Alert(Alert.AlertType.ERROR,"Invalid").show();
            return false;
        }
        if (!(Pattern.matches(String.valueOf(RegexPattern.getIntPattern()),txtTableNo.getText()))){
            new Alert(Alert.AlertType.ERROR,"Invalid").show();
            return  false;
        }

        return true;
    }

    public void lblReservationOnAction(MouseEvent mouseEvent) {
        loadAllReservations();

    }
    public void generateNextId(){
        try {
            String nextId = model.generateNextId();
            txtReservationId.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void setRemoveBtnOnAction(JFXButton button) {
        button.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();
            if (type.orElse(no) == yes) {
                int focusedIndex = tableReservation.getSelectionModel().getSelectedIndex();
                if (focusedIndex >= 0) {
                    ReservationTm selectedReservation = tableReservation.getItems().get(focusedIndex);
                    String reservationId = selectedReservation.getReservationId();
                    try {
                        boolean deleted = model.deleteReservation(reservationId);
                        if (deleted) {
                            new Alert(Alert.AlertType.INFORMATION, "Reservation Deleted").show();
                        }
                    } catch (SQLException ex) {
                        new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
                    }


                }
            }
        });
    }


}

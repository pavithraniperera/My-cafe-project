package lk.ijse.freshBite.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.freshBite.Model.StaffDetailModel;
import lk.ijse.freshBite.dto.StaffDetailDto;
import lk.ijse.freshBite.dto.tm.StaffTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class StaffFormController {

    @FXML
    private TableColumn<?, ?> NicCol;

    @FXML
    private TableColumn<?, ?> addressCol;

    @FXML
    private JFXButton btnAddEmployee;

    @FXML
    private TableColumn<?, ?> chargeCol;

    @FXML
    private TableColumn<?, ?> emailCol;

    @FXML
    private TableColumn<?, ?> empIdCol;

    @FXML
    private TableColumn<?, ?> jobroleCol;

    @FXML
    private TableColumn<?, ?> nameCol;

    @FXML
    private AnchorPane pane2;

    @FXML
    private TableColumn<?, ?> qualificationCol;

    @FXML
    private TableView<StaffTm> tableEmployee;

    @FXML
    private TableColumn<?, ?> telephoneCol;
    private StaffDetailModel model = new StaffDetailModel();

    public   void initialize(){
        setCellValueFactory();
        loadAllEmployees();


    }




    private void loadAllEmployees() {
        ObservableList<StaffTm> oblist = FXCollections.observableArrayList();
        try {
            List<StaffDetailDto> dtoList = model.loadEmployees();
            for (StaffDetailDto dto : dtoList){
                oblist.add(new StaffTm(
                        dto.getEmpId(),
                        dto.getName(),
                        dto.getAddress(),
                        dto.getTelephone(),
                        dto.getEmail(),
                        dto.getChargePerHour(),
                        dto.getQualification(),
                        dto.getJobRole(),
                        dto.getNic()
                ));
            }
            tableEmployee.setItems(oblist);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void setCellValueFactory() {
        empIdCol.setCellValueFactory(new PropertyValueFactory<>("emp_id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        telephoneCol.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        chargeCol.setCellValueFactory(new PropertyValueFactory<>("charge"));
        qualificationCol.setCellValueFactory(new PropertyValueFactory<>("qualification"));
        jobroleCol.setCellValueFactory(new PropertyValueFactory<>("jobRole"));
        NicCol.setCellValueFactory(new PropertyValueFactory<>("nic"));
    }


    public void btnAddEmployeeOnAction(ActionEvent actionEvent) throws IOException {

        Parent fxml = FXMLLoader.load(getClass().getResource("/view/Staff_detail_form.fxml"));
        pane2.getChildren().removeAll();

        pane2.getChildren().setAll(fxml);
    }

    public void tableOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getClickCount()==1){
            StaffTm selectedData = tableEmployee.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Staff_detail_form.fxml"));
            Parent fxml = loader.load();
            if (selectedData!=null){
                StaffDetailFormController detailFormController = loader.getController();
                detailFormController.handleSelectedData(selectedData);
                pane2.getChildren().setAll(fxml);

            }

        }
    }
}

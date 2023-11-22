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
import lk.ijse.freshBite.Model.CustomerDetailModel;
import lk.ijse.freshBite.dto.CustomerDetailDto;
import lk.ijse.freshBite.dto.tm.CustomerTm;
import lk.ijse.freshBite.dto.tm.StaffTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CustomerFormController {

    public JFXButton btnAddCustomer;

    public AnchorPane pane2;
    public JFXButton btnSendEmail;

    @FXML
    private TableColumn<?, ?> addressCol;


    @FXML
    private TableColumn<?, ?> emailCol;

    @FXML
    private TableColumn<?, ?> genderCol;

    @FXML
    private TableColumn<?, ?> idCol;

    @FXML
    private TableColumn<?, ?> membershipCol;

    @FXML
    private TableColumn<?, ?> nameCol;


    @FXML
    private TableColumn<?, ?> phoneCol;

    @FXML
    private TableView<CustomerTm> tableCustomer;
    private CustomerDetailModel model = new CustomerDetailModel();

    public  void initialize(){
        setCellValueFactory();
        loadAllCustomers();
    }

    private void loadAllCustomers() {
        ObservableList<CustomerTm> oblist = FXCollections.observableArrayList();
        try {
            List<CustomerDetailDto> dtoList = model.loadCustomers();
           for (CustomerDetailDto dto : dtoList){
               oblist.add(new CustomerTm(dto.getCustId(), dto.getName(), dto.getAddress(), dto.getEmail(), dto.getTelephone(), dto.getGender(), dto.getMembership()));
           }
           tableCustomer.setItems(oblist);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("cust_id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        membershipCol.setCellValueFactory(new PropertyValueFactory<>("membership"));

    }


    public void btnAddCustomerOnAction(ActionEvent actionEvent) throws IOException {

        Parent fxml = FXMLLoader.load(getClass().getResource("/view/Customer_detail_form.fxml"));
        pane2.getChildren().removeAll();

        pane2.getChildren().setAll(fxml);

    }

    public void tableOnClicked(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getClickCount()==1){
            CustomerTm selectedData = tableCustomer.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Customer_detail_form.fxml"));
            Parent fxml = loader.load();
            if (selectedData!=null){
                CustomerDetailFormController detailFormController = loader.getController();
                detailFormController.handleSelectedData(selectedData);
                pane2.getChildren().setAll(fxml);

            }

        }
    }

    public void btnSendOnAction(ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/view/Mail_form.fxml"));
        pane2.getChildren().removeAll();

        pane2.getChildren().setAll(fxml);
    }
}

package lk.ijse.freshBite.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class StockItemFormController {

    public AnchorPane pane2;
    public JFXButton btnSeeSupplier;
    @FXML
    private JFXButton btnAddItem;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;


    @FXML
    private JFXButton btnUpdate;

    @FXML
    private ComboBox<?> combSupId;

    @FXML
    private TableColumn<?, ?> nameCol;
    @FXML
    private TableColumn<?, ?> priceCol;

    @FXML
    private TableColumn<?, ?> quantityCol;

    @FXML
    private TableColumn<?, ?> stockIdCol;

    @FXML
    private TableColumn<?, ?> supIdCol;

    @FXML
    private TableView<?> tableStockItem;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtStockId;

    @FXML
    void btnAddItemOnAction(ActionEvent event) {

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void comSupIdOnAction(ActionEvent event) {

    }


    public void btnSeeSupplierOnAction(ActionEvent actionEvent) throws IOException {

        Parent fxml = FXMLLoader.load(getClass().getResource("/view/Supplier_form.fxml"));
        pane2.getChildren().removeAll();

        pane2.getChildren().setAll(fxml);
    }
}

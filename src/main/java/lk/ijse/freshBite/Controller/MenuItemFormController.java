package lk.ijse.freshBite.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TableView;
import lk.ijse.freshBite.Model.MenueItemModel;
import lk.ijse.freshBite.dto.AddMenuDto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MenuItemFormController {

    public JFXButton btnNewMenu;
    public JFXButton btnShowTable;
    public AnchorPane pane2;
    @FXML
    private ScrollPane ScrollMenu;

    @FXML
    private TableColumn<?, ?> actionCol;


    @FXML
    private JFXButton btnPay;

    @FXML
    private JFXButton btnRecipt;

    @FXML
    private JFXButton btnRemove;


    @FXML
    private JFXComboBox<?> combCustId;

    @FXML
    private GridPane gridCard;

    @FXML
    private TableColumn<?, ?> itemCol;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblDiscount;

    @FXML
    private Label lblDiscountValue;

    @FXML
    private Label lblName;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblNetTotalValue;

    @FXML
    private Label lblTotal;

    @FXML
    private Label lblTotalValue;


    @FXML
    private AnchorPane paneMenu;

    @FXML
    private AnchorPane panePay;

    @FXML
    private TableColumn<?, ?> priceCol;

    @FXML
    private TableColumn<?, ?> quantityCol;

    @FXML
    private TableView<?> tableAddCart;
    private MenueItemModel model = new MenueItemModel();
   private ObservableList<AddMenuDto> menuList = FXCollections.observableArrayList();
    public void initialize(){
     menuDisplay();

    }



    @FXML
    void btnPayOnAction(ActionEvent event) {

    }

    @FXML
    void btnReciptOnAction(ActionEvent event) {

    }

    @FXML
    void btnRemoveOnAction(ActionEvent event) {

    }



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
    public List<AddMenuDto> getMenuData(){
        try {
            List<AddMenuDto> dtoList = model.getAllMenuItems();
            for (AddMenuDto dto : dtoList){
                System.out.println(dto);
            }
            return dtoList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void menuDisplay(){
        menuList.clear();
        menuList.addAll(getMenuData());
        int row = 0;
        int col =0;
        gridCard.getRowConstraints().clear();
        gridCard.getColumnConstraints().clear();
        for (int i =0;i< menuList.size();i++){

            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/item_form.fxml"));
                AnchorPane pane = loader.load();
                ItemFormController card = loader.getController();
                card.setData(menuList.get(i));
                if (col==3){
                    col=0;
                    row++;
                }
                gridCard.add(pane,col++,row);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

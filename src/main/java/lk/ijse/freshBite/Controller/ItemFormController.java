package lk.ijse.freshBite.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import lk.ijse.freshBite.Model.ItemCardModel;
import lk.ijse.freshBite.dto.AddMenuDto;
import lk.ijse.freshBite.dto.ItemCardDto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemFormController {

    @FXML
    private JFXButton btnAdd;

    @FXML
    private ImageView imgMenu;

    @FXML
    private Label lblName;

    @FXML
    private Label lblPrice;

    @FXML
    private Label lblType;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Spinner<Integer> spinnerQuantity;

    @FXML
    private AnchorPane subPane;
    private AddMenuDto dto;
    private SpinnerValueFactory<Integer> spin;
    private  int qty;
    private ItemCardModel model = new ItemCardModel();
    private MenuItemFormController menuItemFormController ;
    private List<ItemCardDto> cartItems = new ArrayList<>();

    public void initialize(){
        setQuantity();

    }
     void setData(AddMenuDto dto){
       // this.dto = dto;
         System.out.println(dto.getImagePath());
        lblName.setText(dto.getName());
        lblPrice.setText(String.valueOf(dto.getSellPrice()));
        lblType.setText(dto.getType());
        Image image = new Image(dto.getImagePath());
        imgMenu.setImage(image);
        imgMenu.getStyleClass().add("circular-image");
         checkAvailabillity();
    }
    void setQuantity(){
         spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100,0);
         spinnerQuantity.setValueFactory(spin);
         spinnerQuantity.setEditable(true);

    }
    public void setMenuItemFormController(MenuItemFormController menuItemFormController) {
        this.menuItemFormController = menuItemFormController;
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
         qty = spinnerQuantity.getValue();
        try {
            String checkAvailability = model.getStatus(lblName.getText());
            if (checkAvailability.equals("Unavailable")) {
                new Alert(Alert.AlertType.ERROR, lblName.getText() + " is not available in stock").show();
            } else if (qty == 0) {
                new Alert(Alert.AlertType.ERROR, "Please Enter the quantity").show();
            } else {
                String name = lblName.getText();
                AddMenuDto dto = model.getItemDetails(name);
                System.out.println(dto);
                // Add the item to the cart
                cartItems.add(new ItemCardDto(dto, qty));
                System.out.println(cartItems);
                // Update the TableView in MenuItemFormController
                menuItemFormController.setTableValue(dto, qty);
                spinnerQuantity.getValueFactory().setValue(0);
            }
            } catch (SQLException e) {
            throw new RuntimeException(e);
        }





    }
    void checkAvailabillity(){

        try {
            String checkAvailability = model.getStatus(lblName.getText());
            System.out.println(lblName.getText());
            System.out.println(checkAvailability);
            if (checkAvailability.equals("Unavailable")) {
                mainPane.setOpacity(0.5);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

package lk.ijse.freshBite.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import lk.ijse.freshBite.Model.ItemCardModel;
import lk.ijse.freshBite.dto.AddMenuDto;

import java.sql.SQLException;

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

    @FXML
    void btnAddOnAction(ActionEvent event) {
         qty = spinnerQuantity.getValue();
        try {
            String checkAvailability = model.getStatus(lblName.getText());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (qty ==0){

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

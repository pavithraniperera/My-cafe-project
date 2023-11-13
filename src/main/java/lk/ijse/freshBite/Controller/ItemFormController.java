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
import lk.ijse.freshBite.dto.AddMenuDto;

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
     void setData(AddMenuDto dto){
         System.out.println(dto.getImagePath());
        lblName.setText(dto.getName());
        lblPrice.setText(String.valueOf(dto.getSellPrice()));
        lblType.setText(dto.getType());
        Image image = new Image(dto.getImagePath());
        imgMenu.setImage(image);
        imgMenu.getStyleClass().add("circular-image");

    }
    void setQuantity(){
         spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100,0);
         spinnerQuantity.setValueFactory(spin);
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {

    }

}

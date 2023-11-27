package lk.ijse.freshBite.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import lk.ijse.freshBite.Model.AddMenuModel;
import lk.ijse.freshBite.Model.StockItemModel;
import lk.ijse.freshBite.dto.NotificationDto;
import lk.ijse.freshBite.dto.StockItemDto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationFormController {

    public ImageView imgExit;
    public GridPane gridCard;
    public Label lblNoNotification;
    private ObservableList<NotificationDto> oblist = FXCollections.observableArrayList();
    private AddMenuModel itemModel = new AddMenuModel();
    public void initialize(){
        notificationDisplay();
    }

    public void imgExitOnMouseClicked(MouseEvent mouseEvent) {
        Stage notificationStage = (Stage) imgExit.getScene().getWindow();
        notificationStage.close();
    }
    public void notificationDisplay(){
        List<NotificationDto> dtos = getStockData();
        if (dtos!= null) {
            oblist.clear();
            oblist.addAll(dtos);
            int row = 0;
            int col =0;
            gridCard.getRowConstraints().clear();
            gridCard.getColumnConstraints().clear();
            for (int i= 0;i<oblist.size();i++){

                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/view/NotificationCard.fxml"));
                    AnchorPane pane = loader.load();
                    NotificationCardController card = loader.getController();
                    card.setData(oblist.get(i));
                    card.setNotificationController(this);
                    if (col==1){
                        col =0;
                        row++;
                    }
                    gridCard.add(pane,col++,row);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        else {
           lblNoNotification.setVisible(true);
        }
    }

    private List<NotificationDto> getStockData()  {
        List<NotificationDto> dtos = new ArrayList<>();
        try {
           dtos = itemModel.getOutOfStockData();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dtos;

    }
}

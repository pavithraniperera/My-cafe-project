package lk.ijse.freshBite.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lk.ijse.freshBite.dto.NotificationDto;

import java.time.LocalDate;

public class NotificationCardController {
    @FXML
    private Label lblDate;

    @FXML
    private Label lblMessage;

    @FXML
    private Label lblNumber;

    @FXML
    private Label lblSupplierName;
    private NotificationFormController notificationFormController;

    public void setData(NotificationDto notificationDto) {
        lblDate.setText(String.valueOf(LocalDate.now()));
        lblMessage.setText(notificationDto.getItemName()+" is Out of Stock");
        lblNumber.setText(notificationDto.getTelephone());
        lblSupplierName.setText(notificationDto.getSup_name());

    }

    public void setNotificationController(NotificationFormController notificationFormController) {
        this.notificationFormController = notificationFormController;
    }
}

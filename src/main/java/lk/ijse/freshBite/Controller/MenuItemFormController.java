package lk.ijse.freshBite.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import lk.ijse.freshBite.Model.MenueItemModel;
import lk.ijse.freshBite.dto.AddMenuDto;
import lk.ijse.freshBite.dto.MenuItemDto;
import lk.ijse.freshBite.dto.tm.ItemCardTm;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class MenuItemFormController {

    public JFXButton btnNewMenu;
    public JFXButton btnShowTable;
    public AnchorPane pane2;
    public Label lblOrderId;
    public JFXButton btnAddCustomer;
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
    private JFXComboBox<String> combCustId;

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
    private TableView<ItemCardTm> tableAddCart;
    private MenueItemModel model = new MenueItemModel();
    private  ItemFormController itemFormController;
    private ObservableList<ItemCardTm> cartItems = FXCollections.observableArrayList();
   private ObservableList<AddMenuDto> menuList = FXCollections.observableArrayList();
    public void initialize(){
     menuDisplay();
     loadCustId();
     displayDate();
     generateNextOrderId();


    }

    private void displayDate() {
        lblDate.setText(String.valueOf(LocalDate.now()));
    }

    private void loadCustId()  {
        ObservableList<String> custList = FXCollections.observableArrayList();
        try {
            List<String> list = model.getAllCustId();
            for (String id : list){
                custList.add(id);
            }
            combCustId.setItems(custList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void btnPayOnAction(ActionEvent event) {
        String orderId = lblOrderId.getText();
        LocalDate date = LocalDate.parse(lblDate.getText());
        String cust_id = combCustId.getValue();
        double total = Double.parseDouble(lblNetTotalValue.getText());
        List<ItemCardTm> itemList = new ArrayList<>();
        for (ItemCardTm item : cartItems){
            itemList.add(item);
        }
       // System.out.println("Place order form controller: " + itemList);
        var dto = new MenuItemDto(orderId,date,cust_id,total,itemList);
        try {
            boolean isSuccess = model.placeOrder(dto);
            if (isSuccess){
                new Alert(Alert.AlertType.CONFIRMATION,"Orders Save Successfully").show();;
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    @FXML
    void btnReciptOnAction(ActionEvent event) throws JRException {
        // Prepare data for the report
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(cartItems);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("Total", lblTotalValue.getText());
        parameters.put("discount", lblDiscountValue.getText());
        parameters.put("netTotal", lblNetTotalValue.getText());
        parameters.put("customerId",combCustId.getValue());
        parameters.put("orderId",lblOrderId.getText());
        parameters.put("logo","/image/logo.png");
        InputStream resourceAsStream = getClass().getResourceAsStream("/reports/Bill.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);
        JasperReport compileReport = JasperCompileManager.compileReport(load);
        JasperPrint jasperPrint =
                JasperFillManager.fillReport(
                        compileReport,
                        parameters,
                        dataSource
                );
        JasperViewer.viewReport(jasperPrint, false);



    }

    @FXML
    void btnRemoveOnAction(ActionEvent event) {
        tableAddCart.getItems().clear();
        combCustId.setValue(null);

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
                card.setMenuItemFormController(this);
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

    public void SetNameOnAction(ActionEvent actionEvent) {
        String id = combCustId.getValue();
        try {
            String name = model.getName(id);
            if(name!= null){
                lblName.setText(name);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnAddCustomerOnAction(ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/view/Customer_detail_form.fxml"));
        pane2.getChildren().removeAll();

        pane2.getChildren().setAll(fxml);
    }
    private void generateNextOrderId(){
        try {
            String orderId = model.genarateId();
            lblOrderId.setText(orderId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void setTableValue(AddMenuDto dto, int qty){
        JFXButton button = createRemoveButton();
        setRemoveBtnOnAction(button);
       // ItemCardDto itemCardDto = new ItemCardDto(dto,qty);
        itemCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("qty"));
        actionCol.setCellValueFactory(new PropertyValueFactory<>("button"));
        double price = dto.getSellPrice()*qty;
        boolean itemExit = false;
        for (ItemCardTm cartItem : cartItems){
            if (cartItem.getItemName().equals(dto.getName())) {
                // If the item exists, update the quantity
                cartItem.setQty(cartItem.getQty() + qty);
                cartItem.setPrice(dto.getSellPrice()*(cartItem.getQty()));
                itemExit = true;
                break;
            }
        }
        if (!itemExit) {
            cartItems.add(new ItemCardTm(dto.getName(), qty, price, button));
        }
        tableAddCart.setItems(cartItems);
        tableAddCart.refresh();
        calculateTotal();




    }

    private void setRemoveBtnOnAction(JFXButton button) {
        button.setOnAction((e)->{
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();
            if (type.orElse(no) == yes) {
                int focusedIndex = tableAddCart.getSelectionModel().getSelectedIndex();

                cartItems.remove(focusedIndex);
                tableAddCart.refresh();
                calculateTotal();


            }
        });
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
    private  void calculateTotal(){
        double total = 0;
     for (ItemCardTm cartItem : cartItems){
         total+= cartItem.getPrice();
     }
     calculateDisCount(total);
     lblTotalValue.setText(String.valueOf(total));
    }
    public void calculateDisCount(double total){
        String id = combCustId.getValue();
        double discount = 0;
        try {
            String membership = model.getMembership(id);
           switch (membership){
               case "Bronze" : {discount = total*0.05;}
               break;
               case "Gold" :{discount = total*0.2;}
               break;
               case "VIP" : {discount=total*0.15;}
               break;
               case "Silver" :{discount = total*0.1;}
               break;
               case "None" :{discount=total*0;}
           }
           lblDiscountValue.setText(String.valueOf(discount));
           netTotal(total,discount);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void netTotal(double total, double discount){
        float netTotal = (float) (total-discount);
        lblNetTotalValue.setText(String.valueOf(netTotal));
    }



}

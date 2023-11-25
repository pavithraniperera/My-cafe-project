package lk.ijse.freshBite.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CalculaterFormController {

    public ImageView imgExit;
    public AnchorPane titlePane;
    public Label lblResult;
    private  double x,y;
    @FXML
    private Pane btn0;

    @FXML
    private Pane btn1;

    @FXML
    private Pane btn2;

    @FXML
    private Pane btn3;

    @FXML
    private Pane btn4;

    @FXML
    private Pane btn5;

    @FXML
    private Pane btn6;

    @FXML
    private Pane btn7;

    @FXML
    private Pane btn8;

    @FXML
    private Pane btn9;

    @FXML
    private Pane btnClear;

    @FXML
    private Pane btnDevide;

    @FXML
    private Pane btnEqual;

    @FXML
    private Pane btnMultiple;

    @FXML
    private Pane btnPlus;

    @FXML
    private Pane btnSubtraction;
    double num1=0;
    String operater = ".";



    public void imgExitOnAction(MouseEvent mouseEvent) {
        Stage calculaterStage = (Stage) imgExit.getScene().getWindow();
        calculaterStage.close();
    }

    public void titleOnMousedPressed(MouseEvent mouseEvent) {
        x = mouseEvent.getSceneX();
        y= mouseEvent.getSceneY();
    }

    public void titleOnMouseDragged(MouseEvent mouseEvent) {
        Stage stage = (Stage) titlePane.getScene().getWindow();
        stage.setX(mouseEvent.getScreenX());
        stage.setY(mouseEvent.getScreenY());
    }

    public void symbolOnMouseClicked(MouseEvent mouseEvent) {
        Pane clickedPane = (Pane) mouseEvent.getSource();
        String symbol = clickedPane.getId().replace("btn","");
        if (symbol.equals("Equal")){
            double num2 = Double.parseDouble(lblResult.getText());
            switch (operater){
                case "+" :{
                    lblResult.setText(String.valueOf(num2+num1));
                }
                break;
                case "-" :{
                    lblResult.setText(String.valueOf(num1-num2));
                }
                break;
                case "*" :{
                    lblResult.setText(String.valueOf(num2*num1));
                }
                break;
                case "/" :{
                    lblResult.setText(String.valueOf(num1/num2));
                }
                operater=".";
            }


        } else if (symbol.equals("Clear")) {
            lblResult.setText(String.valueOf(0.0));
            operater=".";
        }
        else {
            switch (symbol) {
                case "Plus":
                    operater = "+";
                    break;
                case "Subtraction":
                    operater = "-";
                    break;
                case "Multiple":
                    operater = "*";
                    break;
                case "Devide":
                    operater = "/";
                    break;
            }
            num1 = Double.parseDouble(lblResult.getText());
            lblResult.setText(String.valueOf(0.0));
        }


    }

    public void numberOnMouseClicked(MouseEvent mouseEvent) {
        Pane clickedPane  = (Pane) mouseEvent.getSource();
        int number = Integer.parseInt(clickedPane.getId().substring(3));
        if (lblResult.getText() .equals(0.0) ) {
            lblResult.setText(String.valueOf(Double.parseDouble(String.valueOf(number))));
        }
        else {
            lblResult.setText(String.valueOf((Double.parseDouble(lblResult.getText())* 10) + number));
        }
    }
}

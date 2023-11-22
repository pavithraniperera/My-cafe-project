package lk.ijse.freshBite.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lk.ijse.freshBite.Model.AnalyticsModel;
import lk.ijse.freshBite.Model.CustomerDetailModel;
import lk.ijse.freshBite.Model.OrderItemModel;
import lk.ijse.freshBite.Model.OrderModel;
import lk.ijse.freshBite.dto.AnalyticsDto;
import lk.ijse.freshBite.dto.ExpenseDataDto;
import lk.ijse.freshBite.dto.SalesDataDto;
import lk.ijse.freshBite.dto.TrendingItemDto;
import lk.ijse.freshBite.dto.tm.TrendingItemTm;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalyticsFormController {

    public JFXComboBox combPeriod;
    public NumberAxis y;
    public CategoryAxis x;
    @FXML
    private AreaChart<?, ?> areaChartExpences;

    @FXML
    private BarChart<String, Number> barChartCustomer;

    @FXML
    private JFXButton btnSalesReport;

    @FXML
    private TableColumn<?, ?> colImage;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colTotalOrder;

    @FXML
    private TableView<TrendingItemTm> tableTrendingItem;
    private AnalyticsModel model = new AnalyticsModel();
    private CustomerDetailModel customerDetailModel = new CustomerDetailModel();
    private OrderModel orderModel = new OrderModel();
    private OrderItemModel orderItemModel = new OrderItemModel();

    public void initialize(){
        setCombValues();
       combPeriod.setValue("Daily");
        String period = (String) combPeriod.getValue();
        ObservableList<ExpenseDataDto> observableList = fetchExpenseData(period);
        updateAreaChart(observableList);
        x.setLabel("Customer Location");
        y.setLabel("Customer Count");
        getCustomerDetails();
        setCellValueFactory();
        setTableReservation();


    }


    @FXML
    void btnSalesReportOnAction(ActionEvent event) {
        try {
            Double totalRevenue = orderModel.getTotalRevenue();
            int soldUnits = orderItemModel.getSoldUnits();
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("totalRevenue",totalRevenue);
            parameters.put("totalSoldUnits",soldUnits);
            List<SalesDataDto> salesData = model.getSalesData();
            getReport(salesData,parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }

    }

    private void getReport(List<SalesDataDto> salesData, Map<String, Object> parameters) throws JRException {
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(salesData);
        InputStream resourceAsStream = getClass().getResourceAsStream("/reports/sales_report.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);
        JasperReport compileReport = JasperCompileManager.compileReport(load);
        JasperPrint jasperPrint =
                JasperFillManager.fillReport(
                        compileReport,
                        parameters ,
                        dataSource
                );
        JasperViewer.viewReport(jasperPrint, false);

    }

    public void combPeriodOnAction(ActionEvent actionEvent) {
        String selectedPeriod = String.valueOf(combPeriod.getValue());
        ObservableList<ExpenseDataDto> expenseDataList = fetchExpenseData(selectedPeriod);

        // Update the areaChartExpences with the new data
        updateAreaChart(expenseDataList);

    }

    private void updateAreaChart(ObservableList<ExpenseDataDto> expenseDataList) {
        areaChartExpences.getData().clear(); // Clear existing data

        // Create a new series
        XYChart.Series series = new XYChart.Series();

        // Add data to the series
        for (ExpenseDataDto expenseData : expenseDataList) {
            series.getData().add(new XYChart.Data(expenseData.getDate(), expenseData.getExpenseAmount()));
        }

        // Add the series to the areaChartExpences
        areaChartExpences.getData().add(series);

    }

    private ObservableList<ExpenseDataDto> fetchExpenseData(String selectedPeriod) {
        ObservableList<ExpenseDataDto> expenseDataList = FXCollections.observableArrayList();

        switch (selectedPeriod) {
            case "Daily":
                // Fetch daily expenses data
                try {
                    expenseDataList = model.getDailyChartData();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                break;
            case "Weekly":
                // Fetch weekly expenses data
                try {
                    expenseDataList = model.getWeeklyChartData();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "Monthly":
                // Fetch monthly expenses data
                try {
                    expenseDataList = model.getMonthluChartData();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
        }

        return expenseDataList;

    }

    private void  setCombValues(){
        ObservableList<String>  timeList = FXCollections.observableArrayList("Daily","Weekly","Monthly");
        combPeriod.setItems(timeList);
    }

    private void getCustomerDetails(){
        try {
            List<AnalyticsDto> locationDataList = customerDetailModel.getCustomerLocationData();
            updateBarChart(locationDataList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void updateBarChart(List<AnalyticsDto> locationDataList) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (AnalyticsDto data : locationDataList) {
            series.getData().add(new XYChart.Data<>(data.getLocation(), data.getCustomerCount()));
        }

        ObservableList<XYChart.Series<String, Number>> chartData = FXCollections.observableArrayList(series);
        barChartCustomer.setData(chartData);

    }
    private void  setTableReservation(){
        ObservableList<TrendingItemTm> trendingItemTmList = FXCollections.observableArrayList();
        try {
            List<TrendingItemDto> dtoList = OrderModel.getTrendingItems();
            for (TrendingItemDto dto : dtoList){
                ImageView imageView = new ImageView();
                Image image = new Image(dto.getImagePath());
                imageView.setImage(image);
                imageView.setFitWidth(50);
                imageView.setFitHeight(50);
                trendingItemTmList.add(new TrendingItemTm(dto.getItemName(),imageView, dto.getQuantity()));

            }
            tableTrendingItem.setItems(trendingItemTmList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    private  void setCellValueFactory(){
        colTotalOrder.setCellValueFactory(new PropertyValueFactory<>("itemQuantity"));
        colName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colImage.setCellValueFactory(new PropertyValueFactory<>("imageView"));

    }

}

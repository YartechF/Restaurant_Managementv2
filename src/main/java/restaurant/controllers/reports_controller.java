package restaurant.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import restaurant.models.orders_model;
import restaurant.models.sales_chart_model;

public class reports_controller implements Initializable {

    @FXML
    private BarChart<String, Number> report_chart;
    
    void setdatachart(){
        XYChart.Series<String,Number> series = new XYChart.Series<>();

        series.getData().add(new XYChart.Data<String,Number>("May",1000.5));
        series.getData().add(new XYChart.Data<String,Number>("june",200.0));

        report_chart.getData().add(series);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sales_chart_model SalesChartModel = new sales_chart_model();
        
        setdatachart();
    }
    
}

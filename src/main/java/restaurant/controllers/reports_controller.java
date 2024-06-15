package restaurant.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class reports_controller {

    @FXML
    private BarChart<String, Double> report_chart;

    public void init(){
        setdatachart();
    }
    void setdatachart(){
        XYChart.Series<String,Double> series = new XYChart.Series<>();

        series.getData().add(new XYChart.Data<String,Double>("May",1000.5));
        series.getData().add(new XYChart.Data<String,Double>("june",200.0));

        report_chart.getData().add(series);
    }
    
}

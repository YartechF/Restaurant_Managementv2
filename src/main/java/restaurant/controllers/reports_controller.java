package restaurant.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.util.StringConverter;
import restaurant.models.orders_model;
import restaurant.models.sales_chart_model;
import restaurant.models.sales_model;

public class reports_controller implements Initializable {

    @FXML
    private BarChart<String, Number> report_chart;

    @FXML
    private ChoiceBox<Integer> yearChoiceBox;
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sales_chart_model SalesChartModel = new sales_chart_model();
        this.yearChoiceBox.getItems().addAll(new sales_chart_model().getAvailableYears());

        // Create the axes
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        // Set the axes for the chart
        report_chart.getXAxis().setLabel("Month");
        report_chart.setBarGap(0.2);
        report_chart.setLegendVisible(false);
        report_chart.setTitle("Monthly Sales Report");
        report_chart.setAnimated(false);

        // Set the categories for the x-axis
        xAxis.setCategories(FXCollections.observableArrayList(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        ));

        report_chart.getXAxis().setAnimated(false);

        // Create a data series
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        // Add data to the series
        try {
            series.getData().addAll(new sales_chart_model().getdata(2024));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        yearChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                series.getData().clear();
                try {
                    series.getData().addAll(new sales_chart_model().getdata(newValue));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        // Add the data series to the chart
        report_chart.getData().add(series);
        }
    
}

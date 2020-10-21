package date2020_10_21_Calendar;

import date2020_10_21_Calendar.res.FeiertagAPIParser;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.LinkedList;

public class Diagram extends Application {

    private FeiertagAPIParser parser = new FeiertagAPIParser();

    private int window_width = 640;
    private int window_height = 480;

    private LocalDate start = LocalDate.now();
    private LocalDate end = start.plusYears(20);

    private LinkedList<LocalDate> getData() throws Exception{

        System.out.println("Retrieving data.");
        LinkedList<LocalDate> dates = parser.getDates(start.getYear(), end.getYear());
        Days.countDays(dates);
        Days.orderByHighestCount();
        System.out.println("Data retrieving done!");

        System.out.println("Most occurring day: " + Days.highestDay().toString());
        for(int i = 0; i < Days.orderedByHighestCount().length; ++i){
            System.out.println(Days.orderedByHighestCount()[i].toString());
        }

        return dates;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // Defining the Axis and labelling them
            final CategoryAxis xAxis = new CategoryAxis();
            final NumberAxis yAxis = new NumberAxis();
            xAxis.setLabel("Wochentag");
            yAxis.setLabel("Häufigkeit");

            // BarChart / labelling
            final BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis);
            barChart.setTitle("Visualisierung von Wochentagen, die Feiertage sind");

            // Assigning values
            LinkedList<LocalDate> dates = getData();
            XYChart.Series series = new XYChart.Series();
            series.setName(start.getYear() + " - " + end.getYear());
            for(int i = 0; i < Days.values().length-2; ++i){
                series.getData().add(new XYChart.Data(Days.values()[i].name(), Days.values()[i].getCount()));
            }

            // Setting scene
            Scene scene = new Scene(barChart, window_width, window_height);
            barChart.getData().addAll(series);

            // Show window
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        launch(args);
    }
}

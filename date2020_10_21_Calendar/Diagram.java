package date2020_10_21_Calendar;

import date2020_10_21_Calendar.res.FeiertagAPIParser;
import date2020_10_21_Calendar.res.country.Germany;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.stage.Stage;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class Diagram extends Application {

	private FeiertagAPIParser parser = new FeiertagAPIParser();

	private int window_width = 640;
	private int window_height = 480;

	private LocalDate start = LocalDate.now();
	private LocalDate end = start.plusYears(20);

	/**
	 * Simple data-receiving method. Just receives the dates of the holidays.
	 * @return A LinkedList containing all holidays, received from the feiertage-api
	 */
	private LinkedList<LocalDate> getData() throws Exception{

		// Does not work, but receives everything
		//LinkedList<LocalDate> dates = parser.toNonRedundant(parser.separateCompleteJSON(parser.getAllData(start.getYear(), end.getYear())));

		// Does work, but only receives the dates
		LinkedList<LocalDate> dates = parser.toNonRedundant(parser.getDates(start.getYear(), end.getYear()));

		// DEBUG set
		/*LinkedList<LocalDate> dates = new LinkedList<LocalDate>(Arrays.asList(
			LocalDate.of(2020,01,1),	//Wednesday
			LocalDate.MIN.plusDays(2),	//Wednesday
			LocalDate.MAX.minusDays(2),	//Wednesday
			LocalDate.of(2021,12,24)	//Friday
		));

		for(LocalDate date : dates){
			System.err.println("DayOfWeek: " + date.getDayOfWeek());
		}*/

		Days.countDays(dates);
		Days.orderByHighestCount();

		System.out.println("Most occurring day: " + Days.highestDay().toString());
		for(int i = 0; i < Days.orderedByHighestCount().length; ++i){
			System.out.println(Days.orderedByHighestCount()[i].toString());
		}

		return dates;
	}

	public JSONObject wtf(int yearString){
		String _requestString = "https://deutsche-feiertage-api.de/api/v1/" + yearString;

		java.net.HttpURLConnection connection = null;
		//HttpClient client = HttpClient.newHttpClient();
		try {
			URL url = new URL(_requestString);
			connection = (java.net.HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setRequestProperty("Content-Type", "application/json; uft-8");
			connection.setRequestProperty("X-DFA-Token", "dfa");
			connection.setRequestProperty("Accept", "application/json");
			connection.setDoOutput(true);
			// sending request
			try (java.io.BufferedReader br = new java.io.BufferedReader(
					new java.io.InputStreamReader(connection.getInputStream(), "utf-8"))) {
				StringBuilder response = new StringBuilder();
				String responseLine = null;
				while((responseLine = br.readLine()) != null){
					response.append(responseLine.trim());
				}

				//          System.out.println(response.toString());
				//          JSONObject obj = new JSONObject(response.toString());
				return new JSONObject(response.toString());
			}

		} catch (Exception e) {
			System.out.println("Der requestString kann entweder nicht gefunden werden, oder nicht geöffnet werden");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {

			//parser.DEBUG_compareBothGets();

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

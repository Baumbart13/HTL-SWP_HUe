package date2020_11_25_stockUpdater.src;

import date2020_11_25_stockUpdater.src.files.StockCSV;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import miscForEverything.Environment;
import miscForEverything.UI;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class StockUpdaterVisual extends Application {

	private static BaumbartStock stocks;

	public static String database_hostname = "localhost";
	public static String database_user = "root";
	public static String database_password = "DuArschloch4";
	public static String database_databaseName = "BaumbartStocks";
	private static String api_stockName = "DEFAULT_STOCK";
	private static String api_apiKey = "VBAX1XGSP5QC85SL";

	private static int windowWidth = (int)(miscForEverything.Environment.getDesktopWidth_Multiple() * (1.0/2.5));
	private static int windowHeigth = (int)(miscForEverything.Environment.getDesktopHeight_Multiple() * (1.0/2.7));

	private void argumentHandling(){
		argumentHandling(getParameters().getRaw());
	}

	private static void argumentHandling(String[] params){
		argumentHandling(Arrays.asList(params));
	}

	private static void argumentHandling(List<String> args){
		if(args == null){
			System.exit(0);
		}

		for(int i = 0; i < args.size(); ++i){
			if(args.get(i).equalsIgnoreCase(ProgramArguments.Long.install)){
				// TODO implement BaumbartStock.install
				System.out.println("Not implemented yet");
				exitProgram(0);

			}else if(args.get(i).equalsIgnoreCase(ProgramArguments.Long.uninstall)){
				//TODO implement BaumbartStock.uninstall
				System.out.println("Not implemented yet");
				exitProgram(0);

			}else if(args.get(i).equalsIgnoreCase(ProgramArguments.Long.autoupdate)){
				// --autoupdate [STOCK-NAME] [API-KEY]

				if(i+2 >= args.size()){
					tooFewArgumentsMessage();
					helpMessage();
					exitProgram(0);
				}
				if(args.get(i+1) == null || args.get(i+2) == null ||
					ProgramArguments.isAnyArgumentUsed(args.get(i+1), args.get(i+2))){
					tooFewArgumentsMessage();
					helpMessage();
					exitProgram(0);
				}

				autoupdate(database_hostname, database_user, database_password, database_databaseName,
						args.get(i+1), args.get(i+2));
				i += 2;
				System.out.println("Update complete");
				exitProgram(0);

			}else if(args.get(i).equalsIgnoreCase(ProgramArguments.Long.update)){
				int days = 10;

				if(i+1 < args.size() && args.get(i+1) != null && !args.get(i+1).contains("-")){
					try {
						days = Integer.parseInt(args.get(i + 1));
						i += 1;
					} catch (NumberFormatException e) {
						wrongArgumentsMessage();
					}
				}else {

					System.out.print("Please enter the hostname of your database: ");
					database_hostname = UI.readString();
					System.out.print("Please enter your user of your database: ");
					database_user = UI.readString();
					System.out.print("Please enter your user password: ");
					database_password = UI.readString();
					System.out.print("Please enter your database, where the stocks are saved. ");
					database_databaseName = UI.readString();
					System.out.print("Please enter the stocks you want to update (e.g. IBM, TSLA, ...): ");
					api_stockName = UI.readString();
					System.out.print("Please enter your provided API-key: ");
					api_apiKey = UI.readString();

				}
				if(args.get(i+1) != null && !args.get(i+1).contains("-")){
					api_stockName = args.get(i+1);
				}
				update(database_hostname, database_user, database_password, database_databaseName,
						api_stockName, api_apiKey, days);
			}else if(args.get(i).equalsIgnoreCase(ProgramArguments.Long.updateDebug)){
				// --update-debug [STOCK-NAME] [API-KEY] [LAST-DAY
				if(i+3 >= args.size()){
					tooFewArgumentsMessage();
					helpMessage();
					exitProgram(0);
				}
				if(args.get(i+1) == null || args.get(i+2) == null ||
					ProgramArguments.isAnyArgumentUsed(args.get(i+1), args.get(i+2))){
					System.err.println("Programmer, you're a moron!");
					exitProgram(-1);
				}

				api_stockName = args.get(i+1);
				api_apiKey = args.get(i+2);
				autoupdate(database_hostname, database_user, database_password, database_databaseName,
						api_stockName, api_apiKey);
				i += 2;
				System.out.println("Update complete...starting visuals");
			}
		}
	}

	public static void autoupdate(String database_hostname, String database_user, String database_password,
						   String database_databaseName, String api_stockName, String api_apiKey){
		stocks = new BaumbartStock(database_hostname, database_user, database_password, database_databaseName);

		stocks.updateStock(api_stockName, api_apiKey);
		stocks.updateSymbols();
	}

	public static void update(String database_hostname, String database_user, String database_password,
							  String database_databaseName, String api_stockName, String api_apiKey, int months){
		months = Math.abs(months);

		stocks = new BaumbartStock(database_hostname, database_user, database_password, database_databaseName);
		stocks.updateStock(api_stockName, api_apiKey, months);
		stocks.updateSymbols();

		System.out.println("Update done!");
	}

	@Override
	public void start(Stage primaryStage){

		// part for the program-arguments
		argumentHandling();

		// actual visuals
		final CategoryAxis xAxis_day = new CategoryAxis();
		final NumberAxis yAxis_value = new NumberAxis();
		xAxis_day.setLabel("Day");
		yAxis_value.setLabel("StockValue (Close)");

		// LineChart / Labelling
		final LineChart<LocalDateTime, Number> lineChart = new LineChart(xAxis_day, yAxis_value);
		lineChart.setTitle("Aktienkurs der Aktie " + api_stockName);

		// Scale View


		// Assigning values
		XYChart.Series series = new XYChart.Series();
		String seriesName = LocalDateTime.now().getYear() + "-" + LocalDateTime.now().getMonth().name() + "-" + LocalDateTime.now().getDayOfMonth();
		series.setName(seriesName);

		HashMap<LocalDateTime, Double> records = stocks.getDataFromDatabase(api_stockName);
		List<XYChart.Data> xyChartList = new LinkedList<XYChart.Data>();

		for(LocalDateTime key : records.keySet()){
			series.getData().add(new XYChart.Data(key.toString(), records.get(key)));
		}

		// Setting scene
		Scene scene = new Scene(lineChart, windowWidth, windowHeigth);
		lineChart.getData().addAll(series);

		// Show window
		primaryStage.setTitle("Baumbart13 Stocks");
		primaryStage.setScene(scene);
		primaryStage.setX(getCenterX());
		primaryStage.setY(getCenterY());
		primaryStage.show();
	}

	private double getCenterX(){
		return Environment.getDesktopWidth_Single()/2 - (double)windowWidth/2;
	}

	private double getCenterY(){
		return Environment.getDesktopHeight_Single()/2 - (double)windowWidth/2;
	}

	private static void tooFewArgumentsMessage(){
		System.err.println("Too few arguments");
	}

	private static void wrongArgumentsMessage(){
		System.err.println("Wrong arguments");
		helpMessage();
	}

	private static void exitProgram(int status){
		System.exit(status);
	}

	private static void helpMessage(){
		// TODO implement BaumbartStock.uninstall and BaumbartStock.install
		String newLine = System.lineSeparator();
		System.err.println("Syntax is: [ACTION] [ARGUMENTS]" + newLine +
				"ACTION" + newLine +
				ProgramArguments.Long.update + "\t\tUpdates the stock-values." + newLine +
				ProgramArguments.Long.install + "\t\tInstalls the autoupdater to the system. Note: Not implemented yet." + newLine +
				ProgramArguments.Long.uninstall + "\t\tUninstalls the autoupdater from the system. Note: Not implemented yet." + newLine +
				ProgramArguments.Long.autoupdate + "\t[STOCK-NAME] [API-KEY]." + newLine +
				ProgramArguments.Long.windowed + newLine +
				ProgramArguments.Short.windowed + "\t\t\t\t[WIDTH] [HEIGHT]" + newLine +
				ProgramArguments.Long.help + newLine +
				ProgramArguments.Short.help + "\t\t\t\tShows this message.");
	}

	public static void main(String[] args){

		for(int i = 0; i < args.length; ++i) {
			if (args[i].equalsIgnoreCase(ProgramArguments.Long.windowed) ||
					args[i].equalsIgnoreCase(ProgramArguments.Short.windowed)) {

				// Check for size
				if (i + 2 < args.length) {
					try {
						windowWidth = Integer.parseInt(args[i + 1]);
						windowHeigth = Integer.parseInt(args[i + 2]);
						i += 2;
					} catch (NumberFormatException e) {
						wrongArgumentsMessage();
					}
				}
			}
		}
		launch(args);
	}
}

package date2020_11_25_stockUpdater.src;

import date2020_11_25_stockUpdater.src.api.ApiParser;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.sql.SQLException;
import java.util.*;

public class StockUpdaterVisual extends Application {
	private final boolean isTextBoxUsed = false;

	private StockDatabase database = null;
	private ApiParser apiParser = null;
	private LineChart chart = null;
	private Map<String, String> symbols = null;
	private String currentSymbol;

	private static int windowWidth = (int)(miscForEverything.Environment.getDesktopWidth_Multiple() * (1.0/2.5));
	private static int windowHeigth = (int)(miscForEverything.Environment.getDesktopHeight_Multiple() * (1.0/2.7));

	private static String apiPath = "";
	private static String databasePath = "";
	private static String symbolsPath = "";

	public StockUpdaterVisual(){
		try{
			database = loadDb(databasePath);
			//database.connect();
			//database.createDatabase(database.getDatabase());
			//database.disconnect();

			apiParser = loadApi(apiPath);

			symbols = loadSymbols(symbolsPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Loads all symbols saved to symbols.csv into the program for usage of such. symbols.csv must be in the correct folder to work, else the program will abort.
	 * @param loadFromFile The filepath of symbols.csv.
	 * @return a Map with the symbol as key and the full name as value. If no name is available, then the symbol is inserted for the name.
	 */
	public Map<String, String> loadSymbols(String loadFromFile){
		BufferedReader reader = null;
		var symbolName = new TreeMap<String, String>();
		try {
			reader = new BufferedReader(new FileReader(loadFromFile));

			var line = reader.readLine();
			while (line != null) {
				var splittedLine = line.split(",");
				symbolName.put(splittedLine[0], splittedLine[1]);

				line = reader.readLine();
			}
		}catch(FileNotFoundException e){
			System.err.println("\"symbols.csv\" is missing, exiting program");
			System.exit(-1);
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try {
				reader.close();
			} catch (IOException e) {
				System.err.printf("Couldn't close file\t\"%s\"", loadFromFile);
				e.printStackTrace();
			}
		}
		return symbolName;
	}

	/**
	 * Loads everything needed for the api to work. In this case just the APIKey. api.csv must be in the correct folder to work, else the program will abort.
	 * @param loadFromFile The filepath of api.csv.
	 * @return the Parser for the API of www.alphavantage.co.
	 * @see ApiParser
	 */
	private ApiParser loadApi(String loadFromFile){
		BufferedReader reader = null;
		var apiKey = "";

		try {
			reader = new BufferedReader(new FileReader(loadFromFile));

			var line = reader.readLine();
			while (line != null) {
				apiKey = line;
				line = reader.readLine();
			}

			return new ApiParser(apiKey);


		}catch(FileNotFoundException e){
			System.err.println("\"api.csv\" is missing, exiting program");
			System.exit(-1);
		} catch (IOException e) {
			e.printStackTrace();


		}finally {
			try{
				reader.close();
			} catch (IOException e) {
				System.err.printf("Couldn't close file\t\"%s\"", loadFromFile);
				e.printStackTrace();
			}
		}

		return new ApiParser(apiKey);
	}

	/**
	 * Loads everything needed for the database to work. database.csv must be in the correct folder to work, else the program will abort.
	 * @param loadFromFile The filepath of database.csv.
	 * @return the Database responsible for the stock-database.
	 * @see StockDatabase
	 */
	private StockDatabase loadDb(String loadFromFile){
		BufferedReader reader = null;
		String[] credentials = null;

		try{
			reader = new BufferedReader(new FileReader(loadFromFile));

			var line = reader.readLine();
			while(line != null){
				credentials = line.split(",");

				line = reader.readLine();
			}

			return new StockDatabase(credentials[0], credentials[1], credentials[2], credentials[3]);


		} catch (IOException e) {
			e.printStackTrace();


		}finally{
			try {
				reader.close();
			} catch (IOException e) {
				System.err.printf("Couldn't close file\t\"%s\"", loadFromFile);
				e.printStackTrace();
			}
		}

		assert credentials != null;
		return new StockDatabase(credentials[0], credentials[1], credentials[2], credentials[3]);
	}

	/**
	 * Prepares the whole layout of the application.
	 * @param stage The Stage provided by the <code>start</code> method
	 */
	private void prepareLayout(Stage stage){
		// Main Layout
		var vBoxMain = new VBox();
		vBoxMain.setSpacing(10);
		vBoxMain.setPadding(new Insets(8, 8, 8, 8));

		// Head Layout
		var headBox = new HBox();
		headBox.setSpacing(10);

		//var labelSymbol = new Label("Symbol to load:");

		// Symbol input
		ObservableList<String> ol = FXCollections.observableList(new LinkedList<String>(symbols.keySet()));
		Collections.sort(ol);
		currentSymbol = ol.get(new Random().nextInt(ol.size()));


		//final var text = new TextField(currentSymbol);

		final var cmbBox = new ComboBox<String>(ol);
		cmbBox.getSelectionModel().select(ol.indexOf(currentSymbol));
		// IntelliJ suggests to use a lambda... can't really tell how a lambda works, so I leave this, as it is for now
		cmbBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				currentSymbol = cmbBox.valueProperty().getValue().toString();
			}
		});

		var bttnRequest = new Button();
		bttnRequest.setText("Load data");
		// IntelliJ suggests to use a lambda... can't really tell how a lambda works
		// In the future, I probably will need it anyways... let's try to use it
		bttnRequest.setOnAction((e) -> {
			if(currentSymbol.contains("-")){
				currentSymbol = currentSymbol.replace('-', '_');
			}
			requestStockDataAsync(currentSymbol);
		});


		final var xAxis = new CategoryAxis();
		final var yAxis = new NumberAxis();
		xAxis.setLabel("Date");
		yAxis.setLabel("Value [$]");
		chart = new LineChart(xAxis, yAxis);
		chart.setCreateSymbols(false);
		chart.setAnimated(false);
		VBox.setVgrow(chart, Priority.ALWAYS);

		if(this.isTextBoxUsed){
			//headBox.getChildren().add(labelSymbol);
		}else{
			headBox.getChildren().add(cmbBox);
		}
		headBox.getChildren().addAll(bttnRequest);
		vBoxMain.getChildren().addAll(headBox, chart);

		var scene = new Scene(vBoxMain, windowWidth, windowHeigth);
		stage.setScene(scene);
	}

	@Override
	public void start(Stage primaryStage){

		prepareLayout(primaryStage);



		primaryStage.setX(windowWidth - primaryStage.getWidth());
		primaryStage.setY(windowHeigth - primaryStage.getHeight());

		primaryStage.show();

		if(currentSymbol.contains("-")){
			currentSymbol = currentSymbol.replace('-', '_');
		}
		requestStockDataAsync(currentSymbol);
		//System.exit(0);
	}

	public void updateDatabase(StockResults results){
		try{
			database.connect();
			database.insertOrUpdateStock(results);
			database.disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Requests the stock data in a new thread from the api.
	 * @param symbol The symbol of the stock
	 */
	private void requestStockDataAsync(String symbol){
		new Thread(() -> {
			try {
				final var result = apiParser.request(symbol, ApiParser.Function.TIME_SERIES_DAILY_ADJUSTED);

				updateDatabase(result);
				Platform.runLater(() ->{
					updateChartFromDb(symbol);
				});

			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();
	}

	/**
	 * Updates the chart with given symbol
	 * @param symbol The symbol of the stock
	 */
	private void updateChartFromDb(String symbol){
		var closeSeries = new XYChart.Series();
		var avgSeries = new XYChart.Series();
		var closeSeriesName = "Close values";// + LocalDate.now().getYear() + " - " + LocalDate.now().getMonth().name() + " - " + LocalDate.now().getDayOfMonth();
		var avgSeriesName = "Average over the last days";
		closeSeries.setName(closeSeriesName);
		avgSeries.setName(avgSeriesName);

		StockResults results = null;
		try{
			database.connect();
			results = database.getStockData(symbol);
			database.disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Dang, there's been a problem on receiving data from the db");
		}

		results.Name = symbols.get(results.Symbol);

		for(var item : results.getDataPoints()){
			closeSeries.getData().add(new XYChart.Data(item.DateTime.toString(), item.Values.get(StockValueType.close)));
			avgSeries.getData().add(new XYChart.Data(item.DateTime.toString(), item.Values.get(StockValueType.avg200)));
		}


		chart.getData().clear();
		chart.getData().add(closeSeries);
		chart.getData().add(avgSeries);

		// TODO: Bounds not displayed correctly
		double lowerBound = getLowerBound(results);
		double upperBound = getUpperBound(results);
		((NumberAxis)chart.getYAxis()).setLowerBound(lowerBound);
		((NumberAxis)chart.getYAxis()).setUpperBound(upperBound);

		// TODO: Colors not displayed correctly
		var closeLine = closeSeries.getNode().lookup(".default-color0.chart-series-line");
		var closeLegend = closeSeries.getNode().lookup(".default-color0.chart-line-symbol");
		//if value greater than avg => green line
		if(results.getDataPoints().get(results.getDataPoints().size()-1).Values.get(StockValueType.close) >
				results.getDataPoints().get(results.getDataPoints().size()-1).Values.get(StockValueType.avg200)){
			closeLine.setStyle("-fx-stroke: rgba(0,255,0,1.0);");
			closeLegend.setStyle("-fx-stroke: rgba(0,255,0,1.0);");

		// if value less than avg => red line
		}else if(results.getDataPoints().get(results.getDataPoints().size()-1).Values.get(StockValueType.close) <
				results.getDataPoints().get(results.getDataPoints().size()-1).Values.get(StockValueType.avg200)) {
			closeLine.setStyle("-fx-stroke: rgba(255,0,0,1.0);");
			closeLegend.setStyle("-fx-stroke: rgba(255,0,0,1.0);");
		// else => blue line
		}else{
			closeLine.setStyle("-fx-stroke: rgba(0,0,255,1.0);");
			closeLegend.setStyle("-fx-stroke: rgba(0,0,255,1.0);");
		}

		var avgLine = avgSeries.getNode().lookup(".default-color1.chart-series-line");
		var avgLegend = avgSeries.getNode().lookup(".default-color1.chart-area-symbol");
		avgLine.setStyle("-fx-stroke: rgba(0,200,200,1.0);");
		avgLegend.setStyle("-fx-stroke: rgba(0,200,200,1.0);");

		chart.setTitle(String.format("Stock value for \"%s\"", results.Name));
	}

	private double getLowerBound(StockResults results){
		double out = Double.MAX_VALUE;
		for(var type : StockValueType.values()){
			if(type == StockValueType.volume) continue;
			out = Math.min(out, results.getLowerBound(type));
		}
		return out;
	}

	private double getUpperBound(StockResults results){
		double out = Double.MIN_VALUE;
		for(var type : StockValueType.values()){
			if(type == StockValueType.volume) continue;
			out = Math.max(out, results.getUpperBound(type));
		}
		return out;
	}

	/**
	 * The name of this method shows all functionalities of it.
	 * @see DEBUG
	 */
	public static void DEBUG(boolean inProduction){
		DEBUG.loadAllSymbolsAndSaveAsCsv(inProduction);
	}

	public static void main(String[] args){

		if(!(args == null || args.length < 1)){

			argumentHandling(Arrays.asList(args));
		}
		launch(args);
	}

	/**
	 * Handles all program arguments passed on starting the application.
	 * @param args The array of Strings of the main method as a List.
	 */
	private static void argumentHandling(List<String> args){
		if(args == null || args.size() == 0){
			System.exit(0);
		}

		boolean inProduction = false;
		if(args.contains("inProduction")){
			inProduction = true;
			apiPath = String.format("src%smain%sjava%sdate2020_11_25_stockUpdater%sres%sapi.csv",
					File.separator, File.separator, File.separator, File.separator, File.separator);
			databasePath = String.format("src%smain%sjava%sdate2020_11_25_stockUpdater%sres%sdatabase.csv",
					File.separator, File.separator, File.separator, File.separator, File.separator);
			symbolsPath = String.format("src%smain%sjava%sdate2020_11_25_stockUpdater%sres%ssymbols.csv",
					File.separator, File.separator, File.separator, File.separator, File.separator);
		}else{
			apiPath = String.format("res%sapi.csv", File.separator);
			databasePath = String.format("res%sdatabase.csv", File.separator);
			symbolsPath = String.format("res%ssymbols.csv", File.separator);
		}

		var windowedModeIndex = args.indexOf(ProgramArguments.windowed.toString());
		// why can't java have the ability to exit an if using "break"?
		// in case of better solutions, please do so.. it is just supposed to work
		if(windowedModeIndex != -1){
			if(windowedModeIndex+2 < args.size()){
				if(args.get(windowedModeIndex) != null || args.get(windowedModeIndex+1) != null || args.get(windowedModeIndex+2) != null){

					windowWidth = Integer.parseInt(args.get(windowedModeIndex+1));
					windowHeigth = Integer.parseInt(args.get(windowedModeIndex+2));
				}
			}
		}

		if(args.contains(ProgramArguments.DEBUG.toString())){
			DEBUG(inProduction);
			System.exit(0);
		}

	}
}

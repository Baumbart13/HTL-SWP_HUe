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
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import miscForEverything.Environment;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.sql.SQLException;
import java.time.LocalDate;
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

	private static boolean autoupdate = false;
	private Stage stage;

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

	public Stage getStage(){
		return this.stage;
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

		this.stage = stage;
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

		var bttnScreenshot = new Button();
		bttnScreenshot.setText("Take Screenshot");
		bttnScreenshot.setOnAction((e)->{
			takeScreenshot();
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
		headBox.getChildren().addAll(bttnRequest, bttnScreenshot);
		vBoxMain.getChildren().addAll(headBox, chart);

		var scene = new Scene(vBoxMain, windowWidth, windowHeigth);
		stage.setScene(scene);
	}

	@Override
	public void start(Stage primaryStage){

		prepareLayout(primaryStage);

		primaryStage.setX(windowWidth - primaryStage.getWidth());
		primaryStage.setY(windowHeigth - primaryStage.getHeight());

		if(!autoupdate) {
			primaryStage.show();
		}

		if(currentSymbol.contains("-")){
			currentSymbol = currentSymbol.replace('-', '_');
		}
		requestStockDataAsync(currentSymbol);

		if(autoupdate){
			System.exit(0);
		}
		//System.exit(0);
	}

	public static void main(String[] args) {
		System.out.println("Working dir: " + System.getProperty("user.dir"));

		System.out.println("Handling arguments");
		StockUpdaterVisual.argumentHandling(Arrays.asList(args));

		System.out.println("Launching application");
		StockUpdaterVisual.launch(args);
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

		if(!autoupdate) {
			new Thread(() -> {
				try {

					final var result = apiParser.request(symbol, ApiParser.Function.TIME_SERIES_DAILY_ADJUSTED);

					updateDatabase(result);
					Platform.runLater(() -> {
						updateChartFromDb(symbol);
					});


				} catch (IOException e) {
					e.printStackTrace();
				}
			}).start();
		}else{
			final int timesToFetch = 2;
			for (int i = 0; i < timesToFetch; ++i) {
				try {
					final var result = apiParser.request(symbol, ApiParser.Function.TIME_SERIES_DAILY_ADJUSTED);

					updateDatabase(result);
					updateChartFromDb(symbol);
					takeScreenshot();


					// hmm... see if I can short this to 1 order
					var temp = symbols.keySet().toArray(new String[0]);
					currentSymbol = temp[new Random().nextInt(temp.length)];

					// well.. yeah.. I can short it, but it gets worse... I've got to introduce this part of program a new variable
					//currentSymbol = symbols.keySet().toArray(new String[0])[new Random().nextInt(symbols.keySet().toArray().length)];


					// Prototypes.. did not work well
					//currentSymbol = ((String[])symbols.keySet().toArray())[new Random().nextInt(symbols.keySet().size())];
					//currentSymbol = ol.get(new Random().nextInt(ol.size()));

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
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

		System.out.println("Number of elements in results: " + results.getDataPoints().size());
		System.out.println("Number of elements in closeSeries: " + closeSeries.getData().size());
		System.out.println("Number of elements in avgSeries: " + avgSeries.getData().size());

		chart.getData().clear();
		chart.getData().add(closeSeries);
		chart.getData().add(avgSeries);

		// Value-boundaries
		chart.getYAxis().setAutoRanging(false);
		float lowerBoundY = Math.round((results.getLowerBound(StockValueType.close)) - (results.getRange(StockValueType.close) * 0.1f));
		if(lowerBoundY < 0f) lowerBoundY = 0.0f;
		float upperBoundY = Math.round((results.getUpperBound(StockValueType.close)) + (results.getRange(StockValueType.close) * 0.1f));
		((NumberAxis)chart.getYAxis()).setLowerBound(lowerBoundY);
		((NumberAxis)chart.getYAxis()).setUpperBound(upperBoundY);

		// Date-boundaries
		//chart.getXAxis().setAutoRanging(false);
		//var lowerBoundX = results.getOldestDate();
		//var upperBoundX = results.getNewestDate();
		//((CategoryAxis)chart.getXAxis())

		// TODO: Colors not displayed correctly
		var closeLine = closeSeries.getNode().lookup(".default-color0.chart-series-line");
		var closeLegend = closeSeries.getNode().lookup(".default-color0.series0");
		//if value greater than avg => green line
		if(results.getDataPoints().get(results.getDataPoints().size()-1).Values.get(StockValueType.close) >
				results.getDataPoints().get(results.getDataPoints().size()-1).Values.get(StockValueType.avg200)){
			closeLine.setStyle("-fx-stroke: rgba(0,255,0,1.0);");

			assert closeLegend != null;
			closeLegend.setStyle("-fx-stroke: rgba(0,255,0,1.0);");

		// if value less than avg => red line
		}else if(results.getDataPoints().get(results.getDataPoints().size()-1).Values.get(StockValueType.close) <
				results.getDataPoints().get(results.getDataPoints().size()-1).Values.get(StockValueType.avg200)) {
			closeLine.setStyle("-fx-stroke: rgba(255,0,0,1.0);");

			assert closeLegend != null;
			closeLegend.setStyle("-fx-stroke: rgba(255,0,0,1.0);");
		// else => blue line
		}else{
			closeLine.setStyle("-fx-stroke: rgba(0,0,255,1.0);");

			assert closeLegend != null;
			closeLegend.setStyle("-fx-stroke: rgba(0,0,255,1.0);");
		}

		var avgLine = avgSeries.getNode().lookup(".default-color1.chart-series-line");
		var avgLegend = avgSeries.getNode().lookup(".default-color1.series1");
		avgLine.setStyle("-fx-stroke: rgba(0,200,200,1.0);");
		avgLegend.setStyle("-fx-stroke: rgba(0,200,200,1.0);");

		chart.setTitle(String.format("Stock value for \"%s\"%nNo. of items loaded: %d",
				results.Name, results.getDataPoints().size()));
	}

	/**
	 * Takes a screenshot of application.
	 */
	public void takeScreenshot(){
		var fc = new JFileChooser();
		var fsv = fc.getFileSystemView();
		var homeDir = fsv.getHomeDirectory();
		var myDocuments = homeDir.getParent();
		myDocuments = myDocuments.concat(String.format("%sDocuments%sStockUpdaterBaumi",
				File.separator, File.separator));


		File dir = new File(myDocuments);
		if(!dir.exists()) {
			dir.mkdirs();
			dir.mkdir();
		}
		File file = new File(String.format("%s%s%s_%s.png",
				dir, File.separator, LocalDate.now().toString(), currentSymbol));
		WritableImage writableImage = stage.getScene().snapshot(null);

		// Before testrun:			Did StackOverflow help?
		// After testrun:			Nope... StackOverflow used an Image, but I have an WritableImage
		//							 let's try to convert it somehow functioning
		// Before testrun#2:		Simple casting could work
		// After testrun#2:			Nope.. neither did this idea work..
		// Before testrun#3:		What about do the most obvious thing, when having a "WritableImage"? - Just damn try to write it. It's fucking writable.. gosh, I'm so stupid sometimes
		// While testrun#3-build:	I'm tired of this... (see "Own code for testrun#3 for further info of this comment) where's the maven dependency for SwingFXUtils?
		//							 P.S.: testrun#3 never started.. it went straight to testrun#4
		// Before testrun#4:		Did not want to do it, because of trying to use as few as possible, but can't think any other way anymore
		// After testrun#4:			Did not work due to invalid module name.. can't catch it.. darn

		// Own code for testrun#4
		/*try {
			ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		// Own code for testrun#4


		// Own code for testrun#3
		/*try{
			// Damn... BufferedWriter only supports way too primitive data for this case.. never needed anything other than them
			//var bw = new BufferedWriter(new FileWriter(file));

			var bos = new BufferedOutputStream(new FileOutputStream(file));


			bos.write(writableImage);
		}*/
		// Own code for testrun#3 end
		
		// StackOverflow code
		/*var width = (int)writableImage.getWidth();
		var height = (int)writableImage.getHeight();
		var pixelReader = writableImage.getPixelReader();
		byte[] buffer = new byte[width * height * 4];
		var pixelFormat = PixelFormat.getByteBgraInstance();
		pixelReader.getPixels(0, 0, width, height, pixelFormat, buffer, 0, width*4);
		try{
			var out = new BufferedOutputStream(new FileOutputStream(file));
			for(var count = 0; count < buffer.length; count += 4){
				out.write(buffer[count+2]);
				out.write(buffer[count+1]);
				out.write(buffer[count]);
				out.write(buffer[count+3]);
			}
			out.flush();
			out.close();
		}catch(IOException e){
			e.printStackTrace();
		}*/
		// StackOverflow code end


		// TODO: Change this output to label on application instead of giving it the terminal
		System.out.printf("Saved to \"%s\"%n", file);
	}

	private void test(){
		for(var s : chart.getCssMetaData()){
			System.out.println(s);
		}
	}

	/**
	 * The name of this method shows all functionalities of it.
	 * @see DEBUG
	 */
	public static void DEBUG(boolean inProduction){
		DEBUG.loadAllSymbolsAndSaveAsCsv(inProduction);
	}

	/**
	 * Handles all program arguments passed on starting the application.
	 * @param args The array of Strings of the main method as a List.
	 */
	public static void argumentHandling(List<String> args){
		if(args == null){
			System.exit(0);
		}

		if(args.contains("--install")){

			StockUpdaterInstaller.install();
		}

		if(args.contains("--uninstall")){
			// TODO: implement uninstallation
			StockUpdaterInstaller.uninstall();
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

		if(args.contains("autoupdate")){
			autoupdate = true;
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

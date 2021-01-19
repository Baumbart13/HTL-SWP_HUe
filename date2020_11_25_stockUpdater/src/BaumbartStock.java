package date2020_11_25_stockUpdater.src;

import date2020_11_25_stockUpdater.src.StockAPI.StockAPIParser;
import date2020_11_25_stockUpdater.src.StockAPI.StockAPITable;
import date2020_11_25_stockUpdater.src.StockAPI.TickerAPITable;
import date2020_11_25_stockUpdater.src.files.StockCSV;
import miscForEverything.database.mySQL.Convert;
import miscForEverything.database.mySQL.DatabaseAccess;
import miscForEverything.database.mySQL.Table;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static date2020_11_25_stockUpdater.src.StockValues.*;
import static miscForEverything.database.mySQL.MySQLKeywords.*;

public class BaumbartStock {
	StockAPIParser stockAPIParser = null;

    public static final char API_SEPARATE_SIGN = '&';
    private final String API_KEY = "VBAX1XGSP5QC85SL";

    private final String API_DEFAULT_REQUEST_STRING = "https://www.alphavantage.co/query?" +
            "function=" + API_Functions.TIME_SERIES_INTRADAY + API_SEPARATE_SIGN +
            "symbol=IBM" + API_SEPARATE_SIGN +
            "interval=" + API_Intervals._5MIN + API_SEPARATE_SIGN +
            "apikey=" + "demo";

    private DatabaseAccess Database;

	/**
	 * Functions for the API. The "_ADJUSTED" are more accurate than the normal one.
	 */
	public interface API_Functions {
        /**
         * This API returns raw (as-traded) daily time series (date, daily open, daily high, daily low, daily close, daily volume) of the global equity specified, covering 20+ years of historical data. If you are also interested in split/dividend-adjusted historical data, please use the Daily Adjusted API, which covers adjusted close values and historical split and dividend events.
         */
        String TIME_SERIES_DAILY = "TIME_SERIES_DAILY";
        /**
         * This API returns raw (as-traded) daily open/high/low/close/volume values, daily adjusted close values, and historical split/dividend events of the global equity specified, covering 20+ years of historical data.
         */
        String TIME_SERIES_DAILY_ADJUSTED = TIME_SERIES_DAILY + "_ADJUSTED";
        /**
         * This API returns weekly time series (last trading day of each week, weekly open, weekly high, weekly low, weekly close, weekly volume) of the global equity specified, covering 20+ years of historical data.
         */
        String TIME_SERIES_WEEKLY = "TIME_SERIES_WEEKLY";
        /**
         * This API returns weekly adjusted time series (last trading day of each week, weekly open, weekly high, weekly low, weekly close, weekly adjusted close, weekly volume, weekly dividend) of the global equity specified, covering 20+ years of historical data.
         */
        String TIME_SERIES_WEEKLY_ADJUSTED = TIME_SERIES_WEEKLY + "_ADJUSTED";
        /**
         * This API returns monthly time series (last trading day of each month, monthly open, monthly high, monthly low, monthly close, monthly volume) of the global equity specified, covering 20+ years of historical data.
         */
        String TIME_SERIES_MONTHLY = "TIME_SERIES_MONTHLY";
        /**
         * This API returns monthly adjusted time series (last trading day of each month, monthly open, monthly high, monthly low, monthly close, monthly adjusted close, monthly volume, monthly dividend) of the equity specified, covering 20+ years of historical data.
         */
        String TIME_SERIES_MONTHLY_ADJUSTED = TIME_SERIES_MONTHLY + "_ADJUSTED";
        /**
         * A lightweight alternative to the time series APIs, this service returns the price and volume information for a security of your choice.
         */
        String QUOTE_ENDPOINT = "GLOBAL_QUOTE";
        /**
         * Looking for some specific symbols or companies? Trying to build an auto-complete search box ?
         * We've got you covered! The Search Endpoint returns the best-matching symbols and market information based on keywords of your choice. The search results also contain match scores that provide you with the full flexibility to develop your own search and filtering logic.
         */
        String SEARCH_ENDPOINT = "SYMBOL_SEARCH";
		/**
		 * This API returns intraday time series of the equity specified, covering extended trading hours where applicable (e.g., 4:00am to 8:00pm Eastern Time for the US market). The intraday data is computed directly from the Securities Information Processor (SIP) market-aggregated data feed. You can query both raw (as-traded) and split/dividend-adjusted intraday data from this endpoint.
		 * This API returns the most recent 1-2 months of intraday data and is best suited for short-term/medium-term charting and trading strategy development. If you are targeting a deeper intraday history, please use the Extended Intraday API.
		 */
        String TIME_SERIES_INTRADAY = "TIME_SERIES_INTRADAY";
		/**
		 * This API returns historical intraday time series for the trailing 2 years, covering over 2 million data points per ticker. The intraday data is computed directly from the Securities Information Processor (SIP) market-aggregated data feed. You can query both raw (as-traded) and split/dividend-adjusted intraday data from this endpoint. Common use cases for this API include data visualization, trading simulation/backtesting, and machine learning and deep learning applications with a longer horizon.
		 */
		String TIME_SERIES_INTRADAY_EXTENDED = TIME_SERIES_INTRADAY + "_EXTENDED";
	}

	public interface API_RequestParams{
        String function = "function=";
        String symbol = "symbol=";
        String interval = "interval=";
        String adjusted = "adjusted=";
        String outputsize = "outputsize=";
        String datatype = "datatype=";
        String apikey = "apikey=";
        String slice = "slice=";
    }

    public interface API_Intervals{
		String _1MIN = "1min";
		String _5MIN = "5min";
		String _15MIN = "15min";
		String _30MIN = "30min";
		String _60MIN = "60min";
	}

	public interface API_Slices{
		static String getSlice(int months){
			months = Math.abs(months);

			return getSlice(months/2, months%2);
		}

		static String getSlice(int year, int month){

			year = Math.abs(year);
			month = Math.abs(month);

			if(year == 0){
				year = 1;
			}
			if(month == 0 || month > 12){
				month = 1;
			}

			return "year" + year + "month" + month;
		}
	}

	public void updateSymbols(){
		this.Database.executeQuery("USE " + StockUpdater.database_databaseName + ";");
		this.Database.executeUpdate(
				"CREATE TABLE IF NOT EXISTS " + "tickers" + "(" +
						"symbol VARCHAR(20) NOT NULL," +
						"exchange VARCHAR(100) NOT NULL," +
						"assetType VARCHAR(20) NOT NULL," +
						"ipoDate " + Table.DATETIME + " NOT NULL," +
						"delistingDate " + Table.DATETIME + "," +
						"status VARCHAR(20)," +
						"PRIMARY KEY(symbol)" +
						");");

		TickerAPITable ticker;
	}

	public void writeUpdateToDatabase(StockAPITable table){
		table.mySqlCreateTable(StockUpdater.database_databaseName);
		table.mySqlCreateTable(StockUpdater.database_databaseName);

		for(LocalDateTime dateTime : table.value.keySet()){
			this.Database.executeUpdate("INSERT INTO " + table.getMySQL_tableName() + "(" +
					"stock_datetime, open, high, low, close, volume)VALUES(" +
					"\'" + dateTime.toString().replace('T', ' ') + "\'," +
					table.value.get(dateTime).get(open) + "," +
					table.value.get(dateTime).get(high) + "," +
					table.value.get(dateTime).get(low) + "," +
					table.value.get(dateTime).get(close) + "," +
					table.value.get(dateTime).get(volume) +
					")" +
					"ON DUPLICATE KEY UPDATE " +
					"open="		+ table.value.get(dateTime).get(open) +
					",high="	+ table.value.get(dateTime).get(high) +
					",low="		+ table.value.get(dateTime).get(low) +
					",close="	+ table.value.get(dateTime).get(close) +
					",volume="	+ table.value.get(dateTime).get(volume) +
					";");
		}
		return;
	}

	/**
	 * Receive a List of all Tickers available from used API.
	 *
	 * @return a list of all available tickers of alphavantage's API.
	 *
	 * @see TickerAPITable
	 */
	public List<TickerAPITable> getAPI_Symbols() {
		return new LinkedList<TickerAPITable>();
	}

	/**
	 * Returns the last 200 values if there are any.
	 *
	 * @param stockName the tickername of the stock.
	 *
	 * @return a HashMap with the LocalDateTime, that can easily converted to a LocalDate, as key with the close-values of the stock on the key=day.
	 */
	public HashMap<LocalDateTime, Double> getDataFromDatabase(String stockName){

		java.sql.Connection con = null;

		HashMap<LocalDateTime, Double> output = new HashMap<LocalDateTime, Double>();
		try {
			con = java.sql.DriverManager.getConnection("jdbc:mysql://" + Database.getMySQL_hostname() + "/" +
					Database.getMySQL_databaseName() + "?user=" + Database.getMySQL_user() + "&password=" + Database.getMySQL_password() +"&serverTimezone=UTC");
			java.sql.Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(SELECT + "stock_datetime, close" + FROM + stockName +
					WHERE + "stock_datetime >= " + Convert.javaToMysql_LocalDateTime(LocalDateTime.now().minusDays(200))
			);

			// Extract db-info
			while (rs.next()) {
				/*output.put( Convert.mysqlToJava_LocalDateTime(rs.getString(0)),
						Double.valueOf(rs.getString(1)));*/
				output.put( Convert.mysqlToJava_LocalDateTime(rs.getObject(1).toString()), rs.getDouble(2));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(Exception e){
			System.err.println("FUCK!!! What happened?!");
			e.printStackTrace();
		}finally{
			try{
				if(con != null){
					con.close();
				}
			}catch(SQLException e){
				e.printStackTrace();
			}
			return output;
		}
	}

	/**
	 * Returns values of the last N-Months if there are any.
	 *
	 * @param stockName the tickername of the stock.
	 * @param lastMonths How many months in the past is the oldest Day.
	 *
	 * @return a HashMap with the LocalDateTime, that can easily converted to a LocalDate, as key with the close-values of the stock on the key=day.
	 */
	public HashMap<LocalDateTime, Double> getDataFromDatabase(String stockName, int lastMonths){

		java.sql.Connection con = null;

		HashMap<LocalDateTime, Double> output = new HashMap<LocalDateTime, Double>();
		try {
			con = java.sql.DriverManager.getConnection("jdbc:mysql://" + Database.getMySQL_hostname() + "/" +
					Database.getMySQL_databaseName() + "?user=" + Database.getMySQL_user() + "&password=" + Database.getMySQL_password() +"&serverTimezone=UTC");
			java.sql.Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(SELECT + "stock_datetime, close" + FROM + stockName +
					WHERE + "stock_datetime >= " + Convert.javaToMysql_LocalDateTime(LocalDateTime.now().minusMonths(lastMonths))
			);

			// Extract db-info
			while (rs.next()) {
				/*output.put( Convert.mysqlToJava_LocalDateTime(rs.getString(0)),
						Double.valueOf(rs.getString(1)));*/
				output.put( Convert.mysqlToJava_LocalDateTime(rs.getObject(1).toString()), rs.getDouble(2));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(Exception e){
			System.err.println("FUCK!!! What happened?!");
			e.printStackTrace();
		}finally{
			try{
				if(con != null){
					con.close();
				}
			}catch(SQLException e){
				e.printStackTrace();
			}
			return output;
		}
	}

	public BaumbartStock(String database_hostname, String database_user, String database_password, String database_databaseName){
		this.Database = new DatabaseAccess(database_hostname, database_user, database_password, database_databaseName);
		this.stockAPIParser = new StockAPIParser();
	}

	/**
	 * Updates the Database with a request to the API. Downloaded format is a JSON.
	 *
	 * [Stock Value] is a floating point value with 4 digits after the comma.
	 * JSON is built up like this:
	 *
	 *	JSON{
	 * 		"Meta Data"{
	 *			"1. Information"{
	 *					Some Information about the Request
	 *			}
	 *			"2. Symbol"{
	 *					The StockAbbreviation
	 *			}
	 *			"3. Last Refreshed"{
	 *					[YYYY-MM-DD HH:MM:SS]
	 *			}
	 *			"4. Interval"{
	 *					The Interval of the Request
	 *			}
	 *			"5. Output Size"{
	 *					The style of the output
	 *			}
	 *			"6. Time Zone"{
	 *					[State/Region]
	 *			}
	 *		}
	 * 		"Time Series ([Interval])"{
	 *			[YYYY-MM-DD HH:MM:SS]{
	 *				"1. open"{
	 *					Stock value
	 *				}
	 *				"2. high"{
	 *					Stock value
	 *				}
	 *				"3. low"{
	 *					Stock value
	 *				}
	 *				"4. close"{
	 *					Stock value
	 *				}
	 *				"5. volume"{
	 *					Stock value
	 *				}
	 *			}
	 *		}
	 *	}
	 * @param stockName
	 */
    public void updateStock(String stockName, String apiKey){
		System.err.println("updateStock(String,String)");

		StockAPITable table = new StockAPITable();

		try {
			table = this.stockAPIParser.getStocksFromAPI(stockName, apiKey);
		}catch(SocketException e){
			System.err.println("There has been an error with your connection. Exiting Program now.");
			e.printStackTrace();
			System.exit(-1);
		}catch(MalformedURLException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}catch(Exception e){
			System.err.println("BRUH, what happened?!");
			e.printStackTrace();
		}

		writeUpdateToDatabase(table);
    }

    public void updateStock(String stockName, String apiKey, int lastMonths){
    	System.err.println("updateStock(String,String,int)");

    	StockCSV table = new StockCSV();

		try {
			table = this.stockAPIParser.getStocksFromAPI(stockName, apiKey, lastMonths);
		}catch(SocketException e){
			System.err.println("There has been an error with your connection. Exiting Program now.");
			e.printStackTrace();
			System.exit(-1);
		}catch(MalformedURLException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}catch(Exception e){
			System.err.println("BRUH, what happened?!");
			e.printStackTrace();
		}

		writeUpdateToDatabase(new StockAPITable(table));
	}

	/**
	 * Executes Commands directly written inside of this method.
	 */
	public void DEBUG(){
		this.Database.executeQuery("USE " + StockUpdater.database_databaseName + ";");
		this.Database.executeQuery(
				 "CREATE TABLE IF NOT EXISTS DEFAULT_STOCK (" +
						"stock_datetime " + Table.DATETIME + " NOT NULL," +
						"open FLOAT," +
						"high FLOAT," +
						"low FLOAT," +
						"close FLOAT," +
						"volume FLOAT," +
						"PRIMARY KEY(stock_datetime)" +
						");");
		StockAPITable defaultStock = new StockAPITable();
		for(LocalDateTime dateTime : defaultStock.value.keySet()) {
			this.Database.executeQuery("INSERT INTO " + defaultStock.getMySQL_tableName() + "(" +
					"stock_datetime, open, high, low, close, volume)VALUES(" +
					"\'" + dateTime.toString().replace('T', ' ') + "\'," +
					defaultStock.value.get(dateTime).get(open) + "," +
					defaultStock.value.get(dateTime).get(high) + "," +
					defaultStock.value.get(dateTime).get(low) + "," +
					defaultStock.value.get(dateTime).get(close) + "," +
					defaultStock.value.get(dateTime).get(volume) +
					")" +
					"ON DUPLICATE KEY UPDATE " +
					"open=" + defaultStock.value.get(dateTime).get(open) +
					",high=" + defaultStock.value.get(dateTime).get(high) +
					",low=" + defaultStock.value.get(dateTime).get(low) +
					",close=" + defaultStock.value.get(dateTime).get(close) +
					",volume=" + defaultStock.value.get(dateTime).get(volume) +
					";");
		}
		System.out.println("DEBUG command executed");
		System.err.println("DEBUG command executed");
	}

	/**
	 * Installs the automatic updater of this program.
	 */
    public static void installUpdater(){
    	BaumbartStockInstaller installer = new BaumbartStockInstaller();
    	if(System.getProperty("os.name").toLowerCase().contains("windows")){
    		// schedule task via schtasks.exe
			// official help:
			//
			// SCHTASKS /Params [Arguments]
			// Params-list:
			//	/Create		Creates a new scheduled Task
			//	/Delete		Deletes a scheduled Task
			//	/Query		Shows all scheduled Tasks
			//	/Change		Changes properties of scheduled Tasks
			//	/Run		Runs the scheduled Task
			//	/End		Ends the active planned Task
			//	/ShowSid	Shows the Security-ID, which refers to a scheduled Taskname
			//	/?			Shows the help

			// SCHTASKS /Create /SC HOURLY /\"java C:/Program Files/sharesUpdater/sharesUpdater"

			String stockUpdater_FilePath = System.getenv("ProgramFiles") + "\\stockUpdater\\stockUpdater";
		}else if(System.getProperty("os.name").toLowerCase().contains("linux")){
    		// path is on linux "/etc/cron.hourly/stockUpdater"
		}

    	// path is on windows "C:/Users/Public/Documents/stockUpdater/stockUpdater
		for(String key : System.getProperties().stringPropertyNames()){
			System.out.println(key + ":" + System.lineSeparator() + "\t\"" + System.getProperty(key) + "\"");
		}
    }

    /**
	 * Uninstalls the automatic updater of this program.
     */
    public static void uninstallUpdater(){
		System.err.println("uninstallUpdate");
    }
}

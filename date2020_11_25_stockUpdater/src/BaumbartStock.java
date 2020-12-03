package date2020_11_25_stockUpdater.src;

import date2020_11_25_stockUpdater.src.StockAPI.StockAPIParser;
import date2020_11_25_stockUpdater.src.StockAPI.StockAPITable;
import miscForEverything.database.mySQL.DatabaseAccess;
import miscForEverything.database.mySQL.Table;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;

import static date2020_11_25_stockUpdater.src.StockValues.*;

public class BaumbartStock {
	StockAPIParser stockAPIParser = null;

    public static final char API_SEPARATE_SIGN = '&';
    private final String API_KEY = "VBAX1XGSP5QC85SL";

    private final String API_DEFAULT_REQUEST_STRING = "https://www.alphavantage.co/query?" +
            "function=" + API_Functions.TIME_SERIES_INTRADAY + API_SEPARATE_SIGN +
            "symbol=" + API_Symbols.IBM + API_SEPARATE_SIGN +
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

        String TIME_SERIES_INTRADAY = "TIME_SERIES_INTRADAY";
    }

    public interface API_RequestParams{
        String function = "function=";
        String symbol = "symbol=";
        String interval = "interval=";
        String adjusted = "adjusted=";
        String outputsize = "outputsize=";
        String datatype = "datatype=";
        String apikey = "apikey=";
    }

    public interface API_Symbols {
        String IBM = "IBM";
    }

    public interface API_Intervals{
		String _1MIN = "1min";
		String _5MIN = "5min";
		String _15MIN = "15min";
		String _30MIN = "30min";
		String _60MIN = "60min";
	}

	public void writeUpdateToDatabase(StockAPITable table){
		this.Database.DEBUG_executeStatement("USE " + StockUpdater.database_databaseName + ";");
		this.Database.DEBUG_executeStatement(
				"CREATE TABLE IF NOT EXISTS " + table.stockname + "(" +
						"stock_datetime " + Table.DATETIME + " NOT NULL," +
						"open FLOAT," +
						"high FLOAT," +
						"low FLOAT," +
						"close FLOAT," +
						"volume FLOAT," +
						"PRIMARY KEY(stock_datetime)" +
						");");

		for(LocalDateTime dateTime : table.value.keySet()){
			this.Database.DEBUG_executeStatement("INSERT INTO " + table.stockname + "(" +
					"stock_datetime, open, high, low, close, volume)VALUES(" +
					"\'" + dateTime.toString().replace('T', ' ') + "\'," +
					table.value.get(dateTime).get(open) + "," +
					table.value.get(dateTime).get(high) + "," +
					table.value.get(dateTime).get(low) + "," +
					table.value.get(dateTime).get(close) + "," +
					table.value.get(dateTime).get(volume) +
					");");
		}
		return;
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
		System.err.println("updateStock");

		StockAPITable table = new StockAPITable();

		try {
			table = this.stockAPIParser.getStocks(stockName, apiKey);
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

    public void DEBUG(){
this.Database.DEBUG_executeStatement("USE " + StockUpdater.database_databaseName + ";");
		this.Database.DEBUG_executeStatement(
				 "CREATE TABLE IF NOT EXISTS IBM (" +
						"stock_datetime " + Table.DATETIME + " NOT NULL," +
						"open FLOAT," +
						"high FLOAT," +
						"low FLOAT," +
						"close FLOAT," +
						"volume FLOAT," +
						"PRIMARY KEY(stock_datetime)" +
						");");
		System.out.println("DEBUG command executed");
	}

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

    public static void uninstallUpdater(){
		System.err.println("uninstallUpdate");
    }
}

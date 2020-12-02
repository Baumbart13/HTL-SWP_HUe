package date2020_11_25_sharesUpdater;

public class BaumbartStock {

    private final char API_SEPARATE_SIGN = '&';
    private final String API_KEY = "VBAX1XGSP5QC85SL";
    private final String API_DEFAULT_REQUEST_INTERVAL = "5min";


    private final String API_DEFAULT_REQUEST_STRING = "https://www.alphavantage.co/query?" +
            "function=" + API_Functions.TIME_SERIES_INTRADAY + API_SEPARATE_SIGN +
            "symbol=" + API_Symbols.IBM + API_SEPARATE_SIGN +
            "interval=" + API_DEFAULT_REQUEST_INTERVAL + API_SEPARATE_SIGN +
            "apikey=" + API_KEY;

    private interface API_Functions {
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

    private interface API_RequestParams{
        String function = "function=";
        String symbol = "symbol=";
        String interval = "interval=";
        String adjusted = "adjusted=";
        String outputsize = "outputsize=";
        String datatype = "datatype=";
        String apikey = "apikey=";
    }

    private interface API_Symbols {
        String IBM = "IBM";
    }

    public static void updateStock(){
		System.err.println("updateStock");
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

    }
}

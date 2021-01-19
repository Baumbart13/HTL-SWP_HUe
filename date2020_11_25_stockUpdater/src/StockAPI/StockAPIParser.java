package date2020_11_25_stockUpdater.src.StockAPI;

import static date2020_11_25_stockUpdater.src.BaumbartStock.API_SEPARATE_SIGN;
import date2020_11_25_stockUpdater.src.BaumbartStock.*;
import date2020_11_25_stockUpdater.src.StockValues;
import date2020_11_25_stockUpdater.src.files.StockCSV;
import miscForEverything.database.mySQL.DatabaseAccess;
import miscForEverything.database.mySQL.Table;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;

public class StockAPIParser {

	private final String dataReceiving = "Receiving data.";
	private final String dataReceived = "Data received!";

	/**
	 * Creates the URL for the API.
	 * @param API_Function What function shall be used. Look for BaumbartStock.API_Functions for more information.
	 * @param API_Symbol Which stocks shall be updated? Examples are IBM, TSLA
	 * @param API_Interval
	 * @param API_Key
	 * @return
	 */
	public String URL(String API_Function, String API_Symbol, String API_Interval, String API_Key){
		return "https://www.alphavantage.co/query?" + API_RequestParams.function + API_Function +
				API_SEPARATE_SIGN + API_RequestParams.symbol + API_Symbol +
				API_SEPARATE_SIGN + API_RequestParams.interval + API_Interval +
				API_SEPARATE_SIGN + API_RequestParams.apikey + API_Key;
	}

	/**
	 * Creates the URL for the API.
	 * @param API_Function What function shall be used. Look for BaumbartStock.API_Functions for more information.
	 * @param API_Symbol Which stocks shall be updated? Examples are IBM, TSLA.
	 * @param API_Interval
	 * @param API_Key
	 * @param API_Slice The size of the request.
	 * @return
	 */
	public String URL(String API_Function, String API_Symbol, String API_Interval, String API_Slice, String API_Key){
		return "https://www.alphavantage.co/query?" + API_RequestParams.function + API_Function +
				API_SEPARATE_SIGN + API_RequestParams.symbol + API_Symbol +
				API_SEPARATE_SIGN + API_RequestParams.interval + API_Interval +
				API_SEPARATE_SIGN + API_RequestParams.slice + API_Slice +
				API_SEPARATE_SIGN + API_RequestParams.apikey + API_Key;
	}

	/**
	 * TODO implement getStocksFromDatabase(String symbol, LocalDateTime firstDay, LocalDateTime lastDay)
	 */
	public StockAPITable getStocksFromDatabase(String symbol, LocalDateTime firstDay, LocalDateTime lastDay){
		return new StockAPITable();
	}

	/**
	 * TODO implement getStocksFromDatabase(String symbol, LocalDateTime day)
	 */
	public StockAPITable getStocksFromDatabase(String symbol, LocalDateTime day, String apiKey, DatabaseAccess db) throws MalformedURLException, IOException{
		System.out.println(dataReceiving);



		System.out.println(dataReceived);
		return new StockAPITable();
	}

	/**
	 *
	 *
	 * @param symbol The Symbol of the Stock.
	 * @param apiKey The API-Key provided by www.alphavantage.co
	 * @param months How much months back in time, shall be data received.
	 * @return Returns the original JSON of the API without the
	 * @throws MalformedURLException Thrown in case of API-URL is wrong.
	 * @throws IOException Thrown in any case of IO-Errors.
	 */
	public StockCSV getStocksFromAPI(String symbol, String apiKey, Integer months) throws MalformedURLException, IOException{
		final char quotationMark = '\"';
		System.out.println(dataReceiving);

		int year = months/12;
		months = months%12;


		StockCSV stocks = new StockCSV(symbol, IOUtils.toString(new URL(
				URL(API_Functions.TIME_SERIES_INTRADAY_EXTENDED, symbol.toUpperCase(), API_Intervals._60MIN,
						API_Slices.getSlice(year, months), apiKey)), StandardCharsets.UTF_8));

		System.out.println(dataReceived);
		return stocks;
	}

	/**
	 *
	 * @param symbol The Symbol of the Stock.
	 * @param apiKey The API-Key provided by www.alphavantage.co
	 * @return Returns the original JSON of the API without the
	 * @throws MalformedURLException Thrown in case of API-URL is wrong.
	 * @throws IOException Thrown in any case of IO-Errors.
	 */
	public StockAPITable getStocksFromAPI(String symbol, String apiKey) throws MalformedURLException, IOException {

		final char quotationMark = '\"';
		System.out.println(dataReceiving);

		JSONObject json = new JSONObject();
		json = new JSONObject(IOUtils.toString(new URL(URL(API_Functions.TIME_SERIES_INTRADAY, symbol.toUpperCase(), API_Intervals._60MIN, apiKey)), Charset.forName("UTF-8")));

		// Convert from JSON to Correct DB-Table
		StockAPITable stocks = new StockAPITable(symbol, new HashMap<LocalDateTime, HashMap<StockValues, Float>>());
		String dataString = json.get("Time Series (" + API_Intervals._60MIN + ")").toString();

		// detect a never-ending while-loop
		int oldLength = dataString.length();

		// For every LocalDateTime
		while(dataString.length() > 2){

			//- - - - - -
			// receive LocalDateTime
			int indexStart = dataString.indexOf(quotationMark) + 1;
			int indexEnd = dataString.indexOf(quotationMark, indexStart);
			String dateTime_str = dataString.substring(indexStart, indexEnd).replace(' ', 'T');
			// insert dateTime into output
			LocalDateTime dateTime = LocalDateTime.parse(dateTime_str, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
			stocks.value.put(dateTime, new HashMap<StockValues, Float>());

			//- - - - - -

			for(int i = 0; i < 5; ++i) {
				// receive StockValues_Type
				indexStart = dataString.indexOf(quotationMark, indexEnd + 2) + 1;
				indexEnd = dataString.indexOf(quotationMark, indexStart);
				String stockValue_type_str = dataString.substring(indexStart, indexEnd);
				StockValues stockValue_type = StockValues.of(stockValue_type_str);
				// receive StockValues_Value
				indexStart = dataString.indexOf(quotationMark, indexEnd + 1) + 1;
				indexEnd = dataString.indexOf(quotationMark, indexStart);
				String stockValue_value_str = dataString.substring(indexStart, indexEnd);
				Float stockValue_value = Float.valueOf(stockValue_value_str);
				// insert StockValues into output
				stocks.value.get(dateTime).put(stockValue_type, stockValue_value);
			}
			//- - - - - -

			// trim to size for termination
			dataString = dataString.substring(dataString.indexOf('}', indexEnd));

			// detect a never-ending while-loop
			if(oldLength == dataString.length()){
				System.err.println("Error detected!! Program is stuck and now closes before causing any damage to your system.");
				System.exit(-1);
			}
			oldLength = dataString.length();
		}

		System.out.println(dataReceived);

		/* DEBUG-ConsoleLog
		System.out.println(System.lineSeparator() + System.lineSeparator()+dataString);
		System.out.println(System.lineSeparator() + System.lineSeparator());
		System.out.println(symbol);
		for(LocalDateTime dateTime : stocks.value.keySet()){
			System.out.println(dateTime.toString());
			for(StockValues stockValue_type : stocks.value.get(dateTime).keySet()){
				System.out.println("\t" + stockValue_type + " : " + stocks.value.get(dateTime).get(stockValue_type));
			}
		}
		*/

		return stocks;
	}
}

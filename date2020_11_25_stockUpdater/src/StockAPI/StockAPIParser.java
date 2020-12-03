package date2020_11_25_stockUpdater.src.StockAPI;

import static date2020_11_25_stockUpdater.src.BaumbartStock.API_SEPARATE_SIGN;
import date2020_11_25_stockUpdater.src.BaumbartStock.*;
import date2020_11_25_stockUpdater.src.StockValues;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;

public class StockAPIParser {

	private final String dataReceiving = "Receiving data.";
	private final String dataReceived = "Data received!";

	public String URL(String API_Function, String API_Symbol, String API_Interval, String API_Key){
		return "https://www.alphavantage.co/query?" + API_RequestParams.function + API_Function +
				API_SEPARATE_SIGN + API_RequestParams.symbol + API_Symbol +
				API_SEPARATE_SIGN + API_RequestParams.interval + API_Interval +
				API_SEPARATE_SIGN + API_RequestParams.apikey + API_Key;
	}

	public StockAPITable getStocks(String symbol, String apiKey) throws MalformedURLException, IOException {

		final char quotationMark = '\"';
		System.out.println(dataReceiving);

		JSONObject json = new JSONObject();
		json = new JSONObject(IOUtils.toString(new URL(URL(API_Functions.TIME_SERIES_INTRADAY, symbol.toUpperCase(), API_Intervals._60MIN, apiKey)), Charset.forName("UTF-8")));

		// Convert from JSON to Correct DB-Table
		StockAPITable stocks = new StockAPITable(symbol, new HashMap<LocalDateTime, HashMap<StockValues, Float>>());
		String dataString = json.get("Time Series (" + API_Intervals._60MIN + ")").toString();

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
			System.err.println(dateTime.toString());

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

		}

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

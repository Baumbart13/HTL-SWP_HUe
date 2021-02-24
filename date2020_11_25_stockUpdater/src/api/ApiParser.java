package date2020_11_25_stockUpdater.src.api;

import date2020_11_25_stockUpdater.src.StockDataPoint;
import date2020_11_25_stockUpdater.src.StockResults;
import date2020_11_25_stockUpdater.src.StockValueType;
import jdk.jshell.spi.ExecutionControl;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ApiParser {
	private final String m_ApiUrl = "https://www.alphavantage.co/query?";
	private String m_ApiKey = "demo";

	public ApiParser(String apiKey){
		m_ApiKey = apiKey;
	}

	public ApiParser(File loadFromFile) throws IOException {

		var reader = new BufferedReader(new FileReader(loadFromFile));

		var line = reader.readLine();
		while(line != null){
			line = reader.readLine();
		}

		m_ApiKey = line;
	}

	public enum Function{
		/**
		 * This API returns a list of active or delisted US stocks and ETFs, either as of the latest trading day or at a specific time in history. The endpoint is positioned to facilitate equity research on asset lifecycle and survivorship.
		 */
		LISTING_STATUS,
		/**
		 * This API returns raw (as-traded) daily open/high/low/close/volume values, daily adjusted close values, and historical split/dividend events of the global equity specified, covering 20+ years of historical data.
		 */
		TIME_SERIES_DAILY_ADJUSTED
	}

	public enum Outputsize{
		full,
		compact
	}

	private String makeUrl(Function fun, String symbol){
		if(fun == Function.LISTING_STATUS){
			return String.format(m_ApiUrl + "function=%s&apiKey=%s",
					fun.name(), m_ApiKey);
		}
		if(fun == Function.TIME_SERIES_DAILY_ADJUSTED){
			return String.format(m_ApiUrl + "function=%s&symbol=%s&outputsize=full&datatype=json&apikey=%s",
					fun.name(), symbol, m_ApiKey);
		}
		return String.format(m_ApiUrl + "function=%s&symbol=%s&datatype=json&apikey=%s",
				fun.name(), symbol, m_ApiKey);
	}

	private String makeUrl(Function fun, String symbol, Outputsize size){
		return makeUrl(fun, symbol) + String.format("&outputsize=%s", size.name());
	}

	/**
	 * Calls Functions of the www.alphavantage.co API and returns the request as a StockResults object. Uses compact outputsize. Incompatible with Function.LISTING_STATUS.
	 * @param symbol The symbol of the stock.
	 * @param fun The API-function that will be called.
	 * @return the StockResults of the API request.
	 * @throws IOException if an I/O Exception occurs
	 * @see StockResults
	 * @see Function
	 */
	public StockResults request(@NotNull String symbol, @NotNull Function fun) throws IOException {
		if(fun == Function.LISTING_STATUS){
			return null;
		}
		StockResults results = new StockResults(symbol);

		if(symbol.contains("_")){
			symbol = symbol.replace('_', '-');
		}
		var url = makeUrl(fun, symbol);
		var json = new JSONObject(IOUtils.toString(new URL(url), StandardCharsets.UTF_8));

		convertJsonToStockResults(json, results);

		System.out.printf("Requesting from:%30.28s%nRequested Data:%27.24s%n", url, json);
		System.out.printf("Request has %d elements%n", results.getDataPoints().size());
		return results;
	}

	private void convertJsonToStockResults(JSONObject json, StockResults results){
		var timeSeries = json.getJSONObject(json.keys().next());
		for(var key : timeSeries.keySet()){
			LocalDateTime dateTime;

			dateTime = LocalDate.parse(key).atStartOfDay();

			//1. open
			//2. high
			//3. low
			//4. close
			//5. adjusted close
			//6. volume
			//7. divided amount
			//8. split coefficient
			StockDataPoint dataPoint = new StockDataPoint(dateTime);
			var item = timeSeries.getJSONObject(key);
			dataPoint.addValue(StockValueType.open, item.getFloat("1. open"));
			dataPoint.addValue(StockValueType.high, item.getFloat("2. high"));
			dataPoint.addValue(StockValueType.low, item.getFloat("3. low"));
			dataPoint.addValue(StockValueType.close, item.getFloat("4. close"));
			dataPoint.addValue(StockValueType.volume, item.getFloat("6. volume"));
			dataPoint.addValue(StockValueType.avg200, 0.0f);
			results.addDataPoint(dataPoint);
		}
	}

	/**
	 * Calls Functions of the www.alphavantage.co API and returns the request as a StockResults object. Uses full outputsize.
	 * @param symbol The symbol of the stock.
	 * @param fun The API-function that will be called.
	 * @return the StockResults of teh API request.
	 * @see StockResults
	 * @see Function
	 */
	public StockResults requestAll(@NotNull String symbol, @NotNull Function fun){
		return new StockResults(symbol);
	}
}

package date2020_11_25_stockUpdater.src.StockAPI;


import date2020_11_25_stockUpdater.src.StockValues;
import static date2020_11_25_stockUpdater.src.StockValues.*;

import java.time.LocalDateTime;
import java.util.HashMap;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

public class StockAPITable {
	public HashMap<LocalDateTime, HashMap<StockValues, Float>> value;
	public String stockname;

	public StockAPITable(String stockName, HashMap<LocalDateTime, HashMap<StockValues, Float>> jsonExtractedForm){
		this.stockname = stockName;
		this.value = jsonExtractedForm;
	}

	public StockAPITable(){
		this.stockname = "DEFAULT";
		this.value = new HashMap<LocalDateTime, HashMap<StockValues, Float>>();

		this.value.put(LocalDateTime.parse("2020-11-20T17:00", ISO_LOCAL_DATE_TIME),
				new HashMap<>());
		this.value.get(LocalDateTime.parse("2020-11-20T17:00", ISO_LOCAL_DATE_TIME)).put(
				close, Float.valueOf("116.8078"));
		this.value.get(LocalDateTime.parse("2020-11-20T17:00", ISO_LOCAL_DATE_TIME)).put(
				open, Float.valueOf("117.8"));
		this.value.get(LocalDateTime.parse("2020-11-20T17:00", ISO_LOCAL_DATE_TIME)).put(
				high, Float.valueOf("118.04"));
		this.value.get(LocalDateTime.parse("2020-11-20T17:00", ISO_LOCAL_DATE_TIME)).put(
				volume, Float.valueOf("521565.0"));
		this.value.get(LocalDateTime.parse("2020-11-20T17:00", ISO_LOCAL_DATE_TIME)).put(
				low, Float.valueOf("116.69"));


		this.value.put(LocalDateTime.parse("2020-11-20T11:00", ISO_LOCAL_DATE_TIME),
				new HashMap<>());
		this.value.get(LocalDateTime.parse("2020-11-20T11:00", ISO_LOCAL_DATE_TIME)).put(
				close, Float.valueOf("116.94"));
		this.value.get(LocalDateTime.parse("2020-11-20T11:00", ISO_LOCAL_DATE_TIME)).put(
				open, Float.valueOf("116.94"));
		this.value.get(LocalDateTime.parse("2020-11-20T11:00", ISO_LOCAL_DATE_TIME)).put(
				high, Float.valueOf("116.9517"));
		this.value.get(LocalDateTime.parse("2020-11-20T11:00", ISO_LOCAL_DATE_TIME)).put(
				volume, Float.valueOf("367943.0"));
		this.value.get(LocalDateTime.parse("2020-11-20T11:00", ISO_LOCAL_DATE_TIME)).put(
				low, Float.valueOf("116.85"));
	}
}

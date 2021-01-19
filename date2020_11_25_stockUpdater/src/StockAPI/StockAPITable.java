package date2020_11_25_stockUpdater.src.StockAPI;

import date2020_11_25_stockUpdater.src.StockValues;
import date2020_11_25_stockUpdater.src.files.StockCSV;
import miscForEverything.database.mySQL.Table;
import org.jetbrains.annotations.NotNull;

import static date2020_11_25_stockUpdater.src.StockValues.*;

import java.time.LocalDateTime;
import java.util.HashMap;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

public class StockAPITable extends Table implements StockAPITable_TableColumns{
	public HashMap<LocalDateTime, HashMap<StockValues, Float>> value;

	public StockAPITable(String stockName, HashMap<LocalDateTime, HashMap<StockValues, Float>> jsonExtractedForm){
		super("baumbartstocks", stockName);
		this.value = jsonExtractedForm;
	}

	@Override
	public String mySqlCreateTable(@NotNull String database){
		return USE + database.trim() + _QueryEnd +
				CREATE + TABLE + IF + NOT + EXISTS + this.mySQL_tableName + _QueryOpenBracket +
				"stock_datetime" + DATETIME + NOT + NULL + _QueryComma +
				"open" + FLOAT + _QueryComma +
				"high" + FLOAT + _QueryComma +
				"low" + FLOAT + _QueryComma +
				"close" + FLOAT + _QueryComma +
				"volume" + FLOAT + _QueryComma +
				miscForEverything.database.mySQL.MySQLKeywords.PRIMARY_KEY("stock_datetime") +
				_QueryCloseBracket + _QueryEnd;
	}

	public StockAPITable(StockCSV csv){
		super("baumbartstocks", csv.getMySQL_tableName());

		this.value = (HashMap<LocalDateTime, HashMap<StockValues, Float>>)csv.value.clone();
	}

	/**
	 * Creates a default stock, done with the right pattern and the stockname of "DEFAULT_STOCK". For testing functionality.
	 */
	public StockAPITable(){
		super("baumbartstocks", "DEFAULT_STOCK");
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

package date2020_11_25_stockUpdater.src.files;

import date2020_11_25_stockUpdater.src.StockAPI.StockAPITable_TableColumns;
import date2020_11_25_stockUpdater.src.StockValues;
import miscForEverything.dataTypes.CanSaveCSV;
import miscForEverything.database.mySQL.Convert;
import miscForEverything.database.mySQL.Table;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;
import java.util.HashMap;

public class StockCSV extends Table implements StockAPITable_TableColumns, CanSaveCSV {
	public HashMap<LocalDateTime, HashMap<StockValues, Float>> value;

	public StockCSV(String stockName, String csvString){
		super("Baumbartstocks", stockName);

		String[] rows = csvString.split(System.lineSeparator());
		value = new HashMap<>();

		final char comma = ',';

		// starting with 1, because first line is known header
		for(int i = 1; i < rows.length; ++i){
			int index = rows[i].indexOf(comma);

			// Get the LocalDateTime as primary key
			LocalDateTime key = Convert.mysqlToJava_LocalDateTime(rows[i].substring(0, index));
			value.put(key, new HashMap<StockValues, Float>());
			rows[i] = rows[i].substring(index+1);

			// Get the values in this order: open, high, low, close, volume
			index = rows[i].indexOf(comma);
			value.get(key).put(StockValues.open, Float.valueOf(rows[i].substring(0, index)));
			rows[i] = rows[i].substring(index+1);

			index = rows[i].indexOf(comma);
			value.get(key).put(StockValues.high, Float.valueOf(rows[i].substring(0, index)));
			rows[i] = rows[i].substring(index+1);

			index = rows[i].indexOf(comma);
			value.get(key).put(StockValues.low, Float.valueOf(rows[i].substring(0, index)));
			rows[i] = rows[i].substring(index+1);

			index = rows[i].indexOf(comma);
			value.get(key).put(StockValues.close, Float.valueOf(rows[i].substring(0, index)));
			rows[i] = rows[i].substring(index+1);

			index = rows[i].indexOf(comma);
			value.get(key).put(StockValues.volume, Float.valueOf(rows[i]));
			rows[i] = rows[i].substring(index+1);


		}
	}

	public StockCSV(@NotNull String stockName, @NotNull LocalDateTime dateTime, float open, float high, float low, float close, float volume){
		super("baumbartstocks", stockName);

		value = new HashMap<>();

		value.put(dateTime, new HashMap<>());
		value.get(dateTime).put(StockValues.open, open);
		value.get(dateTime).put(StockValues.high, high);
		value.get(dateTime).put(StockValues.low, low);
		value.get(dateTime).put(StockValues.close, close);
		value.get(dateTime).put(StockValues.volume, volume);
	}

	public StockCSV(){
		super("baumbartstocks", "DEFAULT_STOCK");

		value = new HashMap<>();

		LocalDateTime dateTime = LocalDateTime.parse("2020-11-20T17:00", ISO_LOCAL_DATE_TIME);

		value.put(dateTime, new HashMap<>());
		value.get(dateTime).put(StockValues.close, Float.valueOf("116.8078"));
		value.get(dateTime).put(StockValues.open, Float.valueOf("117.8"));
		value.get(dateTime).put(StockValues.high, Float.valueOf("118.04"));
		value.get(dateTime).put(StockValues.volume, Float.valueOf("521565.0"));
		value.get(dateTime).put(StockValues.low, Float.valueOf("116.69"));

		dateTime = LocalDateTime.parse("2020-11-20T11:00", ISO_LOCAL_DATE_TIME);
		value.put(dateTime, new HashMap<>());
		value.get(dateTime).put(StockValues.close, Float.valueOf("116.94"));
		value.get(dateTime).put(StockValues.open, Float.valueOf("116.94"));
		value.get(dateTime).put(StockValues.high, Float.valueOf("116.9517"));
		value.get(dateTime).put(StockValues.volume, Float.valueOf("367943.0"));
		value.get(dateTime).put(StockValues.low, Float.valueOf("116.85"));
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

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();

		return sb.toString();
	}

	@Override
	public String toCSVString(){
		StringBuilder sb = new StringBuilder();

		sb.append("stock_datetime," + StockValues.open + ',' + StockValues.high + ',' + StockValues.low + ',' +
				StockValues.close + ',' + StockValues.volume + System.lineSeparator());

		for(LocalDateTime date : value.keySet()){

			Convert.javaToMysql_LocalDateTime(date);

		}

		return sb.toString();
	}
}

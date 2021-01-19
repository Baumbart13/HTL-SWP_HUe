package date2020_11_25_stockUpdater.src.StockAPI;

import date2020_11_25_stockUpdater.src.StockUpdater;
import miscForEverything.database.mySQL.MySQL;
import miscForEverything.database.mySQL.Table;

import java.time.LocalDateTime;

import static date2020_11_25_stockUpdater.src.StockValues.*;
import static date2020_11_25_stockUpdater.src.StockValues.volume;
import static miscForEverything.database.mySQL.MySQLKeywords.*;

/**
 * MySQL-Table for the tickers provided by the www.alphavantage.co API.
 */
public class TickerAPITable extends Table {

	/**
	 * The status of the ticker. Is it still active or not?
	 */
	public enum Ticker_Status{
		Active,
		Delisted,
		Default;

		public static Ticker_Status of(Integer x){
			if(x.equals(Active.ordinal())){
				return Active;
			}else if(x.equals(Delisted.ordinal())){
				return Delisted;
			}
			return Default;
		}
	}

	/**
	 * The source of the stock/ticker.
	 */
	public enum Ticker_Exchange{
		NASDAQ,
		NYSE,
		NYSE_ARCA,
		Default;

		public static Ticker_Exchange of(Integer x){
			if(x.equals(NASDAQ.ordinal())){
				return NASDAQ;
			}else if(x.equals(NYSE.ordinal())){
				return NYSE;
			}else if(x.equals(NYSE_ARCA.ordinal())){
				return NYSE_ARCA;
			}
			return Default;
		}
	}

	/**
	 * The type of this ticker-entry.
	 */
	public enum Ticker_AssetType{
		Stock,
		ETF,
		Default;

		public static Ticker_AssetType of(Integer x){
			if(x.equals(Stock.ordinal())){
				return Stock;
			}else if(x.equals(ETF.ordinal())){
				return ETF;
			}
			return Default;
		}
	}

	protected String symbol;
	protected String name;
	protected Ticker_Exchange exchange;
	protected Ticker_AssetType assetType;
	protected LocalDateTime ipoDate;
	protected LocalDateTime delistingDate;
	protected Ticker_Status status;

	public static final String mySQL_tableName = "Ticker_Symbols";
	public static final String mySQL_value0_symbol = "symbol";
	public static final String mySQL_value1_name = "name";
	public static final String mySQL_value2_exchange = "exchange";
	public static final String mySQL_value3_assetType = "assetType";
	public static final String mySQL_value4_ipoDate = "ipoDate";
	public static final String mySQL_value5_delistingDate = "delistingDate";
	public static final String mySQL_value6_status = "status";

	/**
	 * This function creates the
	 * @param database The name of the database, where this MySQL-table shall be created.
	 * @return The String for constructing the appropriate datatype; the tickers.
	 */
	@Override
	public String mySqlCreateTable(String database){
		return USE + database.trim() + _QueryEnd +
				CREATE + TABLE + IF + NOT + EXISTS + mySQL_tableName + _QueryOpenBracket +
				"symbol" + VARCHAR(20) + NOT + NULL + _QueryComma +
				"exchange" + INTEGER + NOT + NULL + _QueryComma +
				"assetType" + INTEGER + NOT + NULL + _QueryComma +
				"ipoDate" + DATETIME + NOT + NULL + _QueryComma +
				"delistingDate" + DATETIME + _QueryComma +
				"status" + INTEGER + _QueryComma +
				PRIMARY_KEY("symbol") + _QueryCloseBracket + _QueryEnd;
	}

	/**
	 *
	 * @param database
	 * @return
	 */
	public static String mySqlInsertUpdateTable(String database, TickerAPITable table){

		return USE + database.trim() + _QueryEnd +
				INSERT + INTO + mySQL_tableName + _QueryOpenBracket +
				table.mySQL_tableName + ",exchange,assetType,ipoDate,delistingDate,status)" + VALUES + _QueryOpenBracket +
				mySQL_value0_symbol + _QueryComma +
				mySQL_value1_name + _QueryComma +
				mySQL_value2_exchange + _QueryComma +
				mySQL_value3_assetType + _QueryComma +
				mySQL_value4_ipoDate + _QueryComma +
				mySQL_value5_delistingDate + _QueryComma +
				mySQL_value6_status + _QueryCloseBracket +
				ON + DUPLICATE + KEY + UPDATE +
				mySQL_value0_symbol + _QueryEquals;

/*
				this.Database.DEBUG_executeStatement("INSERT INTO " + table.stockname + "(" +
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
				*/
	}

	/**
	 * Creates a default Ticker, done with the right pattern and the tickersymbol of "DEFAULT_TICKER". For testing functionality.
	 */
	public TickerAPITable(){
		this("baumbartStocks", "DEFAULT_TICKER", "This is a default-ticker.", Ticker_Exchange.Default,
				Ticker_AssetType.Default, LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0),
				LocalDateTime.of(2021, 1, 1, 0, 0, 0, 0), Ticker_Status.Default);
	}

	/**
	 * Connects to the mySQL-database and is used for storing the Information of a table-row.
	 * @param database The database where this table is included.
	 * @param tickerSymbol The symbol of the stock.
	 * @param tickerName The full name of the company.
	 * @param exchange From which exchange is this stock.
	 * @param assetType What type of entry is this.
	 * @param ipoDate The stocks was published on this date.
	 * @param delistingDate The stocks was delisted on this date.
	 * @param status The status of this stock.
	 *
	 * @see TickerAPITable.Ticker_Exchange
	 * @see TickerAPITable.Ticker_AssetType
	 * @see TickerAPITable.Ticker_Status
	 */
	public TickerAPITable(String database, String tickerSymbol, String tickerName, Ticker_Exchange exchange,
						  Ticker_AssetType assetType, LocalDateTime ipoDate, LocalDateTime delistingDate, Ticker_Status status){
		super(database, mySQL_tableName);

		this.symbol = tickerSymbol;
		this.name = tickerName;
		this.exchange = exchange;
		this.assetType = assetType;
		this.ipoDate = ipoDate;
		this.delistingDate = delistingDate;
		this.status = status;
	}

	/**
	 * Connects to the mySQL-database and is used for storing the Information of a table-row.
	 *
	 * @param database The database where this table is included.
	 * @param tickerSymbol The symbol of the stock.
	 * @param tickerName The full name of the company.
	 * @param exchange From which exchange is this stock.
	 * @param assetType What type of entry is this.
	 * @param ipoDate The stocks was published on this date.
	 * @param delistingDate The stocks was delisted on this date.
	 * @param status The status of this stock.
	 *
	 * @see TickerAPITable.Ticker_Exchange
	 * @see TickerAPITable.Ticker_AssetType
	 * @see TickerAPITable.Ticker_Status
	 */
	public TickerAPITable(String database, String tickerSymbol, String tickerName, Integer exchange,
						  Integer assetType, LocalDateTime ipoDate, LocalDateTime delistingDate, Integer status){

		this(database, tickerSymbol, tickerName, Ticker_Exchange.of(exchange), Ticker_AssetType.of(assetType), ipoDate,
				delistingDate, Ticker_Status.of(status));
	}
}

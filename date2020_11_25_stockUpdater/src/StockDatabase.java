package date2020_11_25_stockUpdater.src;

import miscForEverything.database.Database;

import java.sql.*;

public class StockDatabase extends Database {

	public StockDatabase(String hostname, String user, String password, String database) {
		super(hostname, user, password, database);
	}

	@Override
	public Database clone() {
		return new StockDatabase(this.m_hostname, this.m_user, this.m_password, this.m_database);
	}

	@Override
	public void connect() throws SQLException {

		// Darn NullPointerException... can't figure out other way of checking if m_con is already opened
		if(m_con != null){
			if(!m_con.isClosed()) {
				return;
			}
			return;
		}

		m_con = DriverManager.getConnection(
				String.format("jdbc:mysql://%s/%s?user=%s&password=%s&serverTimezone=UTC",
						m_hostname, m_database, m_user, m_password));
	}

	@Override
	public void disconnect() throws SQLException{
		if(m_con == null || m_con.isClosed()){
			return;
		}

		m_con.close();
		m_con = null;
	}

	@Override
	public void createDatabase(String database) throws SQLException {
		/*String sql = String.format("CREATE DATABASE IF NOT EXISTS %s;",
				database.trim());*/
		Statement stmnt = m_con.createStatement();

		stmnt.execute(String.format("CREATE DATABASE IF NOT EXISTS %s;",
				database.trim()));
	}

	public void createDatabase() throws SQLException{
		this.createDatabase(m_database);
	}

	/**
	 * Executes an update on the Database. Does not work with <code>PreparedStatement.toString()</code>!
	 * @param sql The query itself.
	 * @return either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing
	 * @throws SQLException if a database access error occurs; this method is called on a closed PreparedStatement or the SQL statement returns a ResultSet object
	 */
	public int executeUpdate(String sql) throws SQLException{
		PreparedStatement stmnt = m_con.prepareStatement(sql);
		return stmnt.executeUpdate();
	}

	/**
	 * Executes a query on the Database and returns the ResultSet.
	 * @param sql The PreparedStatement as a String using toString method.
	 * @return a ResultSet object that contains the data produced by the query; never null
	 * @throws SQLException if a database access error occurs; this method is called on a closed PreparedStatement or the SQL statement does not return a ResultSet object
	 * @see PreparedStatement
	 */
	public ResultSet executeQuery(String sql) throws SQLException{
		PreparedStatement stmnt = m_con.prepareStatement(sql);
		return stmnt.executeQuery();
	}

	/**
	 * Requests the data about the stock from the database.
	 * @param symbol The stock that shall be requested.
	 * @return the data of the provided symbol.
	 */
	public StockResults getStockData(String symbol){
		var out = new StockResults(symbol);
		var sql = String.format("SELECT * FROM %s ORDER BY stock_datetime ASC", symbol);

		try{
			var rs = executeQuery(sql);
			while(rs.next()){
				var dataPoint = new StockDataPoint(rs.getTimestamp("stock_datetime").toLocalDateTime());
				dataPoint.Values.put(StockValueType.open, rs.getFloat(StockValueType.open.name()));
				dataPoint.Values.put(StockValueType.close, rs.getFloat(StockValueType.close.name()));
				dataPoint.Values.put(StockValueType.high, rs.getFloat(StockValueType.high.name()));
				dataPoint.Values.put(StockValueType.low, rs.getFloat(StockValueType.low.name()));
				dataPoint.Values.put(StockValueType.volume, rs.getFloat(StockValueType.volume.name()));
				dataPoint.Values.put(StockValueType.avg200, rs.getFloat(StockValueType.avg200.name()));

				out.addDataPoint(dataPoint);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return new StockResults(symbol);
		}
		return out;
	}

	/**
	 * Creates the mySQL table of the symbol, if it does not exist.
	 * @param symbol The symbol of the Stock.
	 * @throws SQLException
	 * @return either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing
	 */
	public int createTable(String symbol) throws SQLException{
		PreparedStatement stmnt = m_con.prepareStatement(String.format(
				"CREATE TABLE IF NOT EXISTS %s(" +
					"stock_datetime DATETIME NOT NULL PRIMARY KEY," +
					"%s FLOAT," +
					"%s FLOAT," +
					"%s FLOAT," +
					"%s FLOAT," +
					"%s FLOAT," +
					"%s FLOAT);",
				symbol, StockValueType.open.name(), StockValueType.close.name(), StockValueType.high.name(),
				StockValueType.low.name(), StockValueType.volume.name(), StockValueType.avg200.name()));

		return stmnt.executeUpdate();
	}

	/**
	 * Updates the database with provided StockResults.
	 * @param resultSet The data that shall be written to the database.
	 * @throws SQLException
	 */
	public void insertOrUpdateStock(StockResults resultSet) throws SQLException{
		createTable(resultSet.Symbol);

		for(var dataPoint : resultSet.getDataPoints()){
			String sql = String.format(
					"INSERT INTO %s (stock_datetime, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?) " +
					"ON DUPLICATE KEY UPDATE %s=?, %s=?, %s=?, %s=?, %s=?;",
					resultSet.Symbol, StockValueType.open.name(), StockValueType.close.name(), StockValueType.high.name(),
					StockValueType.low.name(), StockValueType.volume.name(),
					StockValueType.open.name(), StockValueType.close.name(), StockValueType.high.name(),
					StockValueType.low.name(), StockValueType.volume.name());

			PreparedStatement stmnt = m_con.prepareStatement(sql);
			stmnt.setDate(1, Date.valueOf(dataPoint.DateTime.toLocalDate()));
			stmnt.setFloat(2, dataPoint.Values.get(StockValueType.open));
			stmnt.setFloat(3, dataPoint.Values.get(StockValueType.close));
			stmnt.setFloat(4, dataPoint.Values.get(StockValueType.high));
			stmnt.setFloat(5, dataPoint.Values.get(StockValueType.low));
			stmnt.setFloat(6, dataPoint.Values.get(StockValueType.volume));

			stmnt.setFloat(7, dataPoint.Values.get(StockValueType.open));
			stmnt.setFloat(8, dataPoint.Values.get(StockValueType.close));
			stmnt.setFloat(9, dataPoint.Values.get(StockValueType.high));
			stmnt.setFloat(10, dataPoint.Values.get(StockValueType.low));
			stmnt.setFloat(11, dataPoint.Values.get(StockValueType.volume));

			var startStatement = stmnt.toString().indexOf("I",
					stmnt.toString().indexOf("Statement:")+"Statement: ".length());
			var endStatement = stmnt.toString().indexOf(";")+1;
			executeUpdate(stmnt.toString().substring(startStatement, endStatement));
		}

		updateAverage(200, resultSet);

		return;
	}

	public void updateAverage(int OverLastDays, StockResults stockResults) throws SQLException{

		var outStockResults = new StockResults(stockResults.Symbol);

		// Get the average
		for(var origDataPoint : stockResults.getDataPoints()) {
			var subSelect = String.format("SELECT AVG(%s) AS '%s' FROM %s WHERE stock_datetime > ? AND stock_datetime <= ?;",
					StockValueType.close.name(), "Average", stockResults.Symbol);
			var stmnt = m_con.prepareStatement(subSelect);
			stmnt.setDate(1, Date.valueOf(origDataPoint.DateTime.minusDays(OverLastDays).toLocalDate()));
			stmnt.setDate(2, Date.valueOf(origDataPoint.DateTime.toLocalDate()));

			// load from database
			var startStatement = stmnt.toString().indexOf("S",
					stmnt.toString().indexOf("Statement:")+"Statement: ".length());
			var endStatement = stmnt.toString().indexOf(";") + 1;
			var rs = executeQuery(stmnt.toString().substring(startStatement, endStatement));

			while (rs.next()) {
				origDataPoint.addValue(StockValueType.avg200, rs.getFloat("Average"));
				outStockResults.addDataPoint(origDataPoint.clone());
			}
		}

		// Insert the Average into table
		for(var dataPoint : outStockResults.getDataPoints()){
			var sql = String.format(
					"INSERT INTO %s (stock_datetime, avg%d) VALUES (?, ?) " +
					"ON DUPLICATE KEY UPDATE avg%d=?;",
					outStockResults.Symbol, OverLastDays, OverLastDays);

			var stmnt = m_con.prepareStatement(sql);
			stmnt.setDate(1, Date.valueOf(dataPoint.DateTime.toLocalDate()));
			stmnt.setFloat(2, dataPoint.Values.get(StockValueType.avg200));
			stmnt.setFloat(3, dataPoint.Values.get(StockValueType.avg200));



			var startStatement = stmnt.toString().indexOf("I",
					stmnt.toString().indexOf("Statement:")+"Statement: ".length());
			var endStatement = stmnt.toString().indexOf(";")+1;
			executeUpdate(stmnt.toString().substring(startStatement, endStatement));
		}
		stockResults = outStockResults.clone();
	}
}

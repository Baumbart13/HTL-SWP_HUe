package miscForEverything.database.mySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class Table extends MySQL {

	protected String mySQL_tableName = "";
	protected HashMap<String, String> mySQL_tableValues = new HashMap<String, String>();

	/**
	 * @return A copy of the tableValues-HashMap.
	 */
	public HashMap<String, String> getMySQL_tableValues(){
		return new HashMap<String, String>(this.mySQL_tableValues);
	}

	public String getMySQL_tableName(){
		return this.mySQL_tableName;
	}

	@Override
	public String toString(){
		return "Hostname:" + this.mySQL_hostname + ";Username:" + this.mySQL_user + ";Password:" + mySQL_password +
				"TableName:" + this.mySQL_tableName;
	}

	public Table(String hostname, String user, String password, String databaseName, String tableName){
		super(hostname, user, password, databaseName);
		this.mySQL_tableName = tableName;

		Connection con = null;

		try{
			con = DriverManager.getConnection("jdbc:mysql://" + hostname + "/" +
					databaseName +
					"?user=" + user +
					"&password=" + password +
					"&serverTimeZone=UTC");
			Statement statement = con.createStatement();
			String sql = IF + _QuerySpace + NOT + _QuerySpace + EXISTS + _QuerySpace + databaseName + _QueryEnd;
			statement.execute(sql);
			con.close();
		}catch(SQLException e){
			System.err.println("SQL-Exception: " + e.getMessage() + System.lineSeparator() +
					"SQL-Statement: " + e.getSQLState() + System.lineSeparator() +
					"SQL-ErrorCode: " + e.getErrorCode() + System.lineSeparator());
			e.printStackTrace();
		}
	}
}

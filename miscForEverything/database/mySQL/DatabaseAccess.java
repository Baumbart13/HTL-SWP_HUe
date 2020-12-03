package miscForEverything.database.mySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.LinkedList;
import java.util.List;

public class DatabaseAccess extends MySQL {

	public List<Table> mySQLTables = new LinkedList<Table>();

	@Override
	public String toString(){
		return "Hostname:" + this.mySQL_hostname + ";Username:" + this.mySQL_user + ";Password:" + mySQL_password +
				";DatabaseName:" + mySQL_databaseName;
	}

	/**
	 *
	 * @param hostname
	 * @param user
	 * @param password
	 * @param databaseName
	 */
	public DatabaseAccess(String hostname, String user, String password, String databaseName){
		super(hostname, user, password, databaseName);

		Connection con = null;
		Connection DEBUG_con = null;

		try{
			con = DriverManager.getConnection("jdbc:mysql://" + hostname + "/" + databaseName + "?user=" + user +
					"&password=" + password + "&serverTimezone=UTC");
			Statement statement = con.createStatement();
			String sql = CREATE + _QuerySpace + DATABASE + _QuerySpace + IF + _QuerySpace + NOT + _QuerySpace + EXISTS +
					_QuerySpace + databaseName + _QueryEnd;
			statement.execute(sql);
			con.close();
		}catch(SQLException e){
			System.err.println("SQL-Exception: " + e.getMessage() + System.lineSeparator() +
					"SQL-Statement: " + e.getSQLState() + System.lineSeparator() +
					"SQL-ErrorCode: " + e.getErrorCode() + System.lineSeparator());
			e.printStackTrace();
		}
	}

	/**
	 * Creates a new Table on this database.
	 * @param args The first argument is the name of the table. Follows this pattern: tableName -> [varName -> varType]
	 *             -> "PRIMARY" -> [primaryKey] -> "SECONDARY" -> [secondaryKeys].
	 * @return Whether the table was created successfully or not.
	 */
	public boolean createTable(String... args){

		if(args.length < 3){ return false; }

		return true;
	}

	public void DEBUG_executeStatement(String sql){
		Connection con = null;
		Connection DEBUG_con = null;

		try{
			con = DriverManager.getConnection("jdbc:mysql://" + this.mySQL_hostname + "/" +
					this.mySQL_databaseName + "?user=" + this.mySQL_user + "&password=" + this.mySQL_password +
					"&serverTimezone=UTC");
			Statement statement = con.createStatement();
			statement.execute(sql);
			con.close();
		}catch(SQLException e){
			System.err.println("SQL-Exception: " + e.getMessage() + System.lineSeparator() +
					"SQL-Statement: " + e.getSQLState() + System.lineSeparator() +
					"SQL-ErrorCode: " + e.getErrorCode() + System.lineSeparator());
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}

package miscForEverything.database.mySQL;

import java.sql.*;

import java.util.LinkedList;
import java.util.List;

public class DatabaseAccess extends MySQL {

	@Override
	public String toString(){
		return "Hostname:" + this.mySQL_hostname + ";Username:" + this.mySQL_user + ";Password:" + mySQL_password +
				";DatabaseName:" + mySQL_databaseName;
	}

	/**
	 * Instantiates a new MySQL-Database-connection.
	 * @param hostname The address of the DB-server.
	 * @param user The user, which whom the DB should be accessed.
	 * @param password The password of this user.
	 * @param databaseName The name of the DB.
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

	public void openConnection(Connection con){
		try{
			if(con == null || con.isClosed()){
				System.out.println("Connection gets opened...");

				con = DriverManager.getConnection("jdbc:mysql://" + this.mySQL_hostname + "/" +
						this.mySQL_databaseName + "?user=" + this.mySQL_user + "&password=" + this.mySQL_password +
						"&serverTimezone=UTC");
			}else{
				System.out.println("Connection is already open.");
			}
		}catch(SQLException e){
			closeConnection(con);
			System.err.println("SQL-Exception: " + e.getMessage() + System.lineSeparator() +
					"SQL-Statement: " + e.getSQLState() + System.lineSeparator() +
					"SQL-ErrorCode: " + e.getErrorCode() + System.lineSeparator());
		}
	}

	public void closeConnection(Connection con){
		try{
			if(con != null && !con.isClosed()){
				System.out.println("Connection gets closed...");

				con.close();
			}else{
				System.out.println("Connection is already closed.");
			}
		}catch(SQLException e){
			con = null;
			System.err.println("SQL-Exception: " + e.getMessage() + System.lineSeparator() +
					"SQL-Statement: " + e.getSQLState() + System.lineSeparator() +
					"SQL-ErrorCode: " + e.getErrorCode() + System.lineSeparator());
			closeConnection(con);
		}
	}

	public int executeUpdate(String sql){
		Connection con = null;

		try {
			con = DriverManager.getConnection("jdbc:mysql://" + this.mySQL_hostname + "/" +
					this.mySQL_databaseName + "?user=" + this.mySQL_user + "&password=" + this.mySQL_password +
					"&serverTimezone=UTC");
			Statement statement = con.createStatement();
			return statement.executeUpdate(sql);
		}catch(SQLException e){
			System.err.println("SQL-Exception: " + e.getMessage() + System.lineSeparator() +
					"SQL-Statement: " + e.getSQLState() + System.lineSeparator() +
					"SQL-ErrorCode: " + e.getErrorCode() + System.lineSeparator());
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				if (con != null) {
					con.close();
				}
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return 0;
	}

	public ResultSet executeQuery(String sql){
		Connection con = null;
		Connection DEBUG_con = null;

		try {
			con = DriverManager.getConnection("jdbc:mysql://" + this.mySQL_hostname + "/" +
					this.mySQL_databaseName + "?user=" + this.mySQL_user + "&password=" + this.mySQL_password +
					"&serverTimezone=UTC");
			Statement statement = con.createStatement();
			return statement.executeQuery(sql);
		}catch(SQLException e){
			System.err.println("SQL-Exception: " + e.getMessage() + System.lineSeparator() +
					"SQL-Statement: " + e.getSQLState() + System.lineSeparator() +
					"SQL-ErrorCode: " + e.getErrorCode() + System.lineSeparator());
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				if (con != null) {
					con.close();
				}
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return null;
	}

}

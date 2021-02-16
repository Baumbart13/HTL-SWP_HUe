package miscForEverything.database.mySQL;

import java.sql.*;

public class DatabaseAccess extends MySQL {

	protected Connection con = null;

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

		openConnection();
		try{

			PreparedStatement sql = con.prepareStatement("CREATE DATABASE IF NOT EXISTS ? ;");
			sql.setString(1, databaseName);
			sql.execute();

		}catch(SQLException e){
			System.err.println("SQL-Exception: " + e.getMessage() + System.lineSeparator() +
					"SQL-Statement: " + e.getSQLState() + System.lineSeparator() +
					"SQL-ErrorCode: " + e.getErrorCode() + System.lineSeparator());
			e.printStackTrace();
		}finally{
			closeConnection();
		}
	}

	public DatabaseAccess(DatabaseAccess dbAccess){
		super(dbAccess.mySQL_hostname, dbAccess.mySQL_user, dbAccess.mySQL_password, dbAccess.mySQL_databaseName);
		this.con = dbAccess.con;
	}

	public void openConnection(){
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
			closeConnection();
			System.err.println("SQL-Exception: " + e.getMessage() + System.lineSeparator() +
					"SQL-Statement: " + e.getSQLState() + System.lineSeparator() +
					"SQL-ErrorCode: " + e.getErrorCode() + System.lineSeparator());
		}
	}

	public void closeConnection(){
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
			closeConnection();
		}
	}

}

package miscForEverything.database.mySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public abstract class Table implements MySQLKeywords{

	protected String mySQL_database = "";
	protected String mySQL_tableName = "";

	public String getMySQL_tableName(){
		return this.mySQL_tableName;
	}

	@Override
	public String toString(){
		return "Database: " + this.mySQL_database + "TableName:" + this.mySQL_tableName;
	}

	public Table(String databaseName, String tableName){
		this.mySQL_tableName = tableName;
	}
	
	public abstract String mySqlCreateTable(String database);
}

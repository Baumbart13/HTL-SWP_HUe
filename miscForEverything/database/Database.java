package miscForEverything.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Database {
	protected Connection m_con = null;
	protected String m_hostname = "";
	protected String m_user = "";
	protected String m_password = "";
	protected String m_database = "";

	public Database(String hostname, String user, String password, String database){
		this.m_hostname = hostname;
		this.m_user = user;
		this.m_password = password;
		this.m_database = database;
	}

	public abstract void connect() throws Exception;
	public abstract void disconnect() throws Exception;
	public abstract void createDatabase(String database) throws Exception;

	@Override
	public String toString(){
		return String.format("Hostname:%s;User:%s;Password:%s;Database:%s",
				m_hostname, m_user, m_password, m_database);
	}

	@Override
	public abstract Database clone();

	public String getHostname() {
		return m_hostname;
	}

	public String getUser() {
		return m_user;
	}

	public String getDatabase() {
		return m_database;
	}
}

package miscForEverything.database.mySQL;

public abstract class MySQL implements MySQLKeywords{
	
	/**
	 * 
	 * @param hostname
	 * @param user
	 * @param password
	 * @param databaseName
	 */
	public MySQL(String hostname, String user, String password, String databaseName){
		this.mySQL_hostname = hostname;
		this.mySQL_user = user;
		this.mySQL_password = password;
		this.mySQL_databaseName = databaseName;
		
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e){
			e.printStackTrace();
			System.err.println("Please install JDBC");
			System.exit(-1);
		}
	}
	
	protected String mySQL_hostname		= null;
	protected String mySQL_user			= null;
	protected String mySQL_password		= null;
	protected String mySQL_databaseName	= null;
	
	public String getMySQL_hostname(){
		return this.mySQL_hostname;
	}
	public String getMySQL_user(){
		return this.mySQL_user;
	}
	public String getMySQL_password(){
		return this.mySQL_password;
	}
	public String getMySQL_databaseName(){
		return this.mySQL_databaseName;
	}

	/**
	 * The following is the base of this implementation:
	 *
	 * 	public String toString(){
	 * 		return "Hostname:" + this.mySQL_hostname + ";Username:" + this.mySQL_user + ";Password:" + mySQL_password;
	 * 	}
	 */
	public abstract String toString();
}
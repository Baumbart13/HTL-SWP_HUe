package miscForEverything.database.mySQL;

public interface MySQLKeywords{
	public static final String _QuerySpace = " ";
	public static final String _QueryEnd = ";";
	public static final String CREATE = "CREATE";
	public static final String TABLE = "TABLE";
	public static final String USE = "USE";
	public static final String DATABASE = "DATABASE";
	public static final String WHERE = "WHERE";
	public static final String FROM = "FROM";
	public static final String JOIN = "JOIN";
	public static final String INNER = "INNER";
	public static final String LEFT = "LEFT";
	public static final String RIGHT = "RIGHT";
	public static final String AS = "AS";
	public static final String UNION = "UNION";
	public static final String SELECT = "SELECT";
	public static final String IF = "IF";
	public static final String NOT = "NOT";
	public static final String EXISTS = "EXISTS";
	public static final String ADD = "ADD";
	public static final String ADD_CONSTRAINT = "ADD CONSTRAINT";
	public static final String ALTER = "ALTER";
	public static final String COLUMN = "COLUMN";
	public static final String AND = "AND";
	public static final String ASC = "ASC";
	public static final String DESC = "DESC";
	public static final String HAVING = "HAVING";
	public static final String IN = "IN";
	public static final String NULL = "NULL";
	public static final String ORDER = "ORDER";
	public static final String GROUP = "GROUP";
	public static final String AUTO_INCREMENT = "AUTO_INCREMENT";

	public static final String INTEGER = "INTEGER";
	public static final String BOOL = "BOOL";
	public static final String BIGINT = "BIGINT";
	public static final String FLOAT = "FLOAT";
	public static final String DOUBLE = "DOUBLE";
	public static final String DECIMAL = "DECIMAL";
	public static final String DATE = "DATE";
	public static final String DATETIME = "DATETIME";
	public static final String TIMESTAMP = "TIMESTAMP";

	public static String CHAR(int length) {
		return "CHAR(" + Math.abs(length) + ")";
	}

	public static String VARCHAR(int length) {
		return "VARCHAR(" + Math.abs(length) + ")";
	}

	public static String PRIMARY_KEY(String... args) {
		StringBuilder output = new StringBuilder("PRIMARY KEY(");

		try {
			for (int i = 0; i < args.length - 1; ++i) {
				output.append(args[i] + ", ");
			}
			output.append(args[args.length - 1] + ")");
		} catch (NullPointerException e) {
			e.printStackTrace();
			return "";
		}

		return output.toString();
	}

	public static String SECONDARY_KEY(String... args) {
		StringBuilder output = new StringBuilder("SECONDARY KEY(");

		try {
			for (int i = 0; i < args.length - 1; ++i) {
				output.append(args[i] + ", ");
			}
			output.append(args[args.length - 1] + ")");
		} catch (NullPointerException e) {
			e.printStackTrace();
			return "";
		}
		return output.toString();
	}
}
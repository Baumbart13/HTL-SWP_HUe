package miscForEverything.database.mySQL;

import java.time.LocalDateTime;

public interface Convert {
	public static String javaToMysql_LocalDateTime(LocalDateTime dateTime){
		return "\'" + dateTime.toString().replace('T', ' ') + "\'";
	}

	public static LocalDateTime mysqlToJava_LocalDateTime(String dateTime){
		return LocalDateTime.parse(dateTime.replace(' ', 'T'));
	}
}

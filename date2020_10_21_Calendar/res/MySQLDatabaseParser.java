package date2020_10_21_Calendar.res;

import java.sql.*;

public class MySQLDatabaseParser {

    private final String username = "root";
    private final String password = "Hubertus7362";
    private final String port = "3306";
    private final String hostname = "localhost";
    private final String databaseName = "holidayDates";

    public void foo() {
        try (
                // Step 1: Allocate a database 'Connection' object
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://" + hostname + ":" + port + "/" + databaseName + "?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                        username, password);   // For MySQL only
                // The format is: "jdbc:mysql://hostname:port/databaseName", "username", "password"

                // Step 2: Allocate a 'Statement' object in the Connection
                Statement stmt = conn.createStatement();
        ) {
            String command = "create database if not exists holiday";
            // Step 3: Execute a SQL SELECT query. The query result is returned in a 'ResultSet' object.
            String strSelect = "select title, price, qty from books";
            System.out.println("The SQL statement is: " + command + "\n"); // Echo For debugging

            ResultSet rset = stmt.executeQuery(command);

            // Step 4: Process the ResultSet by scrolling the cursor forward via next().
            //  For each row, retrieve the contents of the cells with getXxx(columnName).
            /*System.out.println("The records selected are:");
            int rowCount = 0;
            while(rset.next()) {   // Move the cursor to the next row, return false if no more row
                String title = rset.getString("title");
                double price = rset.getDouble("price");
                int    qty   = rset.getInt("qty");
                System.out.println(title + ", " + price + ", " + qty);
                ++rowCount;
            }
            System.out.println("Total number of records = " + rowCount);*/


        } catch(SQLException ex) {
            ex.printStackTrace();
        }  // Step 5: Close conn and stmt - Done automatically by try-with-resources (JDK 7)
    }
}

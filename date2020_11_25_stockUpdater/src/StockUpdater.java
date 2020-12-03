package date2020_11_25_stockUpdater.src;

import miscForEverything.UI;

// API-Key = VBAX1XGSP5QC85SL
public class StockUpdater {
    private static BaumbartStock stocks;

	public static final String database_hostname = "localhost";
	public static final String database_user = "root";
	public static final String database_password = "DuArschloch4";
	public static final String database_databaseName = "BaumbartStocks";

    public static void main(String[] args) {

        if(args.length > 1){
            if(args[0].equalsIgnoreCase("update")){
                stocks = new BaumbartStock(database_hostname, database_user, database_password, database_databaseName);

                String APIKey = "";
                if(args[1].equalsIgnoreCase("VBAX1XGSP5QC85SL") ||
					args[1].equalsIgnoreCase("demo")){
                	APIKey = "VBAX1XGSP5QC85SL";
				}else {
					System.out.println("Please enter your APIKey provided by alphavantage.com: ");
					APIKey = UI.readString();
				}

				/*
				args[0] == update
				args[1] == stocks-symbol
				 */
                stocks.updateStock(args[1], APIKey);
				//stocks.DEBUG();
            }
        }else if(args.length > 0){
            switch(args[0].toLowerCase()){
                case "install":
                    //BaumbartStock.installUpdater(); // TODO implement BaumbartStock.installUpdater()
                    System.exit(0);
                case "uninstall":
                    //BaumbartStock.uninstallUpdater(); // TODO implement BaumbartStock.uninstallUpdater()
                    System.exit(0);
            }
        }


    }
}

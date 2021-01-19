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
        	/*
        	autoupdate [STOCKSYMBOL] [APIKEY]
        	 */
			if(args[0].equalsIgnoreCase("autoupdate")) {
				stocks = new BaumbartStock(database_hostname, database_user, database_password, database_databaseName);

				if(args[1].equalsIgnoreCase("debug")){
					stocks.DEBUG();
					System.out.println("DEBUG-Command close successful");
					System.exit(0);
				}

				if(args.length != 3){
					System.exit(-1);
				}


				System.out.println("Automatic update");
				stocks.updateStock(args[1], args[2]);
				System.out.println("Exiting program...");
				System.exit(0);

			}else if(args[0].equalsIgnoreCase("update")){
                stocks = new BaumbartStock(database_hostname, database_user, database_password, database_databaseName);

                String APIKey = "";
                System.out.println("Please enter your APIKey provided by alphavantage.com: ");
                APIKey = UI.readString();

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

package date2020_11_25_sharesUpdater;

// API-Key = VBAX1XGSP5QC85SL
public class StockUpdater {
    public static void main(String[] args) {
        if(args.length > 0){
            for(String arg : args){

                switch(arg.toLowerCase()){
                    case "install":
                        BaumbartStock.installUpdater();
                    case "update":
                        BaumbartStock.updateStock();
                        System.exit(0);
                    case "uninstall":
                        System.exit(0);
                }

            }
        }
    }
}

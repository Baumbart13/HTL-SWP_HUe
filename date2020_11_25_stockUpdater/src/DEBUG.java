package date2020_11_25_stockUpdater.src;

import date2020_11_25_stockUpdater.src.api.ApiParser;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public interface DEBUG {

	/**
	 * This method calls the API and
	 */
	public static void loadAllSymbolsAndSaveAsCsv(boolean inProduction){
		String apiPath = "";
		String listingStatusPath = "";
		String symbolsPath = "";
		if(inProduction){
			apiPath = String.format("src%smain%sjava%sdate2020_11_25_stockUpdater%sres%sapi.csv",
					File.separator, File.separator, File.separator, File.separator, File.separator);
			listingStatusPath = String.format("src%smain%sjava%sdate2020_11_25_stockUpdater%sres%slisting_status.csv",
					File.separator, File.separator, File.separator, File.separator, File.separator);
			symbolsPath = String.format("src%smain%sjava%sdate2020_11_25_stockUpdater%sres%ssymbols.csv",
					File.separator, File.separator, File.separator, File.separator, File.separator);
		}else{
			apiPath = String.format("res%sapi.csv", File.separator);
			listingStatusPath = String.format("res%slisting_status.csv", File.separator);
			symbolsPath = String.format("res%ssymbols.csv", File.separator);
		}

		// Load listing_status.csv from API
		try {
			var file = new File(apiPath);
			if(!file.exists()){
				file.mkdirs();
				file.createNewFile();
			}
			var parser = new ApiParser(new File(apiPath));
			System.out.printf("Started Receiving symbols%n");

			// TODO: implement to load all symbols
			var symbols = parser.requestAll("", ApiParser.Function.LISTING_STATUS);

			//var writer = new BufferedWriter(new FileWriter(listingStatusPath));

		}catch(IOException e){
			e.printStackTrace();
		}

		// Copy symbol and name to symbols.csv
		//new Thread(()-> {
		List<StringBuilder> sbList = new LinkedList<StringBuilder>();
		try {
			var reader = new BufferedReader(new FileReader(listingStatusPath));

			System.out.printf("Started Reading%n");

			long counter = 0;
			var line = reader.readLine();
			while (line != null) {
				if(++counter == Long.MAX_VALUE){
					counter = 0;
				}

				if(counter % 100 == 0){
				System.out.println("Reading line " + counter);
				}

				if(!line.contains("ETF")) {

					var parts = line.split(",");

					if (parts[1] == null || parts[1].equals("")) {
						parts[1] = parts[0];
					}

					sbList.add(new StringBuilder(String.format("%s,%s", parts[0], parts[1])));
				}
				line = reader.readLine();
			}
			reader.close();

			System.out.printf("Reading finished...%nStarted Writing%n");

			var writer = new BufferedWriter(new FileWriter(symbolsPath));
			for (var sb : sbList) {
				writer.write(sb.toString());
				writer.newLine();
			}
			writer.close();

			System.out.printf("Writing finished...%n");

		} catch (IOException e) {
			e.printStackTrace();

		}
		System.exit(0);
		//}).start();
	}
}

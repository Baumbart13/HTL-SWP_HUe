package date2020_10_21_Calendar.res;

import date2020_10_21_Calendar.res.country.Germany;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.net.URL;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.json.JSONException;


public class FeiertagAPIParser{

	private final String dataReceiving = "Receiving data.";
	private final String dataReceived = "Data received!";

	public String URL(Integer year){
		if(year > LocalDate.MAX.getYear() ||
			year < LocalDate.MIN.getYear()){
			return "";
		}
		return "https://feiertage-api.de/api/?jahr=" + year.toString();
	}
	public final char separateSymbol = '&';
	public final String URLExtra_nurDaten = (separateSymbol + "nur_daten=1");
	public String URLExtra_callback(String name){
		return (separateSymbol + "callback=" + name);
	}
	public String URLExtra_nurLand(String federalState){
		return (separateSymbol + "nur_land=" + federalState);
	}

	private String DEBUG_Request = URL(2020) + URLExtra_callback("DEBUG_CALLBACK");

	public LinkedList<LocalDate> getDates(int startYear, int endYear) throws MalformedURLException, IOException {

		LinkedList<LocalDate> holidays = new LinkedList<LocalDate>();

		if(startYear < LocalDate.MIN.getYear() ||
				endYear < startYear ||
				LocalDate.MAX.getYear() < endYear){
			return holidays;
		}

		System.out.println(dataReceiving);
		JSONObject json = new JSONObject();
		// Get holiday-json for every year
		for(int year = endYear; year > startYear; --year){

			json = new JSONObject(IOUtils.toString(new URL(URL(year) + URLExtra_nurDaten), Charset.forName("UTF-8")));

			// For every key, get the holiday
			for(String day : json.keySet()){
				LocalDate date = LocalDate.parse(json.get(day).toString());

				if(!holidays.contains(date)) {
					holidays.add(date);
				}
			}
		}
		System.out.println(dataReceived);

		return holidays;
	}

	public LinkedList<LocalDate> getDates(int startYear, int endYear, String federalState) throws JSONException, MalformedURLException, IOException{

		LinkedList<LocalDate> holidays = new LinkedList<LocalDate>();

		if(startYear < LocalDate.MIN.getYear() ||
			endYear < startYear ||
			LocalDate.MAX.getYear() < endYear){
			return holidays;
		}

		System.out.println(dataReceiving);
		JSONObject json = new JSONObject();
		// Get holiday-json for every year
		for(int year = endYear; year > startYear; --year){
			json = new JSONObject(IOUtils.toString(new URL(URL(year) + URLExtra_nurLand(federalState)), Charset.forName("UTF-8")));

			// For every key, get the holiday
			for(String day : json.keySet()){
				holidays.add(LocalDate.parse(json.get(day).toString()));
			}
		}
		System.out.println(dataReceived);

		return holidays;
	}

	/**
	 * Receives the json form the api and converts it into a own pseudo-json. The List is used for every year. It
	 * contains the Level-1 Keys for the federal states of Germany. The Level-1 maps contain the Level-2 Keys for the
	 * names of the holidays. The Level-2 maps contain the last Level-3 Keys for the date and tip of the corresponding
	 * holiday. Returned as an Object, to suit for a String and a LocalDate.
	 * @param startYear The first year, from which on the data shall be collected. Must be less than endYear and
	 *                  LocalDate.MAX and more than LocalDate.MIN otherwise, it returns an empty Object;
	 * @param endYear Until this year, all holidays shall be collected. Must be more than startYear and LocalDate.MIN
	 *                and less than LocalDate.MAX otherwise, it returns an empty Object.
	 * @return A LinkedList containing all data received from the feiertage-api. See the description for more
	 * information.
	 */
	public LinkedList<HashMap<String, HashMap<String, HashMap<String, Object>>>> getAllData(int startYear, int endYear)
			throws MalformedURLException, IOException{
		/*
		Year(federalState(holidayName(date/tip(dateVal/tipVal))))
		 */
		LinkedList<HashMap<String, HashMap<String, HashMap<String, Object>>>> out =
				new LinkedList<HashMap<String, HashMap<String, HashMap<String, Object>>>>();

		if(startYear < LocalDate.MIN.getYear() || endYear < startYear || LocalDate.MAX.getYear() < endYear){
			return out;
		}

		final char quotationMark = '\"';

		System.out.println(dataReceiving);
		JSONObject json = new JSONObject();
		for(int year = startYear; year < endYear; ++year){

			// get json
			json = new JSONObject(IOUtils.toString(new URL(URL(year)), Charset.forName("UTF-8")));
			//add this year
			out.add(new HashMap<String, HashMap<String, HashMap<String, Object>>>());

			// For every key/federalState, get the data
			for(String key : Germany.getAll()){

				String dataString = json.get(key).toString();

				// For every entry of a holiday
				while(dataString.length() > 2) {
					//- - - - - -

					// Add current federalstate
					out.get(out.size() - 1).put(key, new HashMap<String, HashMap<String, Object>>());

					//- - - - - -

					// receive holidayname
					int indexStart = dataString.indexOf(quotationMark) + 1;
					int indexEnd = dataString.indexOf(quotationMark, indexStart);
					String holidayname = dataString.substring(indexStart, indexEnd);
					// insert currentStatement into output
					out.get(out.size() - 1).get(key).put(holidayname, new HashMap<String, Object>());

					//- - - - - -

					// receive date
					// First get the key of date => datum
					indexStart = dataString.indexOf(quotationMark, indexEnd + 2) + 1;
					indexEnd = dataString.indexOf(quotationMark, indexStart);
					String currentKey = dataString.substring(indexStart, indexEnd);
					// Then get the actual date of this holiday as a string
					indexStart = dataString.indexOf(quotationMark, indexEnd + 1) + 1;
					indexEnd = dataString.indexOf(quotationMark, indexStart);
					String currentValue = dataString.substring(indexStart, indexEnd);
					// insert date
					LocalDate tempDate = LocalDate.of(
							Integer.parseInt(currentValue.substring(0, 4)),    // year
							Integer.parseInt(currentValue.substring(5, 7)),    // month
							Integer.parseInt(currentValue.substring(8)));    // day
					out.get(out.size() - 1).get(key).get(holidayname).put(currentKey, currentValue);

					//- - - - - -

					// receive tip
					// First get the key of tip => hinweis
					indexStart = dataString.indexOf(quotationMark, indexEnd + 1) + 1;
					indexEnd = dataString.indexOf(quotationMark, indexStart);
					currentKey = dataString.substring(indexStart, indexEnd);
					// THen get the actual tip of this holiday as a string
					indexStart = dataString.indexOf(quotationMark, indexEnd + 1) + 1;
					indexEnd = dataString.indexOf(quotationMark, indexStart);
					currentValue = dataString.substring(indexStart, indexEnd);
					// insert tip
					out.get(out.size() - 1).get(key).get(holidayname).put(currentKey, currentValue);

					//- - - - - -

					// trim to size for termination
					dataString = dataString.substring(dataString.indexOf('}', indexEnd));
				}
			}
		}
		System.gc();
		System.out.println(dataReceived);

		return out;
	}

	public LinkedList<LocalDate> toNonRedundant(LinkedList<LocalDate> in){
		LinkedList<LocalDate> out = new LinkedList<>();

		for(LocalDate date : in){
			if(!out.contains(date)){
				out.add(date);
			}
		}

		return out;
	}

	/**
	 * Separates complete parsed JSON from getAllData into only a LinkedList containing
	 * all dates without any doubles.
	 * @param json The complete collected json as received from getAllData.
	 * @return The LinkedList containing all holiday-dates without any doubles.
	 */
	public LinkedList<LocalDate> separateCompleteJSON(LinkedList<HashMap<String, HashMap<String, HashMap<String, Object>>>> json){
		LinkedList<LocalDate> out = new LinkedList<LocalDate>();

		for(int year = 0; year < json.size(); ++year){
			for(String federalState : json.get(year).keySet()){
				for(String holidayName : json.get(year).get(federalState).keySet()){

					String temp = json.get(year).get(federalState).get(holidayName).get("datum").toString();
					LocalDate date = LocalDate.of(
							Integer.parseInt(temp.substring(0, 4)),    // year
							Integer.parseInt(temp.substring(5, 7)),    // month
							Integer.parseInt(temp.substring(8)));    // day

					if(!out.contains(date)){
						out.add(date);
					}
				}
			}
		}

		return out;
	}

	public void DEBUG_compareBothGets(){
		try {
			int startYear = LocalDate.now().getYear();
			int endYear = LocalDate.now().plusYears(1).getYear();

			LinkedList<HashMap<String, HashMap<String, HashMap<String, Object>>>> allData = this.getAllData(startYear, endYear);
			LinkedList<LocalDate> notAllData = this.getDates(startYear, endYear);

			LinkedList<LocalDate> tempAllData = new LinkedList<LocalDate>();

			for(int year = 0; year < allData.size(); ++year) {
				for (String state : allData.get(year).keySet()) {
					for (String holidayName : allData.get(year).get(state).keySet()){

						LocalDate date = LocalDate.parse(allData.get(year).get(state).get(holidayName).get("datum").toString());

						if(!tempAllData.contains(date)){
							tempAllData.add(date);
						}
					}
				}
			}

			System.err.println("AllData Size and Keys: " + tempAllData.size());
			for(int i = 0; i < notAllData.size(); ++i){
				if(tempAllData.contains(notAllData.get(i))){
					System.err.println("Has same");
				}else{
					System.err.println("Has not same");
				}
			}

			System.err.println(System.lineSeparator() + "NotAllData Size: " + notAllData.size());




		}catch(MalformedURLException e){
			System.err.println("MalformedURLException");
		}catch(IOException e){
			System.err.println("IOException");
		}catch(Exception e){
			System.err.println("Something weird happened");
			e.printStackTrace();
		}

	}

	/**
	 * Calls getAllData and prints all Information of the parsed JSON.
	 */
	public void DEBUG_printParsedJSON(){
		try{

			LinkedList<HashMap<String, HashMap<String, HashMap<String, Object>>>> testbar = this.getAllData(LocalDate.now().getYear(), LocalDate.now().plusYears(2).getYear());

			for(int i = 0; i < testbar.size(); ++i){
				for(String L1key : testbar.get(i).keySet()){
					System.err.println("Level-1 Key (Should be federalState): " + L1key);
					System.err.println("Level-1 Value example (toString): " + testbar.get(i).get(L1key).toString());

					for(String L2key : testbar.get(i).get(L1key).keySet()){
						System.err.println("\tLevel-2 Key (Should be holidayname): " + L2key);
						System.err.println("\tLevel-2 Value example (toString): " + testbar.get(i).get(L1key).get(L2key).toString());

						for(String L3key : testbar.get(i).get(L1key).get(L2key).keySet()){
							System.err.println("\t\tLevel-3 Key (should be datum or hinweis): " + L3key);
							System.err.println("\t\tLevel-3 Value example (toString): " + testbar.get(i).get(L1key).get(L2key).get(L3key).toString());
						}
					}
				}
			}
		}catch(IndexOutOfBoundsException e){
			System.err.println("Index out of bounds");
			e.printStackTrace();
		}catch(Exception e){
			System.err.println("Something weird, unexpected happened");
			e.printStackTrace();
		}
	}
}
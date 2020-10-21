package date2020_10_21_Calendar.res;

import date2020_10_21_Calendar.res.country.Germany;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.LinkedList;
import java.net.URL;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.json.JSONException;


public class FeiertagAPIParser{
	private String URL(Integer year){
		if(year > LocalDate.MAX.getYear() ||
			year < LocalDate.MIN.getYear()){
			return "";
		}
		return "https://feiertage-api.de/api/?jahr=" + year.toString();
	}
	private final char separateSymbol = '&';
	public final String URLExtra_nurDaten = (separateSymbol + "nur_daten=1");
	public String URLExtra_callback(String name){
		return (separateSymbol + "callback=" + name);
	}
	public String URLExtra_nurLand(String federalState){
		return (separateSymbol + "nur_land=" + federalState);
	}

	private String DEBUG_Request = URL(2020) + URLExtra_nurLand(Germany.Bayern.toString()) + URLExtra_callback("DEBUG_CALLBACK");

	public LinkedList<LocalDate> getDates(int startYear, int endYear) throws MalformedURLException, IOException {

		LinkedList<LocalDate> holidays = new LinkedList<LocalDate>();

		if(startYear < LocalDate.MIN.getYear() ||
				endYear < startYear ||
				LocalDate.MAX.getYear() < endYear){
			return holidays;
		}

		JSONObject json = new JSONObject();
		// Get holiday-json for every year
		for(int year = endYear; year > startYear; --year){

			json = new JSONObject(IOUtils.toString(new URL(URL(year) + URLExtra_nurDaten), Charset.forName("UTF-8")));

			// For every key, get the holiday
			for(String day : json.keySet()){
				holidays.add(LocalDate.parse(json.get(day).toString()));

			}
		}

		return holidays;
	}

	public LinkedList<LocalDate> getDates(int startYear, int endYear, String federalState) throws JSONException, MalformedURLException, IOException{

		LinkedList<LocalDate> holidays = new LinkedList<LocalDate>();

		if(startYear < LocalDate.MIN.getYear() ||
			endYear < startYear ||
			LocalDate.MAX.getYear() < endYear){
			return holidays;
		}

		JSONObject json = new JSONObject();
		// Get holiday-json for every year
		for(int year = endYear; year > startYear; --year){
			json = new JSONObject(IOUtils.toString(new URL(URL(year) + URLExtra_nurLand(federalState)), Charset.forName("UTF-8")));

			// For every key, get the holiday
			for(String day : json.keySet()){
				holidays.add(LocalDate.parse(json.get(day).toString()));
			}
		}

		return holidays;
	}
}
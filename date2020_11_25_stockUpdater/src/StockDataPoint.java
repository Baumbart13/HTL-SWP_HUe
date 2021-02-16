package date2020_11_25_stockUpdater.src;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class StockDataPoint {
	public LocalDateTime DateTime;
	public HashMap<StockValueType, Float> Values;

	@Override
	public StockDataPoint clone(){
		return new StockDataPoint(this);
	}

	public StockDataPoint(StockDataPoint other){
		this(other.DateTime);
		this.Values = new HashMap<>();
		for(var key : other.Values.keySet()){
			this.Values.put(key, other.Values.get(key));
		}
	}

	public StockDataPoint(LocalDateTime DateTime){
		this.DateTime = DateTime;
		this.Values = new HashMap<StockValueType, Float>();
	}

	public void addValue(StockValueType type, float value){
		Values.put(type, value);
	}
}

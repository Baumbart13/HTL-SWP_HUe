package date2020_11_25_stockUpdater.src;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class StockResults {
	private HashMap<StockValueType, Float> m_LowerBounds;
	private HashMap<StockValueType, Float> m_UpperBounds;
	private List<StockDataPoint> m_DataPoints;

	/**
	 * The short form of the stock.
	 */
	public String Symbol;
	/**
	 * The full name of the stock.
	 */
	public String Name;

	public StockResults clone(){
		return new StockResults(this);
	}

	public StockResults(StockResults results){
		this(results.Symbol, results.Name);
		this.m_LowerBounds = results.m_LowerBounds;
		this.m_UpperBounds= results.m_UpperBounds;
		this.m_DataPoints = List.copyOf(results.m_DataPoints);
	}

	public StockResults(String symbol, String name){
		this(symbol);
		this.Name = name;
	}

	public StockResults(String symbol){
		this.Symbol = symbol;
		this.Name = "";
		this.m_LowerBounds = new HashMap<StockValueType, Float>();
		this.m_UpperBounds = new HashMap<StockValueType, Float>();
		this.m_DataPoints = new LinkedList<StockDataPoint>();

		for(var type : StockValueType.values()){
			m_LowerBounds.put(type, Float.MAX_VALUE);
			m_UpperBounds.put(type, -Float.MAX_VALUE);
		}
	}

	public void addDataPoint(StockDataPoint Point){
		m_DataPoints.add(Point);
		for(var type : StockValueType.values()){

			if(type.equals(StockValueType.avg200) && Point.Values.get(type) == null){
				continue;
			}
			if (Point.Values.get(type) < m_LowerBounds.get(type)) {
				m_LowerBounds.put(type, Point.Values.get(type));
			}
			if (Point.Values.get(type) > m_UpperBounds.get(type)) {
				m_UpperBounds.put(type, Point.Values.get(type));
			}
		}
	}

	public float getLowerBound(StockValueType type){
		return m_LowerBounds.get(type);
	}

	public float getUpperBound(StockValueType type){
		return m_UpperBounds.get(type);
	}

	public float getRange(StockValueType type){
		return m_UpperBounds.get(type) - m_LowerBounds.get(type);
	}

	public List<StockDataPoint> getDataPoints(){
		return m_DataPoints;
	}
}

package date2020_11_25_stockUpdater.src.StockAPI;

import java.util.Collections;
import java.util.Map;

public interface StockAPITable_TableColumns {
	Map<String, String> TableColumns = Collections.unmodifiableMap(Map.of(
			"Datetime", "stock_datetime",
			"Open Value", "open",
			"High Value", "high",
			"Low Value", "low",
			"Close Value", "close",
			"Volume", "volume"
	));
}

package date2020_11_25_stockUpdater.src;

public enum StockValues {
	open,
	high,
	low,
	close,
	volume;

	public static StockValues of(String s){
		if(s.equals(open.toString())){
			return open;
		}
		if(s.equals(high.toString())){
			return high;
		}
		if(s.equals(low.toString())){
			return low;
		}
		if(s.equals(close.toString())){
			return close;
		}
		if(s.equals(volume.toString())){
			return volume;
		}
		return null;
	}

	@Override
	public String toString(){
		return this.ordinal()+1 + ". " + this.name().toLowerCase();
	}
}

package date2020_11_25_stockUpdater.src;

public enum ProgramArguments {
	DEBUG,
	windowed;

	@Override
	public String toString(){
		return String.format("--%s", this.name());
	}
}

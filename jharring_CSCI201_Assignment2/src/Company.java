public class Company {
	private String name;
	private String ticker;
	private String startDate;
	private int stockBrokers;
	private String description;
	private String exchangeCode;
	
	Company(String name, String ticker, String startDate, int stockBrokers, 
			String description, String exchangeCode)  {
		this.name = name;
		this.ticker = ticker;
		this.startDate = startDate;
		this.stockBrokers = stockBrokers;
		this.description = description;
		this.exchangeCode = exchangeCode;
	}
	public String get_Name() {
		return this.name;
	}
	public String get_ticker() {
		return this.ticker;
	}
	public String get_start_date() {
		return this.startDate;
	}
	public int get_stockBrokers() {
		return this.stockBrokers;
	}
	public String get_description() {
		return this.description;
	}
	public String get_exchangeCode() {
		return this.exchangeCode;
	}
}
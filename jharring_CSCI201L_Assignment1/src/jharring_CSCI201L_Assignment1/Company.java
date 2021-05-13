package jharring_CSCI201L_Assignment1;



public class Company {
	
	public String name;
	public String ticker;
	public String startDate;
	public String description;
	public String exchangeCode;
	
	Company(){}
	
	Company(String name, String ticker, String startDate, 
			String description, String exchangeCode)  {
		this.name = name;
		this.ticker = ticker;
		this.startDate = startDate;
		this.description = description;
		this.exchangeCode = exchangeCode; 
	}
	
	
	
	public String getName() {
		return this.name;
	}
}
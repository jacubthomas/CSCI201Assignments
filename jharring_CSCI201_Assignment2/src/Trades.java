public class Trades{
	private int second;
	private String exchange;
	private int count;
	
	public Trades(int second, String exchange, int count) {
		this.second = second;
		this.exchange = exchange;
		this.count = count;
	}
	public int getSeconds() {
		return second;
	}
	public int getCount() {
		return count;
	}
	public String getExchange() {
		return exchange;
	}
}

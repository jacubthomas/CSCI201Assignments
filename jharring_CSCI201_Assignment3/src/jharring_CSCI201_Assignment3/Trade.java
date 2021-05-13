package jharring_CSCI201_Assignment3;

import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.concurrent.locks.ReentrantLock;

public class Trade extends Thread{
	private int second;
	private String exchange;
	private int count;
	private String date;
	private double price;
	private double calculation;
	private Trader trader;
	private String transaction;
	public PrintWriter pw;
	private boolean assigned = false;
	// prevents multiple trades from occurring simultaneously from the same trader
	public ReentrantLock lock = new ReentrantLock();
	
	public Trade(int second, String exchange, int count, String date) {
		this.second = second;
		this.exchange = exchange;
		if(count < 0) transaction = "sale";
		else transaction = "purchase";
		this.count = Math.abs(count);
		this.date = date;
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
	public String getDate() {
		return date;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
		calculation = count * price;
	}
	public Trader getTrader() {
		return trader;
	}
	public void setTrader(Trader trader) {
		this.trader = trader;
	}
	public void setPrintWriter(PrintWriter pw) {
		this.pw = pw;
	}
	public boolean isSale() {
		if(transaction.equals("sale"))
			return true;
		return false;
	}
	public String getTransaction() {
		return transaction;
	}
	public boolean getAssigned() {
		return assigned;
	}
	public void setAssigned() {
		assigned = true;
	}
	public void run() {
		try {
			trader.getLock();
			// preserves pretty print
			lock.lock();
			Stopwatch.timeElapsed(pw);
			if(isSale()) {
				pw.print("Starting " + transaction + " of " + count +
						" stocks of " + exchange + ". Total gain: $"+ 
						price + "*" + count + " = " +  calculation + ".\n");
				pw.flush();
				double profit = trader.getProfit();
				double update = profit + calculation;
				DecimalFormat df = new DecimalFormat("#.##");
				df.format(update); 
				lock.unlock();
				Thread.sleep(1000);
				lock.lock();
				Stopwatch.timeElapsed(pw);
				pw.print("Finished " + transaction + " of " + count +
						" stocks of " + exchange + ". Profit earned till now: $" +
						profit  + " + " + calculation + " = " + update +
						".\n");
				pw.flush();
				trader.updateProfit(calculation);
			} else {
				pw.print("Starting " + transaction + " of " + count +
						" stocks of " + exchange + ". Total cost: $" + price +
						"*" + count + " = $" + calculation + ".\n");
				pw.flush();
				double balance = trader.getBalance();
				double update = balance - calculation;
				DecimalFormat df = new DecimalFormat("#.##");
				df.format(update); 
				lock.unlock();
				Thread.sleep(1000);
				lock.lock();
				Stopwatch.timeElapsed(pw);
				pw.print("Finished " + transaction + " of " + count +
						" stocks of " + exchange + ". Remaining balance: $" +
						balance + "-$" + calculation + " = $" + update +
						".\n");
				pw.flush();
				trader.updateBalance(calculation);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
			trader.releaseLock();			
		}
	}
}

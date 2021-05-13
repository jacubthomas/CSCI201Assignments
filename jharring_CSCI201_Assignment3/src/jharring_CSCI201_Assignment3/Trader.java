package jharring_CSCI201_Assignment3;

import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;



public class Trader {
	private int ID;
	private double balance;
	private double profit;
	private ReentrantLock lock = new ReentrantLock();
	private List<Trade> assigned = Collections.synchronizedList(new ArrayList<Trade>());
	private PrintWriter pw;
	
	public Trader(int ID, double balance) {
		this.ID = ID;
		this.balance = balance;
		profit = 0.0;
	}
	public int getID() {
		return ID;
	}
	public double getBalance() {
		return balance;
	}
	public void updateBalance(double p) {
		balance -= p;
		DecimalFormat df = new DecimalFormat("#.##");
		df.format(balance); 
	}
	public double getProfit() {
		return profit;
	}
	public void updateProfit(double p) {
		profit += p;
		DecimalFormat df = new DecimalFormat("#.##");
		df.format(profit); 
	}
	public void getLock() {
		lock.lock();
	}
	public void releaseLock() {
		lock.unlock();
	}
	public void assign(Trade t, double cost) {
		assigned.add(t);
		int count = t.getCount();
		String gain_cost = "";
		if(t.isSale()) gain_cost = "gain";
		else gain_cost = "cost";
		Stopwatch.timeElapsed(pw);
		pw.print("Assigned " + t.getTransaction() + " of " + count + " stock(s) of "
				+ t.getExchange() + ". Total " + gain_cost + " estimate : $" + t.getPrice() +
				"*" + count + " = " + cost + ".\n");
		pw.flush();
	}
	public int totalAssigned() {
		return assigned.size();
	}
	public List<Trade> getAssigned(){
		return assigned;
	}
	public boolean tryLock() {
		return lock.isLocked();
	}
	public void setPrintWriter(PrintWriter pw) {
		this.pw = pw;
	}
	public PrintWriter getPW() {
		return pw;
	}
}

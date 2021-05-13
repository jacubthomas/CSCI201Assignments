import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class TradeConductor extends Thread {
	private Trades trade;
//	private Map<String,Semaphore> map;
	private int count;
	private String exchange;
	private String transaction;
	private Semaphore semaphore;
//	private boolean trade_conducted = false;
	private static ReentrantLock lock = new ReentrantLock(true);
	
	public TradeConductor(Trades trade, Semaphore semaphore) {
		this.trade = trade;
		this.semaphore = semaphore;
		this.count = Math.abs(trade.getCount());
		this.transaction = count > 0 ? "purchase" : "sale";
		this.exchange = trade.getExchange();
	}
	
	// Sleeps current thread until trade is ready to initiate, as specified by input .csv
	public void run() {
//		while(!trade_conducted) {
//			try {
//				Thread.sleep(1000 * trade.getSeconds());
//				trade_conducted = conductTrade();
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
		conductTrade();
	}
	
	// Performs trade by considering semaphore permit limitations, which can translate to offset completion times,
	// and locks output to prevent multiple threads from interrupting eachother's print statements.
	public boolean conductTrade() {
//		int total_count = count;
		int tickets = 0;
		try {
			semaphore.acquire();
			lock.lock();
			Stopwatch.timeElapsed();
			System.out.print("Starting " + transaction + " of " + count +
					" stocks of " + exchange + "\n");
			lock.unlock();
			Thread.sleep(1000);
			lock.lock();
			Stopwatch.timeElapsed();
			System.out.print("Finished " + transaction + " of " + count +
					" stocks of " + exchange + "\n");
			lock.unlock();
			semaphore.release();
//			while(count != 0) {
//				// case 1: company has enough brokers to conduct trade in 1 second
//				if(count <= semaphore.availablePermits()) {
//					semaphore.acquire(count);
//					tickets = count;
//				} 
//				// case 2: company has less brokers than trade requires, thus,
//				// trade will extend beyond 1 second
//				else if(count > semaphore.availablePermits()) {
//					tickets = semaphore.availablePermits();
//					semaphore.acquire(tickets);
////					semaphore.acquire(semaphore.availablePermits());
//				}
//				Thread.sleep(1000);
//				count -= tickets;
//				semaphore.release(tickets);
//			}
//			lock.lock();
//			Stopwatch.timeElapsed();
//			System.out.print("Finished " + transaction + " of " + total_count +
//					" stocks of " + exchange + "\n");
//			lock.unlock();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return true;
	}
}

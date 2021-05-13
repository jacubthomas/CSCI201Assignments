import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

//import java.io.IOException;
import java.time.Instant;
import java.util.Collections;
//import java.util.InputMismatchException;
import java.util.Map;
import java.util.HashMap;


public class Main {
	private static String inputFileName;
	
	private static String promptForInputFile(Scanner sc) {
		System.out.print("What is the name of the company file? ");
		String filename = sc.nextLine();
		if(filename.equalsIgnoreCase("stock.json")) {
			filename =  "stock.json";
		} else if (filename.equalsIgnoreCase("assignment2.json")) {
			filename =  "assignment2.json";
		}
		return filename;
	}
	private static String promptForInputFile2(Scanner sc) {
		System.out.print("What is the name of the schedule file? ");
		String filename = sc.nextLine();
		if(filename.equalsIgnoreCase("schedule.csv")) {
			filename =  "schedule.csv";
		}
		return filename;
	}
	
	public static void main(String[] args) throws InterruptedException {
		Scanner sc = new Scanner(System.in);
		Data companies = null;
		@SuppressWarnings("unused") // Note that tr is in fact used on line 109...
		TradeReader tr;
		boolean goodfile = false;
		while(!goodfile)
		try {
			inputFileName = promptForInputFile(sc);
			companies  = new Data(inputFileName);
			goodfile = true;
		}  catch (Exception e) {
			continue;
		}
		goodfile = false;
		while(!goodfile) {
			try {
				String csv = promptForInputFile2(sc);
				tr  = new TradeReader(csv, companies.getComps());
				goodfile = true;
			}  catch (Exception e) {
				System.out.println(e.getMessage());	
				continue;
			}
		}
		
		HashMap<String, Semaphore> hash = new HashMap<String, Semaphore>();
		long startTime =  System.currentTimeMillis();
		long elapsedTime = 0;
		int nextTaskIndex = 0;
		for(int i=0; i<companies.numComps(); i++) {
			String ticker = companies.getCompAt(i).get_ticker();
			int brokers = companies.getCompAt(i).get_stockBrokers();
			Semaphore semaphores = new Semaphore(brokers);
			hash.put(ticker,semaphores);
		}
		
		Map<String, Semaphore> map_safe = Collections.synchronizedMap(hash);
		
		ExecutorService executor = 
				Executors.newCachedThreadPool();
		
		System.out.println("\nStarting execution of program...");
//		
//		for(int i=0; i<TradeReader.trades_safe.size(); i++) {
//			TradeConductor tc = new TradeConductor(TradeReader.trades_safe.get(i), map_safe);
//			Stopwatch.begin = Instant.now();
//			executor.execute(tc);
//		}
		Stopwatch.begin = Instant.now();
		while(true) {
        	while ((elapsedTime / 1000) >= TradeReader.trades_safe.get(nextTaskIndex).getSeconds()) {
        		TradeConductor tc = new TradeConductor(TradeReader.trades_safe.get(nextTaskIndex), 
        				map_safe.get(TradeReader.trades_safe.get(nextTaskIndex).getExchange()));
        		
        		executor.execute(tc);
        		nextTaskIndex ++;
        		
        		if (nextTaskIndex == TradeReader.trades_safe.size()) {
        			break;
        		}
        	}

        	if (nextTaskIndex == TradeReader.trades_safe.size()) {
    			break;
    		}

        	Thread.sleep(1000);
        	elapsedTime = System.currentTimeMillis() - startTime;
        }
		
		executor.shutdown();
		while(!executor.isShutdown()) {
			Thread.yield();
		}
		Thread.sleep(3000);
		System.out.println("\nAll trades completed!");
	}
}
/*
 *
        while(true) {
        	while ((elapsedTime / 1000) >= schedule.getTaskList().get(nextTaskIndex).getTime()) {
        		Task task = schedule.getTaskList().get(nextTaskIndex);
        		String ticker = task.getTicker();
        		executors.execute(new Trade(companyLocks.get(ticker), task.getTicker(), task.getTradeQuantity()));

        		nextTaskIndex += 1;
        		if (nextTaskIndex == schedule.getTaskList().size()) {
        			break;
        		}
        	}

        	if (nextTaskIndex == schedule.getTaskList().size()) {
    			break;
    		}

        	Thread.sleep(1000);
        	elapsedTime = System.currentTimeMillis() - startTime;
        }
*/

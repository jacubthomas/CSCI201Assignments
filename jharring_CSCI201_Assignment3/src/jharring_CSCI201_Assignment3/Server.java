package jharring_CSCI201_Assignment3;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;
import java.time.Instant;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {
	static Socket[] socket;
	static BufferedReader[] br;
	static PrintWriter[] pw;
	public Server() {
		Scanner sc = new Scanner(System.in);
		@SuppressWarnings("unused")
		TradeReader tr;
		@SuppressWarnings("unused")
		TraderReader tr2;
		ServerSocket server;
		Map<Integer, PrintWriter>  client_list = new HashMap<>();
		boolean goodfile = false;
		
		// Read input files	-------------------------------------------------------------
		while(!goodfile)
			try {
				String scheduleCSV = promptForInputFile(sc, "schedule");
				tr  = new TradeReader(scheduleCSV);
				goodfile = true;
			}  catch (Exception e) {
				continue;
			}
		goodfile = false;
		while(!goodfile) {
			try {
				String tradersCSV = promptForInputFile(sc, "trader");
				tr2 = new TraderReader(tradersCSV);
				goodfile = true;
			}  catch (Exception e) {
				continue;
			}
		}
		// Await & connect to traders	-------------------------------------------------
		int numtraders = TraderReader.TraderCollect.size();
		socket = new Socket[numtraders];
		br = new BufferedReader[numtraders];
		pw = new PrintWriter[numtraders];
		int numconnect = 0;
		try {
			System.out.println("Listening on port 3456. Waiting for traders...");
			server = new ServerSocket(3456);
			while(numconnect < numtraders) {
				socket[numconnect] = server.accept();
				Trader t = TraderReader.TraderCollect.get(numconnect);
				numconnect++;
				br[numconnect-1] = new BufferedReader(new InputStreamReader(socket[numconnect-1].getInputStream()));
				pw[numconnect-1] = new PrintWriter(socket[numconnect-1].getOutputStream());
				t.setPrintWriter(pw[numconnect-1]);
				InetAddress address = socket[numconnect-1].getInetAddress();
				client_list.put(numconnect,pw[numconnect-1]);
				if(numconnect == numtraders) break;
				System.out.println("Connection from " + address + ".\nWaiting for " + (numtraders-numconnect) +
						" more trader(s).");
				pw[numconnect-1].print((numtraders-numconnect) + " more trader needed for service to begin.\nWaiting...\n");
				pw[numconnect-1].flush();
			}
			System.out.println("Starting service.");
			
			
			// Stores the client PrintWriters to communicate sales
			for(Map.Entry<Integer,PrintWriter> entry: client_list.entrySet()) {
				PrintWriter p = entry.getValue();
				p.print("All traders have arrived!\nStarting service.\n");
				p.flush();
			}
			
			// Manages threads
			ExecutorService executor = Executors.newCachedThreadPool();
			
			Stopwatch.begin = Instant.now();
			long startTime = System.currentTimeMillis();
			long elapsed = 0;
			int nextTaskIndex = 0;
			int[] executed = new int[numtraders];
			for(int i=0; i<executed.length;i++) {
				executed[i] = 0;
			}
			double insitu_balance = 0.0;
			// keeps the server alive, so long as trades are occuring
			while(true) {
				// assigns trades to traders each second
				while((elapsed  / 1000) >=  TradeReader.schedule.get(nextTaskIndex).getSeconds()) {
					Trade trade = TradeReader.schedule.get(nextTaskIndex);
					double cost = trade.getPrice() * trade.getCount();
					for(Trader trader: TraderReader.TraderCollect) {
						// if trader is not actively trading, try to assign
						if(!trader.tryLock()) {
							// if purchase, check if trader can afford
							if(trader.getBalance() >= cost && !trade.isSale() 
									&& insitu_balance <= trader.getBalance()) {
								trader.assign(trade, cost);
								trade.setPrintWriter(client_list.get(trader.getID()));
								trade.setAssigned();
								insitu_balance += cost;
								DecimalFormat df = new DecimalFormat("#.##");
								df.format(insitu_balance); 
								break;
							} else if(trade.isSale()) {
								// if sale, assign
								trader.assign(trade, cost);
								trade.setPrintWriter(client_list.get(trader.getID()));
								trade.setAssigned();
								break;
							}
						}
					}
					nextTaskIndex++;
					if (nextTaskIndex == TradeReader.schedule.size()) {
	        			break;
	        		}
				}
				
				int j = 0;
				// after trades are assigned for the second, carry out trades
				for(Trader trader: TraderReader.TraderCollect) {
					List<Trade> assigned = trader.getAssigned();
					for(; executed[j] < assigned.size(); executed[j]++) {
						assigned.get(executed[j]).setTrader(trader);
						executor.execute(assigned.get(executed[j]));
					}
					j++;
				}
				
				// check for finito
				if (nextTaskIndex == TradeReader.schedule.size()) {
	    			break;
	    		}
				
				// wait a second, then repeat trade assignment/execution
	        	Thread.sleep(1000);
	        	elapsed = System.currentTimeMillis() - startTime;
			}
			
			Thread.sleep(10000);
			System.out.print("Incomplete trades: ");
			for(Trade trade : TradeReader.schedule) {
				if(!trade.getAssigned()) {
				String output = "(" + trade.getSeconds() + ", " + trade.getExchange() +
						", " + trade.getDate() + ")\n";
				System.out.print(output);
				}
			}
			for(Map.Entry<Integer,PrintWriter> entry: client_list.entrySet()) {
				PrintWriter p = entry.getValue();
				Stopwatch.timeElapsed(p);
				p.print("\nIncomplete trades: ");
				for(Trade trade : TradeReader.schedule) {
					if(!trade.getAssigned()) {
					String output = "(" + trade.getSeconds() + ", " + trade.getExchange() +
							", " + trade.getDate() + ")\n";
					p.print(output);
					}
				}
				p.flush();
			}
			
			for(Trader trader: TraderReader.TraderCollect) {
				PrintWriter p = client_list.get(trader.getID());
				p.print("Total Profit Earned: " + trader.getProfit());
				p.println("\nProcessing complete!");
				p.flush();	
				p.print("byebye");
				p.flush();
				p.close();
			}
			
			System.out.println("Processing complete!");
			try {
				for(PrintWriter p : pw) {
					if(p!= null)
						p.close();
				}
				for(BufferedReader b : br) {
					if(b!= null)
						b.close();
				}
				for(Socket sock : socket) {
					if(sock != null)
					sock.close();
				}
				if(server != null) {
					server.close();
				}
			} catch (Exception e) {
				System.out.println("failed to close up shop, server-side.");
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		} catch(Exception e) {
			return;
		}
	}
	private static String promptForInputFile(Scanner sc, String type) {
		System.out.print("What is the name of the " +  type + " file? ");
		String filename = sc.nextLine();
		if(filename.equalsIgnoreCase("schedule.csv")) {
			filename =  "schedule.csv";
		}
		else if(filename.equalsIgnoreCase("traders.csv")) {
			filename = "traders.csv";
		}
		return filename;
	}
	
	public static void main(String[] args) throws InterruptedException {
		new Server();
	}

}

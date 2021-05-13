import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class TradeReader{
	public static ArrayList<Trades> trades;
	public static List<Trades> trades_safe;
	public TradeReader(String filename, ArrayList<Company> companies) throws IOException, FileNotFoundException {
		Scanner sc = null;
		FileReader fr = null;
		BufferedReader br = null;
		trades = new ArrayList<Trades>();
		try {
			fr = new FileReader(filename);
			br = new BufferedReader(fr);
			String line = br.readLine();
			while(line != null) {
				if(line.isBlank() || line.isEmpty()) {
					line = br.readLine();
					continue;
				}
				int second = 0; String exchange = null; int count = 0;
				sc = new Scanner(line).useDelimiter(",");
				if(sc.hasNextInt()) {
					second = sc.nextInt();
				} else { 
					sc.close();
					throw new IOException("Bad CSV file: Invalid second input");
					}
				if(sc.hasNext()) {
					exchange = sc.next();
				} else { 
					sc.close();
					throw new IOException("Bad CSV file: Invalid ticker input");
				}
				if(sc.hasNextInt()) {
					count = sc.nextInt();
				} else { 
					sc.close();
					throw new IOException("Bad CSV file: Invalid count input");
				}
				boolean valid_ticker = false;
				for(int i=0; i<companies.size(); i++) {
					String tick = companies.get(i).get_ticker();
					if(tick.equalsIgnoreCase(exchange)) {
						valid_ticker = true;
					}
				}
				if(!valid_ticker) { 
					sc.close();
					throw new IOException("Bad CSV file: invalid ticker input");
				}
				Trades input = new Trades(second, exchange, count);
				trades.add(input);
				
				line = br.readLine();
				sc.close();
			}
		
		} catch (FileNotFoundException fnfe) {
			System.out.println(fnfe.getMessage());
			throw fnfe;
		}  catch (IOException ioe) {
			System.out.println(ioe.getMessage());
			throw ioe;
		} finally {
			if(sc != null) {
				sc.close();
			}
			if(br != null) {
				try {
					br.close();
				} catch(IOException ioee) {
					System.out.println(ioee.getMessage());	
				}
			}
			if(fr != null) {
				try {
					fr.close();	
				} catch(IOException ioee) {
					System.out.println(ioee.getMessage());	
				}
			}
		}
		trades_safe = Collections.synchronizedList(trades);
		System.out.println("\nThe file has been properly read\n");
//		printTrades();
	}
	public void printTrades() {
		for(Trades trade: trades) {
			System.out.println("Second: " + trade.getSeconds() + ", Exchange: " + 
								trade.getExchange() + ", count: " + trade.getCount());
		}
	}
}

package jharring_CSCI201_Assignment3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class TradeReader {
	public static List<Trade> schedule =
			Collections.synchronizedList(new ArrayList<Trade>());
	public TradeReader(String filename) throws IOException, FileNotFoundException {
		Scanner sc = null;
		FileReader fr = null;
		BufferedReader br = null;
		schedule = new ArrayList<Trade>();
		try {
			fr = new FileReader(filename);
			br = new BufferedReader(fr);
			String line = br.readLine();
			while(line != null) {
				if(line.isBlank() || line.isEmpty()) {
					line = br.readLine();
					continue;
				}
				int second = 0; String exchange = null; int count = 0; String date = "";
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
				if(sc.hasNext()) {
					date = sc.next();
				} else { 
					sc.close();
					throw new IOException("Bad CSV file: Invalid date input");
				}
				
				Trade input = new Trade(second, exchange, count, date);
				schedule.add(input);
				
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
		schedule = Collections.synchronizedList(schedule);
		TiingoUpdate();
		System.out.println("\nThe schedule file has been properly read\n");
	}
	public void TiingoUpdate() {
		Tiingo tiingo = new Tiingo();
		// TODO:  synchronize this iterator!
		for(Trade t : schedule) {
			String ticker = t.getExchange();
			String temp = t.getDate();
			String request = tiingo.request(temp, ticker);
			request = request.substring(1, request.length()-1);
			try {
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				JsonElement jE = gson.fromJson(request, JsonElement.class);
				JsonObject jO = jE.getAsJsonObject();
				String temp_close = jO.get("close").toString();
				double close = Double.parseDouble(temp_close);
				t.setPrice(close);
			} catch(JsonParseException jpe) {
				System.out.println(jpe.getMessage());
			}
		}
	}
	public void printTrades() {
		
		// TODO: Synchronize this iterator!
		for(Trade trade: schedule) {
			System.out.println("Second: " + trade.getSeconds() + ", Exchange: " + 
								trade.getExchange() + ", count: " + trade.getCount() +
								", price: " + trade.getPrice() + 
								", date: " + trade.getDate() + '\n');
		}
	}

}

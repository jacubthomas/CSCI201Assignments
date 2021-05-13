package jharring_CSCI201_Assignment3;

import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class TraderReader {
	public static List<Trader> TraderCollect = 
			Collections.synchronizedList(new ArrayList<Trader>());
	public TraderReader(String filename) throws IOException, FileNotFoundException {
		Scanner sc = null;
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(filename);
			br = new BufferedReader(fr);
			String line = br.readLine();
			while(line != null) {
				if(line.isBlank() || line.isEmpty()) {
					line = br.readLine();
					continue;
				}
				int ID = 0;
				double balance = 0;
				int count = 1;
				sc = new Scanner(line).useDelimiter(",");
				if(sc.hasNextInt()) {
					ID = sc.nextInt();
				} else { 
					ID = count;
					}
				if(sc.hasNextDouble()) {
					balance = sc.nextDouble();
				} else { 
					sc.close();
					throw new IOException("Bad CSV file: Invalid ticker input");
				}
				
				Trader input = new Trader(ID, balance);
				TraderCollect.add(input);
				count++;
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
		System.out.println("\nThe schedule file has been properly read\n");
	}
	public void printTrades() {
		for(Trader trader: TraderCollect) {
			System.out.println("ID: " + trader.getID() + ", Balance: " + 
								trader.getBalance() + ", Profit: " + trader.getProfit() + '\n');
		}
	}
}

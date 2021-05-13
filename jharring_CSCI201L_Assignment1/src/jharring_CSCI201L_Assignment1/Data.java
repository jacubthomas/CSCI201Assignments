package jharring_CSCI201L_Assignment1;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileNotFoundException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;

public class Data {
	
	@SerializedName("data")
    private ArrayList<Company> companies;
	FileReader fr;
	Gson gson;
	String filename;
    public Data(String filename) throws FileNotFoundException, JsonParseException, Exception {
    	this.filename = filename;
    	try {
    	fr = new FileReader(filename);
    	gson = new GsonBuilder().setPrettyPrinting().create();
    	Data temp = gson.fromJson(fr, Data.class);
    	this.companies = temp.companies;
    	
    	for(Company comps: companies) {
    		if(comps.name  == null || comps.ticker == null || comps.startDate == null  ||
    				comps.exchangeCode == null || comps.description == null) {
    			System.out.println("Fields are incomplete in json file. Specifically regarding company: " 
        				+ comps.name + "\n");
    			throw new Exception();
    		}
    	}
    	System.out.println("\nThe file has been properly read\n");
    	
    	
    	} catch (FileNotFoundException fnfe){
    		System.out.println(fnfe.getMessage());
    		System.out.println("File: " + filename + " was not found." );
    		throw fnfe;
    	} catch(JsonParseException jpe) {
    		System.out.println(jpe.getMessage());
    		System.out.println("Input file was incorrectly formatted.");
    		throw jpe;
    	} finally {
	    	if(fr !=  null) {
				try {
					fr.close();
				} catch (IOException ioe) {
					System.out.println(ioe.getMessage());
				}
			}
    	}
	}
    
    public Company getCompany(int index) {
    	return companies.get(index);
    }
    
    public void printAll() {
    	for(Company comp: companies) {
    		System.out.println(comp.name + ", symbol " + comp.ticker + ", started on " + 
    				comp.startDate + ", listed on " + comp.exchangeCode);    		
    		System.out.println(comp.description + "\n" );
    	}
    }
    
    public void printComp(Company comp) {
    	System.out.println(comp.name + ", symbol " + comp.ticker + ", started on " + 
				comp.startDate + ", listed on " + comp.exchangeCode);    		
		System.out.println(comp.description + "\n");
    }
    
	public void retrieveTicker(Scanner sc) {
		boolean found = false;
		while(!found) {
			System.out.print("What is the name of the company you would like to search for? ");	
			System.out.println();
			String tick = sc.nextLine();
			for(Company comp: companies) {
				if(comp.ticker.equalsIgnoreCase(tick)) {
					printComp(comp);
					found = true;
					return;
				}
			}
			System.out.println(tick + " could not be found. \n");
		}
		
	}
	
	public void retrieveByExchange(Scanner sc) {
		boolean found = false;
		while(!found) {
			System.out.print("What Stock Exchange would you like to search for? ");	
			String exchange = sc.nextLine();
			System.out.println();
			
			for(Company comp: companies) {
				if(comp.exchangeCode.equalsIgnoreCase(exchange)) {
					printComp(comp);
					found = true;
				}
			}
			if(!found) {
				System.out.println(exchange + " could not be found. \n");
			}
		}
	}
	
	public void addNewCompany(Scanner sc) {
		boolean loopCondition = true;
		String compName = null;
		String stockSymbol = null;
		String startDate = null;
		String exchange = null;
		String descript = null;
		while(loopCondition) {
			System.out.println("What is the name of the company you would like to add? ");
			compName = sc.nextLine();
			System.out.println();
			for(Company comp: companies) {
				if(comp.name.equalsIgnoreCase(compName)) {
					printComp(comp);
					loopCondition = false;
				}
			}
			if(!loopCondition) {
				System.out.println(compName + " is already an entry \n");
				loopCondition = true;
			} else {
				loopCondition = false;
			}
		}
		loopCondition = true;
		while(loopCondition) {
			System.out.print("What is the stock symbol of " + compName + "? ");
			stockSymbol = sc.nextLine();
			System.out.println();
			for(Company comp: companies) {
				if(comp.ticker.equalsIgnoreCase(stockSymbol)) {
					printComp(comp);
					loopCondition = false;
				}
			}
			if(!loopCondition) {
				System.out.println(stockSymbol + " is already taken \n");
				loopCondition = true;
			} else {
				loopCondition = false;
			}
		}
		
		System.out.print("What is the start date of " + compName);
		startDate = sc.nextLine();
		
		System.out.print("What is the exchange where " + compName + " is listed? ");
		exchange = sc.nextLine();
		
		System.out.print("What is the description of " + compName + "? ");
		descript = sc.nextLine();
		
		Company newCompany = new Company(compName, stockSymbol, startDate, exchange, descript);
		companies.add(newCompany);
		
		System.out.println("There is now a new entry for:");
		this.printComp(newCompany);
	}
	
	public void removeCompany(Scanner sc) {
		int i = 1;
		for(Company comp: companies) {
			System.out.println(i +") " + comp.name);
			i++;
		}
		System.out.print("What company would you like to remove?");
		i = sc.nextInt();
		while(i < 0 || i > companies.size()) {
			System.out.println("Not an option. Try again");
			i = sc.nextInt();
		}
		String name = null;
		name = companies.get(i-1).name;
		companies.remove(i-1);
		
		System.out.println(name + " is now removed. ");
	}
	
	public void sort(Scanner sc) {
		System.out.println("1) A to Z\n"
				+ "2) Z to A\n"
				+ "How would you like to sort by?");
		int option = sc.nextInt();
		while(option != 1 && option != 2) {
			System.out.println("Not an option. Try again");
			option = sc.nextInt();
		}
		switch(option) {
			case 1: // A-Z
				azCompare az = new azCompare();
				Collections.sort(companies, az);
				break;
			case 2: // Z-A
				zaCompare za = new zaCompare();
				Collections.sort(companies, za);
				break;
		}
		this.printAll();
	}
	
	public void saveExit(Scanner sc) throws IOException{
		int input = 0;
		while(input != 1 && input != 2) {
			System.out.println("Would you like to save your changes\n");
			System.out.println("1) Yes \n2) No \n ");
			if(!sc.hasNextInt()) {
				System.out.println("That wasn't an option. Try again.");
				continue;
			}
			input = sc.nextInt();
		}
		switch(input) {
			case 1:
				String filehead = "{\n"
						+ "  \"data\": ";
				String jsonInString = gson.toJson(companies);
				FileWriter fw = null;
				try {
					fw = new FileWriter(this.filename,false);
				} catch (FileNotFoundException fnfe){
					System.out.println(fnfe.getMessage() + "\nOccurred in save operation");
				}
				fw.write(filehead);
				fw.write(jsonInString);
				fw.write("\n}");
				fw.flush();
				fw.close();
			case 2:
				System.out.println("Goodbye!");
				System.exit(0);
		}
	}
}
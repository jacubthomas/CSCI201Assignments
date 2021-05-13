package jharring_CSCI201L_Assignment1;

import java.util.Scanner;
import java.io.IOException;
import java.util.InputMismatchException;


public class Main {
	private static String inputFileName;
	private static int option;
	
	private static String promptForInputFile(Scanner sc) {
		System.out.print("What is the name of the company file? ");
		String filename = sc.nextLine();
		if(filename.equalsIgnoreCase("stock.json")) {
			filename =  "stock.json";
		}
		return filename;
	}
	
	private static void userMenu(Scanner sc, Data companies) {
		option = 1;
		while(option > 0 && option < 7) {
			System.out.println("\t 1) Display all public companies \n"
					+ "\t 2) Search for a stock (by ticker) \n"
					+ "\t 3) Search for all stocks on an exchange \n"
					+ "\t 4) Add a new company/stocks \n"
					+ "\t 5) Remove a company \n"
					+ "\t 6) Sort companies \n"
					+ "\t 7) Exit");
			try {
				System.out.print("What would you like to do? ");
				option = sc.nextInt();
				sc.nextLine();
				if(option < 1 || option > 7) {
					System.out.println("That is not a valid option\n"); 
					option = 1;
					continue;
				}
			} catch (InputMismatchException ime) {
				System.out.println("That is not a valid option\n");
				sc.nextLine();
			}
			
			switch(option) {
				case 1: 
					companies.printAll();
					break;
				case 2:
					companies.retrieveTicker(sc);
					break;
				case 3:
					companies.retrieveByExchange(sc);
					break;
				case 4:
					companies.addNewCompany(sc);
					break;
				case 5:
					companies.removeCompany(sc);
					break;
				case 6:
					companies.sort(sc);
					break;
				case 7:
				try {
					companies.saveExit(sc);
				} catch (IOException e) {}
					break;
			}
		}
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Data companies = null;
		boolean goodfile = false;
		while(!goodfile)
		try {
			inputFileName = promptForInputFile(sc);
			companies  = new Data(inputFileName);
			goodfile = true;
		}  catch (Exception e) {
			continue;
		}
		userMenu(sc, companies);
	}
}


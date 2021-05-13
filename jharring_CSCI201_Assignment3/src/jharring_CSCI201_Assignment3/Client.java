package jharring_CSCI201_Assignment3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client extends Thread{
	Trader trader;
	Socket socket;
	int ID;
	public Client(){}
	public void run() {
		try {
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(System.in);
			System.out.println("Welcome to SAL stocks v2.0!");
			System.out.print("Enter the server hostname: ");
			String hostname = sc.nextLine();
			System.out.print("Enter the server port: ");
			int port = sc.nextInt();
			socket = new Socket(hostname, port);
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			while(true) {
				String line = br.readLine();
				if(line.equalsIgnoreCase("byebye")) {
					pw.close();
					br.close();
					socket.close();
					break;
				} else {
				System.out.println(line);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void setTrader(Trader t) {
		trader = t;
	}
	public Trader getTrader() {
		return trader;
	}
	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.execute(new Client());
		executor.shutdown();
		while(!executor.isTerminated()) {
			Thread.yield();
		}
	}

}

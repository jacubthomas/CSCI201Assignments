package jharring_CSCI201_Assignment3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Tiingo{
	private String token = "token=31b3e38a943aa7cbe18253280d724e7b60409217";
	private URL url;
	private HttpURLConnection plug;
	public Tiingo() {}
	public String request(String date, String ticker) {
		String site = "https://api.tiingo.com/tiingo/daily/" +
				ticker + "/prices?startDate=" + date + 
				"&endDate=" + date + "&format=json&resampleFreq=monthly&" +
				token;
		 try {
			url = new URL(site);
			plug = (HttpURLConnection)url.openConnection();
			BufferedReader  in = new BufferedReader(new InputStreamReader(plug.getInputStream()));
			String response;
			StringBuffer content = new StringBuffer();
			while ((response = in.readLine()) != null) {
			    content.append(response);
			}
			in.close();
			return content.toString();
		} catch (MalformedURLException e) {
			System.out.println("Bad url...");
		} catch (IOException e) {
			System.out.println("Url openConnection() failed...");
		}
		return "Did not work...";
	}
}

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

@WebServlet("/company")
public class Company extends HttpServlet{
	private String name;
	private String ticker;
	private String description;
	private String startDate;
	private String exchangeCode; 
	private static final long serialVersionUID = 2;
	public Company(String ticker) {
		this.ticker = ticker;
		String request = Tiingo.searchCompany(ticker);
		try {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonElement jE = gson.fromJson(request, JsonElement.class);
			JsonObject jO = jE.getAsJsonObject();
			this.name = jO.get("name").toString();
			this.description = jO.get("description").toString();
			this.startDate =  jO.get("startDate").toString();
			this.exchangeCode = jO.get("exchangeCode").toString();
		} catch(JsonParseException jpe) {
			System.out.println(jpe.getMessage());
		}
	}
	
	@SuppressWarnings("resource")
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
		    Class.forName("com.mysql.cj.jdbc.Driver");

		} 
		catch (ClassNotFoundException e) {
		    e.printStackTrace();
		} 
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String ticker_ = request.getParameter("Ticker");
		PrintWriter out = response.getWriter();
		try {	
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Assignment4?user=root&password=root");
			String query = "SELECT * from Company WHERE Ticker = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, ticker_);
			rs = ps.executeQuery();
			if(rs.next()) {
				System.out.println("Company Exists in DB.");
				
				// respond with json boiiiii
				response.setContentType("application/json");
				out.println("{");
				out.println("\"CID\":" + rs.getInt("CID") + ",");
				out.println("\"companyname\":" + "\"" + rs.getString("CompanyName") + "\",");
				out.println("\"ticker\":" + "\"" + rs.getString("Ticker") + "\",");
				out.println("\"exchangecode\":" + "\"" + rs.getString("ExchangeCode") + "\",");
				out.println("\"description\":" + "\"" + rs.getString("Description") + "\"");
				out.println("}");
				return;
			}
			// Fetch/construct company w/ tiingo query
			Company company = new Company(ticker_);
			
			// if company not currently tracked in DB, insert into DB
			query = "INSERT into Company (Ticker, CompanyName, ExchangeCode, Description)" +
							" values (?, ?, ?, ?)";
			ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, company.ticker);
			ps.setString(2, company.name);
			ps.setString(3, company.exchangeCode);
			ps.setString(4, company.description);
			ps.execute();		
			rs = ps.getGeneratedKeys();
			response.setContentType("application/json");
			out.println("{");
			out.println("\"CID\":" + rs.getInt("CID") + ",");
			out.println("\"companyname\":" + "\"" + rs.getString("CompanyName") + "\",");
			out.println("\"ticker\":" + "\"" + rs.getString("Ticker") + "\",");
			out.println("\"exchangecode\":" + "\"" + rs.getString("ExchangeCode") + "\",");
			out.println("\"description\":" + "\"" + rs.getString("Description") + "\"");
			out.println("}");
			
		}catch(SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		}finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
	}
}

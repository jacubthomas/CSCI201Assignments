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

@WebServlet("/lateststock")
public class LatestStock extends HttpServlet {
	
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
		String CID = request.getParameter("CID");
		PrintWriter out = response.getWriter();
		try {
			Stock stock = new Stock(ticker_);
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Assignment4?user=root&password=root");
			System.out.println("established connection");
			// Fetch/construct company w/ tiingo query
			// if company not currently tracked in DB, insert into DB
			String query = "INSERT into Stock(CID, Prev_Close, Mid_Price, Ask_Price, Ask_Size, Bid_Price, Bid_Size, Open_,"
					+ "High_Price, Low_Price, Last, Volume, Date_Timestamp)" +
							" values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, CID);
				ps.setDouble(2, stock.prev_close);
				ps.setDouble(3, stock.mid_price);
				ps.setDouble(4, stock.ask_price);
				ps.setDouble(5, stock.ask_size);
				ps.setDouble(6, stock.bid_price);
				ps.setDouble(7, stock.bid_size);
				ps.setDouble(8, stock.open);
				ps.setDouble(9, stock.high_price);
				ps.setDouble(10, stock.low_price);
				ps.setDouble(11, stock.last);
				ps.setInt(12, stock.volume);
				ps.setString(13, stock.date_timestamp);
				ps.execute();		
				rs = ps.getGeneratedKeys();	
				response.setContentType("application/json");
				String midprice = "";
				if(stock.mid_price == -1.0)
					midprice = "-";
				else midprice = stock.mid_price.toString();
				
				String askprice = "";
				if(stock.ask_price == -1.0)
					askprice = "-";
				else askprice = stock.ask_price.toString();
				
				String asksize = "";
				if(stock.ask_size == -1.0)
					asksize = "-";
				else asksize = stock.ask_size.toString();
				
				String bidsize = "";
				if(stock.bid_size == -1.0)
					bidsize = "-";
				else bidsize = stock.bid_size.toString();
				
				String bidprice = "";
				if(stock.bid_price == -1.0)
					bidprice = "-";
				else bidprice = stock.bid_price.toString();
				
				out.println("{");
				out.println("\"CID\":" + CID + ",");
				out.println("\"prev_close\":" + "\"" + stock.prev_close + "\",");
				out.println("\"mid_price\":" + "\"" + midprice + "\",");
				out.println("\"ask_price\":" + "\"" + askprice + "\",");
				out.println("\"ask_size\":" + "\"" + asksize + "\",");
				out.println("\"bid_price\":" + "\"" + bidprice + "\",");
				out.println("\"bid_size\":" + "\"" + bidsize + "\",");
				out.println("\"open\":" + "\"" + stock.open + "\",");
				out.println("\"high_price\":" + "\"" + stock.high_price + "\",");
				out.println("\"low_price\":" + "\"" + stock.low_price + "\",");
				out.println("\"last\":" + "\"" + stock.last + "\",");
				out.println("\"volume\":" + "\"" +  stock.volume + "\",");
				out.println("\"date_timestamp\":" + stock.date_timestamp);
				out.println("}");
				out.flush();
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

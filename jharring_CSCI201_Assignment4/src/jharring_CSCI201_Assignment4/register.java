package jharring_CSCI201_Assignment4;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/register")
public class register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("resource")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("su_username");
		String password = request.getParameter("su_password");
		String email = request.getParameter("su_email");
		double balance = 50000.00;
		double accountvalue = 50000.00;
		int UserID = 0;
		PrintWriter out = response.getWriter();

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
		String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		try {	
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Assignment4?user=root&password=root");
			String query = "INSERT into Users (Username, Password, Email, Balance, AccountValue)" +
							" values (?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, email);
			ps.setDouble(4, balance);
			ps.setDouble(5, accountvalue);
			ps.execute();		
			rs = ps.getGeneratedKeys();
			response.setContentType("application/json");
			out.println("{");
			out.println("\"UserID\":" + UserID + ",");
			out.println("\"username\":" + "\"" + username + "\",");
			out.println("\"password\":" + "\"" + password + "\",");
			out.println("\"email\":" + "\"" + email + "\"");
			out.println("\"balance\":" + "\"" + balance + "\"");
			out.println("\"account value\":" + "\"" + accountvalue + "\"");
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
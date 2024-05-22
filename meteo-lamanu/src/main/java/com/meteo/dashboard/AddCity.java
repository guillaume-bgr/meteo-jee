package com.meteo.dashboard;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.meteo.authenticate.PasswordAuthentication;
import com.meteo.database.Database;

/**
 * Servlet implementation class AddCity
 */
@WebServlet("/add-city")
public class AddCity extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddCity() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Connection connection =  Database.getConnection();
		HttpSession session = request.getSession(false);
		
		if (session.getAttribute("user_id") == null) {
			response.sendRedirect(request.getContextPath() + "/login");
		} else {
			Connection connection =  Database.getConnection();
	        String sql = "SELECT COUNT(*) FROM user_cities WHERE user_id = ? AND place_id = ?";
	        try (PreparedStatement statement = connection.prepareStatement(sql)) {
	            statement.setString(1, session.getAttribute("user_id").toString());
	            statement.setString(2, request.getParameter("place_id"));
	            try (ResultSet resultSet = statement.executeQuery()) {
	                if (resultSet.next()) {
	                    int count = resultSet.getInt(1);
	                    if (count == 0) {
	                    	String sqlAdd = "INSERT INTO `user_cities` (`id`, `user_id`, `name`, `place_id`, `lat`, `lng`) VALUES (NULL, ?, ?, ?, ?, ?)";
	            	        try (PreparedStatement addStatement = connection.prepareStatement(sqlAdd)) {
	            	        	addStatement.setString(1, session.getAttribute("user_id").toString());
	            	        	addStatement.setString(2, request.getParameter("name"));
	            	        	addStatement.setString(3, request.getParameter("place_id"));
	            	        	addStatement.setString(4, request.getParameter("lat"));
	            	        	addStatement.setString(5, request.getParameter("lng"));
	            	        	addStatement.execute();
	            	        	response.sendRedirect(request.getContextPath() + "/");
	            	        }
	                    } else {
	                    	response.sendRedirect(request.getContextPath() + "/");
	                    }
	                }
	            }
	        } catch(SQLException e) {
	        	e.printStackTrace();
	        }
		}
	}

}

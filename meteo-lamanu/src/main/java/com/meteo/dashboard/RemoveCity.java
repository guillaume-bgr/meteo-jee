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

import com.meteo.database.Database;

/**
 * Servlet implementation class RemoveCity
 */
@WebServlet("/remove-city")
public class RemoveCity extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveCity() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Connection connection =  Database.getConnection();
		HttpSession session = request.getSession(false);
		
		if (session.getAttribute("user_id") == null) {
			response.sendRedirect(request.getContextPath() + "/login");
		} else {
			Connection connection =  Database.getConnection();
	        String sql = "DELETE FROM user_cities WHERE user_id = ? AND place_id = ?";
	        try (PreparedStatement statement = connection.prepareStatement(sql)) {
	            statement.setString(1, session.getAttribute("user_id").toString());
	            statement.setString(2, request.getParameter("place_id"));
	            statement.executeUpdate(); // Use executeUpdate for DELETE
	            response.sendRedirect(request.getContextPath() + "/");
	        } catch(SQLException e) {
	        	e.printStackTrace();
	        }
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

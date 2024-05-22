package com.meteo.authenticate;

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
 * Servlet implementation class Register
 */

@WebServlet("/register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/Register.jsp" ).forward(req, res);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection =  Database.getConnection();
        String sql = "SELECT COUNT(*), id, email, password FROM users WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, request.getParameter("email"));
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    if (count < 1) {
                    	PasswordAuthentication hasher = new PasswordAuthentication();
                    	String hash = hasher.hash(request.getParameter("password").toCharArray());
                    	String sqlCreate = "INSERT INTO `users` (`id`, `email`, `password`) VALUES (NULL, ?, ?)";
                    	try (PreparedStatement statementCreate = connection.prepareStatement(sqlCreate)) {
                    		statementCreate.setString(1, request.getParameter("email"));
                    		statementCreate.setString(2, hash);
                    		statementCreate.execute();
                    		response.sendRedirect(request.getContextPath() + "/login");
                    	}
                    } else {
                    	System.out.println("count < 0");
                    	doGet(request, response);
                    }
                }
            }
        } catch(SQLException e) {
        	e.printStackTrace();
        }
	}

}

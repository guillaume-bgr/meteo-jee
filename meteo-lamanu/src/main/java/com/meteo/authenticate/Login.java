package com.meteo.authenticate;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.meteo.database.Database;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/Login.jsp" ).forward(req, res);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// response.getWriter().append(request.getParameter("email"));
		Connection connection =  Database.getConnection();
        String sql = "SELECT COUNT(*), id, email, password FROM users WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, request.getParameter("email"));
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    int id = resultSet.getInt(2);
                    if (count > 0) {
                    	PasswordAuthentication hasher = new PasswordAuthentication();
                    	Boolean isAuthenticated = hasher.authenticate(request.getParameter("password").toCharArray(), resultSet.getString("password"));
                    	System.out.println(isAuthenticated);
                    	if (isAuthenticated) {
                    		HttpSession session = request.getSession();
                    		
                    		session.setAttribute("user_id", id);
                    		session.setAttribute("email", request.getParameter("email"));
                    		response.sendRedirect(request.getContextPath() + "/");
                    	} else {
                    		doGet(request, response);
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

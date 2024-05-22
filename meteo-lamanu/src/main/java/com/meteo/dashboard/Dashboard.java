package com.meteo.dashboard;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URL;
import java.net.http.HttpRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.meteo.database.Database;
import com.meteo.models.UserCity;

/**
 * Servlet implementation class Dashboard
 */
@WebServlet("")
public class Dashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Dashboard() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession();
		boolean isConnected = (session.getAttribute("user_id") != null);
		req.setAttribute("isConnected", isConnected);
		if (isConnected) {
			Connection connection =  Database.getConnection();
			String sql = "SELECT id, user_id, name, place_id, lat, lng FROM user_cities WHERE user_id = ?";
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setString(1, session.getAttribute("user_id").toString());
				try (ResultSet resultSet = statement.executeQuery()) {
					List<UserCity> userCities = new ArrayList<>();
					int count = 0;
					while (resultSet.next()) {
						count = count + 1;
						count = resultSet.getInt(1);
						int id = resultSet.getInt("id");
						int user_id = resultSet.getInt("user_id");
						String name = resultSet.getString("name");
						String placeId = resultSet.getString("place_id");
						String lat = resultSet.getString("lat");
						String lng = resultSet.getString("lng");
						
						UserCity usercity = new UserCity(id, user_id, name, placeId, lat, lng);
						userCities.add(usercity);
					}
					if (count > 0) {						
						req.setAttribute("userCities", userCities);
					}
					this.getServletContext().getRequestDispatcher("/Base.jsp" ).forward(req, res);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			this.getServletContext().getRequestDispatcher("/Base.jsp" ).forward(req, res);
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

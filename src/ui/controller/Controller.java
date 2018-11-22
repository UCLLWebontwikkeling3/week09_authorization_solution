package ui.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.model.Role;
import domain.model.User;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Controller() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);

	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		String destination = "index.jsp";
		HttpSession session = request.getSession();
		Role[] roles;

		if (action == null)
			action = "";
		try {
			switch (action) {
			case "logIn":
				logIn(request, session);
				break;
			case "logOut":
				logOut(session);
				break;
			case "everyone":
				destination = everyOne();
				break;
			case "admin":
				destination = admin(request);
				break;
			case "allRoles":
				destination = allRoles(request);
				break;
			default:
				destination = "index.jsp";
				break;
			}
		} catch (NotAuthorizedException e) {
			request.setAttribute("notAuthorized", "You have insufficient rights to have a look at the requested page.");
		}
		RequestDispatcher view = request.getRequestDispatcher(destination);
		view.forward(request, response);

	}

	private void logIn(HttpServletRequest request, HttpSession session) {
		Role role = Role.valueOf(request.getParameter("role").toUpperCase());
		User user = new User(role.getStringValue(), role);
		session.setAttribute("user", user);
	}

	private void logOut(HttpSession session) {
		session.invalidate();
	}

	private String everyOne() {
		return "everyOne.jsp";
	}

	private String admin(HttpServletRequest request) {
		Role[] roles = { Role.ADMIN };
		checkRole(request, roles);
		return "admin.jsp";
	}

	private String allRoles(HttpServletRequest request) {
		Role[] roles = { Role.ADMIN, Role.CUSTOMER };
		checkRole(request, roles);
		return "allRoles.jsp";
	}

	private void checkRole(HttpServletRequest request, Role[] roles) {
		boolean found = false;
		User user = (User) request.getSession().getAttribute("user");
		if (user != null)
			for (Role role : roles) {
				if (user.getRole().equals(role))
					found = true;
			}
		if (!found)
			throw new NotAuthorizedException();

	}

}

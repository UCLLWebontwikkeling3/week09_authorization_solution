package ui.controller;

import domain.model.Role;
import domain.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogIn extends RequestHandler {

    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
        Role role = Role.valueOf(request.getParameter("role").toUpperCase());
        User user = new User(role.getStringValue(), role);
        request.getSession().setAttribute("user", user);
        return "index.jsp";
    }
}

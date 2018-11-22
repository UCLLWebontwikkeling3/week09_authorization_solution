package ui.controller;

import domain.model.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AllRoles extends RequestHandler {
    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
        Role[] roles = { Role.ADMIN, Role.CUSTOMER };
        Utility.checkRole(request, roles);
        return "allRoles.jsp";

    }
}

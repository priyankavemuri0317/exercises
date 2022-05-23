package com.ems.servlet;

import com.ems.authenticate.AuthenticateEmployee;
import com.ems.entity.Employee;
import com.ems.entity.Ticket;
import com.ems.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class TicketServlet extends HttpServlet {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
            IOException {
        try {
            PrintWriter out = response.getWriter();
            Employee authenticateEmployee = AuthenticateEmployee.authenticateEmployee;
            if (authenticateEmployee == null) {
                out.println("Session timeout");
                return;
            }

            String ticketType = null;
            if ("PendingTicket".equals(request.getParameter("action"))) {
                ticketType = "P";
            }
            System.out.println("ticketType=" + ticketType);
            EmployeeService employeeService = new EmployeeService();
            response.setContentType("text/json");
            List tickets = employeeService.viewTickets(authenticateEmployee, ticketType);
            out.println(tickets);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
            IOException {
        try {
            PrintWriter out = response.getWriter();
            Employee authenticateEmployee = AuthenticateEmployee.authenticateEmployee;
            if (authenticateEmployee == null) {
                out.println("Session timeout");
                return;
            }
            ObjectMapper mapper = new ObjectMapper();
            EmployeeService employeeService = new EmployeeService();
            response.setContentType("text/json");
            if ("NewTicket".equals(request.getParameter("action"))) {
                Ticket ticket = mapper.readValue(request.getInputStream(), Ticket.class);
                ticket.setEmployeeId(authenticateEmployee.getEmployeeId());
                Long ticketId = employeeService.newTicket(ticket);
                out.println("Ticket created " + ticketId);
            } else if ("Decision".equals(request.getParameter("action"))) {
                System.out.println("EmployeeType " + authenticateEmployee.getEmployeeType());
                if (!"manager".equals(authenticateEmployee.getEmployeeType() )) {
                    out.println("You are not manager");
                    return;
                }

                Ticket ticket = mapper.readValue(request.getInputStream(), Ticket.class);
                ticket.setEmployeeId(authenticateEmployee.getEmployeeId());
                int ticketId = employeeService.updateTicket(ticket);
                if (ticketId > 0 )
                    out.println("Ticket updated " + ticketId);
                else
                    out.println("Ticket not updated");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

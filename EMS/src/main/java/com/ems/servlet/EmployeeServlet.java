package com.ems.servlet;

import com.ems.authenticate.AuthenticateEmployee;
import com.ems.entity.Employee;
import com.ems.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class EmployeeServlet extends HttpServlet {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
            IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            PrintWriter out = response.getWriter();
            response.setContentType("text/json");
            EmployeeService employeeService = new EmployeeService();
            if ("Login".equals(request.getParameter("action"))) {
                Employee employee = mapper.readValue(request.getInputStream(), Employee.class);
                if (employeeService.login(employee)) {
                    System.out.println("Login successful" + employee.getEmployeeId());
                    AuthenticateEmployee.authenticateEmployee = employee;
                    RequestDispatcher rd = request.getRequestDispatcher("ticket");
                    rd.forward(request, response);
                } else {
                    out.println("Username or password is wrong");
                }
            } else if ("Logout".equals(request.getParameter("action"))) {
                AuthenticateEmployee.authenticateEmployee = null;
                out.println("Logout successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
            IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Employee employee = mapper.readValue(request.getInputStream(), Employee.class);

            PrintWriter out = response.getWriter();
            response.setContentType("text/json");
            EmployeeService employeeService = new EmployeeService();
            if (employee.getEmployeeType() == null) {
                employee.setEmployeeType("employee");            }

            // Writing the message on the web page
            Long empId = employeeService.registerEmployee(employee);
            out.println("Employee created " + empId);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

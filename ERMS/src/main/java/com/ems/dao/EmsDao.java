package com.ems.dao;

import com.ems.entity.Employee;
import com.ems.entity.Ticket;
import org.postgresql.util.PGmoney;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EmsDao {
    public Connection connect() {
        Connection conn = null;
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("dbConfig");
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(resourceBundle.getString("url"), resourceBundle.getString("username"), resourceBundle.getString("password"));
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public Long registerEmployee(Employee employee) {
        Connection connection = connect();
        Long empId = null;
        String insert = "insert into dbo.employee (FirstName ,LastName,username,password,email,created_on,last_login, employeeType) " +
                "values(?,?,?,?,?,current_timestamp, null, ?)";
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());
            statement.setString(3, employee.getUsername());
            statement.setString(4, employee.getPassword());
            statement.setString(5, employee.getEmail());
            statement.setString(6, employee.getEmployeeType());
            statement.executeUpdate();

            rs = statement.getGeneratedKeys();
            if (rs.next()) {
                empId = rs.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return empId;
    }

    public Long loginEmployee(Employee employee) {
        Connection connection = connect();
        Long employeeId = null;
        String query = "select EmployeeId, employeeType from dbo.Employee where username = ? and password = ?";
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, employee.getUsername());
            statement.setString(2, employee.getPassword());
            rs = statement.executeQuery();
            if (rs.next()) {
                employeeId = rs.getLong(1);
                employee.setEmployeeId(rs.getLong(1));
                employee.setEmployeeType(rs.getString(2));
                System.out.println("EmployeeId "+ employee.getEmployeeId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return employeeId;
    }

    public List viewTickets(Employee employee, String ticketType) {
        Connection connection = connect();
        List<Ticket> tickets = new ArrayList<Ticket>();
        String query = "select TicketId, Description, ReimbursementAmount::numeric, EmployeeId, ManagerId, created_on, updated_on, statusCd from dbo.Ticket  ";
        if (!"manager".equals(employee.getEmployeeType())) {
            query = query + " where employeeId = ?";
            if (ticketType != null && ticketType.length() > 0) {
                query = query + " and statusCd = 'P'";
            }
        } else if (ticketType != null && ticketType.length() > 0) {
            query = query + " where statusCd = 'P'";
        }
        query = query + " order by created_on desc";
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.prepareStatement(query);
            if (!"manager".equals(employee.getEmployeeType())) {
                statement.setLong(1, employee.getEmployeeId());
            }

            rs = statement.executeQuery();
            while (rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setTicketId(rs.getLong(1));
                ticket.setDescription(rs.getString(2));
                ticket.setReimbursementAmount(rs.getDouble(3));
                ticket.setEmployeeId(rs.getLong(4));
                ticket.setManagerId(rs.getLong(5));
                ticket.setCreatedOn(rs.getTimestamp(6));
                ticket.setUpdatedOn(rs.getTimestamp(7));
                ticket.setStatusCd(rs.getString(8));

                tickets.add(ticket);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return tickets;
    }


    public Long newTicket(Ticket ticket) {
        Connection connection = connect();
        Long ticketId = null;
        String insert = "insert into dbo.Ticket (Description, ReimbursementAmount, EmployeeId, ManagerId, created_on, updated_on, statusCd) " +
                "values(?,?,?,null, current_timestamp, current_timestamp, 'P')";
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, ticket.getDescription());
            PGmoney pGmoney = new PGmoney(ticket.getReimbursementAmount());
            statement.setObject(2, pGmoney);
            statement.setLong(3, ticket.getEmployeeId());
            statement.executeUpdate();

            rs = statement.getGeneratedKeys();
            if (rs.next()) {
                ticketId = rs.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ticketId;
    }

    public int updateTicket(Ticket ticket) {
        Connection connection = connect();
        int updated = 0;
        String insert = "update dbo.Ticket set ManagerId = ?, updated_on = current_timestamp, statusCd = ? where TicketId = ? " ;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.prepareStatement(insert);
            statement.setLong(1, ticket.getEmployeeId());
            statement.setString(2, ticket.getStatusCd());
            statement.setLong(3, ticket.getTicketId());
            updated = statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return updated;
    }
}
package com.ems.service;

import com.ems.dao.EmsDao;
import com.ems.entity.Employee;
import com.ems.entity.Ticket;

import java.util.List;

public class EmployeeService {

    public Long registerEmployee(Employee employee) {
        System.out.println("registerEmployee");
        EmsDao emsDao = new EmsDao();
        return emsDao.registerEmployee(employee);
    }

    public Boolean login(Employee employee) {
        System.out.println("loginEmployee");
        EmsDao emsDao = new EmsDao();
        Long empId = emsDao.loginEmployee(employee);
        if (empId == null)
            return false;
        else {
            return true;
        }
    }

    public List viewTickets(Employee employee, String ticketType) {
        System.out.println("viewTickets");
        EmsDao emsDao = new EmsDao();
        return emsDao.viewTickets(employee, ticketType);
    }

    public Long newTicket(Ticket ticket) {
        System.out.println("newTicket");
        EmsDao emsDao = new EmsDao();
        return emsDao.newTicket(ticket);
    }
    public int updateTicket(Ticket ticket) {
        System.out.println("newTicket");
        EmsDao emsDao = new EmsDao();
        return emsDao.updateTicket(ticket);
    }


}

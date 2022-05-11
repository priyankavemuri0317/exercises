package com.ems.entity;

import java.util.Date;

public class Ticket {
    private Long ticketId;
    private String description;
    private Double reimbursementAmount;
    private Long employeeId;
    private Long managerId;
    private Date createdOn;
    private Date updatedOn;
    private String statusCd;

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getReimbursementAmount() {
        return reimbursementAmount;
    }

    public void setReimbursementAmount(Double reimbursementAmount) {
        this.reimbursementAmount = reimbursementAmount;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }


    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", Description='" + description + '\'' +
                ", reimbursementAmount=" + reimbursementAmount +
                ", employeeId=" + employeeId +
                ", managerId=" + managerId +
                ", createdOn=" + createdOn +
                ", updatedOn=" + updatedOn +
                ", statusCd='" + statusCd + '\'' +
                '}';
    }
}

package com.bank.bo;

import com.bank.constants.BankConstants;

public class Account {
    private Long accountId;
    private String accountType;
    private Double balanceAmount;
    private String accountStatus;
    private Long customerId;


    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Double getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(Double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", accountType='" + BankConstants.accountType.get(accountType) + '\'' +
                ", balanceAmount=" + balanceAmount +
                ", accountStatus='" + BankConstants.status.get(accountStatus) + '\'' +
                ", customerId=" + customerId +
                '}';
    }
}

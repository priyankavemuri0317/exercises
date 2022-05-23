P0 - Banking Operations

CREATE TABLE dbo.userdata (
userid serial4 NOT NULL, firstname varchar(50) NOT NULL, lastname varchar(50) NOT NULL, 
CONSTRAINT userdata_pkey PRIMARY
KEY (userid)
);

CREATE TABLE dbo.customerdata (
customerid serial4 NOT NULL, firstname varchar(50) NOT NULL, lastname varchar(50) NOT NULL, CONSTRAINT customerdata_pkey
PRIMARY KEY (customerid)
);

CREATE TABLE dbo.account (
AccountId serial4 NOT NULL, BalanceAmount money NOT NULL, AccountType char(1) NOT NULL, CustomerId bigint NOT NULL,
AccountStatus char(1)  not null, CONSTRAINT account_pkey PRIMARY KEY (AccountId),

CONSTRAINT fk_CustomerData FOREIGN KEY(CustomerId)
REFERENCES dbo.customerdata(CustomerId)
ON DELETE cascade ON UPDATE CASCADE
);

CREATE TABLE dbo.transactiondata (
TransactionId serial4 NOT NULL, TransactionAmount money NOT null, BalanceAmount money NOT NULL, TransactionType char(1)
NOT NULL, CustomerId bigint, UserId bigint, ModifiedDate timestamp, AccountID bigint not null, CONSTRAINT
transactiondata_pkey PRIMARY KEY (TransactionId), CONSTRAINT fk_transactiondata_customerData FOREIGN KEY(CustomerId)
REFERENCES dbo.customerdata(CustomerId) ON DELETE cascade ON UPDATE cascade, CONSTRAINT fk_transactiondata_userdata
FOREIGN KEY(UserId) REFERENCES dbo.userdata(UserId) ON DELETE cascade ON UPDATE cascade  );
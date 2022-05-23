CREATE TABLE dbo.Employee (
EmployeeId serial PRIMARY KEY, FirstName VARCHAR ( 36 ) NOT NULL, LastName VARCHAR ( 36 ) NOT NULL, username VARCHAR (
50 ) UNIQUE NOT NULL, password VARCHAR ( 50 ) NOT NULL, email VARCHAR ( 255 ) UNIQUE NOT NULL, created_on TIMESTAMP NOT
NULL, last_login TIMESTAMP
);

CREATE TABLE dbo.Ticket (
TicketId serial PRIMARY KEY, Description VARCHAR ( 100 ) NOT NULL, ReimbursementAmount money, EmployeeId Int NOT
NULL,    
ManagerId Int NULL, created_on TIMESTAMP not NULL, updated_on TIMESTAMP not NULL, statuscd char(1) not null,

CONSTRAINT fk_EmployeeData FOREIGN key (EmployeeId) REFERENCES dbo.employee(EmployeeId) ON DELETE cascade ON UPDATE
cascade,

CONSTRAINT fk_EmployeeManager FOREIGN key (ManagerId) REFERENCES dbo.employee(EmployeeId) ON DELETE cascade ON UPDATE
CASCADE

); 
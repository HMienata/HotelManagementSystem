drop table Branch cascade constraints;
drop table BranchLocation cascade constraints;
drop table Customer cascade constraints;
drop table RoomType cascade constraints;
drop table Room cascade constraints;
drop table Manager cascade constraints;
drop table RentCost cascade constraints;
drop table Reservation cascade constraints;

create table BranchLocation(
	City varchar(20),
	Province varchar(20),
	PostalCode varchar(20),
	primary key (PostalCode)
);

create table Branch(
	Street varchar(40),
	HouseNumber varchar(20),
	PostalCode varchar(20),
	primary key (Street, HouseNumber, PostalCode),
	foreign key (PostalCode) references BranchLocation(PostalCode)
);

create table Manager(
	Street varchar(40),
	HouseNumber varchar(20),
	PostalCode varchar(20),
	ManagerId integer,
	Name varchar(40),
	Salary integer,
	primary key (ManagerId),
	foreign key (Street, HouseNumber, PostalCode) references Branch
);

create table RoomType(
	TypeName varchar(20) primary key,
	Price integer,
	InternetAccess number,
	Kitchen number,
	SatelliteTV number
);

create table Room(
	RoomNumber integer,
	Street varchar(40),
	HouseNumber varchar(20),
	PostalCode varchar(20),
	TypeName varchar(20) not null,
	primary key (RoomNumber, Street, HouseNumber, PostalCode),
	foreign key (TypeName) references RoomType(TypeName),
	foreign key (Street, HouseNumber, PostalCode) references Branch(Street, HouseNumber, PostalCode)
);

create table Customer(
	CustomerID integer primary key,
	Name varchar(40) not null,
	PaymentMethod varchar(20),
	PhoneNumber varchar(20)
);

create table Reservation(
	ConfirmationID integer primary key,
	StartDate date not null,
	EndDate date not null,
	RoomNumber integer not null,
	Street varchar(40) not null,
	HouseNumber varchar(20) not null,
	PostalCode varchar(20) not null,
	CustomerID integer not null,
	check (StartDate < EndDate),
	foreign key (RoomNumber, Street, HouseNumber, PostalCode) references Room(RoomNumber, Street, HouseNumber, PostalCode),
	foreign key (CustomerID) references Customer(CustomerID) ON DELETE Cascade
);


create table RentCost(
	ConfirmationID integer primary key,
	TotalCost integer,
	foreign key (ConfirmationID) references Reservation(ConfirmationID) ON DELETE Cascade
);

--BranchLocation
Insert into BranchLocation values ('Vancouver', 'BC', 'V6N 2Z5');
Insert into BranchLocation values ('Vancouver', 'BC', 'V7R 9B8');
Insert into BranchLocation values ('Thetford Mines', 'QC', 'G6H 7B5');
Insert into BranchLocation values ('Yellowknife', 'NT', 'Y8J 0F9');
Insert into BranchLocation values ('Wallaceburg', 'ON', 'P9N 8A5');

--Branch
Insert into Branch values ('Ackman Ave', '4150A', 'V6N 2Z5');
Insert into Branch values ('Rupert St', '8777', 'V7R 9B8');
Insert into Branch values ('Tower St', '192', 'G6H 7B5');
Insert into Branch values ('Armann Dr', '222', 'Y8J 0F9');
Insert into Branch values ('Agarr St', '1231C', 'P9N 8A5');

--RoomType
Insert into RoomType values ('Luxury', 225,1,1,1);
Insert into RoomType values ('Business', 180,1,1,0);
Insert into RoomType values ('Double', 130,1,0,0);
Insert into RoomType values ('Single', 100,1,0,0);
Insert into RoomType values ('Penthouse', 330,1,1,1);

--room
Insert into Room values (101,'Ackman Ave', '4150A', 'V6N 2Z5','Single');
Insert into Room values (102,'Ackman Ave', '4150A', 'V6N 2Z5','Double');
Insert into Room values (201,'Ackman Ave', '4150A', 'V6N 2Z5','Business');
Insert into Room values (202,'Ackman Ave', '4150A', 'V6N 2Z5','Luxury');
Insert into Room values (301,'Ackman Ave', '4150A', 'V6N 2Z5','Penthouse');
Insert into Room values (111,'Rupert St', '8777', 'V7R 9B8','Single');
Insert into Room values (222,'Rupert St', '8777', 'V7R 9B8','Double');
Insert into Room values (333,'Rupert St', '8777', 'V7R 9B8','Luxury');
Insert into Room values (101,'Tower St', '192', 'G6H 7B5','Single');
Insert into Room values (201,'Tower St', '192', 'G6H 7B5','Luxury');
Insert into Room values (301,'Tower St', '192', 'G6H 7B5','Penthouse');
Insert into Room values (101,'Armann Dr', '222', 'Y8J 0F9','Single');
Insert into Room values (201,'Armann Dr', '222', 'Y8J 0F9','Double');
Insert into Room values (301,'Armann Dr', '222', 'Y8J 0F9','Single');
Insert into Room values (101,'Agarr St', '1231C', 'P9N 8A5','Luxury');
Insert into Room values (201,'Agarr St', '1231C', 'P9N 8A5','Single');
Insert into Room values (202,'Agarr St', '1231C', 'P9N 8A5','Business');

--Customer
Insert into Customer values (5100, 'John Smith', 'Credit', '3337895656');
Insert into Customer values (5101, 'Joanne Dunham', 'Debit', '5163825588');
Insert into Customer values (5102, 'Ben Hill', 'Cash', '2444831245');
Insert into Customer values (5103, 'Angela Spahn', 'Debit', '6198884512');
Insert into Customer values (5104, 'Frank Martinez', 'Credit', '3631007812');
Insert into Customer values (5105, 'Adam Lee', 'Debit', '3337895656');
Insert into Customer values (5106, 'Frank Smith', 'Cash', '3337895656');
Insert into Customer values (5200, 'Cheapo NoReservo', 'Cash', '7787787788');

--Manager
Insert into Manager values ('Ackman Ave', '4150A', 'V6N 2Z5',1001,'Yehonatan Hamo',5000);
Insert into Manager values ('Rupert St', '8777', 'V7R 9B8',1002,'Vishal Gilad',15000);
Insert into Manager values ('Tower St', '192', 'G6H 7B5',1003,'Stevie Mahendra',2000);
Insert into Manager values ('Armann Dr', '222', 'Y8J 0F9',1004,'Kelly Howell',3000);
Insert into Manager values ('Agarr St', '1231C', 'P9N 8A5',1005,'Maurus Burkhart',4000);

--reservation
Insert into Reservation values (2001,to_date('2015/10/10', 'yyyy/mm/dd'),to_date('2015/11/10', 'yyyy/mm/dd'),101,'Ackman Ave', '4150A', 'V6N 2Z5',5100);
Insert into Reservation values (2002,to_date('2015/11/11', 'yyyy/mm/dd'),to_date('2015/11/15', 'yyyy/mm/dd'),102,'Ackman Ave', '4150A', 'V6N 2Z5',5100);
Insert into Reservation values (2003,to_date('2015/11/16', 'yyyy/mm/dd'),to_date('2015/11/19', 'yyyy/mm/dd'),202,'Ackman Ave', '4150A', 'V6N 2Z5',5100);
Insert into Reservation values (2015,to_date('2015/12/22', 'yyyy/mm/dd'),to_date('2015/12/23', 'yyyy/mm/dd'),201,'Ackman Ave', '4150A', 'V6N 2Z5',5100);

Insert into Reservation values (2004,to_date('2015/11/20', 'yyyy/mm/dd'),to_date('2015/11/29', 'yyyy/mm/dd'),301,'Ackman Ave', '4150A', 'V6N 2Z5',5100);
Insert into Reservation values (2005,to_date('2015/03/20', 'yyyy/mm/dd'),to_date('2015/04/29', 'yyyy/mm/dd'),111,'Rupert St', '8777', 'V7R 9B8',5101);
Insert into Reservation values (2006,to_date('2015/07/22', 'yyyy/mm/dd'),to_date('2015/07/24', 'yyyy/mm/dd'),201,'Tower St', '192', 'G6H 7B5',5102);
Insert into Reservation values (2007,to_date('2015/01/20', 'yyyy/mm/dd'),to_date('2015/01/29', 'yyyy/mm/dd'),301,'Tower St', '192', 'G6H 7B5',5103);
Insert into Reservation values (2008,to_date('2015/01/20', 'yyyy/mm/dd'),to_date('2015/02/02', 'yyyy/mm/dd'),101,'Armann Dr', '222', 'Y8J 0F9',5104);
Insert into Reservation values (2009,to_date('2015/02/20', 'yyyy/mm/dd'),to_date('2015/03/09', 'yyyy/mm/dd'),201,'Agarr St', '1231C', 'P9N 8A5',5105);
Insert into Reservation values (2010,to_date('2015/10/20', 'yyyy/mm/dd'),to_date('2015/10/22', 'yyyy/mm/dd'),111,'Rupert St', '8777', 'V7R 9B8',5106);
Insert into Reservation values (2013,to_date('2017/03/19', 'yyyy/mm/dd'),to_date('2018/03/22', 'yyyy/mm/dd'),111,'Rupert St', '8777', 'V7R 9B8',5100);


--RENTCOST

Insert into RentCost values (2001,3100);
Insert into RentCost values (2002,520);
Insert into RentCost values (2003,675);
Insert into RentCost values (2004,2970);
Insert into RentCost values (2005,4000);
Insert into RentCost values (2006,450);
Insert into RentCost values (2007,2970);
Insert into RentCost values (2008,1300);
Insert into RentCost values (2009,1600);
Insert into RentCost values (2010,200);
Insert into RentCost values (2015,180);


--null
Insert into Customer values (5107, 'John Mack', null, '3337895656');
Insert into Customer values (5108, 'Andrew Ridge', null, null);
Insert into Reservation values (2011,to_date('2015/10/21', 'yyyy/mm/dd'),to_date('2015/10/22', 'yyyy/mm/dd'),222,'Rupert St', '8777', 'V7R 9B8',5107);
Insert into RentCost values (2011,null);
Insert into Reservation values (2012,to_date('2015/10/11', 'yyyy/mm/dd'),to_date('2015/10/22', 'yyyy/mm/dd'),333,'Rupert St', '8777', 'V7R 9B8',5108);
Insert into RentCost values (2012,null);
Insert into BranchLocation values ('London', null, 'L1N 3F5');
--...


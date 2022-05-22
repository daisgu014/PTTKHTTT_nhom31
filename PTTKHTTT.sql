create DATABASE PTTKHTTT
use PTTKHTTT
--Cơ sở dữ liệu chung cho nhân viên
--Nhân viên
create table Employee
(
	ID int identity(1, 1) not null,
	[EmployeeID] as ('EMP' + right(replicate('0', 4) + cast(ID as varchar(4)), 4)) persisted not null,
	EmployeeName nvarchar(30) not null,
	EmployeePhone varchar(12) not null,
	WorkPositionID int not null,
	WorkTypeID int not null
);

--Tài khoản
create table Account
(
	AccountUsername varchar(20) not null,
	AccountPassword varchar(20) not null,
	EmployeeID int not null
);

--Vị trí làm việc của nhân viên
create table WorkPosition
(
	ID int identity(1, 1) not null,
	[WorkPositionID] as ('WP' + right(replicate('0', 3) + cast(ID as varchar(3)), 3)) persisted not null,
	WorkPositionName nvarchar(30) not null
);

--Loại nhân viên
create table WorkType
(
	ID int identity(1, 1) not null,
	[WorkTypeID] as ('WT' + right(replicate('0', 3) + cast(ID as varchar(3)), 3)) persisted not null,
	WorkTypeName nvarchar(30) not null
);

--Chi tiết vị trí việc làm của nhân viên
create table PositionType
(
	WorkPositionID int not null,
	WorkTypeID int not null,
	SalaryPerHour int not null
);

--Giờ giấc làm việc của nhân viên
create table DailyWork
(
	DailyDate date not null,
	EmployeeID int not null,
	Checkin time not null,
	Checkout time not null,
	WorkingHour float not null
);



--Loại
create table Category
(
	ID int identity(1, 1) not null,
	[CategoryID] as ('CAT' + right(replicate('0', 3) + cast(ID as varchar(3)), 3)) persisted not null,
	CategoryName nvarchar(30) not null
);
create table Product
(
	ID int identity (1,1) not null,
	[ProductID] as ('PD' +	right(replicate('0', 4) + cast(ID as varchar(4)), 4)) persisted not null,
	ProductName nvarchar(50) not null unique,
	ProductPrice int not null,
	CategoryId int not null,
	SuppilerID int  
);
create table Customer
(
	NumberPhone varchar(10) not null unique,
	Customer nvarchar(30) not null,
);
create table Orders
(
	ID int identity (1,1) not null,
	[OrderID] as ('OD' + right(replicate('0', 4) + cast(ID as varchar(4)), 4)) persisted not null,
	TotalPrice int not null,
	OrderDate date not null,
	OrderTime time not null,
	EmployeeID int not null,
	NumberPhoneCustomer varchar(10) unique
);
create table OrderDetails
(
	OrderID int not null,
	ProductID int not null,
	Quantity int not null
);
create table Supplier
(
	ID int identity (1,1) not null,
	[SupplierID] as  ('SP' + right(replicate('0', 3) + cast(ID as varchar(3)), 3)) persisted not null,
	SupplierName nvarchar(30) not null
);
create table ImportBill
(	
	ID int identity (1,1) not null,
	[ImportBillID] as  ('IB' + right(replicate('0', 3) + cast(ID as varchar(3)), 3)) persisted not null,
	CreateDate date not null,
	SupplierID int not null
);
create table ImportBillDetails
(
	ImportBillID int not null,
	ProductID int not null,
	Quantity int not null
);

--Tạo khóa chính cho bảng WorkPosition
alter table WorkPosition add constraint PK_WorkPosition primary key (ID);

--Tạo khóa chính cho bảng WorkType
alter table WorkType add constraint PK_WorkType primary key (ID);

--Tạo khóa chính cho bảng Employee
alter table Employee add constraint PK_Employee primary key (ID);
					     

--Tạo khóa chính cho bảng Account
alter table Account add constraint PK_Account primary key (AccountUsername);

--Tạo khóa chính cho bảng PositionType
alter table PositionType add constraint PK_WorkPosition_WorkType primary key (WorkPositionID, WorkTypeID);

--Tạo khóa chính cho bảng DailyWork
alter table DailyWork add constraint PK_DailyWork_Employee primary key (DailyDate, EmployeeID);
						
--Tạo khóa chính cho bảng loại
alter table Category add constraint PK_Category primary key (ID);

--Tạo khóa chính cho bảng sản phẩm
alter table Product add constraint PK_Product primary key (ID);

--Oders
alter table Orders add constraint PK_Orders primary key (ID);
--OrderDetails
alter table OrderDetails add constraint PK_OrderDetails primary key (OrderID, ProductID);
--Supplier
alter table Supplier add constraint PK_Suppiler primary key (ID);
--ImportBill
alter table ImportBill add constraint PK_ImportBill primary key (ID);
--ImportBillDetails
alter table ImportBillDetails add constraint PK_ImportBillDetails primary key (ImportBillID, ProductID);
--Customer
alter table Customer add constraint PK_Customer primary key (NumberPhone);


--Khoá ngoại
--Products
alter table Product add constraint FK_Product_Category foreign key (CategoryID)
references Category (ID);
--Orders
alter table Orders add  constraint FK_Order_Employee foreign key ( EmployeeID)
references Employee (ID), constraint FK_Order_Customer foreign key (NumberPhoneCustomer)
references Customer(NumberPhone);
--OrderDetails
alter table OrderDetails add constraint FK_OrderDetails_Product foreign key (ProductID)
references Product (ID), constraint FK_OrderDetails_Orders foreign key (OrderID)
references Orders(ID)
--Khóa ngoại cho Employee to PositionType
alter table Employee add Constraint FK_Employee_PositionType foreign key (WorkPositionID,WorkTypeID)
	references PositionType (WorkPositionID,WorkTypeID);
--PositionType_FK
	alter table PositionType add constraint FK_PositionType_WorkPosition foreign key (WorkPositionID) references WorkPosition (ID),
							 constraint	FK_PositionType_WorkType foreign key (WorkTypeID) references WorkType (ID);
--Khóa ngoại account đến nhân viên
alter table Account add constraint FK_Account_Employee foreign key (EmployeeID) references Employee (ID);

--Khóa ngoại bảng chấm công đến nhân viên
alter table DailyWork add constraint FK_DailyWork_Employee foreign key (EmployeeID) references Employee (ID);

--ImportBill_FK
alter table ImportBill add constraint FK_ImportBill_Supplier foreign key (SupplierID) references Supplier (ID);

--ImportBillDetails_FK
alter table ImportBillDetails add constraint FK_ImportBillDetails_ImportBill foreign key (ImportBillID) references ImportBill(ID),
								  constraint FK_ImportBillDetails_Product foreign key (ProductID) references Product(ID);
alter table Product add Storage int;

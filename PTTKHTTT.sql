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
	WorkPositionID varchar(5) not null,
	WorkTypeID varchar(5) not null
);

--Tài khoản
create table Account
(
	AccountUsername varchar(20) not null,
	AccountPassword varchar(20) not null,
	EmployeeID varchar(7) not null
);

--Vị trí làm việc của nhân viên
create table WorkPosition
(
	ID int identity(1, 1) not null,
	[WorkPositionID] as ('WP' + right(replicate('0', 3) + cast(ID as varchar(3)), 3)) persisted not null,
	WorkPositionName nvarchar(30) not null,
	WorkPositionLVL varchar(10)
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
	WorkPositionID varchar(5) not null,
	WorkTypeID varchar(5) not null,
	SalaryPerHour int not null
);

--Giờ giấc làm việc của nhân viên
create table DailyWork
(
	DailyDate date not null,
	EmployeeID varchar(7) not null,
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
	CategoryId varchar(6) not null,
	Storage int not null,
	SuppilerID varchar(5)  
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
	EmployeeID varchar(7) not null,
	NumberPhoneCustomer varchar(10) 
);
create table OrderDetails
(
	OrderID varchar(6) not null,
	ProductID varchar(6) not null,
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
	SupplierID varchar(5) 
);
create table ImportBillDetails
(
	ImportBillID varchar(5) not null,
	ProductID varchar(6) not null,
	Quantity int not null
);

--Tạo khóa chính cho bảng WorkPosition
alter table WorkPosition add constraint PK_WorkPosition primary key (WorkPositionID);

--Tạo khóa chính cho bảng WorkType
alter table WorkType add constraint PK_WorkType primary key (WorkTypeID);

--Tạo khóa chính cho bảng Employee
alter table Employee add constraint PK_Employee primary key (EmployeeID);
					     

--Tạo khóa chính cho bảng Account
alter table Account add constraint PK_Account primary key (AccountUsername);

--Tạo khóa chính cho bảng PositionType
alter table PositionType add constraint PK_WorkPosition_WorkType primary key (WorkPositionID, WorkTypeID);

--Tạo khóa chính cho bảng DailyWork
alter table DailyWork add constraint PK_DailyWork_Employee primary key (DailyDate, EmployeeID);
						
--Tạo khóa chính cho bảng loại
alter table Category add constraint PK_Category primary key (CategoryID);

--Tạo khóa chính cho bảng sản phẩm
alter table Product add constraint PK_Product primary key (ProductID);

--Oders
alter table Orders add constraint PK_Orders primary key (OrderID);
--OrderDetails
alter table OrderDetails add constraint PK_OrderDetails primary key (OrderID, ProductID);
--Supplier
alter table Supplier add constraint PK_Suppiler primary key (SupplierID);
--ImportBill
alter table ImportBill add constraint PK_ImportBill primary key (ImportBillID);
--ImportBillDetails
alter table ImportBillDetails add constraint PK_ImportBillDetails primary key (ImportBillID, ProductID);
--Customer
alter table Customer add constraint PK_Customer primary key (NumberPhone);


--Khoá ngoại
--Products
alter table Product add constraint FK_Product_Category foreign key (CategoryID)
references Category (CategoryID);
--Orders
alter table Orders add  constraint FK_Order_Employee foreign key ( EmployeeID)
references Employee (EmployeeID), constraint FK_Order_Customer foreign key (NumberPhoneCustomer)
references Customer(NumberPhone);
--OrderDetails
alter table OrderDetails add constraint FK_OrderDetails_Product foreign key (ProductID)
references Product (ProductID), constraint FK_OrderDetails_Orders foreign key (OrderID)
references Orders(OrderID)
--Khóa ngoại cho Employee to PositionType
alter table Employee add Constraint FK_Employee_PositionType foreign key (WorkPositionID,WorkTypeID)
	references PositionType (WorkPositionID,WorkTypeID);
--PositionType_FK
	alter table PositionType add constraint FK_PositionType_WorkPosition foreign key (WorkPositionID) references WorkPosition (WorkPositionID),
							 constraint	FK_PositionType_WorkType foreign key (WorkTypeID) references WorkType (WorkTypeID);
--Khóa ngoại account đến nhân viên
alter table Account add constraint FK_Account_Employee foreign key (EmployeeID) references Employee (EmployeeID);

--Khóa ngoại bảng chấm công đến nhân viên
alter table DailyWork add constraint FK_DailyWork_Employee foreign key (EmployeeID) references Employee (EmployeeID);

--ImportBill_FK
alter table ImportBill add constraint FK_ImportBill_Supplier foreign key (SupplierID) references Supplier (SupplierID);

--ImportBillDetails_FK
alter table ImportBillDetails add constraint FK_ImportBillDetails_ImportBill foreign key (ImportBillID) references ImportBill(ImportBillID),
								  constraint FK_ImportBillDetails_Product foreign key (ProductID) references Product(ProductID);


-- Thêm dữ liệu cho bảng WorkType
INSERT INTO dbo.WorkType(WorkTypeName) VALUES ('Parttime'), ('Fulltime');
-- Thêm dữ liệu cho bảng WorkPosition
INSERT INTO dbo.WorkPosition(WorkPositionName, WorkPositionLVL) VALUES ('Manager',2), ('Sale',1);

INSERT INTO PositionType (WorkPositionID,WorkTypeID,SalaryPerHour) VALUES ('WP001','WT002',50000), ('WP002','WT001',25000), ('WP002','WT002',30000)

--Thêm thể loại máy ảnh cho bảng Category
insert into Category(CategoryName) values ('Canon'), ('Fujifilm'), ('Nikon'), ('Pentax'), ('Sony');

Select * FROM Category
--Thêm máy ảnh cho bảng 
insert into Product(ProductName, ProductPrice, Storage, CategoryID) values
('Canon EOS 700D', 11990000,10, 'CAT001'),
('Canon EOS 1300D', 11490000,10, 'CAT001'),
('Canon EOS 2000D', 9990000, 10,'CAT001'),
('Canon EOS 200D', 15850000, 10,'CAT001'),
('Canon EOS 850D', 18800000,10, 'CAT001'),

('Fujifilm XF10', 12990000,10, 'CAT002'),
('Fujifilm X-T30', 11900000,10, 'CAT002'),
('Fujifilm X-T30 Mark', 31000000,10, 'CAT002'),
('Fujifilm X-T30 Kit', 23000000,10, 'CAT002'),
(' Fujifilm X-S10 Kit', 32000000,10, 'CAT002'),

('Nikon Z FC Body ', 20800000,10, 'CAT003'),
('Nikon Z FC Kit Z ', 25000000,10, 'CAT003'),
('Nikon Coolpix P950 ', 20500000,10, 'CAT003'),
('Nikon Z5 Body ', 30000000,10, 'CAT003'),
('Nikon Coolpix W300 ', 9200000,10, 'CAT003'),

('Pentax KP Body ', 23500000,10, 'CAT004'),
('Pentax K-70 Kit ', 23000000,10, 'CAT004'),
('Pentax K-S1 Lens ', 8600000,10, 'CAT004'),
('Pentax K-80 kit ', 19000000,10, 'CAT004'),
('PentaxK-70kitDA ', 23500000,10, 'CAT004'),

('Sony RX1 ', 15500000,10, 'CAT005'),
('Sony DSC HX350 ', 7900000,10, 'CAT005'),
('Sony CYBERSHOT DSC-HX350 ', 8390000,10, 'CAT005'),
('sony HX350 ', 8000000, 10,'CAT005'),
('Sony HX99 ', 10900000, 10,'CAT005')


SELECT Product.ProductName, Category.CategoryName, ImportBillDetails.Quantity
FROM ImportBill, ImportBillDetails, Product, Category
Where ImportBill.ImportBillID=ImportBillDetails.ImportBillID
AND ImportBill.ImportBillID='IB001'
AND ImportBillDetails.ProductID=Product.ProductID
AND Product.CategoryId=Category.CategoryID
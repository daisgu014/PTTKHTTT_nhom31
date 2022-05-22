create database CNPM;
use CNPM;

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

--Size và giá
create table ProductPrice
(
	ProductID varchar(5) not null,
	ProductSize varchar(2) not null,
	ProductPrice int not null
);

--Cơ sở dữ liệu chung cho sản phẩm
--Sản phẩm
create table Product
(
	ID int identity(1, 1) not null,
	[ProductID] as ('PD' + right(replicate('0', 3) + cast(ID as varchar(3)), 3)) persisted not null,
	ProductName nvarchar(30) not null,
	CategoryID varchar(6) not null
);


--Cơ sở dữ liệu chung cho hóa đơn
--Hóa đơn
create table Orders
(
	ID int identity(1, 1) not null,
	[OrderID] as ('ODR' + right(replicate('0', 7) + cast(ID as varchar(7)), 7)) persisted not null,
	TotalPrice int not null,
	OrderDate date not null,
	OrderTime time not null
);

--Chi tiết hóa đơn
create table OrderDetails
(
	OrderID varchar(10) not null,
	ProductID varchar(5) not null,
	Quantity int not null
);

--Cơ sở dữ liệu chung cho báo cáo
--Tổng bán trong ngày
create table DailySales
(
	DailySalesDate date not null,
	Total int not null
)


--Bảng nguyên vật liệu
create table Ingredients
(
	id int identity(1,1),
	ingredientID as ('ING' + right(replicate('0', 4) + cast(ID as varchar(4)), 4)) persisted not null,
	ingredientName nvarchar(40),
	ingredientType nvarchar(20),
	storage int,
	constraint PK_Ingredients primary key (ingredientID)
)

--Bảng công thức sản phẩm
create table ProductRecipes
(
	productID varchar(5),
	ingredientID varchar(7),
	productQty float,
	ingredientQty float,
	constraint PK_ProductRecipes primary key (productID,ingredientID)
)

--Bảng lưu báo cáo hàng hủy
create table DiscardReports
(
	id int identity(1,1),
	reportID as ('DISCRP' + right(replicate('0', 4) + cast(ID as varchar(4)), 4)) persisted not null,
	created varchar(7),
	comfirmed varchar(7),
	reportDate Date,
	constraint PK_DiscardReports primary key (reportID)
)

--Bảng lưu báo cáo doanh thu
create table SaleReports
(
	id int identity(1,1),
	reportID as ('SALERP' + right(replicate('0', 4) + cast(ID as varchar(4)), 4)) persisted not null,
	created varchar(7),
	comfirmed varchar(7),
	reportDate Date,
	constraint PK_SaleReports primary key (reportID)
)

--Bảng lưu báo cáo nhập hàng
create table IncomeReports
(
	id int identity(1,1),
	reportID as ('INRP' + right(replicate('0', 4) + cast(ID as varchar(4)), 4)) persisted not null,
	created varchar(7),
	comfirmed varchar(7),
	reportDate Date,
	constraint PK_IncomeReports primary key (reportID)
)

--Bảng lưu chi tiết hàng hủy
create table DiscardDetails
(
	productID varchar(5),
	reportID varchar(10),
	qty int,
	discardDate date,
	constraint PK_DISCARDDETAILS primary key (productID,reportID)
)

-- Bảng lưu chi tiết hàng nhập
create table IncomeDetails
(
	reportID varchar(8),
	ingredientID varchar(7),
	qty int,
	constraint PK_INCOMEDETAILS primary key (reportID,ingredientID)
)

-- Bảng lưu trữ các hàng đã hủy theo ngày
create table ProductCancel
(
	ProductID varchar(5) not null,
	ProductName nvarchar(30) not null,
	ProductQty int not null,
	ProductDate date
)


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

--Tạo khóa chính cho bảng size và giá
alter table ProductPrice add constraint PK_ProductPrice primary key (ProductID, ProductSize);

--Tạo khóa chính cho bảng hóa đơn
alter table Orders add constraint PK_Orders primary key (OrderID);

--Tạo khóa chính, khóa ngoại cho bảng chi tiết hóa đơn
alter table OrderDetails add constraint PK_OrderDetails primary key (OrderID, ProductID);

--Tạo khóa chính, khóa ngoại cho bảng tổng bán trong ngày
alter table DailySales add constraint PK_DailySales primary key (DailySalesDate);





--Khóa ngoại cho Employee to PositionType
alter table Employee add Constraint FK_Employee_PositionType foreign key (WorkPositionID,WorkTypeID)
	references PositionType (WorkPositionID,WorkTypeID);


--Khóa ngoại
alter table PositionType add constraint FK_PositionType_WorkPosition foreign key (WorkPositionID) references WorkPosition (WorkPositionID),
							 constraint	FK_PositionType_WorkType foreign key (WorkTypeID) references WorkType (WorkTypeID);

--Khóa ngoại account đến nhân viên
alter table Account add constraint FK_Account_Employee foreign key (EmployeeID) references Employee (EmployeeID);

--Khóa ngoại bảng chấm công đến nhân viên
alter table DailyWork add constraint FK_DailyWork_Employee foreign key (EmployeeID) references Employee (EmployeeID);

--Khóa ngoại giá sản phẩm đến sản phẩm
alter table ProductPrice add constraint FK_ProductPrice_Product foreign key (ProductID) references Product (ProductID);

--Khóa ngoại cho bảng đơn hàng
alter table Orders add constraint FK_Orders_DailySales foreign key (OrderDate) references DailySales (DailySalesDate);
--Thêm thông tin nhân viên lập đơn hàng
alter table Orders add Cashier varchar(7),
	constraint FK_Orders_Employee foreign key (Cashier) references Employee(EmployeeID)

--Khóa ngoại Thể loại sản phầm
alter table Product add constraint FK_Product_Category foreign key (CategoryID) references Category (CategoryID);

--Khóa ngoại Chi tiết đơn hàng
alter table OrderDetails add constraint FK_OrderDetails_Orders foreign key (OrderID) references Orders (OrderID),
						     constraint FK_OrderDetails_Product foreign key (ProductID) references Product (ProductID)

--Khóa ngoại công thức sản phẩm
alter table ProductRecipes add
	constraint FK_PRODUCTRECIPES_PRODUCT foreign key (productID)
		references Product(productID),
	constraint FK_PRODUCTRECIPES_INGREDIENTS foreign key (ingredientID)
		references INGREDIENTS(ingredientID)

--Khóa ngoại chi tiết hàng hủy
alter table DiscardDetails add
	constraint FK_DISCARDDETAILS_PRODUCT foreign key (productID)
		references Product(productID),
	constraint FK_DISCARDDETAILS_DISCARDREPORTS foreign key (reportID)
		references DiscardReports(reportID)

--Khóa ngoại chi tiết hàng nhập
alter table IncomeDetails add
	constraint FK_INCOMEDETAILS_INGREDIENTS foreign key (ingredientID)
		references Ingredients (ingredientID),
	constraint FK_INCOMEDETAILS_INCOMEREPORTS foreign key (reportID)
		references IncomeReports (reportID)

--Khóa ngoại doanh thu mỗi ngày đến báo cáo
alter table DailySales add reportId varchar(10),
	constraint FK_DAILYSALES_SALESREPORT foreign key (reportId)
		references SaleReports(reportID)

--Thêm cột số lượng nhận vào bảng IncomeDetails
alter table IncomeDetails add receiveQty int		

--Thêm cột nhà sản xuất vào bảng Ingredients (nguyên vật liệu)
alter table Ingredients add Producer nvarchar(30)

--Thêm cột giá nhập vào bảng Ingredients (nguyên vật liệu)
alter table Ingredients add price int

--Thêm cột trạng thái đơn đặt hàng vào bảng IncomeReports (bảng lưu báo cáo nhận hàng)
alter table IncomeReports add StateReport varchar(20)

--Thêm cột nhà cung cấp vào bảng IncomeReports (bảng lưu báo cáo nhận hàng)
alter table IncomeReports add supplier nvarchar(30)

--Thêm cột WorkPositionLVL vào bảng WorkPosition (Gia Đại)
alter table WorkPosition add WorkPositionLVL varchar(10)

--Them
alter table OrderDetails add productSize varchar(2)

--Tạo dữ liệu thể loại
insert into Category(CategoryName) values
(N'Cà phê'),
(N'Trà'),
(N'Bánh mì')

--Tạo dữ liệu sản phẩm
insert into Product(ProductName, CategoryID) values
(N'Cà phê đen', 'CAT002'),
(N'Cà phê sữa đá', 'CAT002'),
(N'Bạc xỉu đá', 'CAT002'),
(N'Espresso', 'CAT002'),
(N'Trà sen vàng', 'CAT003'),
(N'Trà thanh đào', 'CAT003'),
(N'Trà thạch vải', 'CAT003'),
(N'Trà xanh đậu đỏ', 'CAT003'),
(N'Bánh mì ốp la', 'CAT004'),
(N'Bánh mì thịt', 'CAT004'),
(N'Bánh mì cá ngừ', 'CAT004'),
(N'Bánh mì gà xé', 'CAT004')

-- Tạo dữ liệu giá sản phẩm theo size
insert into ProductPrice(ProductID, ProductSize, ProductPrice) values
('PD001', 'S', 18000),
('PD001', 'M', 21000),
('PD001', 'L', 23000),

('PD002', 'S', 20000),
('PD002', 'M', 24000),
('PD002', 'L', 26000),

('PD003', 'S', 24000),
('PD003', 'M', 30000),
('PD003', 'L', 33000),

('PD004', 'S', 20000),
('PD004', 'M', 24000),
('PD004', 'L', 26000),

('PD005', 'S', 30000),
('PD005', 'M', 38000),
('PD005', 'L', 42000),

('PD006', 'S', 30000),
('PD006', 'M', 38000),
('PD006', 'L', 42000),

('PD007', 'S', 30000),
('PD007', 'M', 38000),
('PD007', 'L', 42000),

('PD008', 'S', 30000),
('PD008', 'M', 38000),
('PD008', 'L', 42000),

('PD009', 'M', 30000),
('PD010', 'M', 30000),
('PD011', 'M', 30000),
('PD012', 'M', 30000),

('PD009', 'S', 30000),
('PD010', 'S', 30000),
('PD011', 'S', 30000),
('PD012', 'S', 30000),

('PD009', 'L', 42000),
('PD010', 'L', 42000),
('PD011', 'L', 42000),
('PD012', 'L', 42000)

--Thêm dữ liệu nguyên liệu cho bảng Ingredients
insert into Ingredients(ingredientName, ingredientType, storage, Producer, price) values
(N'Cà phê hạt nguyên chất', N'Cà phê hạt', 100, N'Trung Nguyên Legend', 150000),
(N'Đường', N'Gia vị', 100, N'Dalat Farm', 12000),
(N'Sữa đặc nguyên chất', N'Sữa' , 100, N'Dalat Farm', 20000),
(N'Sữa tươi', N'Sữa' , 100, N'Dalat Farm', 12000),
(N'Trà lài khô', N'Trà', 100, N'Dalat Farm', 20000),
(N'Trà đào khô', N'Trà', 100, N'Dalat Farm', 20000),
(N'Trà hương vải khô', N'Trà', 100, N'Dalat Farm', 20000),
(N'Trà xanh matcha khô', N'Trà', 100, N'Dalat Farm', 22000),
(N'Bột mì', N'Bột', 100, N'Dalat Farm', 8000),
(N'Bột vitamin C', N'Bột', 100, N'Dalat Farm', 15000),
(N'Giấm', N'Gia vị', 100, N'Dalat Farm', 15000),
(N'Muối', N'Gia vị', 100, N'Dalat Farm', 17000),
(N'Dầu ăn', N'Gia vị', 100, N'Dalat Farm', 18000),
(N'Trứng gà', N'Trứng', 100, N'Dalat Farm', 26000),
(N'Đồ chua', N'Đồ chế biến', 100, N'Dalat Farm', 20000),
(N'Rau thơm', N'Rau củ', 100, N'Dalat Farm', 10000),
(N'Dưa leo', N'Rau củ', 100, N'Dalat Farm', 20000),
(N'Chà bông', N'Đồ chế biến', 100, N'Dalat Farm', 30000),
(N'Bơ', N'Đồ chế biến', 100, N'Dalat Farm', 20000),

(N'Thịt xá xíu', N'Thịt', 10, N'Dalat Farm', 40000),
(N'Salad', N'Rau củ', 100, N'Dalat Farm', 10000),
(N'Cá ngừ đống hộp', N'Đồ chế biến', 50, N'Dalat Farm', 35000),
(N'Gà xé chế biến', N'Đồ chế biến', 10, N'Dalat Farm', 40000),
(N'Ớt', N'Đồ chế biến', 3, N'Dalat Farm', 5000)


INSERT INTO dbo.WorkType(WorkTypeName) VALUES ('Parttime'), ('Fulltime');
INSERT INTO WorkPosition(WorkPositionName) VALUES ('Cashier'),('Barista'),('Manager'),('ShiftLeader');
INSERT INTO PositionType VALUES
('WP001','WT001','20000'),
('WP001','WT002','30000'),
('WP002','WT001','25000'),
('WP002','WT002','35000'),
('WP003','WT002','50000')
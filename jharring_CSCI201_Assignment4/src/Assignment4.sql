DROP DATABASE IF EXISTS Assignment4;

CREATE DATABASE Assignment4;

USE Assignment4;


CREATE TABLE User(
UID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
Username VARCHAR(15) NOT NULL,
Password VARCHAR(15) NOT NULL,
Email VARCHAR(25) NOT NULL,
Balance DOUBLE NOT NULL,
AccountValue DOUBLE NOT NULL
);
CREATE TABLE Company(
CID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
Ticker VARCHAR(10) NOT NULL,
CompanyName VARCHAR(30),
ExchangeCode VARCHAR(10),
StartDate VARCHAR(20),
Description_ VARCHAR(16000)
);

CREATE TABLE Stock(
SID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
CID INT NOT NULL,
	FOREIGN KEY fk2(CID) references Company(CID),
Prev_Close DOUBLE NOT NULL,
Mid_Price DOUBLE NOT NULL,
Ask_Price DOUBLE NOT NULL,
Ask_Size DOUBLE NOT NULL,
Bid_Price DOUBLE NOT NULL,
Bid_Size DOUBLE NOT NULL,
Open_ DOUBLE NOT NULL,
High_Price DOUBLE NOT NULL,
Low_Price DOUBLE NOT NULL,
Last DOUBLE NOT NULL,
Volume INT NOT NULL,
Date_Timestamp VARCHAR(50) NOT NULL
);


CREATE TABLE Favorites(
FID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
UID INT NOT NULL,
    FOREIGN KEY fk1(UID) references User(UID),
CID INT NOT NULL,
	FOREIGN KEY fk2(CID) references Company(CID)
);

SELECT * FROM Company;
SELECT * FROM User;
SELECT * FROM Favorites;

SELECT CID, UID FROM Company, User
WHERE Ticker = '"AAPL"' AND Username = 'jacubthomas';

SELECT * FROM Favorites
WHERE UID = 2 AND CID =  6;

SELECT UID FROM User
WHERE Username = 'jacubthomas';

SELECT * FROM  Favorites
WHERE UID = 2;

SELECT * FROM Stock;
SELECT * FROM Favorites;

DROP TABLE Stock;

INSERT into User (Username, Password, Email, Balance, AccountValue)
VALUES ('test', '123', 'test@gmail.com', 50000.00, 50000.00);

INSERT INTO Company (Ticker, CompanyName, ExchangeCode, StartDate, Description_)
VALUES ('test', '123', 'had', 'h', 'oiuhasoih');

DELETE FROM Favorites
WHERE FID =2;

SELECT Balance, 
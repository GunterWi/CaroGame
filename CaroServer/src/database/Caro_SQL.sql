CREATE DATABASE Account
GO 
USE Account
GO
CREATE TABLE "user"(
ID int IDENTITY(1,1) PRIMARY KEY,
username varchar(50) UNIQUE,
pass varchar(50),
nickname varchar(50),
avatar varchar(50),
numberOfGame int,
numberOfWin int DEFAULT 0,
numberOfDraw int DEFAULT 0,
IsOnline int DEFAULT 0,
IsPlaying int DEFAULT 0
);
CREATE TABLE friend(
ID_User1 int NOT NULL,
ID_User2 int NOT NULL,
FOREIGN KEY (ID_User1) REFERENCES "user"(ID),
FOREIGN KEY (ID_User2) REFERENCES "user"(ID),
PRIMARY KEY (ID_User1,ID_User2)
);
CREATE TABLE BANNED_USER(
ID_User int PRIMARY KEY NOT NULL,
FOREIGN KEY (ID_User) REFERENCES "user"(ID)
);
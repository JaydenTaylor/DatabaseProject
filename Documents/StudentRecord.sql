CREATE DATABASE IF NOT EXISTS StudentRecord;
USE StudentRecord;
SHOW FIELDS FROM Prof;
CREATE USER 'Group'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON StudentRecord.* TO 'Group'@'localhost' IDENTIFIED BY 'password';

CREATE TABLE IF NOT EXISTS Student (
  `St_ID` INT NOT NULL,
  `F_Name` VARCHAR(45) NULL,
  `L_Name` VARCHAR(45) NULL,
  `Email` VARCHAR(45) NULL,
  PRIMARY KEY (`St_ID`)
);
  
CREATE TABLE IF NOT EXISTS Prof (
  `Prof_ID` INT NOT NULL,
  `F_Name` VARCHAR(45) NULL,
  `L_Name` VARCHAR(45) NULL,
  PRIMARY KEY (`Prof_ID`)
);

CREATE TABLE IF NOT EXISTS Department (
  `Department_ID` INT NOT NULL,
  `Department_Name` VARCHAR(45) NULL,
  PRIMARY KEY (`Department_ID`)
);

CREATE TABLE IF NOT EXISTS Course (
  `Course_ID` INT NOT NULL,
  `Course_Name` VARCHAR(45) NULL,
  `Department_ID` INT NOT NULL,
  `Prof_ID` INT NOT NULL,
  PRIMARY KEY (`Course_ID`),
  FOREIGN KEY (`Department_ID`) REFERENCES Department(`Department_ID`),
  FOREIGN KEY (`Prof_ID`) REFERENCES Prof(`Prof_ID`)
);

CREATE TABLE IF NOT EXISTS Enrollment (
  `St_ID` INT NOT NULL,
  `Course_ID` INT NOT NULL,
  FOREIGN KEY (`Course_ID`) REFERENCES Course(`Course_ID`),
  FOREIGN KEY (`St_ID`) REFERENCES Student(`St_ID`)
);

CREATE TABLE IF NOT EXISTS Textbook (
  `Book_ID` INT NOT NULL,
  `Title` VARCHAR(45) NULL,
  `Course_ID` INT NOT NULL,
  PRIMARY KEY (`Book_ID`),
  FOREIGN KEY (`Course_ID`) REFERENCES Course(`Course_ID`)
);

INSERT INTO Student VALUES(1001,"John","Smith","Jsmith@email.com");
INSERT INTO Student VALUES(1002,"Selena","Lowry","Selena.Lowry@email.com");
INSERT INTO Student VALUES(1003,"Talia","Harris","TaliaH@email.com");
INSERT INTO Student VALUES(1004,"Roy","Grimes","Roy_G@email.com");
INSERT INTO Student VALUES(1005,"Abigail","Brook","AbigailB@email.com");
INSERT INTO Student VALUES(1006,"Abdi","Kaur","Akaur@email.com");
INSERT INTO Student VALUES(1007,"Mina","Sharp","Msharp@email.com");
INSERT INTO Student VALUES(1008,"Grady","Phelps","GHPhelps@email.com");
INSERT INTO Student VALUES(1009,"Reece","Anderson","ReeceA@email.com");
INSERT INTO Student VALUES(1010,"Brendon","Mcfarland","Brendon_Mcfarland@email.com");
INSERT INTO Student VALUES(1011,"Daniel","Vinson","Dvinson@email.com");
INSERT INTO Student VALUES(1012,"Alan","Tierney","Atierney@email.com");
INSERT INTO Student VALUES(1013,"Nicky","Shepard","NickyS@email.com");
INSERT INTO Student VALUES(1014,"Juno","Mccray","Juno_M@email.com");
INSERT INTO Student VALUES(1015,"Maddy","Page","MaddyPage@email.com");
INSERT INTO Student VALUES(1016,"Garin","Gray","Ggray@email.com");
INSERT INTO Student VALUES(1017,"Hadley","Guerra","Hguerra@email.com");
INSERT INTO Student VALUES(1018,"David","Winters","D_Winters@email.com");
INSERT INTO Student VALUES(1019,"Dana","Jordan","DanaJ@email.com");
INSERT INTO Student VALUES(1020,"Reid","Henry","Reid_H@email.com");

INSERT INTO Prof VALUES(2001,"Jane","Brown");
INSERT INTO Prof VALUES(2002,"Mike","Jones");
INSERT INTO Prof VALUES(2003,"Mary","Matthews");
INSERT INTO Prof VALUES(2004,"Andrew","Kane");
INSERT INTO Prof VALUES(2005,"George","Jackson");
INSERT INTO Prof VALUES(2006,"Ben","Merritt");
INSERT INTO Prof VALUES(2007,"Reggie","Tanner");
INSERT INTO Prof VALUES(2008,"Aaron","Garner");
INSERT INTO Prof VALUES(2009,"Carter","Fraser");
INSERT INTO Prof VALUES(2010,"Elliott","Terry");

INSERT INTO Department VALUES(4001,"Mathematics");
INSERT INTO Department VALUES(4002,"Computer Science");
INSERT INTO Department VALUES(4003,"English");
INSERT INTO Department VALUES(4004,"Chemistry");
INSERT INTO Department VALUES(4005,"Arts");

INSERT INTO Course VALUES(3001,"Calculus I",4001,2001);
INSERT INTO Course VALUES(3002,"Calculus II",4001,2001);
INSERT INTO Course VALUES(3016,"Programming for Begginers",4002,2004);
INSERT INTO Course VALUES(3003,"Algorithms",4002,2004);
INSERT INTO Course VALUES(3004,"Creative Writing",4003,2007);
INSERT INTO Course VALUES(3005,"Intensive Grammar",4003,2007);
INSERT INTO Course VALUES(3006,"Organic Chemistry",4004,2008);
INSERT INTO Course VALUES(3007,"Biochemistry",4004,2008);
INSERT INTO Course VALUES(3008,"Introduction to Databases",4002,2006);
INSERT INTO Course VALUES(3009,"History of Painting",4005,2009);
INSERT INTO Course VALUES(3010,"Music",4005,2010);
INSERT INTO Course VALUES(3011,"Fundamentals of Drawing",4005,2009);
INSERT INTO Course VALUES(3012,"Linear Algebra",4001,2002);
INSERT INTO Course VALUES(3013,"Data Structures",4002,2005);
INSERT INTO Course VALUES(3014,"Modern Poetry",4003,2007);
INSERT INTO Course VALUES(3015,"Geometry",4001,2003);

INSERT INTO Textbook VALUES(5001,"Integral Calculus",3002);
INSERT INTO Textbook VALUES(5002,"Basics of Java",3003);
INSERT INTO Textbook VALUES(5003,"Linear Algebra",3013);
INSERT INTO Textbook VALUES(5004,"Sheet Music",3011);

INSERT INTO Enrollment VALUES(1001,3001);
INSERT INTO Enrollment VALUES(1001,3008);
INSERT INTO Enrollment VALUES(1001,3003);
INSERT INTO Enrollment VALUES(1002,3001);
INSERT INTO Enrollment VALUES(1003,3012);
INSERT INTO Enrollment VALUES(1003,3015);
INSERT INTO Enrollment VALUES(1004,3009);
INSERT INTO Enrollment VALUES(1004,3014);
INSERT INTO Enrollment VALUES(1005,3005);
INSERT INTO Enrollment VALUES(1005,3012);
INSERT INTO Enrollment VALUES(1005,3015);
INSERT INTO Enrollment VALUES(1006,3006);
INSERT INTO Enrollment VALUES(1006,3007);
INSERT INTO Enrollment VALUES(1007,3009);
INSERT INTO Enrollment VALUES(1008,3011);
INSERT INTO Enrollment VALUES(1009,3004);
INSERT INTO Enrollment VALUES(1009,3005);
INSERT INTO Enrollment VALUES(1009,3014);
INSERT INTO Enrollment VALUES(1009,3010);
INSERT INTO Enrollment VALUES(1010,3005);
INSERT INTO Enrollment VALUES(1010,3006);
INSERT INTO Enrollment VALUES(1011,3001);
INSERT INTO Enrollment VALUES(1012,3002);
INSERT INTO Enrollment VALUES(1013,3001);
INSERT INTO Enrollment VALUES(1014,3006);
INSERT INTO Enrollment VALUES(1014,3010);
INSERT INTO Enrollment VALUES(1015,3007);
INSERT INTO Enrollment VALUES(1015,3008);
INSERT INTO Enrollment VALUES(1015,3016);
INSERT INTO Enrollment VALUES(1016,3015);
INSERT INTO Enrollment VALUES(1017,3003);
INSERT INTO Enrollment VALUES(1017,3009);
INSERT INTO Enrollment VALUES(1018,3012);
INSERT INTO Enrollment VALUES(1019,3013);
INSERT INTO Enrollment VALUES(1019,3014);
INSERT INTO Enrollment VALUES(1019,3002);
INSERT INTO Enrollment VALUES(1020,3010);
INSERT INTO Enrollment VALUES(1020,3011);

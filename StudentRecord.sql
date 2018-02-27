CREATE DATABASE IF NOT EXISTS StudentRecord;
USE StudentRecord;

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
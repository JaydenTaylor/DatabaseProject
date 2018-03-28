CREATE DATABASE IF NOT EXISTS StudentRecord;
USE StudentRecord;

#temp view for supquery in Fees

CREATE VIEW tempView AS SELECT ST_ID, COURSE_NAME, 
    
    CASE    WHEN DEPARTMENT_NAME = 'Mathematics' 
            THEN 500
        
        WHEN DEPARTMENT_NAME = 'Computer Science'
    
            THEN 550
            
        WHEN DEPARTMENT_NAME = 'Chemistry'
    
            THEN 600
            
        WHEN DEPARTMENT_NAME = 'Arts'
        
            THEN 450
            
        ELSE 400
    
    END AS 'Fee'
FROM ENROLLMENT NATURAL JOIN COURSE NATURAL JOIN DEPARTMENT;


#Stored View (bonus marks)
#Shows students fees

CREATE VIEW Fees AS (

SELECT CONCAT(F_NAME, ' ', L_NAME) AS 'Student Name', 
COUNT(COURSE_ID) AS 'Number of Courses',
CASE    WHEN COUNT(COURSE_ID) >= 3 
        
        THEN 'Full time'
    ELSE 'Part time' 
END AS 'Student Type',
SUM(Fee) AS 'Student Fees'
FROM STUDENT NATURAL JOIN ENROLLMENT NATURAL JOIN COURSE NATURAL JOIN DEPARTMENT 
NATURAL JOIN tempView
GROUP BY ST_ID);


#Stored Procedure (bonus marks)
#Shows students in given course

DELIMITER //
CREATE PROCEDURE CourseView (IN Course VARCHAR(100))
BEGIN
	SELECT student.St_ID, F_Name, L_Name
	FROM student, enrollment, course 
	WHERE student.St_ID = enrollment.St_ID
		AND enrollment.Course_ID = course.Course_ID
		AND Course_Name =  Course;
END //
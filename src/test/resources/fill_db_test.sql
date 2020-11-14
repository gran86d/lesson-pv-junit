# CREATE DATABASE school;
# SET GLOBAL time_zone = '+3:00';
# USE school;

CREATE TABLE student (
                         ID INT(10) NOT NULL AUTO_INCREMENT,
                         FIRST_NAME VARCHAR (64) NOT NULL,
                         SECOND_NAME VARCHAR (64) NOT NULL,
                         BIRTH_DATE DATE,
                         ENTER_YEAR INT (4),
                         PRIMARY KEY (ID)
);

CREATE TABLE subject (
                         ID INT(10) NOT NULL AUTO_INCREMENT,
                         SUBJ_NAME VARCHAR (64) NOT NULL,
                         PRIMARY KEY (ID)
);

CREATE TABLE marks (
                       ID INT(10) NOT NULL AUTO_INCREMENT,
                       STUDENT_ID INT(10) NOT NULL,
                       SUBJECT_ID INT(10) NOT NULL,
                       MARK INT,
                       PRIMARY KEY (ID),
                       FOREIGN KEY (STUDENT_ID) REFERENCES student(ID),
                       FOREIGN KEY (SUBJECT_ID) REFERENCES subject(ID)
);

INSERT INTO student
(FIRST_NAME, SECOND_NAME, BIRTH_DATE, ENTER_YEAR)
VALUES ('Pavel', 'Soloviev' , '1985-03-09', 1991),
       ('Sergei', 'Pavlov', '1985-10-06', 1992),
       ('Valik', 'Durov', '1987-01-26', 1993);

INSERT INTO subject
(SUBJ_NAME)
VALUES ('biology'), ('chemistry'), ('math'), ('literature'), ('physics');

INSERT INTO marks
(STUDENT_ID, SUBJECT_ID, MARK)
VALUES (1, 1, 9), (1, 2, 5), (1, 3, 6),
       (2, 1, 7), (2, 2, 7), (2, 4, 4),
       (3, 1, 7), (3, 4, 10), (3, 5, 7);






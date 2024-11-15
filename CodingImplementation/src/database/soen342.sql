-- This SQL File creates all the tables necessary for our projects.
-- Create database
 CREATE DATABASE soen342;

-- Use the created database
USE soen342;

-- Table for Administrator
CREATE TABLE
 Administrator (
    adminId INT PRIMARY KEY
);

-- Table for Client
CREATE TABLE Client (
    clientId INT PRIMARY KEY,
    name VARCHAR(50),
    age INT,
    email VARCHAR(100),
    isOver18 BOOLEAN,
    guardianClientId INT,
    FOREIGN KEY (guardianClientId) REFERENCES Client(clientId) ON DELETE SET NULL
);

-- Table for Instructor
CREATE TABLE Instructor (
    instructorId INT PRIMARY KEY,
    name VARCHAR(50),
    phoneNumber VARCHAR(15),
    specialization VARCHAR(50),
    cities VARCHAR(255) -- comma-separated string or consider a separate table for the array
);

-- Table for Booking
CREATE TABLE Booking (
    bookingId INT PRIMARY KEY ,
    clientId INT,
    offeringId INT,
    FOREIGN KEY (clientId) REFERENCES Client(clientId)
);

use soen342;
-- Table for Schedule
CREATE TABLE Schedule (
    scheduleId INT PRIMARY KEY,
    date DATE,
    locationId INT,
    startTime DECIMAL(4, 2), -- as a decimal value (e.g., 2.5 for 2:30 and 2.75 for 2:45)
    endTime DECIMAL(4, 2)
);

-- Table for Space
CREATE TABLE Space (
    spaceId INT PRIMARY KEY,
    city VARCHAR(50),
    scheduleId INT,
    FOREIGN KEY (scheduleId) REFERENCES Schedule(scheduleId)
);

-- Table for lessons
CREATE TABLE Lesson (
    lessonId INT PRIMARY KEY,
    type VARCHAR(50) NOT NULL, 
    scheduleId INT , 
    spaceId INT , 
    locationId INT,
    privacy ENUM('Private', 'Public') NOT NULL, 
    instructorId INT, 
    isAvailable BOOLEAN NOT NULL DEFAULT TRUE, 
    capacity INT, 
    numberRegistered INT DEFAULT 0, 
    FOREIGN KEY (scheduleId) REFERENCES Schedule(scheduleId), 
    FOREIGN KEY (spaceId) REFERENCES Space(spaceId),
    FOREIGN KEY (instructorId) REFERENCES Instructor(instructorId)
    );
-- Table for Offering
CREATE TABLE Offering (
    offeringId INT PRIMARY KEY,
    lessonId INT,
    instructorId INT,
    isAvailable BOOLEAN,
    FOREIGN KEY (lessonId) REFERENCES Lesson(lessonId), 
    FOREIGN KEY (instructorId) REFERENCES Instructor(instructorId)
);

-- Table for Location
CREATE TABLE Location (
    locationId INT PRIMARY KEY,
    locationName VARCHAR(50),
    locationCity VARCHAR(50)
);


ALTER TABLE Booking
ADD FOREIGN KEY (offeringId) REFERENCES Offering(offeringId);

ALTER TABLE Schedule
ADD FOREIGN KEY (locationID) REFERENCES Location(locationID);

ALTER TABLE Lesson
ADD FOREIGN KEY (locationId) REFERENCES Location(locationId);

-- When an offering is deleted, all related bookings are also deleted
ALTER TABLE Booking
ADD CONSTRAINT fk_offering
FOREIGN KEY (offeringId) REFERENCES Offering(offeringId)
ON DELETE CASCADE;

ALTER TABLE Client
ADD COLUMN password VARCHAR(255);

ALTER TABLE Instructor
    ADD COLUMN password VARCHAR(255);

INSERT INTO Administrator (adminId) VALUES (12345); --Set admin Id





INSERT INTO Location (locationId, locationName, locationCity)
VALUES 
(1, 'Building A - Room 101', 'Montreal'),
(2, 'Building A - Room 102', 'Laval'),
(3, 'Building B - Room 201', 'Montreal'),
(4, 'Building B - Room 202', 'Montreal'),
(5,  'Building C - Room 301', 'Quebec'),
(6, 'Building C - Room 302','Ottawa'),
(7, 'Library - Meeting Room', 'Montreal'),
(8, 'Auditorium A', 'Toronto'),
(9, 'Auditorium B', 'Toronto'),
(10,'Outdoor Patio', 'Montreal');


INSERT INTO Schedule (scheduleId, date, locationId, startTime, endTime)
VALUES 
(1, '2024-11-01', 1, 4, 5),
(2, '2024-11-02', 2, 2.25, 3),
(3, '2024-11-03', 3, 2, 3),
(4, '2024-11-04', 4, 1, 5),
(5, '2024-11-05', 5, 8, 8.5),
(6, '2024-11-06', 6, 9, 10),
(7, '2024-11-07', 7, 1, 1.5),
(8, '2024-11-08', 8, 8, 9),
(9, '2024-11-09', 9, 9, 10),
(10, '2024-11-10', 10, 5, 6.75);


INSERT INTO Space (spaceId, city, scheduleId)
VALUES 
(1, 'Concordia', 1),
(2, 'Udem', 2),
(3, 'Uqam', 3),
(4, 'Mcgill', 4),
(5, 'Harvard', 5),
(6, 'MIT', 6),
(7, 'UofT', 7),
(8, 'ULaval', 8),
(9, 'Yale', 9),
(10, 'Dartmouth', 10);

INSERT INTO Instructor(instructorId, name,phoneNumber, specialization,cities, soen342.Instructor.password)
VALUES
(1, 'Sara', '514-99', 'Judo', 'Montreal, Laval, Toronto', '123'),
(2, 'Fatema', '514-23', 'Pilates', 'Laval, Toronto', '123'),
(3, 'Bob', '514-06', 'Boxe', 'Laval, Toronto, Ottawa', '123'),
(4, 'Mark', '514-34', 'Basketball', 'Quebec', '123');

INSERT INTO Lesson(lessonId, type, scheduleId, spaceId, locationId, privacy, instructorId, isAvailable, capacity, numberRegistered)
    VALUES
        (1, 'Judo', 1, 1, 1, 'Private', 1, 1, 4,0 );


    USE soen342;
SELECT * FROM Lesson;
-- DROP DATABASE soen342;


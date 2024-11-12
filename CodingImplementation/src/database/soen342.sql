-- This SQL File creates all the tables necessary for our projects.
-- Create database
-- CREATE DATABASE soen342;

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
    locationId INT
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
    clientId INT,
    isAvailable BOOLEAN,
    FOREIGN KEY (lessonId) REFERENCES Lesson(lessonId), 
    FOREIGN KEY (clientId) REFERENCES Client(clientId)
);

-- Table for Location
CREATE TABLE Location (
    locationId INT PRIMARY KEY,
    locationName VARCHAR(50)
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
ADD COLUMN password VARCHAR(255) NOT NULL;


INSERT INTO Administrator (adminId) VALUES (12345); --Set admin Id





INSERT INTO Location (locationId, locationName)
VALUES 
(1, 'Building A - Room 101'),
(2, 'Building A - Room 102'),
(3, 'Building B - Room 201'),
(4, 'Building B - Room 202'),
(5,  'Building C - Room 301'),
(6, 'Building C - Room 302'),
(7, 'Library - Meeting Room'),
(8, 'Auditorium A'),
(9, 'Auditorium B'),
(10,'Outdoor Patio');


INSERT INTO Schedule (scheduleId, date, locationId)
VALUES 
(1, '2024-11-01', 1),
(2, '2024-11-02', 2),
(3, '2024-11-03', 3),
(4, '2024-11-04', 4),
(5, '2024-11-05', 5),
(6, '2024-11-06', 6),
(7, '2024-11-07', 7),
(8, '2024-11-08', 8),
(9, '2024-11-09', 9),
(10, '2024-11-10', 10);


INSERT INTO Space (spaceId, city, scheduleId)
VALUES 
(1, 'New York', 1),
(2, 'Los Angeles', 2),
(3, 'Chicago', 3),
(4, 'Miami', 4),
(5, 'San Francisco', 5),
(6, 'Dallas', 6),
(7, 'Houston', 7),
(8, 'Seattle', 8),
(9, 'Boston', 9),
(10, 'Washington, D.C.', 10);


USE soen342;
SELECT * FROM Lesson;
--DROP DATABASE soen342;


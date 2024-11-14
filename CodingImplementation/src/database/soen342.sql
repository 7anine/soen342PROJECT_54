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
    cities VARCHAR(255), -- comma-separated string or consider a separate table for the array
    password VARCHAR(255)
);

-- Table for Booking
CREATE TABLE Booking (
    bookingId INT PRIMARY KEY ,
    clientId INT,
    offeringId INT,
    FOREIGN KEY (clientId) REFERENCES Client(clientId)
);


-- Table for Schedule
CREATE TABLE Schedule (
    scheduleId INT PRIMARY KEY,
    date DATE, 
    full BOOLEAN,
    startTime TIME,
    duration int,
    locationId INT
);

-- Table for Space
CREATE TABLE Space (
    spaceId INT PRIMARY KEY,
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
    city VARCHAR(50),
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

INSERT INTO Administrator (adminId) VALUES (12345); -- Set admin Id

INSERT INTO Location (locationId, city, locationName)
VALUES 
(1, 'New York', 'Building A - Room 101'),
(2, 'New York', 'Building A - Room 102'),
(3, 'Chicago', 'Building B - Room 201'),
(4, 'Miami','Building B - Room 202'),
(5, 'Miami', 'Building C - Room 301'),
(6, 'San Francisco', 'Building C - Room 302'),
(7, 'San Francisco', 'Library - Meeting Room'),
(8, 'Montreal', 'Auditorium A'),
(9, 'Montreal', 'Auditorium B'),
(10,'Montreal', 'Outdoor Patio');

INSERT INTO Schedule (scheduleId, date, full, startTime, duration, locationId)
VALUES 
(1, '2024-11-01', '08:00:00', 60, 1),
(2, '2024-11-02', '09:00:00', 90, 2),
(3, '2024-11-03', '10:00:00', 120, 3),
(4, '2024-11-04', '11:00:00', 60, 4),
(5, '2024-11-05', '12:00:00', 90, 5),
(6, '2024-11-06', '13:00:00', 120, 6),
(7, '2024-11-07', '14:00:00', 60, 7),
(8, '2024-11-08', '15:00:00', 90, 8),
(9, '2024-11-09', '16:00:00', 120, 9),
(10, '2024-11-10', '17:00:00', 60, 10);


INSERT INTO Space (spaceId, scheduleId)
VALUES 
(1,  1),
(2,  2),
(3,  3),
(4, 4),
(5,  5),
(6,  6),
(7, 7),
(8,  8),
(9,  9),
(10, 10);


USE soen342;

SELECT * FROM Schedule;
-- DROP DATABASE soen342;


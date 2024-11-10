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
    scheduleId INT NOT NULL, 
    spaceId INT NOT NULL, 
    privacy ENUM('Private', 'Public') NOT NULL, 
    instructorId INT, 
    isAvailable BOOLEAN NOT NULL DEFAULT TRUE, 
    capacity INT NOT NULL, 
    numberRegistered INT DEFAULT 0, 
    FOREIGN KEY (scheduleId) REFERENCES Schedule(scheduleId), 
    FOREIGN KEY (spaceId) REFERENCES Space(spaceId),
    FOREIGN KEY (instructorId) REFERENCES Instructor(instructorId) -- Foreign key relationship to Instructor
);
-- Table for Offering
CREATE TABLE Offering (
    offeringId INT PRIMARY KEY,
    lessonId INT,
    clientId INT,
    isAvailable BOOLEAN,
    FOREIGN KEY (lessonId) REFERENCES Lesson(lessonId), 
    FOREIGN KEY (clientId) REFERENCES Client(clientId),
);

-- Table for Location
CREATE TABLE Location (
    locationId INT PRIMARY KEY,
    scheduleId INT,
    FOREIGN KEY (scheduleId) REFERENCES Schedule(scheduleId)
);


ALTER TABLE Booking
ADD FOREIGN KEY (offerindId) REFERENCES Offering(offerindId);

ALTER TABLE Schedule
ADD FOREIGN KEY (locationID) REFERENCES Location(locationID);

-- When an offering is deleted, all related bookings are also deleted
ALTER TABLE Booking
ADD CONSTRAINT fk_offering
FOREIGN KEY (offeringId) REFERENCES Offering(offeringId)
ON DELETE CASCADE;

ALTER TABLE Client
ADD COLUMN password VARCHAR(255) NOT NULL;

-- DROP DATABASE soen342;


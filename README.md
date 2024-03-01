# Project Time Tracking Application

This application allows you to track the time spent on different projects.

## Features

- Start and stop tracking for each project.
- Record project name, start time, end time, and total duration.
- Store project data in a MySQL database.

## Requirements

- Python 3.x
- MySQL server

## Installation

1. Clone the repository:
2. Install the required dependencies:
3. Set up MySQL database and configure the connection in the code.

## Usage

1. Run the application:
2. Enter the project name and click on "Start Project" to begin tracking time.
3. Click on "End Project" when finished to stop tracking and save the data.

## Database Structure

The application uses a MySQL database with the following table structure:
## Database Structure

The application uses a MySQL database with the following table structure:

```sql
CREATE TABLE sureler (
    id INT AUTO_INCREMENT PRIMARY KEY,
    proje_adi VARCHAR(255) NOT NULL,
    baslangic_zamani DATETIME NOT NULL,
    bitis_zamani DATETIME,
    sure VARCHAR(255)
);
```
This section describes the table structure of the MySQL database used in the project. Feel free to ask if you need anything else!

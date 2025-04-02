This project is a Student Management System built using Spring Boot, which provides functionalities for managing student data. The system allows for various operations such as fetching student details, finding top students in a state, and retrieving the topper across all states. It supports a database connection using JPA for data persistence, and Excel file handling for data import.
Features
Student Management: Allows you to fetch all student details stored in the database.

Topper by State: Retrieve the top student in a particular state based on the highest percentage.

Topper Across All States: Retrieve the topper across all states based on the highest percentage.

Data Import: Import student data from an Excel file into the database.

Database Integration: PostgreSQL is used as the database to store and manage student records.

Technologies Used
Spring Boot: Framework used for building the backend REST API.

Spring Data JPA: For database interaction and persistence.

PostgreSQL: Database used for storing student data.

Apache POI: For handling Excel file imports and reading data from Excel sheets.

Java 21: Language used for backend development.

Endpoints
1. Get All Students
GET /students

Fetches a list of all students from the database.

2. Get Top Student by State
GET /students/top/{state}

Fetches the student with the highest percentage in a particular state.

3. Get Topper Across All States
GET /students/topper

Fetches the student with the highest percentage across all states.

How to Run the Project
Clone the repository:

bash
Copy
Edit
git clone https://github.com/vikas171717/Student.git
Navigate to the project directory:

bash
Copy
Edit
cd Student
Run the project using Maven:

bash
Copy
Edit
mvn spring-boot:run
The application will start on http://localhost:8081.

Prerequisites
Java 21 or higher

Maven

PostgreSQL (Configured with the correct application.properties for database connection)

How to Import Excel Data
The system allows you to import student data from an Excel file into the database. Make sure the Excel file follows the correct format for student records, with fields like:

srno (Serial Number)

name

state

percentage

yop (Year of Passing)



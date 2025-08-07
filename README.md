🌐 User Management System – Spring Boot Mini Project
This is a User Management Web Application developed using Spring Boot, Spring Data JPA, Thymeleaf, and MySQL. The goal of this project is to implement a real-world user registration and authentication flow with secure password handling and dynamic form population.

🔑 Features
✅ User Registration Page

Users can register with their name, email, phone number, and location (Country, State, City).

Country, State, and City dropdowns are dynamic and fetched from the database using REST APIs.

Based on country selection, states are loaded; based on state, cities are loaded.

✅ Email Password Feature

On successful registration, the app generates a random password and sends it to the user via email.

✅ Login Page

Users can log in using the credentials received via email.

On first login, the user is redirected to a Reset Password page.

After resetting the password, the user is redirected to the dashboard on all future logins.

✅ Reset Password Flow

Old password verification

New and confirm password match validation

Once updated, user won’t see this page again

✅ Dashboard with Quotes API

After login, users see a dashboard with a random motivational quote fetched from the third-party API: https://type.fit/api/quotes.

🧰 Technologies Used
Spring Boot

Spring Data JPA

MySQL

Thymeleaf

HTML, CSS, Bootstrap

Java Mail Sender

REST API integration

🧠 What I Learned
Building layered architecture (Controller, Service, Repository)

Creating RESTful endpoints for dynamic UI

Secure password handling

Integration with external APIs and mail services

Real-world form validations and UI feedback

📌 How to Run
Clone the repo

Configure MySQL DB and mail properties in application.properties

Run the Spring Boot application

Access the app via http://localhost:8080

# Smart Contact Manager

## Overview
Smart Contact Manager is a robust web application designed to manage contacts efficiently. Users can store and manage detailed contact information, including images, emails, and descriptions. The application also features user authentication, secure password management, and an OTP-based password recovery system.

## Features

- **Contact Management**: 
  - Add, update, and delete contacts.
  - Store contact details such as name, email, image, and description.

- **User Authentication**: 
  - Secure user registration and login functionality.

- **Password Recovery**: 
  - Reset passwords using an OTP (One-Time Password) sent to the registered email.

- **Form Validation**: 
  - Ensures proper data integrity with front-end and back-end validation.

- **Responsive Design**: 
  - A clean and user-friendly interface built using Thymeleaf templates, CSS, and JavaScript for a seamless experience across devices.

## Technology Stack

- **Backend**: 
  - Spring Boot
  - Hibernate

- **Frontend**: 
  - Spring MVC
  - Thymeleaf
  - CSS
  - JavaScript

- **Database**: 
  - SQL 

## Setup Instructions

### Prerequisites
- Java 11 or higher
- Maven
- MySQL or any SQL-based database

### Steps to Run

1. **Clone the repository**:
   ```bash
   git clone https://github.com/itsdhruvrpandey01/smart-contact-manager.git
   cd smart_contact_manager
   ```

2. **Configure the database**:
   - Update your `application.properties` (or `application.yml`) with your database configurations.
   ```properties
        spring.servlet.multipart.max-file-size=-1
        spring.servlet.multipart.max-request-size=-1
        
        spring.application.name=smartcontactmanager
        server.port=8080
        spring.datasource.url=jdbc:mysql://localhost:3306/yourdatabse
        spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
        spring.datasource.username=yourusername
        spring.datasource.password=yourpassword
        spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
        spring.jpa.hibernate.ddl-auto=update
   ```

3. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**:
   - Open your browser and navigate to `http://localhost:8080`.

### Folder Structure

```
smart_contact_manager/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │        └── smartcontactmanager/
│   │   │              ├── config/
│   │   │              ├── controllers/
│   │   │              ├── dao/
│   │   │              ├── entities/
│   │   │              ├── helpers/
│   │   │              └── services/
│   │   └── resources/
│   │       ├── static/
│   │       ├── templates/
│   │       └── application.properties
└── pom.xml
```

## Contribution Guidelines

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Commit your changes (`git commit -am 'Add some feature'`).
4. Push the branch (`git push origin feature-branch`).
5. Create a new Pull Request.

## Screenshots
![Home page](https://github.com/user-attachments/assets/5444b4c5-7af7-43bd-abbb-7fb595b98da7)
![SignUp](https://github.com/user-attachments/assets/cf2e8e57-c722-472c-8ca3-3318126f012d)
![Home page](https://github.com/user-attachments/assets/76101a70-ec96-4872-b279-a83e6fcef517)
![userDashBoard ](https://github.com/user-attachments/assets/adde9a52-6464-45fe-be1d-4963b4fc9339)
![user profiles](https://github.com/user-attachments/assets/11b96e82-71f9-4310-b10b-a869520474bc)
![add contact](https://github.com/user-attachments/assets/609056a2-c15f-47f0-8e26-a954da95f9ad)
![contact profile](https://github.com/user-attachments/assets/13bfa210-725e-428d-af38-49f705d9ba96)
![contact page](https://github.com/user-attachments/assets/a4df7968-6703-4ec9-b843-f1bf33fa5214)
![search functionality](https://github.com/user-attachments/assets/fbf1c7ee-0c74-44ac-ae53-fadf3493fb97)
![delete contact](https://github.com/user-attachments/assets/f2454474-6527-4d29-84d8-b1fadf689210)
![change password settings](https://github.com/user-attachments/assets/9f5c8c52-f402-4a99-8757-96ba9a51e494)

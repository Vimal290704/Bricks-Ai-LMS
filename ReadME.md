# Bricks LMS

## Student-Centric Learning Management System for Schools and Coaching Centers

![Bricks LMS Logo](backend/src/main/resources/static/logo.jpg)

## Overview

Bricks LMS is a modern, student-centric learning management system designed specifically for schools and coaching centers. Our platform enables educators to create engaging learning experiences while providing students with interactive tools for effective learning and revision.

## Key Features

### For Educators
- **Custom Quiz Creation**: Build assessments from our extensive question bank covering multiple subjects and difficulty levels
- **Performance Analytics**: Track student progress with detailed insights and reporting
- **Automated Grading**: Save time with instant assessment of student responses
- **Curriculum Management**: Organize your content by subjects, topics, and learning objectives

### For Students
- **Interactive Learning**: Engage with content through quizzes, challenges, and rewards
- **Revision Reels**: Quick, bite-sized revision content in an engaging reel format
- **Performance Tracking**: Monitor progress and identify areas for improvement
- **Personalized Learning Paths**: Adaptive content based on individual strengths and weaknesses

## Technology Stack

- **Backend**: Spring Boot, Java
- **Frontend**: React.js, Material UI
- **Database**: MySQL
- **Authentication**: JWT-based security
- In development.....

## Getting Started

### Prerequisites
- Java 17 or higher
- Node.js 16 or higher
- MySQL 8.0 or higher
- Maven

### Installation

1. Clone the repository
```bash
git clone https://github.com/Vimal290704/Bricks-Ai-LMS
```

2. Configure environment variables
```bash
# In the application.properties (in backend folder)
spring.application.name=Bricks-Ai-LMS
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/bricks_db?createDatabaseIfNotExist=true
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
jwt.secret=your_SECRET_KEY
```

3. Run the backend
```bash
cd backend
./mvnw spring-boot:run
```

4. Run the frontend
```bash
cd frontend
npm install
npm start
```

5. Access the application at `http://localhost:3000`

## QuestionBank API

The QuestionBank module provides a comprehensive API for accessing and filtering questions based on multiple criteria:

- Filter by topic
- Filter by subject
- Filter by difficulty level (EASY, MEDIUM, HARD)
- Filter by question type (MCQ, Essay, etc.)
- Combined filters for advanced searching

### Example API Usage

```
GET /api/questions/filter?topicId=1&difficulty=MEDIUM&subjectId=2&type=MCQ
```

## Contribution Guidelines

We welcome contributions to Bricks LMS! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

Please ensure your code follows our coding standards and includes appropriate tests.

## Contact

For support or inquiries, please contact us at [support@bricks.org.in]() or [vimalyadavkr001@gmail.com](vimalyadavkr001@gmail.com).

---

Made with ❤️ by Bricks | Empowering Education Through Technology
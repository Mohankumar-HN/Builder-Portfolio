The Builder Portfolio Management System (BPMS) is a Java-based console application designed to manage construction projects, users, and tasks with role-based access.

It supports three user roles:

Project Manager
Builder
Client

The system allows project creation, task assignment, progress tracking, and persistent storage using JSON files.

PROJECT STRUCTURE
BPMS
│
├── console
│   ├── App.java
│   ├── AllMenu.java
│
├── entity
│   ├── User.java
│   ├── Project.java
│   ├── Task.java
│
├── services
│   ├── AuthService.java
│   ├── ProjectService.java
│
├── dao
│   ├── UserDao.java
│   ├── ProjectDao.java
│   ├── TaskDao.java
│
├── users.json
├── projects.json
├── tasks.json



TECHNOLOGIES USED

Java
Maven
Junit

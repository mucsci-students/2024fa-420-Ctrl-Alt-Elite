# 2024fa-420-Ctrl-Alt-Elite

## Table of Contents
- [Project Description](https://github.com/mucsci-students/2024fa-420-Ctrl-Alt-Elite/tree/README#project-description)
- [Instructions for Downloading and Running the UML Editor](https://github.com/mucsci-students/2024fa-420-Ctrl-Alt-Elite/tree/README#instructions-for-downloading-and-running-the-uml-editor)
  - [Step 1: Clone the Repository](https://github.com/mucsci-students/2024fa-420-Ctrl-Alt-Elite/tree/README#step-1-clone-the-repository)
  - [Step 2: Navigate to the Project Directory](https://github.com/mucsci-students/2024fa-420-Ctrl-Alt-Elite/tree/README#step-2-navigate-to-the-project-directory)
  - [Step 3: Build the Project](https://github.com/mucsci-students/2024fa-420-Ctrl-Alt-Elite/tree/README#step-3-build-the-project)
  - [Step 4: Run the Program](https://github.com/mucsci-students/2024fa-420-Ctrl-Alt-Elite/tree/README#step-4-run-the-program)
- [Dependencies](https://github.com/mucsci-students/2024fa-420-Ctrl-Alt-Elite/tree/README#dependencies)
- [Testing](https://github.com/mucsci-students/2024fa-420-Ctrl-Alt-Elite/tree/README#testing)
- [Developers](https://github.com/mucsci-students/2024fa-420-Ctrl-Alt-Elite/tree/README#developers)

## Project Description
This project results from a large-stakes, group assessment in the capstone course, Software Engineering, from the 2024 Fall semester. The main purpose of this project is to give the team of students an opportunity to engage with the principles of software development in an encouraging and educational environment. 

## Instructions
### Step 1: Clone the Repository
```sh
   git clone https://github.com/mucsci-students/2024fa-420-Ctrl-Alt-Elite.git
```
### Step 2: Navigate to the Project Directory
```sh
   cd 2024fa-420-Ctrl-Alt-Elite
```
### Step 3: Build the Project
+ On Windows
```sh
   .\gradlew build
```
+ On macOS/Linux
```sh
   ./gradlew build
```
### Step 4: Run the Program
+ On Windows (CLI)
```sh
   .\gradlew run --console=plain --args="--cli"
```
+ On Windows (GUI)
```sh
   .\gradlew run 
```
+ On macOS/Linux (CLI)
```sh
   .\gradlew run --console=plain --args="--cli"
```
+ On macOS/Linux (GUI)
```sh
   ./gradlew run 
```
## Dependencies
To run this project, ensure you have the following installed on your machine:
+ [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/downloads/#java23) (Version 17 or later)

## Testing
To ensure the quality and functionality of the project, automated tests have been written using JUnit. Follow the instructions below to run the tests using Gradle.
### Running All Tests
+ To run all the tests in the project, execute the following command:
```sh
   ./gradlew test
```
### Running a Specific Test Class
+ If you want to run a specific test class, use the following command:
```sh
   ./gradlew test --tests "TestClassName"
```
### Running a Specific Test Method
+ To run a particular test method within a test class, use:
```sh
   ./gradlew test --tests "TestClassName.TestMethodName"

```

## Developers
- Sydney Norgaard - sydney-norgaard
- Jordan Rios - Jordan130
- Sujan Gurung - sugurung6
- Bishal Subedi - lahsibsubedi

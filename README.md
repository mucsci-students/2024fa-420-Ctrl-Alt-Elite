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
- [Design Patterns](https://github.com/mucsci-students/2024fa-420-Ctrl-Alt-Elite/tree/README#design-patterns)
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

## Design Patterns
### 1. Model-View-Controller (MVC) 
- **Definition**: A design pattern that separates an application into three components—Model (data), View (UI), and Controller (logic)—to improve organization and scalability.

- **Usage**: In our UML editor project, we implement the MVC pattern by organizing our code into three main folders: **Model**, **View**, and **Controller**.

  - **Model**: This folder contains files responsible for the application's data and logic. The `UmlEditorModel.java` is the core model representing the current state of the UML editor. The other files in this folder, like `UmlClass.java`, `UmlRelationship.java`, and `JsonUtils.java`, define the UML elements, their relationships, and handle data serialization.

  - **View**: The View is responsible for presenting the data to the user. In our project, the **View** folder contains `GUI.java` and `CLI.java`. These files define the user interfaces for the UML editor: `GUI.java` handles the graphical user interface (GUI) with visual elements, and `CLI.java` provides a text-based interface for interacting with the UML data.

  - **Controller**: The **Controller** folder contains files like `UmlEditor.java`, `UmGuiController.java`, and `UmCliController.java`, which manage the interactions between the View and the Model. The controller listens for user inputs (such as adding or removing UML elements) from the View, modifies the Model accordingly, and updates the View to reflect the changes.

### 2. State
+ **Definition**: Alter an object's behavior when its state changes.

+ **Usage**: We used the State pattern in `UmlGuiController.java`. The `updateButtonStates()` function dynamically updates the enabled/disabled states of menu items based on the current state of the UML editor model. This ensures that only relevant options are available to the user, depending on the classes, fields, methods, and relationships present in the model.
  
### 3. Null Object
+ **Definition**: Designed to act as a "nothing" version of an object.

+ **Usage**: The **Null Object** pattern in our UML editor project is implemented in `UmlClass` to represent a method's parameters when the method was not assigned any parameters by the user. The logic has been abstracted so that an empty parameter object is handled independently from parameter objects with values, and is written in a way that it can be adjusted in the future.
    
### 4. Memento
+ **Definition**: Capture and restore an object's internal state, allowing for undo and redo functionality.

+ **Usage**: The **Memento** pattern in our UML editor project is implemented using the `UmlEditorModel` class to capture and restore the internal state of the UML editor. The key elements of this implementation are:

    - **State Representation**: The `UmlEditorModel` class represents the state of the UML editor, which includes all the data (such as UML classes, attributes, and relationships) the user interacts with. This model's state is essential for undo and redo functionality.

    - **Deep Copying**: When an action is performed (e.g., adding, removing, or modifying a UML element), a deep copy of the `UmlEditorModel` is created and saved in the `undoStack`. This ensures that the original state remains unchanged and is available for future reverts or redos. The `new UmlEditorModel(model)` is responsible for cloning the model.

    - **State Management**: The `undoStack` and `redoStack` store these deep copies of `UmlEditorModel`. The `undoState()` and `redoState()` methods allow the editor to navigate between different states of the model, enabling undo and redo operations. When an undo operation is performed, the most recent state is popped from the `undoStack` and pushed onto the `redoStack`. Similarly, when redo is invoked, the state is moved back to the `undoStack`.


## Developers
- Sydney Norgaard - sydney-norgaard
- Jordan Rios - Jordan130
- Sujan Gurung - sugurung6
- Bishal Subedi - lahsibsubedi

# 2024fa-420-Ctrl-Alt-Elite
### Table of Contents
- [Project Description](https://github.com/mucsci-students/2024fa-420-Ctrl-Alt-Elite/tree/README#project-description)
  - [Instructions for Downloading and Running the UML Editor](https://github.com/mucsci-students/2024fa-420-Ctrl-Alt-Elite/tree/README#instructions-for-downloading-and-running-the-uml-editor)
- [Developers](https://github.com/mucsci-students/2024fa-420-Ctrl-Alt-Elite/tree/README?tab=readme-ov-file#developers)

## Project Description
This project results from a large-stakes, group assessment in the capstone course, Software Engineering, from the 2024 Fall semester. The main purpose of this project is to give the team of students an opportunity to engage with the principles of software development in an encouraging and educational environment. 

## Instructions for Downloading and Running the UML Editor
Firstly, ensure that you have the repository cloned onto your machine in an accessible folder.

### Adding the Gson JAR file to the Classpath
We have implemented a way to save and load data entered into the UML editor. This is done by using a Gson library to create a local file in the JSON format to save to and load from. For these commands to work, you have to ensure that the proper file is installed and that it is connected to the program. Here are instructions for doing so in both Eclipse and VsCode:

### Eclipse
- Go to this link and download the .jar file, https://mvnrepository.com/artifact/com.google.code.gson/gson/2.11.0 
- Right click on the default package/scr folder that contains the .java files for the UML editor
- Go to "Build Path" and click "Configure Build Path..."
- Select the "Libraries" tab
- Click on "Classpath"
- Click "Add External JARs..." on the right
- Locate the Gson file and select it
- Click "Apply and Close"

### VS Code
- Go to this link and download the .jar file, https://mvnrepository.com/artifact/com.google.code.gson/gson/2.11.0
- Press Ctrl + Shift + P to open the Command Palette.
- Type "Java: Configure Classpath" and select it.
- Go to “libraries” tab
- Click “Add Library”
- Navigate to the “gson-2.11.0jar” file

### Testing
For the test files, they were created using JUnit 5. If you want to run them, you can visit [this website](https://junit.org/junit5/) for details on downloading and running JUnit.

## Developers: 
- Sydney Norgaard - sydney-norgaard
- Jordan Rios - Jordan130
- Sujan Gurung - sugurung6
- Bishal Subedi - lahsibsubedi

package com.example.assignment;
import javafx.application.Application;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.sql.*;


/**
 * This class represents a simple Student Record application with four tabs for adding students, adding modules, adding
 * student records, and viewing records. It uses JavaFX for the user interface and SQLite for the database.
 */
public class Student_Record extends Application {

    /**
     * The start method initializes the GUI and sets up the four tabs.
     *
     * @param primaryStage the primary stage for the application
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            // set the title of our window
            primaryStage.setTitle("MTU Student Record System");

            //set tabs
            TabPane tabPane = new TabPane();
            BorderPane borderPane = new BorderPane();
            tabPane.setSide(Side.TOP);

            //create tabs
            //addStudent tab
            Tab addStudent = new Tab("Add Student");
            addStudent.setClosable(false);
            tabPane.getTabs().add(addStudent);   // to add addStudent tab to tabPane

            //addModule tab
            Tab addModule = new Tab("Add Module");
            addModule.setClosable(false);
            tabPane.getTabs().add(addModule); // to add addModule tab to tabPane

            //addRecord tab
            Tab addRecord = new Tab("Add Record");
            addRecord.setClosable(false);
            tabPane.getTabs().add(addRecord); // to add addRecord tab to tabPane

            //viewRecord tab
            Tab viewRecord = new Tab("View Record");
            viewRecord.setClosable(false);
            tabPane.getTabs().add(viewRecord); // to add viewRecord tab to tabPane

            // set controller
            Controller control = new Controller();

            // set name field
            Label nameLabel = new Label("Enter Name:");
            TextField nameField = new TextField();
            nameField.setEditable(true);

            // set student number field
            Label numberLabel = new Label("Enter Student Number:");
            TextField numberField = new TextField();
            numberField.setEditable(true);


            // set date of birth field
            Label dobLabel = new Label("Enter Date of Birth:");
            TextField dob = new TextField();
            dob.setEditable(true);

            //set current semester field
            Label semesterLabel = new Label("Enter Semester:");
            TextField semesterField = new TextField();
            semesterField.setEditable(true);

            //set textarea for students
            TextArea studentArea = new TextArea("Students in the application: \n");
            studentArea.setEditable(false);

            //set textarea for modules
            TextArea moduleArea = new TextArea("Modules: \n");
            studentArea.setEditable(false);

            //set textarea for records
            TextArea recordArea = new TextArea("Student Records: \n");
            recordArea.setEditable(false);

            //set pick student field
            Label pickStudentLabel = new Label("Pick Student:");
            TextField pickStudentField = new TextField();
            pickStudentField.setEditable(true);


            //set pick Module field 1
            Label pickModuleLabel = new Label("Pick Module:");
            TextField pickModuleField = new TextField();
            pickModuleField.setEditable(true);

            //set add module field
            Label addModuleLabel = new Label("Enter Module Name:");
            TextField addModuleField = new TextField();
            addModuleField.setEditable(true);


            //set add module code field
            Label addCodeLabel = new Label("Enter Module Code:");
            TextField addCodeField = new TextField();
            addCodeField.setEditable(true);


            //set add grade field
            Label addGradeLabel = new Label("Add Student Grade:");
            TextField addGradeField = new TextField();
            addGradeField.setEditable(true);

            //set add semester field
            Label addSemesterLabel = new Label("Add Module Semester:");
            TextField addSemesterField = new TextField();
            addGradeField.setEditable(true);

            //set find student field
            Label findStudentLabel = new Label("Find Student by Number:");
            TextField findStudentField = new TextField();
            findStudentField.setEditable(true);


            try {
                // Load the SQLite JDBC driver
                Class.forName("org.sqlite.JDBC");

                // Create a connection to the database
                String url = "jdbc:sqlite:src/main/java/com/example/assignment/studentDatabase.db";
                Connection connection = DriverManager.getConnection(url);

                // Execute the SQL query to select all students from the table
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM students");

                // Loop through the result set and append each student to the text area
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String studentNumber = resultSet.getString("student_number");
                    String date = resultSet.getString("dob");
                    String semester = resultSet.getString("semester");


                    studentArea.appendText("Name: " + name + "\n");
                    studentArea.appendText("Student Number: " + studentNumber + "\n");
                    studentArea.appendText("Date of Birth: " + date + "\n");
                    studentArea.appendText("Semester: " + semester + "\n\n");

                }

                // Close the database resources
                resultSet.close();
                statement.close();
                connection.close();

            } catch (ClassNotFoundException | SQLException e) {
                System.out.println(e.getMessage());
            }

            //set buttons for add student tab
            Button addButton = new Button("Add");
            addButton.setOnAction(e -> control.addStudent (nameField, numberField, dob, semesterField));

            Button removeButton = new Button("Remove");
            removeButton.setOnAction(e -> control.removeStudent(numberField));

            Button removeModuleButton = new Button("Remove");
            removeModuleButton.setOnAction(e -> control.removeModule(addCodeField));

            Button removeRecordButton = new Button("Remove");
            removeRecordButton.setOnAction(e -> control.removeRecord(pickModuleField, pickStudentField));

            Button editRecordButton = new Button("Edit");
            editRecordButton.setOnAction(e -> control.editRecord(pickModuleField, pickStudentField, addGradeField));

            Button listButton = new Button("List");
            listButton.setOnAction(e -> control.listStudent(studentArea));

            Button loadButton = new Button("Load");
            loadButton.setOnAction(e -> control.loadStudent(studentArea));

            Button loadModuleButton = new Button("Load");
            loadModuleButton.setOnAction(e -> control.loadModule(moduleArea));

            Button saveButton = new Button("Save");
            saveButton.setOnAction(e -> control.newStudent(nameField, numberField, dob, semesterField));

            Button addRecordButton = new Button("Add Record");
            addRecordButton.setOnAction(e -> control.addRecord(pickStudentField, pickModuleField, addGradeField));

            Button addModuleButton = new Button("Add Module");
            addModuleButton.setOnAction(e -> control.addModule(addModuleField, addCodeField, addSemesterField));

            Button findButton = new Button("Find Student");
            findButton.setOnAction(e -> control.findStudent(recordArea, findStudentField));

            Button exitButton = new Button("Exit");
            exitButton.setOnAction(e -> primaryStage.close());
            //end of buttons

            // set grid pane layout for  add student tab
            GridPane gridpane1 = new GridPane();
            gridpane1.setHgap(5);
            gridpane1.setVgap(10);

            gridpane1.add(nameLabel, 0, 0, 1, 1);
            gridpane1.add(nameField, 1, 0, 1, 1);
            gridpane1.add(numberLabel, 0, 1, 1, 1);
            gridpane1.add(numberField, 1, 1, 1, 1);
            gridpane1.add(dobLabel, 0, 2, 1, 1);
            gridpane1.add(dob, 1, 2, 1, 1);
            gridpane1.add(semesterLabel, 0, 3, 1, 1);
            gridpane1.add(semesterField, 1, 3, 1, 1);
            gridpane1.add(addButton, 0, 4, 1, 1);
            gridpane1.add(removeButton, 1, 4, 1, 1);
            gridpane1.add(listButton, 2, 4, 1, 1);
            gridpane1.add(studentArea, 0, 5, 3, 1);
            gridpane1.add(loadButton, 0, 6, 1, 1);
            gridpane1.add(saveButton, 1, 6, 1, 1);
            gridpane1.add(exitButton, 2, 6, 1, 1);

            addStudent.setContent(gridpane1); // to add to tabPane
            //end of layout for add student tab

            //grid pane layout for add Module tab
            GridPane gridpane2 = new GridPane();
            gridpane2.setHgap(5);
            gridpane2.setVgap(10);

            gridpane2.add(addModuleLabel, 0, 0, 1, 1);
            gridpane2.add(addModuleField, 1, 0, 1, 1);
            gridpane2.add(addCodeLabel, 0, 1, 1, 1);
            gridpane2.add(addCodeField, 1, 1, 1, 1);
            gridpane2.add(addSemesterLabel, 0, 2, 1, 1);
            gridpane2.add(addSemesterField, 1, 2, 1, 1);
            gridpane2.add(addModuleButton, 0, 3, 1, 1);
            gridpane2.add(loadModuleButton, 1, 3, 1, 1);
            gridpane2.add(removeModuleButton, 2, 3, 1, 1);
            gridpane2.add(moduleArea, 0, 4, 3, 1);



            addModule.setContent(gridpane2);

            //end of layout for add Module tab

            //grid pane layout for add Record tab
            GridPane gridpane3 = new GridPane();
            gridpane3.setHgap(5);
            gridpane3.setVgap(10);

            gridpane3.add(pickStudentLabel, 0, 0, 1, 1);
            gridpane3.add(pickStudentField, 1, 0, 1, 1);
            gridpane3.add(pickModuleLabel, 0, 1, 1, 1);
            gridpane3.add(pickModuleField, 1, 1, 1, 1);
            gridpane3.add(addGradeLabel, 0, 2, 1, 1);
            gridpane3.add(addGradeField, 1, 2, 1, 1);
            gridpane3.add(addRecordButton, 0, 3, 1, 1);
            gridpane3.add(editRecordButton, 1, 3, 1, 1);
            gridpane3.add(removeRecordButton, 2, 3, 1, 1);


            addRecord.setContent(gridpane3);

            //end of layout for add Module tab

            //grid pane layout for Search Student tab
            GridPane gridpane4 = new GridPane();
            gridpane4.setHgap(5);
            gridpane4.setVgap(10);

            gridpane4.add(findStudentLabel, 0, 0, 1, 1);
            gridpane4.add(findStudentField, 1, 0, 1, 1);
            gridpane4.add(findButton, 0, 1, 1, 1);
            gridpane4.add(recordArea, 0, 2, 3, 1);
            viewRecord.setContent(gridpane4);
            //end of layout for view Record tab


            Scene scene = new Scene(tabPane, 400, 400);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        public static void main(String[]args){
            launch(args);
        }
    }
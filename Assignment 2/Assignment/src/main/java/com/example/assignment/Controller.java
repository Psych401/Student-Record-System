package com.example.assignment;

import javafx.scene.control.*;

import java.sql.*;
import java.util.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The controller class manages the interactions between the user interface and the model in the application.
 */
public class Controller {

    /**
     * ArrayList to store student objects
     */
    ArrayList<Students> studentList = new ArrayList<>();

    /**
     * ArrayList to store record objects
     */
    ArrayList<Records> moduleList = new ArrayList<>();

    /**
     * ComboBox to display student names
     */
    ComboBox<String> studentBox = new ComboBox<>();

    /**
     * ComboBox to display student names for record purposes
     */
    ComboBox<String> studentBox2 = new ComboBox<>();

    /**
     * Initializes the SQLite database connection
     */
    public void initialize() {
        try {
            Connection connection = databaseConnection.getConnection();
            Statement statement = connection.createStatement();
            statement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new student object to the studentList ArrayList
     * @param nameField The text field for entering the student's name
     * @param numberField The text field for entering the student's number
     * @param dob The text field for entering the student's date of birth
     * @param semesterField The text field for entering the student's semester
     */
    void addStudent(TextField nameField, TextField numberField, TextField dob, TextField semesterField ) {
        String name = nameField.getText();
        String studentNumber = numberField.getText();
        String date = dob.getText();
        String semester = semesterField.getText();
        // We create the new student object
        Students newStudents = new Students(
                name,
                studentNumber,
                date,
                semester
        );

        // We add it to the students list
        this.studentList.add(newStudents);
    }

    /**
     * Adds a new student to the SQLite database
     * @param nameField The text field for entering the student's name
     * @param numberField The text field for entering the student's number
     * @param dob The text field for entering the student's date of birth
     * @param semesterField The text field for entering the student's semester
     */
    public void newStudent(TextField nameField, TextField numberField, TextField dob, TextField semesterField) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Load the SQLite JDBC driver and establish a connection to the database
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:src/main/java/com/example/assignment/studentDatabase.db");

            // Create the students table if it does not exist
            String createTableSql = "CREATE TABLE IF NOT EXISTS students (name TEXT, student_number TEXT, dob TEXT, semester TEXT)";
            preparedStatement = connection.prepareStatement(createTableSql);
            preparedStatement.executeUpdate();

            // Insert the new student data into the table
            String insertStudentSql = "INSERT INTO students (name, student_number, dob, semester) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(insertStudentSql);
            preparedStatement.setString(1, nameField.getText());
            preparedStatement.setString(2, numberField.getText());
            preparedStatement.setString(3, dob.getText());
            preparedStatement.setString(4, semesterField.getText());
            preparedStatement.executeUpdate();

            System.out.println("New student added to the database.");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    /**
     * Lists all the students in the studentList in the given TextArea.
     *
     * @param studentArea The TextArea in which to display the list of students.
     */
    void listStudent(TextArea studentArea) {
        studentArea.clear();
        for (int i = 0; i < studentList.size(); i++){
            studentArea.appendText("Student Name:" + studentList.get(i).getName() + "\n" + "Student Number:" + studentList.get(i).getStudentNumber() + "\n" + "Date of Birth:" + studentList.get(i).getDob() + "\n" + "Semester:" + studentList.get(i).getSemester() + "\n" + "\n");
        }
    }

    /**
     * Removes the student with the given student number from both the studentList and the database.
     *
     * @param numberField The TextField containing the student number of the student to remove.
     */
    public void removeStudent(TextField numberField) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/java/com/example/assignment/studentDatabase.db");
            Statement statement = connection.createStatement();

            String sql = "DELETE FROM students WHERE student_number = " + numberField.getText();
            int rowsDeleted = statement.executeUpdate(sql);
            if (rowsDeleted > 0) {
                System.out.println("Student with ID " + numberField.getText() + " was deleted successfully.");
            } else {
                System.out.println("No student with ID " + numberField.getText() + " was found in the database.");
            }

            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error removing student from database: " + e.getMessage());
        }

        this.studentList.removeIf(aux -> Objects.equals(numberField.getText(), aux.getStudentNumber()));

    }

    /**
     * Removes the module with the given code from the database.
     *
     * @param addcodeField The TextField containing the code of the module to remove.
     */
    public void removeModule(TextField addcodeField) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/java/com/example/assignment/studentDatabase.db");
            Statement statement = connection.createStatement();

            String sql = "DELETE FROM module WHERE code = " + addcodeField.getText();
            int rowsDeleted = statement.executeUpdate(sql);
            if (rowsDeleted > 0) {
                System.out.println("Module with code " + addcodeField.getText() + " was deleted successfully.");
            } else {
                System.out.println("No module with code " + addcodeField.getText() + " was found in the database.");
            }

            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error removing module from database: " + e.getMessage());
        }

    }

    /**
     * Loads all the students from the database and displays them in the given TextArea.
     *
     * @param studentArea The TextArea in which to display the list of students.
     */
    void loadStudent(TextArea studentArea){
        studentArea.clear();
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
                String dob = resultSet.getString("dob");
                String semester = resultSet.getString("semester");


                studentArea.appendText("Name: " + name + "\n");
                studentArea.appendText("Student Number: " + studentNumber + "\n");
                studentArea.appendText("Date of Birth: " + dob + "\n");
                studentArea.appendText("Semester: " + semester + "\n\n");

            }

            // Close the database resources
            resultSet.close();
            statement.close();
            connection.close();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Loads all the modules from the database and displays them in the given TextArea.
     *
     * @param moduleArea The TextArea in which to display the list of modules.
     */
    void loadModule(TextArea moduleArea){
        moduleArea.clear();
        try {
            // Load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");

            // Create a connection to the database
            String url = "jdbc:sqlite:src/main/java/com/example/assignment/studentDatabase.db";
            Connection connection = DriverManager.getConnection(url);

            // Execute the SQL query to select all modules from the table
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM module");

            // Loop through the result set and append each module to the text area
            while (resultSet.next()) {
                String module = resultSet.getString("module");
                String code = resultSet.getString("code");
                String semester = resultSet.getString("semester");


                moduleArea.appendText("Module Name: " + module + "\n");
                moduleArea.appendText("Module Code: " + code + "\n");
                moduleArea.appendText("Semester: " + semester + "\n\n");

            }

            // Close the database resources
            resultSet.close();
            statement.close();
            connection.close();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method adds a new record to the database.
     * @param pickStudentField TextField that contains the name of the student for the new record.
     * @param pickModuleField TextField that contains the module code for the new record.
     * @param addGradeField TextField that contains the grade for the new record.
     */
    void addRecord(TextField pickStudentField, TextField pickModuleField, TextField addGradeField) {
        String student = pickStudentField.getText();
        String code = pickModuleField.getText();
        String grade = addGradeField.getText();

        // create a connection to the database
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:src/main/java/com/example/assignment/studentDatabase.db")) {
            // create a statement
            Statement stmt = conn.createStatement();

            // create the record table if it does not exist
            String createTableQuery = "CREATE TABLE IF NOT EXISTS record (student TEXT, code TEXT, grade TEXT);";
            stmt.execute(createTableQuery);

            // insert the new record into the record table
            String insertQuery = "INSERT INTO record (student, code, grade) VALUES ('" + student + "', '" + code + "', '" + grade + "');";
            stmt.execute(insertQuery);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        // We create the new record object
        Records newRecords = new Records(
                student,
                code,
                grade
        );

        // We add it to the module list
        this.moduleList.add(newRecords);

        pickModuleField.setText("");
        addGradeField.setText("");
    }

    /**
     * This method removes a record from the database.
     * @param pickStudentField TextField that contains the name of the student for the record to be deleted.
     * @param pickModuleField TextField that contains the module code for the record to be deleted.
     */
    public void removeRecord(TextField pickModuleField, TextField pickStudentField) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/java/com/example/assignment/studentDatabase.db");
            Statement statement = connection.createStatement();

            String sql = "DELETE FROM record WHERE code = " + pickModuleField.getText() + " AND student = " + pickStudentField.getText();
            int rowsDeleted = statement.executeUpdate(sql);
            if (rowsDeleted > 0) {
                System.out.println("Record with code " + pickModuleField.getText() + " for student " + pickStudentField.getText() + " was deleted successfully.");
            } else {
                System.out.println("No record with code " + pickModuleField.getText() + " for student " + pickStudentField.getText() + " was found in the database.");
            }

            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error removing record from database: " + e.getMessage());
        }

    }

    /**
     * This method edits a record in the database.
     * @param pickStudentField TextField that contains the name of the student for the record to be edited.
     * @param pickModuleField TextField that contains the module code for the record to be edited.
     * @param addGradeField TextField that contains the new grade for the record to be edited.
     */
    public void editRecord(TextField pickModuleField, TextField pickStudentField, TextField addGradeField) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/java/com/example/assignment/studentDatabase.db");
            Statement statement = connection.createStatement();

            String sql = "UPDATE record SET grade = '" + addGradeField.getText() + "' WHERE code = '" + pickModuleField.getText() + "' AND student = '" + pickStudentField.getText() + "'";
            int rowsUpdated = statement.executeUpdate(sql);
            if (rowsUpdated > 0) {
                System.out.println("Record with code " + pickModuleField.getText() + " for student " + pickStudentField.getText() + " was updated successfully.");
            } else {
                System.out.println("No record with code " + pickModuleField.getText() + " for student " + pickStudentField.getText() + " was found in the database.");
            }

            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error editing record in database: " + e.getMessage());
        }

    }

    /**
     * Adds a new module to the database and creates a new Module object.
     *
     * @param addModuleField The TextField containing the name of the module to be added.
     * @param addCodeField The TextField containing the code of the module to be added.
     * @param addSemesterField The TextField containing the semester of the module to be added.
     */
    void addModule(TextField addModuleField, TextField addCodeField, TextField addSemesterField) {
        String module = addModuleField.getText();
        String code = addCodeField.getText();
        String semester = addSemesterField.getText();

        // create a connection to the database
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:src/main/java/com/example/assignment/studentDatabase.db")) {
            // create a statement
            Statement stmt = conn.createStatement();

            // create the module table if it does not exist
            String createTableQuery = "CREATE TABLE IF NOT EXISTS module (module TEXT, code TEXT, semester TEXT);";
            stmt.execute(createTableQuery);

            // insert the new module into the module table
            String insertQuery = "INSERT INTO module (module, code, semester) VALUES ('" + module + "', '" + code + "', '" + semester + "');";
            stmt.execute(insertQuery);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        addCodeField.setText("");
        addSemesterField.setText("");
    }

    /**
     * Finds a student's record in the database and displays it in the given TextArea.
     *
     * @param recordArea The TextArea in which to display the student's record.
     * @param findStudentField The TextField containing the student number to search for.
     */
    void findStudent(TextArea recordArea, TextField findStudentField){
        recordArea.clear();
        try {
            // Load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");

            // Create a connection to the database
            String url = "jdbc:sqlite:src/main/java/com/example/assignment/studentDatabase.db";
            Connection connection = DriverManager.getConnection(url);

            // Execute the SQL query to select all modules from the table
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM students WHERE student_number = " +  findStudentField.getText());

            // Loop through the result set and append the student to the text area
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String studentNumber = resultSet.getString("student_number");
                String dob = resultSet.getString("dob");
                String semester = resultSet.getString("semester");


                recordArea.appendText("Name: " + name + "\n");
                recordArea.appendText("Student Number: " + studentNumber + "\n");
                recordArea.appendText("Date of Birth: " + dob + "\n");
                recordArea.appendText("Semester: " + semester + "\n\n");

            }
            recordArea.appendText("Modules passed: \n\n");

            // Execute the SQL query to select all modules from the table
            Statement statement2 = connection.createStatement();
            ResultSet rs = statement2.executeQuery("SELECT module, grade FROM record INNER JOIN module ON record.code = module.code AND record.grade >= 40");
            // Loop through the result set and append the student to the text area
            while (rs.next()) {
                String module = rs.getString("module");
                String grade = rs.getString("grade");


                recordArea.appendText("Module: " + module + "\n");
                recordArea.appendText("Semester: " + grade + "\n\n");

            }

            // Close the database resources
            resultSet.close();
            statement.close();
            statement2.close();
            connection.close();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
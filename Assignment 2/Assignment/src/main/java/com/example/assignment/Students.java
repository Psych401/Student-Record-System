package com.example.assignment;

/**
 * The Students class represents a student.
 */
public class Students {
    //Fields
    private final String name;
    private final String studentNumber;
    private final String dob;
    private final String semester;

    /**
     * The constructor creates 1 instance (1 object) of the class Students<br>
     * @param _name - The name of the student.
     * @param _studentNumber - The student's student number.
     * @param _dob - The student's date of birth.
     * @param _semester - The semester the student is taking.
     */
    public Students(
            String _name,
            String _studentNumber,
            String _dob,
            String _semester
    ){
        this.name = _name;
        this.studentNumber = _studentNumber;
        this.dob = _dob;
        this.semester = _semester;

    }

    // Get Methods

    /**
     * Get the name of the student.
     * @return A string representing the name of the student.
     */
    public String getName(){return  this.name;}

    /**
     * Get the student number of the student.
     * @return A string representing the student number of the student.
     */
    public String getStudentNumber(){return  this.studentNumber;}

    /**
     * Get the date of birth of the student.
     * @return A string representing the date of birth of the student.
     */
    public String getDob(){return this.dob;}

    /**
     * Get the semester that the student is taking.
     * @return A string representing the semester the student is taking.
     */
    public String getSemester(){return this.semester;}

}
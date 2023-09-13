package com.example.assignment;

/**
 * The Records class represents a record of a student's grade in a specific module.
 */
public class Records {
    /**
     * The student number of the student.
     */
    private final String student;

    /**
     * The code of the module.
     */
    private final String code;

    /**
     * The grade the student received.
     */
    private final String grade;

    /**
     * Creates a new instance of the Records class with the specified student number, module code, and grade.
     * @param _student The student number of the student.
     * @param _code The code of the module.
     * @param _grade The grade the student received.
     */
    public Records(String _student, String _code, String _grade) {
        this.student = _student;
        this.code = _code;
        this.grade = _grade;
    }

    /**
     * Returns the student number of the student.
     * @return The student number.
     */
    public String getStudent() {
        return this.student;
    }

    /**
     * Returns the grade the student received.
     * @return The grade.
     */
    public String getGrade() {
        return this.grade;
    }

    /**
     * Returns the code of the module.
     * @return The module code.
     */
    public String getCode() {
        return this.code;
    }
}
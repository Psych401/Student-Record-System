package com.example.assignment;

/**
 * The Module class represents a module.
 */
public class Module {
    //Fields
    /**
     * The name of the module.
     */
    private final String module;
    /**
     * The code of the module.
     */
    private final String code;
    /**
     * The semester of the module.
     */
    private final String semester;

    //Constructor
    /**
     * The constructor creates 1 instance (1 object) of the class Module<br>
     * @param _module - The name of the module.
     * @param _code - The code of the module.
     * @param _semester - The semester of the module.
     */
    public Module(
            String _module,
            String _code,
            String _semester
    ){
        this.module = _module;
        this.code = _code;
        this.semester = _semester;

    }

    // Get Methods

    /**
     * Get the name of the module.
     * @return The name of the module.
     */
    public String getModule(){return  this.module;}

    /**
     * Get the code of the module.
     * @return The code of the module.
     */
    public String getCode(){return this.code;}

    /**
     * Get the semester of the module.
     * @return The semester of the module.
     */
    public String getSemester(){return this.semester;}

}
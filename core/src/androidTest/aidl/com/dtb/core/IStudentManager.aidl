// IStudentManager.aidl
package com.dtb.core;

// Declare any non-default types here with import statements
import com.dtb.core.Student;
interface IStudentManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
    List<Student> getStudents();
    void addStudent(in Student s);
}

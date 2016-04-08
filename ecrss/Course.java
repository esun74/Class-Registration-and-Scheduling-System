/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecrss;
import java.util.ArrayList;
/**
 *
 * @author user
 */
public class Course {
    private String courseName;
    private int time;
    private Room classRoom;
    private ArrayList<Student> roster;
    
    
    Course(String courseName, Room classroom, int time) { 
        setCourseName(courseName);
        setTime(time);
        this.classRoom = classroom;
        classroom.addCourse(this);
        roster = new ArrayList();
    }
    
    public void setCourseName(String name) {
        for (int i = 0; i < name.length(); i++) {
            if (!Character.isLetterOrDigit(name.charAt(i)) && !Character.isSpaceChar(name.charAt(i)))
                throw new IllegalArgumentException("Course name has to consist of alphabetic characters. Input was: " + name);
        }
        this.courseName = name;
    }
    public void setTime(int time) { this.time = time; }
    
    public String getCourseName() { return this.courseName;}
    public int getTime() { return this.time; }
    public int getRoomNumber() { return this.classRoom.getRoomNumber(); }
    public ArrayList<Student> getRoster() { return this.roster; }
    
    // checks if this student is already enrolled and if not, adds the student
    // also adds this class to the student's schedule
    public void addStudent(Student s) { 
        if (!containsStudent(s)) { 
            roster.add(s); 
            if (!s.hasClass(this)) s.addClass(this); 
        }
    //else System.out.println("addStudent from Course: Student " + s.getFirstName() + " " + s.getLastName() + " is either already enrolled or is tryng to double book. ");
    //if (containsStudent(s)) System.out.println("Did not add student to class because the student is already enrolled in this class. "
    
    }
    
    // removes the student if the student is registered
    public void removeStudent(Student s) {
        if (!containsStudent(s)) throw new IllegalArgumentException("Cannot find specified student to remove.");
        roster.remove(s);
    }
    
    // checks if the course contains this student
    public boolean containsStudent(Student s) { return roster.contains(s); }
    
    // checks if the course has no students
    public boolean isEmpty() { return roster.isEmpty(); }
    
    @Override
    public String toString() { return getCourseName() + " in room " + classRoom.getRoomNumber() + " at " + ((getTime() > 12) ? getTime() - 12 : getTime()); }
}

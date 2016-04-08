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
public class Student {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private ArrayList<Course> classes;
    
    Student(String firstname, String lastname, String phonenumber) {
        this.setFirstName(firstname);
        this.setLastName(lastname);
        this.setPhoneNumber(phonenumber);
        classes = new ArrayList();
    }
    
    // searches schedule time conflicts and adds if there are none. 
    // also adds this student to the class' student roster.
    public void addClass(Course c) { 
        for (Course schedule : classes)
            if (schedule.getTime() == c.getTime()) 
                throw new IllegalArgumentException(getFirstName() + " " + getLastName() + " did not add " + c.getCourseName() + " at " + c.getTime() + " because it conflicts with " + schedule.getCourseName() + " at " + schedule.getTime());
                
        // adds class to student
        classes.add(c); 
        
        // if the course does not have this student then add this student
        if (!c.containsStudent(this)) c.addStudent(this);
       
    }
    // didn't just removes the course from this student's schedule and removes this student from the class' roster. 
    public void removeClass(Course c) { classes.remove(c); if (c.containsStudent(this)) c.removeStudent(this); }
    
    // basic sets and gets making sure the names are all alphabetic
    public void setFirstName(String name) {
        if (name.length() < 1) throw new IllegalArgumentException("You didn't enter anything for the name, did you?");
        for (int i = 0; i < name.length(); i++) {
            if (!Character.isAlphabetic(name.charAt(i)))
                throw new IllegalArgumentException("First name has to consist of alphabetic characters.");
        }
        this.firstName = name;
    }
    public void setLastName(String name) {
        if (name.length() < 1) throw new IllegalArgumentException("You didn't enter anything for the last name!");
        for (int i = 0; i < name.length(); i++) {
            if (!Character.isAlphabetic(name.charAt(i)))
                throw new IllegalArgumentException("Last name has to consist of alphabetic characters.");
        }
        this.lastName = name;
    }
    
    // same thing but with digits
    public void setPhoneNumber(String number) {
        if (number.length() != 10) throw new IllegalArgumentException("Phone number has to have 10 digits");
        for(int i = 0; i < number.length(); i++)
            if (!Character.isDigit(number.charAt(i))) 
                throw new IllegalArgumentException("Phone number has to consist of numerical characters.");
        
        boolean allTheSame = true;
        char first = number.charAt(0);
        for (int i = 0; i < number.length(); i++) 
            if (first != number.charAt(i)) allTheSame = false;
        if (allTheSame) throw new IllegalArgumentException("Phone number has all the same digits");
        this.phoneNumber = number;
    }
    
    // getters
    public String getFirstName() { return this.firstName; }
    public String getLastName() { return this.lastName; }
    public String getPhoneNumber() { return this.phoneNumber; }
    
    // checks if the student has classes
    public boolean hasClasses() { return !this.classes.isEmpty(); }
    
    // checks if the student has class c
    public boolean hasClass(Course c) { return this.classes.contains(c); }
    
    // prints all the classes the student has
    // to do: say that there are no classes if there are none,
    public ArrayList<Course> getClasses() {
        return this.classes;
    }
    
    @Override
    public String toString() {
        return String.format("%-10s %-10s", this.getFirstName(), this.getLastName());
    }
}

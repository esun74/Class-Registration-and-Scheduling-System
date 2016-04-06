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
public class Room {
    private int roomNumber;
    
    private ArrayList<Course> classes;
    private boolean[] timeBooked;
    
    
    Room(int roomNumber) {
        setRoomNumber(roomNumber);
        classes = new ArrayList();
        // time is always equal to the element e.g 9 am = 9, 2 pm = 14, etc.
        timeBooked = new boolean[25];
    }

    // sets room number, checks if negative
    public void setRoomNumber(int roomNumber) {
        if (roomNumber < 0) throw new IllegalArgumentException("Room number is negative?");
        this.roomNumber = roomNumber;
    }
    
    // returns room number
    public int getRoomNumber() { return this.roomNumber; }
    
    // adds course and checks if it is already booked, if the course has a bad time, and if the time is already booked.
    public void addCourse(Course theClass) {
        if (classes.contains(theClass)) throw new IllegalArgumentException("Trying to book an already booked course.");
        if (theClass.getTime() < 9 || theClass.getTime() > 16) throw new IllegalArgumentException("Course time is not valid.");
        if (timeBooked[theClass.getTime()]) throw new IllegalArgumentException("Selected time is already booked.");
        
        // adds the class
        classes.add(theClass);
        
        // and marks the time as booked
        timeBooked[theClass.getTime()] = true;
    }
    
    // removes the course - checks if the class exists and if the time is booked - it should be
    public void removeCourse(Course aClass) {
        if (!classes.contains(aClass)) throw new IllegalArgumentException("Trying to remove a nonexistent course.");
        if (!timeBooked[aClass.getTime()]) throw new IllegalArgumentException("Course time is not booked?!");
        
        // removes the class
        classes.remove(aClass);
        
        // unbooks the class
        timeBooked[aClass.getTime()] = false;
    }
    
    // basically returns whether the room has classes
    public boolean isEmpty() { return classes.isEmpty(); }
    
    // prints the classes in the room
    public ArrayList<Course> getClasses() {
        return this.classes;
    }
    
    // simple tostring that prints room #
    @Override
    public String toString() { return "Room " + getRoomNumber(); }
    
}

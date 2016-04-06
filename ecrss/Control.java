/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecrss;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
/**
 *
 * @author user
 */
public class Control {
    private ArrayList<Room> rooms = new ArrayList();
    private ArrayList<Course> courses = new ArrayList();
    private ArrayList<Student> students = new ArrayList();
    private final Scanner input = new Scanner(System.in);
    private final int bufferSize = 1024;
    
    // no argument constructor
    Control() {
        rooms = new ArrayList();
        courses = new ArrayList();
        students = new ArrayList();
    }
    
    public Object[] returnNameStrings(int type) {
        ArrayList<String> returnArray = new ArrayList<>();
        switch(type) {
            case 1: for (Course c : courses) returnArray.add(c.getCourseName());
                break;
            case 2: for (Room r : rooms) returnArray.add("Room " + r.getRoomNumber());
                break;
            case 3: for (Student s : students) returnArray.add(s.getFirstName() + " " + s.getLastName());
                break;
            default: throw new IllegalArgumentException("returnNameStrings does not refer to a class, room, or student.");
        }
        return returnArray.toArray();
    }
    public Object[] returnNameStringsOfObject(int type, int index) {
        ArrayList<String> returnArray = new ArrayList<>();
        switch(type) {
            case 1: // course - student
                for (Student s : courses.get(index).getRoster()) returnArray.add(s.getFirstName() + " " + s.getLastName());
                break;
            case 2: // student - course
                for (Course c : students.get(index).getClasses()) returnArray.add(c.getCourseName());
                break;
            case 3: // rooms - classes
                for (Course c : rooms.get(index).getClasses()) returnArray.add(c.getCourseName());
                break;
            case 4: // time - classes
                for (Course c : courses) 
                    if (index + 9 == c.getTime())
                        returnArray.add(c.getCourseName());
                break;
            default: throw new IllegalArgumentException("returnNameStringsOfObject does not have a valid input");
        }
        return returnArray.toArray();
    }
    public void addClass(String className, int indexOfClassRoom, int classTime) {
        for (Course c : courses)
            if (c.getCourseName().equalsIgnoreCase(className)) 
                throw new IllegalArgumentException("This course already exists!");
        courses.add(new Course(className, rooms.get(indexOfClassRoom) , classTime));
    }
    public void addRoom(int roomNumber) {
        boolean roomNumberAlreadyTaken = false;
        for (Room r : rooms)
            if (r.getRoomNumber() == roomNumber)
                roomNumberAlreadyTaken = true;
        if (!roomNumberAlreadyTaken) rooms.add(new Room(roomNumber));
        else throw new IllegalArgumentException("There already exists a room " + roomNumber);        
    }
    public void addStudent(String firstName, String lastName, String phoneNumber) {
        for (Student s: students)
            if (firstName.equalsIgnoreCase(s.getFirstName()) && lastName.equalsIgnoreCase(s.getLastName()))
                throw new IllegalArgumentException("Student " + firstName + " " + lastName + " already exists");
        students.add(new Student(firstName, lastName, phoneNumber));        
    }
    public void addStudentToClass(int indexOfStudent, int indexOfClass) {
        students.get(indexOfStudent).addClass(courses.get(indexOfClass));
    }
    public void deleteClass(int indexOfClass) {
        if (courses.get(indexOfClass).getRoster().isEmpty()) {
            for (Room r : rooms)
                if (r.getRoomNumber() == courses.get(indexOfClass).getRoomNumber()) {
                    System.out.println("Removed course from room and course schedule");
                    r.removeCourse(courses.get(indexOfClass)); 
                    courses.remove(indexOfClass);
                    break;
                }
        }
        else throw new IllegalArgumentException("This class still contains " + courses.get(indexOfClass).getRoster().size() + " students!");    
    }
    public void deleteRoom(int indexOfRoom) {
        if (rooms.get(indexOfRoom).isEmpty()) rooms.remove(indexOfRoom);
        else throw new IllegalArgumentException("This room still has " + rooms.get(indexOfRoom).getClasses().size() + " courses booked!");        
    }
    public void deleteStudent(int indexOfStudent) {
        if (!students.get(indexOfStudent).hasClasses()) students.remove(students.get(indexOfStudent));
        else throw new IllegalArgumentException("This student still has " + students.get(indexOfStudent).getClasses().size() + " classes.");        
    }
    public void removeStudentFromClass(int indexOfStudent, int indexOfClass) {
        students.get(indexOfStudent).removeClass(courses.get(indexOfClass));
    }
    
    public void fileInitialization() {
        classFileInitialization("classdata.txt");
        studentFileInitialization("studentdata.txt");
        roomFileInitialization("roomdata.txt");
    }
    public void roomFileInitialization(String fileName) {
        // this is what the file looks for
        final String classRoomMarker = "Room ";

        // reads in the file
        try(FileInputStream inputStream = new FileInputStream(fileName);) {
            byte[] buffer  = new byte[bufferSize];
            while(inputStream.read(buffer) != -1) {
                String fileInput = new String(buffer);
                for(int i = 0; i < fileInput.length(); i++) {
                    // splits the file into lines
                    int lineLength = 0;
                    while (fileInput.charAt(i + lineLength) != '\n' && fileInput.charAt(i + lineLength) != '\r' && fileInput.charAt(i + lineLength) != Character.UNASSIGNED) {
                        if (fileInput.charAt(i + lineLength) == Character.UNASSIGNED) i = fileInput.length();
                            lineLength++;
                    }
                    
                    // in some systems \n\r is required so this
                    if (lineLength > 1) {
                        String line = new String(fileInput.substring(i, i + lineLength));
                        
                        
                        // if the line contains a classRoomMarker, split it into class name, room, and time
                        if (line.contains(classRoomMarker)) {
                            String classRoom = line.substring(line.indexOf(classRoomMarker) + 5, line.length());
                            
                            //System.out.println("ClassFileInitialization reads room " + classRoom);
                            boolean roomAlreadyExists = false;
                            // if there exists a room with the same number
                            for (Room r : rooms)
                                if (r.getRoomNumber() == Integer.parseInt(classRoom)) 
                                    roomAlreadyExists = true; 
                                
                            // if the classroom is not yet created
                            if (!roomAlreadyExists) 
                                rooms.add(new Room(Integer.parseInt(classRoom)));
                        }
                        
                        // if there is no classRoomMarker that means it is a student line
                        else throw new IllegalArgumentException("Room file has unreadable format. ");
                        
                        if (fileInput.charAt(i + lineLength) == Character.UNASSIGNED) i = fileInput.length();
                        else i += lineLength;
                    }
                } 
            }
            inputStream.close();
        }
        catch(FileNotFoundException e) { System.out.println("Unable to open file '" + fileName + "'"); }
        catch(IOException e) { System.out.println("Error reading file '" + fileName + "'"); }
    }
    public void studentFileInitialization(String fileName) {
        final char studentMarker = ',';
        try(FileInputStream inputStream = new FileInputStream(fileName);) {
            byte[] buffer  = new byte[bufferSize];
            while(inputStream.read(buffer) != -1) {
                String fileInput = new String(buffer);
                for(int i = 0; i < fileInput.length(); i++) {
                    int lineLength = 0;
                    while (fileInput.charAt(i + lineLength) != '\n' && fileInput.charAt(i + lineLength) != '\r' && fileInput.charAt(i + lineLength) != Character.UNASSIGNED) {
                        if (fileInput.charAt(i + lineLength) == Character.UNASSIGNED) i = fileInput.length();
                            lineLength++;
                    }
                    if (lineLength > 1) {
                        String line = new String(fileInput.substring(i, i + lineLength));
                      
                        if (line.indexOf(studentMarker) != -1) {
                            String lastName = line.substring(0, line.indexOf(studentMarker));
                            String firstName = line.substring(line.indexOf(studentMarker) + 2, line.lastIndexOf(studentMarker));
                            String phoneNumber = line.substring(line.lastIndexOf(studentMarker) + 2, line.length());
                            
                            boolean studentAlreadyExists = false;
                            for (Student s : students)
                                if (s.toString().equals(new Student(firstName, lastName, phoneNumber).toString()))
                                    studentAlreadyExists = true;
                            if (!studentAlreadyExists) students.add(new Student(firstName, lastName, phoneNumber));     

                        }
                        else throw new IllegalArgumentException("Student file should only contain student information.");
                        if (fileInput.charAt(i + lineLength) == Character.UNASSIGNED) i = fileInput.length();
                        else i += lineLength;
                    }
                } 
            }
            inputStream.close();
        }
        catch(FileNotFoundException e) { System.out.println("Unable to open file '" + fileName + "'"); }
        catch(IOException e) { System.out.println("Error reading file '" + fileName + "'"); }
       
    }
    public void classFileInitialization(String fileName) {
        final char classRoomMarker = '-';
        final char studentMarker = ',';
        
        // splits file into lines
        try(FileInputStream inputStream = new FileInputStream(fileName);) {
            byte[] buffer  = new byte[bufferSize];
            while(inputStream.read(buffer) != -1) {
                String fileInput = new String(buffer);
                for(int i = 0; i < fileInput.length(); i++) {
                    int lineLength = 0;
                    while (fileInput.charAt(i + lineLength) != '\n' && fileInput.charAt(i + lineLength) != '\r' && fileInput.charAt(i + lineLength) != Character.UNASSIGNED) {
                        if (fileInput.charAt(i + lineLength) == Character.UNASSIGNED) i = fileInput.length();
                            lineLength++;
                    }
                    if (lineLength > 1) {
                        String line = new String(fileInput.substring(i, i + lineLength));
                        
                        
                        // if the line contains a classRoomMarker, split it into class name, room, and time
                        if (line.indexOf(classRoomMarker) != -1) {
                            String className = line.substring(0, line.indexOf(classRoomMarker) - 1);
                            String classRoom = line.substring(line.indexOf(classRoomMarker) + 2, line.lastIndexOf(classRoomMarker) - 1);
                            String classTime = line.substring(line.lastIndexOf(classRoomMarker) + 2, line.length());
                            
                            boolean roomAlreadyExists = false;
                            // if there exists a room with the same number, add a new course to that room
                            for (Room r : rooms)
                                if (r.getRoomNumber() == Integer.parseInt(classRoom)) {
                                    //System.out.println("ClassFileInitialization adds class " + className + " using existing room " + classRoom);
                                    courses.add(new Course(className, r, Integer.parseInt(classTime)));  
                                    roomAlreadyExists = true; 
                                }
                            
                            // if the classroom is not yet created
                            if (!roomAlreadyExists) {
                                //System.out.println("ClassFileInitialiation adds class " + className + " and creates a new room " + classRoom);
                                // create a new room with room number from the line
                                rooms.add(new Room(Integer.parseInt(classRoom)));
                                
                                // create a course using the topmost room (the one we just created)
                                courses.add(new Course(className, rooms.get(rooms.size() - 1), Integer.parseInt(classTime)));
                            } 
                        }
                        
                        // if there is no classRoomMarker that means it is a student line
                        else {
                            // splits the student line into first and last name along with the phone number
                            String lastName = line.substring(0, line.indexOf(studentMarker));
                            String firstName = line.substring(line.indexOf(studentMarker) + 2, line.lastIndexOf(studentMarker));
                            String phoneNumber = line.substring(line.lastIndexOf(studentMarker) + 2, line.length());
                            
                            //System.out.println("ClassFileInitialization adds student " + firstName + " " + lastName);
                            
                            boolean studentAlreadyExists = false;
                            
                            // checks if a student already exists with the same toString
                            for (Student s : students)
                                if (s.toString().equals((new Student(firstName, lastName, phoneNumber)).toString())) {
                                    s.addClass(courses.get(courses.size() - 1));
                                    studentAlreadyExists = true;
                                }
                            
                            // if the student does not exist
                            if (!studentAlreadyExists) {
                                // adds the new student
                                students.add(new Student(firstName, lastName, phoneNumber)); 
                                // topmost student .addClass topmost course
                                students.get(students.size() - 1).addClass(courses.get(courses.size() - 1));
                            }  
                        }
                        if (fileInput.charAt(i + lineLength) == Character.UNASSIGNED) i = fileInput.length();
                        else i += lineLength;
                    }
                } 
            }
            inputStream.close();
        }
        catch(FileNotFoundException e) { System.out.println("Unable to open file '" + fileName + "'"); }
        catch(IOException e) { System.out.println("Error reading file '" + fileName + "'"); }
        
    }
    public void fileUpdate() {
        classFileUpdate();
        roomFileUpdate();
        studentFileUpdate();
    }
    public void classFileUpdate() {
        String fileName = "classdata.txt";
        String fileInput = new String();
        for (Course c : courses) {
            fileInput += (c.getCourseName() + " - " + c.getRoomNumber() + " - " + c.getTime() + "\r\n");
            for (Student s : c.getRoster())
                fileInput += (s.getLastName() + ", " + s.getFirstName() + ", " + s.getPhoneNumber() + "\r\n");
            fileInput += "\r\n";
        }
        fileUpdate(fileName, fileInput);
    }
    public void roomFileUpdate() {
        String fileName = "roomdata.txt";
        String fileInput = new String();
        for (Room r : rooms)
                fileInput += (r.toString() + "\r\n");
        fileUpdate(fileName, fileInput);
    }
    public void studentFileUpdate() {
        String fileName = "studentdata.txt";
            String fileInput = new String();
                for (Student s : students) 
                    fileInput += (s.getLastName() + ", " + s.getFirstName() + ", " + s.getPhoneNumber() + "\r\n");
            fileUpdate(fileName, fileInput);
    }
    public void fileUpdate(String fileName, String fileInput) {
        try(FileOutputStream outputStream = new FileOutputStream(fileName);) {
            
            byte[] buffer = fileInput.getBytes();

            outputStream.write(buffer);

            outputStream.close();       

        }
        catch(IOException exception) { System.out.println("Error writing to file '"+ fileName + "'"); }
    }
    public int askUser(String... options) {
        String askUser = "";
        int i = 1;
        for (String s : options) {
            askUser += String.format("%d. \t%s%n", i, s);
            i++;
        }
        int response = numberInputValidation(askUser);
        if (response > options.length || response < 1) throw new IllegalArgumentException("Response is not a valid choice."); 
        return response;
    }
    public int numberInputValidation(String inputDialog) {
        String userStringResponse = JOptionPane.showInputDialog(inputDialog);
        int userIntegerResponse = Integer.parseInt(userStringResponse);
        System.out.println("User entered " + userIntegerResponse);
        return userIntegerResponse;
    }
    public ArrayList<String> returnStringArray(int type) {
        ArrayList<String> returnArray = new ArrayList<>();
        switch(type) {
            case 1: 
                for (Course c : courses) {
                    returnArray.add(c.getCourseName() + " - " + c.getRoomNumber() + " - " + c.getTime() + "\r\n");
                    for (Student s : c.getRoster())
                        returnArray.add(s.getFirstName() + " " + s.getLastName() + "\r\n");
                    returnArray.add("\r\n");
                }
                break;
            default: throw new IllegalArgumentException("returnStringArray does not have a valid input");
        }
        return returnArray;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecrss;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.*;
/**
 *
 * @author user
 */
public class ecrssFrame extends JFrame {
    private Control program = new Control();
    private final JPanel westernPanel = new JPanel();
    private final JPanel centralPanel = new JPanel();
    private final JPanel easternPanel = new JPanel();
    private final String[] primaryMenuStrings = {
        "Add", 
        "Delete", 
        "Display"};
    private final String[] addMenuStrings = {
        "Class", 
        "Room", 
        "Student", 
        "Students to a Class"};
    private final String[] deleteMenuStrings = {
        "Class", 
        "Room", 
        "Student", 
        "Students from a Class"};
    private final String[] displayMenuStrings = {
        "Classes - Students", 
        "Students - Classes", 
        "Rooms - Classes", 
        "Times - Classes"};
    private final String[] timeStrings = { 
        "9-10 AM", 
        "10-11 AM", 
        "11-12 AM", 
        "12-1 PM", 
        "1-2 PM", 
        "2-3 PM", 
        "3-4 PM", 
        "4-5 PM" };
    private final Box primaryBox = new Box(BoxLayout.PAGE_AXIS);
    private final Box secondaryBox = new Box(BoxLayout.PAGE_AXIS);
    private final Box tertiaryBox = new Box(BoxLayout.PAGE_AXIS);
    private JComboBox primaryMenu;
    private JList secondaryMenu;
    
    private final int SELECT_COURSES = 1;
    private final int SELECT_ROOMS = 2;
    private final int SELECT_STUDENTS = 3;
    
    private int currentPrimaryMenu;
    private int currentSecondaryMenu;
    
    ecrssFrame() {
        super("Eric's ECRSS");
        /*
        // for convenience
        if (JOptionPane.showConfirmDialog(null, "Do you want to load some test data?", "Auto-Load 2000", JOptionPane.YES_NO_OPTION)
                == JOptionPane.OK_OPTION) 
            program.fileInitialization();
        */
        // some code from Professor Ibrahim
        Toolkit tool = Toolkit.getDefaultToolkit();
        Dimension screenSize = tool.getScreenSize();
        int screenH = screenSize.height;
        int screenW = screenSize.width;
        this.setLocation(screenW/4, screenH/4); 
        
        // sets the panels
        westernPanel.setName("Western Panel");
        centralPanel.setName("Central Panel");
        easternPanel.setName("Eastern Panel");
        add(westernPanel, BorderLayout.WEST);
        add(centralPanel, BorderLayout.CENTER);
        add(easternPanel, BorderLayout.EAST);
        
        
        updatePanels(1, 0);

    }
    
    // converts arrays to ArrayLists (String)
    public final ArrayList<String> array_To_ArrayList(String[] strings) {
        return new ArrayList<>(java.util.Arrays.asList(strings));
    }
    
    // creates the descriptive labels on the top of the box
    public void createLabel(String labelString, Box destination) {
        JLabel label = new JLabel(labelString);
        label.setPreferredSize(new Dimension(150, 20));
        destination.add(label);
        destination.add(Box.createVerticalStrut(20));
    }
    
    // standard format of a list on the left and options on the right
    public void standardFormat(String title1, String title2, JList display, JButton button, JComponent... components) {
        
        // start with a clean slate
        clearPanel(centralPanel);
        clearPanel(easternPanel);
        secondaryBox.removeAll();
        tertiaryBox.removeAll();
        
        // creates the label
        createLabel(title1, secondaryBox);

        // sets up the list and adds it
        display.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        display.setFixedCellWidth(100);
        display.setVisibleRowCount(7);
        secondaryBox.add(new JScrollPane(display));

        //---------------------------------------

        // creates the second label
        createLabel(title2, tertiaryBox);
        
        // sets all of them to be a uniform size
        for (JComponent c : components)
            c.setPreferredSize(new Dimension(100, 20));
        
        // adds them into boxes in pairs
        Box[] componentBox = new Box[components.length / 2];
        for (int i = 0; i < components.length / 2; i++) {
            componentBox[i] = new Box(BoxLayout.LINE_AXIS);
            componentBox[i].add(components[2 * i]);
            componentBox[i].add(components[2 * i + 1]);
        }

        // adds the boxes
        for (int i = 0; i < components.length / 2; i++) {
            tertiaryBox.add(componentBox[i]);
            tertiaryBox.add(Box.createVerticalStrut(20));
        }
        
        // adds the button that does things
        tertiaryBox.add(button);
        
        // adds the boxes to their panels
        centralPanel.add(secondaryBox);
        easternPanel.add(tertiaryBox);
    }
    
    // format with two lists
    public void doubleListFormatTemplate(String title1, String title2, JList display1, JList display2) {
        
        // clears things
        clearPanel(centralPanel);
        clearPanel(easternPanel);
        secondaryBox.removeAll();
        tertiaryBox.removeAll();
        
        createLabel(title1, secondaryBox);

        // basically sets up lists
        display1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        display1.setFixedCellWidth(100);
        display1.setFixedCellHeight(18);
        display1.setVisibleRowCount(7);
        secondaryBox.add(new JScrollPane(display1));

        //---------------------------------------

        createLabel(title2, tertiaryBox);
        
        display2.setFixedCellWidth(100);
        display2.setFixedCellHeight(18);
        display2.setVisibleRowCount(7);
        tertiaryBox.add(new JScrollPane(display2));

    }
    
    // without a button
    public void doubleListFormat(String title1, String title2, JList display1, JList display2) {
        doubleListFormatTemplate(title1, title2, display1, display2);
        centralPanel.add(secondaryBox);
        easternPanel.add(tertiaryBox);
    }
    
    // with a button
    public void doubleListFormat(String title1, String title2, JList display1, JList display2, JButton button) {
        doubleListFormatTemplate(title1, title2, display1, display2);
        tertiaryBox.add(button);   
        centralPanel.add(secondaryBox);
        easternPanel.add(tertiaryBox);
    }
    
    // updates the panels
    public final void updatePanels(int primaryMenuChoice, int secondaryMenuChoice) {
        
        // if the user changes the left menu (add/delete/display)
        if (primaryMenuChoice != currentPrimaryMenu) {
            clearPanel(westernPanel);
            clearPanel(easternPanel);
            clearPanel(centralPanel);

            // empties the box and puts in the combobox
            primaryBox.removeAll();
            primaryMenu = new JComboBox(primaryMenuStrings);
            primaryMenu.setSelectedItem(primaryMenuStrings[primaryMenuChoice - 1]);
            primaryMenu.setPreferredSize(new Dimension(150, 25));
            //System.out.println("Added primaryMenu");
            primaryBox.add(primaryMenu);
            primaryBox.add(Box.createVerticalStrut(20));
            // what to do if an element in the combobox is selected
            primaryMenu.addItemListener(
                new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent event) {
                        if (event.getStateChange() == ItemEvent.SELECTED) {
                            //System.out.println("primaryMenu updated panel - " + (array_To_ArrayList(primaryMenuStrings).indexOf(event.getItem()) + 1) + " " + 0 + " " + 0);
                            updatePanels((array_To_ArrayList(primaryMenuStrings).indexOf(event.getItem()) + 1), 0);
                        }
                    }
                }
            );

            // determines what to put in the left list
            switch(primaryMenuChoice) {
                case 1: secondaryMenu = new JList(addMenuStrings);
                    break;
                case 2: secondaryMenu = new JList(deleteMenuStrings);
                    break;
                case 3: secondaryMenu = new JList(displayMenuStrings);
                    break;
                default: throw new IllegalArgumentException("secondaryMenuChoice is not valid");
            }

            secondaryMenu.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            secondaryMenu.setFixedCellWidth(150);
            //System.out.println("Added secondaryMenu");
            primaryBox.add(secondaryMenu);
            secondaryMenu.addListSelectionListener(
                new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent event) {
                        if (event.getValueIsAdjusting()) {
                            //System.out.println("secondaryMenu updated panel - " + currentPrimaryMenu + " " + (secondaryMenu.getSelectedIndex() + 1) + " " + 0);
                            updatePanels(currentPrimaryMenu, (secondaryMenu.getSelectedIndex() + 1));
                        }
                    } 
                }
            );
            
            // creates the save and load button
            final JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Serialized Objects", "ser"));
            JButton loadButton = new JButton("Load");
            JButton saveButton = new JButton("Save");

            // LOAD LOAD LOAD LOAD LOAD LOAD LOAD LOAD
            loadButton.addActionListener(new ActionListener() {
                @Override public void actionPerformed(ActionEvent event) {
                    // if the user presses "open" then run this
                    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                        try {
                            FileInputStream ifstream = new FileInputStream(chooser.getSelectedFile().getPath());
                            ObjectInputStream fInput = new ObjectInputStream (ifstream);
                            Object thing = fInput.readObject();
                            if (thing instanceof Control) program = (Control) thing;
                            else { JOptionPane.showMessageDialog(null, "Class is not of type Control."); }
                        }
                        catch (FileNotFoundException e) { 
                            JOptionPane.showMessageDialog(null, "File not found.");
                        }
                        catch (ClassNotFoundException e) {
                            JOptionPane.showMessageDialog(null, "A class cannot be found.");
                        }
                        catch (IOException e) {
                            JOptionPane.showMessageDialog(null, "Incompatible File, please try again.");
                            System.out.println(e.fillInStackTrace());
                        }
                    }
                    
                    // refresh
                    updatePanels(currentPrimaryMenu, currentSecondaryMenu);

                }
            });
            
            // SAVE SAVE SAVE SAVE SAVE SAVE SAVE SAVE
            saveButton.addActionListener(new ActionListener() {
                @Override public void actionPerformed(ActionEvent event) {
                    try {
                        // if no file is selected yet, ask user - if user does not choose a file, do nothing
                        if (chooser.getSelectedFile() == null) 
                            if (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) return;

                        // write control into file
                        FileOutputStream ofstream = new FileOutputStream(chooser.getSelectedFile().getPath());
                        ObjectOutputStream fOutput = new ObjectOutputStream (ofstream);
                        fOutput.writeObject(program);
                    }
                    catch(FileNotFoundException e) {
                            JOptionPane.showMessageDialog(null, "File not found??");
                    }
                    catch(IOException e) {
                            JOptionPane.showMessageDialog(null, "IOException thrown. (save button)");
                            System.out.println(e.fillInStackTrace());
                    }
                }
            });
            primaryBox.add(Box.createVerticalStrut(30));
            
            // creates a box for the save and load buttons
            Box saveloadBox = new Box(BoxLayout.LINE_AXIS);
            saveloadBox.add(loadButton);
            saveloadBox.add(Box.createHorizontalStrut(5));
            saveloadBox.add(saveButton);
            primaryBox.add(saveloadBox);

            westernPanel.add(primaryBox);
        }
        else {
            clearPanel(easternPanel);
            clearPanel(centralPanel);
        }
        
        // a lot of this is copy and paste but I can't really do anything since 
        // I can't pass listeners into a method without being able to access the textfields and lists  and stuff
        // switch between add/delete/display
        switch(primaryMenuChoice) {
            case 1: // add
                switch(secondaryMenuChoice) {
                    case 0: // no choice chosen
                        clearPanel(easternPanel);
                        clearPanel(centralPanel);
                        break;
                    case 1: // add class
                        final JTextField classNameField = new JTextField();
                        final JComboBox classRoomCbBox = new JComboBox(program.returnNameStrings(SELECT_ROOMS));
                        final JComboBox classTimeCbBox = new JComboBox(timeStrings);
                        final JButton addClassButton = new JButton("Add");
                        addClassButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                if (classNameField.getText().equals("")) return;
                                if (classRoomCbBox.getSelectedIndex() == -1) return;
                                try {
                                    program.addClass(classNameField.getText(), classRoomCbBox.getSelectedIndex(), classTimeCbBox.getSelectedIndex() + 9);
                                }
                                catch (IllegalArgumentException exception){
                                    JOptionPane.showMessageDialog(centralPanel, exception.getMessage(), "Error!", JOptionPane.WARNING_MESSAGE);
                                }
                                updatePanels(currentPrimaryMenu, currentSecondaryMenu);
                            }
                        });
                        
                        standardFormat(
                                "Current Classes", 
                                "Add Class", 
                                new JList(program.returnNameStrings(SELECT_COURSES)), 
                                addClassButton,
                                new JLabel("Class Name:"), 
                                classNameField, 
                                new JLabel("Class Room:"), 
                                classRoomCbBox, 
                                new JLabel("Class Time:"), 
                                classTimeCbBox);                        
                        break;
                        
                    case 2: // add room
                        final JTextField addRoomField = new JTextField();
                        final JButton addRoomButton = new JButton("Add");
                        addRoomButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                try {
                                    program.addRoom(Integer.parseInt(addRoomField.getText()));
                                }
                                catch (NumberFormatException exception) {
                                    JOptionPane.showMessageDialog(centralPanel, "That either wasn't a number or it was too large!", "Error!", JOptionPane.WARNING_MESSAGE);
                                }
                                catch (IllegalArgumentException exception) {
                                    JOptionPane.showMessageDialog(centralPanel, exception.getMessage(), "Error!", JOptionPane.WARNING_MESSAGE);
                                }
                                updatePanels(currentPrimaryMenu, currentSecondaryMenu);
                            }
                        });
                        standardFormat(
                                "Current Rooms", 
                                "Add Room", 
                                new JList(program.returnNameStrings(SELECT_ROOMS)), 
                                addRoomButton,
                                new JLabel("Room Number:"),
                                addRoomField);
                        break;
                    case 3: // add student
                        final JTextField addStudentFirstName = new JTextField();
                        final JTextField addStudentLastName = new JTextField();
                        final JTextField addStudentPhoneNumber = new JTextField();
                        final JButton addStudentButton = new JButton("Add");
                        addStudentButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                try {
                                    program.addStudent(addStudentFirstName.getText(), addStudentLastName.getText(), addStudentPhoneNumber.getText());
                                }
                                catch (IllegalArgumentException exception) {
                                    JOptionPane.showMessageDialog(centralPanel, exception.getMessage(), "Error!", JOptionPane.WARNING_MESSAGE);
                                }
                                    
                                updatePanels(currentPrimaryMenu, currentSecondaryMenu);
                            }
                        });
                        standardFormat(
                                "Current Students", 
                                "Add Student", 
                                new JList(program.returnNameStrings(SELECT_STUDENTS)), 
                                addStudentButton,
                                new JLabel("First Name:"),
                                addStudentFirstName,
                                new JLabel("Last Name:"),
                                addStudentLastName,
                                new JLabel("Phone Number:"),
                                addStudentPhoneNumber);
                        break;
                    case 4: // add student to class
                        final JList addToClassList = new JList(program.returnNameStrings(SELECT_COURSES));
                        final JList addThisStudentList = new JList();
                        addThisStudentList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                        final JButton addStudentToClassButton = new JButton("Add");
                        final ArrayList<Student> possibleStudents = new ArrayList();
                        addToClassList.addListSelectionListener(new ListSelectionListener() {
                            @Override public void valueChanged(ListSelectionEvent event) {
                                possibleStudents.removeAll(program.getStudents());
                                for (Student s : program.getStudents()) {
                                    boolean timeConflict = false;
                                    // check if the student has a class at the same time
                                    for (Course c : s.getClasses())
                                        if (c.getTime() == program.getCourses().get(addToClassList.getSelectedIndex()).getTime())
                                            timeConflict = true;
                                    if (!timeConflict) possibleStudents.add(s);
                                }
                                addThisStudentList.setListData(possibleStudents.toArray());
                            }
                        }); 
                        addStudentToClassButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                ArrayList<Student> studentsToAdd = new ArrayList();
                                for (int i : addThisStudentList.getSelectedIndices()) { studentsToAdd.add(possibleStudents.get(i)); }
                                for (Student s : studentsToAdd) {program.addStudentToClass(program.getStudents().indexOf(s), addToClassList.getSelectedIndex());}
                                
                                possibleStudents.removeAll(program.getStudents());
                                for (Student s : program.getStudents()) {
                                    boolean timeConflict = false;
                                    // check if the student has a class at the same time
                                    for (Course c : s.getClasses())
                                        if (c.getTime() == program.getCourses().get(addToClassList.getSelectedIndex()).getTime())
                                            timeConflict = true;
                                    if (!timeConflict) possibleStudents.add(s);
                                }
                                addThisStudentList.setListData(possibleStudents.toArray());
                            }
                        });
                        doubleListFormat(
                                "Courses",
                                "Available Students",
                                addToClassList,
                                addThisStudentList,
                                addStudentToClassButton
                                );
                        break;
                    default: throw new IllegalArgumentException("secondaryMenuChoice is not valid.");
                }
                break;
            case 2: // delete
                switch(secondaryMenuChoice) {
                    case 0: // no choice chosen
                        clearPanel(easternPanel);
                        clearPanel(centralPanel);
                        break;
                    case 1: // delete class
                        final ArrayList<Course> deletableCourses = new ArrayList();
                        for (Course c : program.getCourses())
                            if (c.isEmpty()) deletableCourses.add(c);
                        final JList deleteClassList = new JList(deletableCourses.toArray());
                        final JButton deleteClassButton = new JButton("Delete");
                        deleteClassButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                if (deleteClassList.getSelectedIndex() == -1) return;
                                    program.deleteClass(program.getCourses().indexOf(deletableCourses.get(deleteClassList.getSelectedIndex())));
                                updatePanels(currentPrimaryMenu, currentSecondaryMenu);
                            }
                        });
                        standardFormat(
                                "Deletable Classes", 
                                "Delete Selected Class", 
                                deleteClassList, 
                                deleteClassButton);
                        break;
                    case 2: // delete room
                        final ArrayList<Room> deletableRooms = new ArrayList();
                        for (Room r : program.getRooms())
                            if (r.isEmpty()) deletableRooms.add(r);
                        final JList deleteRoomList = new JList(deletableRooms.toArray());
                        final JButton deleteRoomButton = new JButton("Delete");
                        deleteRoomButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                if (deleteRoomList.getSelectedIndex() == -1) return;
                                    program.deleteRoom(
                                            program.getRooms().indexOf(
                                                    deletableRooms.get(
                                                            deleteRoomList.getSelectedIndex())));
                                updatePanels(currentPrimaryMenu, currentSecondaryMenu);
                            }
                        });
                        standardFormat(
                                "Deletable Rooms", 
                                "Delete Selected Room", 
                                deleteRoomList, 
                                deleteRoomButton);
                        break;
                    case 3: // delete student
                        final ArrayList<Student> deletableStudents = new ArrayList();
                        for (Student s : program.getStudents())
                            if (!s.hasClasses()) deletableStudents.add(s);
                        final JList deleteStudentList = new JList(deletableStudents.toArray());
                        final JButton deleteStudentButton = new JButton("Delete");
                        deleteStudentButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                if (deleteStudentList.getSelectedIndex() == -1) return;
                                program.deleteStudent(
                                        program.getStudents().indexOf(
                                                deletableStudents.get(
                                                        deleteStudentList.getSelectedIndex()
                                                )
                                        )
                                );
                                updatePanels(currentPrimaryMenu, currentSecondaryMenu);
                            }
                        });
                        standardFormat(
                                "Deletable Students", 
                                "Delete Selected Student", 
                                deleteStudentList, 
                                deleteStudentButton);
                        break;
                    case 4: // remove student from class
                        final JList removeFromClassList = new JList(program.returnNameStrings(SELECT_COURSES));
                        final JList removeThisStudentList = new JList();
                        removeThisStudentList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                        final JButton removeStudentFromClassButton = new JButton("Drop");
                        final ArrayList<Student> enrolledStudents = new ArrayList();
                        removeFromClassList.addListSelectionListener(new ListSelectionListener() {
                            @Override public void valueChanged(ListSelectionEvent event) {
                                enrolledStudents.removeAll(program.getStudents());
                                enrolledStudents.addAll(
                                        program.getCourses().get(
                                                removeFromClassList.getSelectedIndex()
                                        ).getRoster()
                                );
                                removeThisStudentList.setListData(enrolledStudents.toArray());
                            }}); 
                        removeStudentFromClassButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                
                                // basically gets the students that were selected and removes them
                                ArrayList<Student> dropStudents = new ArrayList();
                                for (int i : removeThisStudentList.getSelectedIndices()) dropStudents.add(enrolledStudents.get(i));
                                for (Student s : dropStudents) { program.removeStudentFromClass(program.getStudents().indexOf(s), removeFromClassList.getSelectedIndex()); }
                                
                                // updates the lists in a very roundabout way
                                // but it works! ¯\_(ツ)_/¯
                                enrolledStudents.removeAll(program.getStudents());
                                enrolledStudents.addAll(
                                        program.getCourses().get(
                                                removeFromClassList.getSelectedIndex()
                                        ).getRoster()
                                );
                                removeThisStudentList.setListData(enrolledStudents.toArray());
                            }
                        });
                        doubleListFormat(
                                "Courses",
                                "Enrolled Students",
                                removeFromClassList,
                                removeThisStudentList,
                                removeStudentFromClassButton
                                );
                        break;
                    default: throw new IllegalArgumentException("secondaryMenuChoice is not valid. ");
                }
                break;
            case 3: // display
                switch(secondaryMenuChoice) {
                    case 0: // no choice chosen
                        clearPanel(easternPanel);
                        clearPanel(centralPanel);
                        break;
                    case 1: // classes - students
                        final JList selectionList1 = new JList(program.returnNameStrings(SELECT_COURSES));
                        selectionList1.addListSelectionListener(new ListSelectionListener() {
                            @Override public void valueChanged(ListSelectionEvent event) {
                                doubleListFormat(
                                        "Courses", 
                                        "Students", 
                                        selectionList1,
                                        new JList(
                                                program.getCourses().get(
                                                        selectionList1.getSelectedIndex()
                                                ).getRoster().toArray()
                        ));}}); 
                        doubleListFormat("Courses", "Students", selectionList1, new JList());
                        break;
                    case 2: // students - classes
                        final JList selectionList2 = new JList(program.returnNameStrings(SELECT_STUDENTS));
                        selectionList2.addListSelectionListener(new ListSelectionListener() {
                            @Override public void valueChanged(ListSelectionEvent event) {
                                doubleListFormat(
                                        "Students", 
                                        "Courses", 
                                        selectionList2, 
                                        new JList(
                                                program.getStudents().get(
                                                        selectionList2.getSelectedIndex()
                                                ).getClasses().toArray()
                        ));}}); 
                        doubleListFormat("Students", "Courses", selectionList2, new JList());
                        break;
                    case 3: // rooms - classes
                        final JList selectionList3 = new JList(program.returnNameStrings(SELECT_ROOMS));
                        selectionList3.addListSelectionListener(new ListSelectionListener() {
                            @Override public void valueChanged(ListSelectionEvent event) {
                                doubleListFormat(
                                        "Rooms", 
                                        "Courses", 
                                        selectionList3,
                                        new JList(
                                                program.getRooms().get(
                                                        selectionList3.getSelectedIndex()
                                                ).getClasses().toArray()
                        ));}});
                        doubleListFormat("Rooms", "Courses", selectionList3, new JList());
                        break;
                    case 4: // time - classes
                        final JList selectionList4 = new JList(timeStrings);
                        selectionList4.addListSelectionListener(new ListSelectionListener() {
                            @Override public void valueChanged(ListSelectionEvent event) {
                                ArrayList<Course> coursesNow = new ArrayList();
                                for (Course c : program.getCourses())
                                    if (c.getTime() == selectionList4.getSelectedIndex() + 9)
                                        coursesNow.add(c);
                                doubleListFormat("Times", "Courses", selectionList4, 
                                        new JList(
                                                coursesNow.toArray()
                        ));}}); 
                        doubleListFormat("Times", "Courses", selectionList4, new JList());
                        break;
                    default: throw new IllegalArgumentException("secondaryMenuChoice is not valid. "); 
                }
                break;
            default: throw new IllegalArgumentException("primaryMenuChoice is not valid");
        }
        currentPrimaryMenu = primaryMenuChoice;
        currentSecondaryMenu = secondaryMenuChoice;
    }
    
    // clears a particular panel
    public void clearPanel(JPanel panel) {
        //System.out.println("Cleared " + panel.getName());
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
    }   
}
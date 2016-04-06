
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
/**
 *
 * @author user
 */
public class ecrssFrame extends JFrame {
    private final Control program = new Control();
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
        "Student to a Class"};
    private final String[] deleteMenuStrings = {
        "Class", 
        "Room", 
        "Student", 
        "Student from a Class"};
    private final String[] displayMenuStrings = {
        "Classes - Students", 
        "Students - Classes", 
        "Rooms - Classes", 
        "Times - Classes", 
        "All courses and students"};
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
        
        program.fileInitialization();
        
        westernPanel.setName("Western Panel");
        centralPanel.setName("Central Panel");
        easternPanel.setName("Eastern Panel");
        add(westernPanel, BorderLayout.WEST);
        add(centralPanel, BorderLayout.CENTER);
        add(easternPanel, BorderLayout.EAST);
        
        updatePanels(1, 0, 0);

    }

    
    public final ArrayList<String> array_To_ArrayList(String[] strings) {
        return new ArrayList<>(java.util.Arrays.asList(strings));
    }
    public void standardFormat(String title1, String title2, JList display, JButton button, JComponent... components) {
        
        JLabel label1 = new JLabel(title1);
        label1.setPreferredSize(new Dimension(150, 20));
        secondaryBox.add(label1);

        secondaryBox.add(Box.createVerticalStrut(20)); 

        display.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        display.setFixedCellWidth(100);
        display.setVisibleRowCount(7);
        secondaryBox.add(new JScrollPane(display));

        //---------------------------------------

        JLabel label2 = new JLabel(title2);
        label2.setPreferredSize(new Dimension(150, 20));
        tertiaryBox.add(label2);

        tertiaryBox.add(Box.createVerticalStrut(20));
        
        for (JComponent c : components)
            c.setPreferredSize(new Dimension(100, 20));
        
        Box[] componentBox = new Box[components.length / 2];
        for (int i = 0; i < components.length / 2; i++) {
            componentBox[i] = new Box(BoxLayout.LINE_AXIS);
            componentBox[i].add(components[2 * i]);
            componentBox[i].add(components[2 * i + 1]);
        }

        for (int i = 0; i < components.length / 2; i++) {
            tertiaryBox.add(componentBox[i]);
            tertiaryBox.add(Box.createVerticalStrut(20));
        }
        
        tertiaryBox.add(button);
    }
    public void doubleListFormat(String title1, String title2, JList display1, JList display2) {
        JLabel label1 = new JLabel(title1);
        label1.setPreferredSize(new Dimension(150, 20));
        secondaryBox.add(label1);

        secondaryBox.add(Box.createVerticalStrut(20)); 

        display1.setFixedCellWidth(100);
        display1.setVisibleRowCount(7);
        secondaryBox.add(new JScrollPane(display1));

        //---------------------------------------

        JLabel label2 = new JLabel(title2);
        label2.setPreferredSize(new Dimension(150, 20));
        tertiaryBox.add(label2);

        tertiaryBox.add(Box.createVerticalStrut(20));
        
        display2.setFixedCellWidth(100);
        display2.setVisibleRowCount(7);
        tertiaryBox.add(new JScrollPane(display2));

    }
    public void doubleListFormat(String title1, String title2, JList display1, JList display2, JButton button) {
        doubleListFormat(title1, title2, display1, display2);
        tertiaryBox.add(button);        
    }
    public void comboAndListFormat(String title1, String title2, JComboBox display1, JList display2) {
        JLabel label1 = new JLabel(title1);
        label1.setPreferredSize(new Dimension(150, 20));
        secondaryBox.add(label1);

        secondaryBox.add(Box.createVerticalStrut(20)); 

        display1.setPreferredSize(new Dimension(100, 20));
        secondaryBox.add(display1);

        //---------------------------------------

        JLabel label2 = new JLabel(title2);
        label2.setPreferredSize(new Dimension(150, 20));
        tertiaryBox.add(label2);

        tertiaryBox.add(Box.createVerticalStrut(20));
        
        display2.setFixedCellWidth(100);
        display2.setVisibleRowCount(7);
        tertiaryBox.add(new JScrollPane(display2));
    }
    
    
    public final void updatePanels(int primaryMenuChoice, int secondaryMenuChoice, int tertiaryMenuChoice) {
        
        secondaryBox.removeAll();
        tertiaryBox.removeAll();
        
        if (primaryMenuChoice != currentPrimaryMenu)
            initializeWesternPanel(primaryMenuChoice);
        else {
            clearPanel(easternPanel);
            clearPanel(centralPanel);
        }
        
        switch(primaryMenuChoice) {
            case 1: // add
                switch(secondaryMenuChoice) {
                    case 0: // no choice chosen
                        System.out.println("add - no choice");
                        break;
                    case 1: // add class
                        final JTextField classNameField = new JTextField();
                        final JComboBox classRoomCbBox = new JComboBox(program.returnNameStrings(SELECT_ROOMS));
                        final JComboBox classTimeCbBox = new JComboBox(timeStrings);
                        final JButton addClassButton = new JButton("Add");
                        addClassButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                try {
                                    program.addClass(classNameField.getText(), classRoomCbBox.getSelectedIndex(), classTimeCbBox.getSelectedIndex() + 9);
                                }
                                catch (IllegalArgumentException exception){
                                    JOptionPane.showMessageDialog(centralPanel, exception.getMessage(), "Error!", JOptionPane.WARNING_MESSAGE);
                                }
                                updatePanels(currentPrimaryMenu, currentSecondaryMenu, 0);
                            }
                        });
                        
                        standardFormat(
                                "Current Classes", 
                                "Add Class", 
                                new JList(program.returnNameStrings(SELECT_COURSES)), 
                                addClassButton,
                                new JLabel("Class Name: "), 
                                classNameField, 
                                new JLabel("Class Room: "), 
                                classRoomCbBox, 
                                new JLabel("Class Time: "), 
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
                                updatePanels(currentPrimaryMenu, currentSecondaryMenu, 0);
                            }
                        });
                        standardFormat(
                                "Current Rooms", 
                                "Add Room", 
                                new JList(program.returnNameStrings(SELECT_ROOMS)), 
                                addRoomButton,
                                new JLabel("Room Number: "),
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
                                    
                                updatePanels(currentPrimaryMenu, currentSecondaryMenu, 0);
                            }
                        });
                        standardFormat(
                                "Current Students", 
                                "Add Student", 
                                new JList(program.returnNameStrings(SELECT_STUDENTS)), 
                                addStudentButton,
                                new JLabel("First Name: "),
                                addStudentFirstName,
                                new JLabel("Last Name: "),
                                addStudentLastName,
                                new JLabel("Phone Number: "),
                                addStudentPhoneNumber);
                        break;
                    case 4: // add student to class
                        final JList addToClassList = new JList(program.returnNameStrings(SELECT_COURSES));
                        addToClassList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                        final JList addThisStudentList = new JList(program.returnNameStrings(SELECT_STUDENTS));
                        addThisStudentList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                        final JButton addStudentToClassButton = new JButton("Add");
                        addStudentToClassButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                int[] classesSelected = addToClassList.getSelectedIndices();
                                int[] studentsSelected = addThisStudentList.getSelectedIndices();
                                for (int i = 0; i < classesSelected.length; i++)
                                    for (int ii = 0; ii < studentsSelected.length; ii++) {
                                        try {
                                        program.addStudentToClass(studentsSelected[ii], classesSelected[i]);
                                        }
                                        catch (IllegalArgumentException exception) {
                                            JOptionPane.showMessageDialog(centralPanel, exception.getMessage(), "Error!", JOptionPane.WARNING_MESSAGE);
                                        }
                                        updatePanels(currentPrimaryMenu, currentSecondaryMenu, 0);
                                    }
                            }
                        });
                        doubleListFormat(
                                "Courses",
                                "Students",
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
                        System.out.println("delete - no choice");
                        break;
                    case 1: // delete class
                        final JList deleteClassList = new JList(program.returnNameStrings(SELECT_COURSES));
                        final JButton deleteClassButton = new JButton("Delete");
                        deleteClassButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                try {
                                    program.deleteClass(deleteClassList.getSelectedIndex());
                                }
                                catch (IllegalArgumentException exception) {
                                    JOptionPane.showMessageDialog(centralPanel, exception.getMessage(), "Error!", JOptionPane.WARNING_MESSAGE);
                                }
                                updatePanels(currentPrimaryMenu, currentSecondaryMenu, 0);
                            }
                        });
                        standardFormat(
                                "Current Classes", 
                                "Delete Selected Class", 
                                deleteClassList, 
                                deleteClassButton);
                        break;
                    case 2: // delete room
                        final JList deleteRoomList = new JList(program.returnNameStrings(SELECT_ROOMS));
                        final JButton deleteRoomButton = new JButton("Delete");
                        deleteRoomButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                try {
                                    program.deleteRoom(deleteRoomList.getSelectedIndex());
                                }
                                catch (IllegalArgumentException exception) {
                                    JOptionPane.showMessageDialog(centralPanel, exception.getMessage(), "Error!", JOptionPane.WARNING_MESSAGE);
                                }
                                updatePanels(currentPrimaryMenu, currentSecondaryMenu, 0);
                            }
                        });
                        standardFormat(
                                "Current Rooms", 
                                "Delete Selected Room", 
                                deleteRoomList, 
                                deleteRoomButton);
                        break;
                    case 3: // delete student
                        final JList deleteStudentList = new JList(program.returnNameStrings(SELECT_STUDENTS));
                        final JButton deleteStudentButton = new JButton("Delete");
                        deleteStudentButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                try {
                                    program.deleteStudent(deleteStudentList.getSelectedIndex());
                                }
                                catch (IllegalArgumentException exception) {
                                    JOptionPane.showMessageDialog(centralPanel, exception.getMessage(), "Error!", JOptionPane.WARNING_MESSAGE);
                                }
                                updatePanels(currentPrimaryMenu, currentSecondaryMenu, 0);
                            }
                        });
                        standardFormat(
                                "Current Students", 
                                "Delete Selected Student", 
                                deleteStudentList, 
                                deleteStudentButton);
                        break;
                    case 4: // remove student from class
                        final JList removeFromClassList = new JList(program.returnNameStrings(SELECT_COURSES));
                        removeFromClassList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                        final JList removeThisStudentList = new JList(program.returnNameStrings(SELECT_STUDENTS));
                        removeThisStudentList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                        final JButton removeStudentFromClassButton = new JButton("Drop");
                        removeStudentFromClassButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                int[] classesSelected = removeFromClassList.getSelectedIndices();
                                int[] studentsSelected = removeThisStudentList.getSelectedIndices();
                                for (int i = 0; i < classesSelected.length; i++)
                                    for (int ii = 0; ii < studentsSelected.length; ii++) {
                                        try {
                                            program.removeStudentFromClass(studentsSelected[ii], classesSelected[i]);
                                        }
                                        catch (IllegalArgumentException exception) {
                                            JOptionPane.showMessageDialog(centralPanel, exception.getMessage(), "Error!", JOptionPane.WARNING_MESSAGE);
                                        }
                                        updatePanels(currentPrimaryMenu, currentSecondaryMenu, 0);
                                    }
                            }
                        });
                        doubleListFormat(
                                "Courses",
                                "Students",
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
                        System.out.println("display - no choice");
                        break;
                    case 1: // classes - students
                        comboBoxDisplay("Courses", "Students", program.returnNameStrings(SELECT_COURSES), secondaryMenuChoice, tertiaryMenuChoice);
                        break;
                    case 2: // students - classes
                        comboBoxDisplay("Students", "Courses", program.returnNameStrings(SELECT_STUDENTS), secondaryMenuChoice, tertiaryMenuChoice);
                        break;
                    case 3: // rooms - classes
                        comboBoxDisplay("Rooms", "Courses", program.returnNameStrings(SELECT_ROOMS), secondaryMenuChoice, tertiaryMenuChoice);
                        break;
                    case 4: // time - classes
                        comboBoxDisplay("Times", "Courses", timeStrings, secondaryMenuChoice, tertiaryMenuChoice);
                        break;
                    case 5: // all courses and students
                        JList displayAll = new JList(program.returnStringArray(1).toArray());
                        displayAll.setVisibleRowCount(20);
                        JOptionPane.showMessageDialog(null, new JScrollPane(displayAll), "All Items", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    default: throw new IllegalArgumentException("secondaryMenuChoice is not valid. "); 
                }
                break;
            default: throw new IllegalArgumentException("primaryMenuChoice is not valid");
        }
        centralPanel.add(secondaryBox);
        easternPanel.add(tertiaryBox);
        currentPrimaryMenu = primaryMenuChoice;
        currentSecondaryMenu = secondaryMenuChoice;
    }
    
    public void comboBoxDisplay(String title1, String title2, Object[] nameStrings, int type, int tertiaryMenuChoice) {
        final JComboBox selectionComboBox = new JComboBox(nameStrings);
        final JList displayList = new JList(program.returnNameStringsOfObject(type, tertiaryMenuChoice));
        selectionComboBox.setSelectedIndex(tertiaryMenuChoice);
        selectionComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                updatePanels(currentPrimaryMenu, currentSecondaryMenu, selectionComboBox.getSelectedIndex());
            }
        });
        comboAndListFormat(
                title1,
                title2,
                selectionComboBox,
                displayList
        );
    }
    
    
    
    public void initializeWesternPanel(int primaryMenuChoice) {
        clearPanel(westernPanel);
        clearPanel(easternPanel);
        clearPanel(centralPanel);
        
        primaryBox.removeAll();
        primaryMenu = new JComboBox(primaryMenuStrings);
        primaryMenu.setSelectedItem(primaryMenuStrings[primaryMenuChoice - 1]);
        primaryMenu.setPreferredSize(new Dimension(250, 25));
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
                        updatePanels((array_To_ArrayList(primaryMenuStrings).indexOf(event.getItem()) + 1), 0, 0);
                    }
                }
            }
        );



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
        secondaryMenu.setFixedCellWidth(250);
        //System.out.println("Added secondaryMenu");
        primaryBox.add(secondaryMenu);
        secondaryMenu.addListSelectionListener(
            new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent event) {
                    if (event.getValueIsAdjusting()) {
                        //System.out.println("secondaryMenu updated panel - " + currentPrimaryMenu + " " + (secondaryMenu.getSelectedIndex() + 1) + " " + 0);
                        updatePanels(currentPrimaryMenu, (secondaryMenu.getSelectedIndex() + 1), 0);
                    }
                } 
            }
        );


        westernPanel.add(primaryBox);
    }
    
    public void clearPanel(JPanel panel) {
        //System.out.println("Cleared " + panel.getName());
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
    }
    
}


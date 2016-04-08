package ecrss;
import javax.swing.JFrame;
/**
 *
 * @author user
 */

// todo: 
//
// known issues: 
// sometimes rooms get saved out of order e.g. 1 2 3 4 99 5
public class ECRSS {
    public static void main(String[] args) {
        ecrssFrame frame = new ecrssFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 250);
        frame.setVisible(true); 
    } 
}

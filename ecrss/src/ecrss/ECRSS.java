package ecrss;
import javax.swing.JFrame;
/**
 *
 * @author user
 */

// todo: 
//      test window centered-ness on other screen sizes
// known issues: 
//

public class ECRSS {
    public static void main(String[] args) {
        ecrssFrame frame = new ecrssFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 250);
        frame.setVisible(true); 
    } 
}

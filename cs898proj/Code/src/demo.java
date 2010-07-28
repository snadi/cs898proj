import javax.swing.JFrame;
import javax.swing.UIManager;

import cs898.project.ApplicationFrame;
import java.awt.Dimension;


public class demo {
	public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
//                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    System.err.println("Couldn't use system look and feel.");
                }

                JFrame frame = new ApplicationFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
                frame.setPreferredSize(new Dimension(1000, 600));
                frame.pack();
                frame.setVisible(true);
            }
        });
	}
}

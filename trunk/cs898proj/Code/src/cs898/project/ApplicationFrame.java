package cs898.project;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class ApplicationFrame extends JFrame {

    private JTabbedPane mainPane;

    public ApplicationFrame() {
        super("CS898 - Demo Application");
        buildGui();
    }

    private void buildGui() {
        mainPane = new JTabbedPane();
        mainPane.setFont(new Font(Font.SERIF, Font.BOLD, 16));

        mainPane.addTab("Scenarios", new ScenarioPanel());
        mainPane.addTab("Free Query", new QueryRunnerPanel());
                      
        add(mainPane);

        
        mainPane.setBackgroundAt(0, Utils.getScenarioTabColor());        
        mainPane.setBackgroundAt(1, Utils.getQueryTabColor());        
    }
}

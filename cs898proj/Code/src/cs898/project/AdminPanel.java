package cs898.project;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class AdminPanel extends JPanel {

	private static final long serialVersionUID = 3932974320457310549L;
	
	private JTabbedPane pane;
	
	public AdminPanel() {
		super(new GridLayout(1,0));
		buildGui();
	}
	
	private void buildGui() {
		pane = new JTabbedPane(JTabbedPane.RIGHT);
		add(pane);
	}
	
}

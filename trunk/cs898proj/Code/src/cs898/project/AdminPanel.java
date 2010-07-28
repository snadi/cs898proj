package cs898.project;

import java.awt.GridLayout;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class AdminPanel extends JPanel {

	private JTabbedPane pane;
	
	public AdminPanel() {
		super(new GridLayout(1,0));
		buildGui();
	}
	
	private void buildGui() {
		pane = new JTabbedPane(JTabbedPane.RIGHT);
		
		//pane.addTab("Rules", new FileEditorComponent(new File(Configuration.getRulesFileLocation())));
		//pane.addTab("Facts", new FileEditorComponent(new File(Configuration.getFactsFileLocation())));
		add(pane);
	}
	
}

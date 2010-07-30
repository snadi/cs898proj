package cs898.project;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import cs898.project.engine.EulerEngineWrapper;

public class QueryRunnerPanel extends JPanel {

	private static final long serialVersionUID = -6112526766243667533L;
	
	private JTextArea query;
    private FileEditorComponent facts;
    private FileEditorComponent rules;
    private JTextArea result;
    private JButton run;
    private JButton reset;

    public QueryRunnerPanel() {
        super(new BorderLayout());
        buildGui();
    }

    private void buildGui() {

        add(buildTopPanel(), BorderLayout.NORTH);
        add(buildMainPanel(), BorderLayout.CENTER);
        add(buildResultPanel(), BorderLayout.SOUTH);

    }

    private JPanel buildTopPanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel queryLabel = new JLabel("Query: ");
        queryLabel.setFont(Utils.getLabelFont());
        p.add(queryLabel);
        p.add(new JScrollPane(query = new JTextArea(5, 50)));

        run = new JButton("Execute");
        run.setFont(Utils.getButtonFont());
        run.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String r = null;
                try {
                    String queryString = query.getText();
                    r = EulerEngineWrapper.evaluate(facts.getText(), rules.getText(), queryString);
                    
                } catch (Exception ex) {
                    ex.printStackTrace();
                    r = ex.getMessage();
                }
                result.setText(r);
            }
        });
        p.add(run);


        reset = new JButton("Reset");
        reset.setFont(Utils.getButtonFont());
        reset.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                query.setText(null);
                facts.clearFields();
                rules.clearFields();
                result.setText(null);
            }
        });
        p.add(reset);
        p.setBackground(Utils.getQueryTabColor());

        return p;
    }

    private JPanel buildMainPanel() {
        JPanel p = new JPanel(new GridLayout(1, 1));

        JPanel factsPanel = new JPanel();
        factsPanel.setLayout(new BoxLayout(factsPanel, BoxLayout.Y_AXIS));        
        facts = new FileEditorComponent(new File(Configuration.getFactsFileLocation()), "  Facts:");
        factsPanel.add(facts);
        factsPanel.setBackground(Utils.getQueryTabColor());
        p.add(factsPanel);

        JPanel rulesPanel = new JPanel();
        rulesPanel.setLayout(new BoxLayout(rulesPanel, BoxLayout.Y_AXIS));        
        rulesPanel.add(rules = new FileEditorComponent(new File(Configuration.getRulesFileLocation()), "  Rules:"));
        rulesPanel.setBackground(Utils.getQueryTabColor());
        p.add(rulesPanel);

        
        
        return p;
    }

    private JPanel buildResultPanel() {
        JPanel p = new JPanel(new GridLayout(1, 0));

        p.add(new JScrollPane(result = new JTextArea(10, 50)));

        return p;
    }
}

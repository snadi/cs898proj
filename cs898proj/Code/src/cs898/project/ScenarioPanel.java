package cs898.project;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import cs898.project.Configuration.Doctor;
import cs898.project.Configuration.Document;
import cs898.project.Configuration.Scenario;
import cs898.project.engine.EulerEngineWrapper;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JOptionPane;

public class ScenarioPanel extends JPanel {

    private JComboBox doctorsComboBox;
    private JComboBox documentsComboBox;
    private ActionListener listener = new MyActionListener();
    private JTabbedPane tabbedPane;
//    private JTextArea doctorInfo;
//    private JTextArea documentInfo;
    private JLabel iconLabel;
    private JLabel resultStatus;
    private ResultDisplayComponent results;
    private JButton testButton;
    private JPanel northPanel;
    private JPanel centerPanel;

    public ScenarioPanel() {
        super(new BorderLayout());

        buildNorthPanel();
        buildMainPanel();

        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        doctorsComboBox.setSelectedIndex(1);
        documentsComboBox.setSelectedIndex(0);

    }

    private void buildNorthPanel() {

        doctorsComboBox = new JComboBox(Configuration.listDoctors());
        documentsComboBox = new JComboBox(Configuration.listDocuments());
        doctorsComboBox.addActionListener(listener);
        documentsComboBox.addActionListener(listener);

        northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        northPanel.add(doctorsComboBox);
        JLabel accessLabel = new JLabel(" access ");
        accessLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        northPanel.add(accessLabel);
        northPanel.add(documentsComboBox);

        testButton = new JButton("Check Access");
        testButton.setFont(new Font("Arial", Font.BOLD, 14));
        northPanel.add(testButton);
        testButton.addActionListener(listener);

        northPanel.add(resultStatus = new JLabel(""));
        resultStatus.setFont(new Font(null, Font.BOLD, 14));

        northPanel.setBackground(Utils.getScenarioTabColor());

    }

    private void buildMainPanel() {
        centerPanel = new JPanel(new BorderLayout());

        JPanel pn = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        doctorInfo = new JTextArea(5, 20);
//        doctorInfo.setText("d");

//        documentInfo = new JTextArea(5, 20);
//        documentInfo.setText("doc");
//        pn.add(new JScrollPane(doctorInfo));
//        pn.add(new JScrollPane(documentInfo));

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setFont(new Font(null, Font.BOLD, 14));


        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        iconLabel = new JLabel("Icon goes Here");
        iconLabel.setMinimumSize(new Dimension(600, 400));
        imagePanel.setBackground(Color.WHITE);
        imagePanel.add(iconLabel);

        JPanel proofPanel = new JPanel(new GridLayout(1, 0));
        results = new ResultDisplayComponent();
        proofPanel.add(new JScrollPane(results));



        tabbedPane.addTab("Figure", imagePanel);
        tabbedPane.addTab("Proof", proofPanel);

        centerPanel.add(pn, BorderLayout.NORTH);
        centerPanel.add(tabbedPane, BorderLayout.CENTER);
        pn.setBackground(Utils.getScenarioTabColor());
        centerPanel.setBackground(Utils.getScenarioTabColor());
    }

    private class MyActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == doctorsComboBox || e.getSource() == documentsComboBox) {
                populateScenarioInfo();

            } else if (e.getSource() == testButton) {
                runQuery();
            }

        }
    }

    private Scenario getScenario() {
        Doctor d = (Doctor) doctorsComboBox.getSelectedItem();
        Document doc = (Document) documentsComboBox.getSelectedItem();
        return Configuration.query(d, doc);
    }

    private void populateScenarioInfo() {
        Doctor d = (Doctor) doctorsComboBox.getSelectedItem();
//        doctorInfo.setText(d.getInformation());

        Document doc = (Document) documentsComboBox.getSelectedItem();
//        documentInfo.setText(doc.getInformation());

        Scenario s = Configuration.query(d, doc);

        //clear the fields
        iconLabel.setText(null);
        iconLabel.setIcon(null);
        results.setText(null);
        resultStatus.setText(null);
        tabbedPane.setSelectedIndex(0); //set to the image

        if (s == null) {
            testButton.setEnabled(false);
            return;
        }

        testButton.setEnabled(true);
        iconLabel.setIcon(new ImageIcon(s.getIcon()));

    }

    private void runQuery() {
        Scenario s = getScenario();
        if (s == null) {
            return;
        }

        String query = s.getQuery();
        String result = null;
        try {
            result = EulerEngineWrapper.evaluate(
                    Configuration.getProjectFactsFromFile(),
                    Configuration.getProjectRulesFromFile(),
                    query);

            tabbedPane.setSelectedIndex(1);


            if (Utils.isProofSuccessful(result)) {
                JOptionPane.showMessageDialog(this, "Access Granted!", "Results", JOptionPane.INFORMATION_MESSAGE);
                resultStatus.setForeground(new Color(69, 139, 116));
                resultStatus.setText("Access Granted!");


            } else {
                JOptionPane.showMessageDialog(this, "Access Denied!", "Results", JOptionPane.INFORMATION_MESSAGE);
                resultStatus.setForeground(Color.red);
                resultStatus.setText("Access Denied!");
                result = EulerEngineWrapper.evaluate(
                        Configuration.getProjectFactsFromFile(),
                        Configuration.getProjectRulesFromFile(),
                        query.replace(":access", ":deny"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            result = e.getMessage();
        }

        results.setText(result);
    }
}

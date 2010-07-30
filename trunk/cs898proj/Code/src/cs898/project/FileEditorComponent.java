package cs898.project;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class FileEditorComponent extends JPanel {

	private static final long serialVersionUID = 6305475780523535986L;
	
	private File file;
    private JTextArea contents;
    private JButton saveButton;
    private JButton reloadButton;
    private String title;

    public FileEditorComponent(File file, String title) {
        super(new BorderLayout());
        setFile(file);
        this.title = title;
        buildGui();
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        if (file == null) {
        }

        if (!file.exists()) {
            throw new RuntimeException("File does not exist. " + file);
        }
        if (!file.canRead()) {
            throw new RuntimeException("File can not be read from. " + file);
        }
        if (!file.canWrite()) {
            throw new RuntimeException("File can not be written to. " + file);
        }

        this.file = file;
    }

    public void clearFields() {
    }

    protected void buildGui() {
        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Utils.saveToFile(file, contents.getText());
                setButtons(false);
            }
        });
        saveButton.setEnabled(false);
        saveButton.setFont(Utils.getButtonFont());

        reloadButton = new JButton("Reload");
        reloadButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                contents.setText(Utils.fileToText(file));
                setButtons(false);
            }
        });
        reloadButton.setEnabled(false);
        reloadButton.setFont(Utils.getButtonFont());

        JPanel titlePanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(reloadButton);
        buttonPanel.add(new JLabel("    "));
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(Utils.getLabelFont());
        titlePanel.setBackground(Utils.getQueryTabColor());
        buttonPanel.setBackground(Utils.getQueryTabColor());
        titlePanel.add(titleLabel, BorderLayout.WEST);
        titlePanel.add(buttonPanel, BorderLayout.EAST);


        add(titlePanel, BorderLayout.NORTH);

        JPanel textPanel = new JPanel(new GridLayout(1, 0));
        contents = new JTextArea(Utils.fileToText(file));
        textPanel.add(new JScrollPane(contents));
        contents.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                setButtons(true);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                setButtons(true);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                setButtons(true);
            }
        });
        add(textPanel, BorderLayout.CENTER);
    }

    private void setButtons(boolean value) {
        saveButton.setEnabled(value);
        reloadButton.setEnabled(value);
    }

    public String getText() {
        return contents.getText();
    }
}

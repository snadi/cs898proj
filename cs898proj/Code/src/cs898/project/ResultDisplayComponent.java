package cs898.project;

import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class ResultDisplayComponent extends JTextPane {

	private static final long serialVersionUID = -3298254534428708939L;

	public ResultDisplayComponent() {
		StyledDocument doc = getStyledDocument();
        addStylesToDocument(doc);
		setEditable(false);
		setBackground(Color.WHITE);
	}
	
	private void addStylesToDocument(StyledDocument doc) {
		Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
		StyleConstants.setFontFamily(def, "SansSerif");

		Style regular = doc.addStyle("regular", def);
		StyleConstants.setFontSize(regular, 18);
		
		Style prefix  = doc.addStyle("prefix", regular);
		StyleConstants.setItalic(prefix, true);
		StyleConstants.setForeground(prefix, Color.MAGENTA);
		
		Style comments  = doc.addStyle("comments", regular);
		StyleConstants.setForeground(comments, Color.GRAY);
		
		Style noproof  = doc.addStyle("noproof", regular);
		StyleConstants.setForeground(noproof, Color.RED);
		StyleConstants.setBold(noproof, true);
		
		Style prooffound  = doc.addStyle("prooffound", regular);
		StyleConstants.setForeground(prooffound, Color.BLUE);
		StyleConstants.setBold(prooffound, true);
		
		Style object  = doc.addStyle("object", regular);
		StyleConstants.setForeground(object, Color.RED);
		
		Style verb  = doc.addStyle("verb", regular);
		StyleConstants.setForeground(verb, Color.GREEN);
		StyleConstants.setBold(verb, true);
		
		Style subject  = doc.addStyle("subject", regular);
		StyleConstants.setForeground(subject, Color.BLUE);

		Style evidence  = doc.addStyle("evidence", regular);
		StyleConstants.setForeground(evidence, Color.RED);
		StyleConstants.setBold(evidence, true);
	}
	
	@Override
	public void setText(String t) {
		
		if(t == null){
			super.setText(t);
			return;
		}
		
		StyledDocument doc = getStyledDocument();
		
		Style s = null;
		
		int pos = 0;
		try {
			String[] lines = t.split("\n");
			
			for (String line : lines) {
				if(line.trim().toLowerCase().startsWith("@prefix")){
					s = doc.getStyle("prefix");
				} else if(line.trim().toLowerCase().startsWith("# no proof found")) {
					s = doc.getStyle("noproof");
				} else if(line.trim().toLowerCase().startsWith("# proof found")) {
					s = doc.getStyle("prooffound");
				} else if(line.trim().toLowerCase().startsWith("#")) {
					s = doc.getStyle("comments");
				} else if(line.trim().toLowerCase().startsWith("{") && line.trim().length() >1) {
					pos = writeProofLine(line, doc, pos);
					continue;
				} else {
					s = doc.getStyle("regular");
				}
				
				doc.insertString(pos, line+"\n", s);
				pos=pos+(line.length()+1);
			}
			
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	private int writeProofLine(String line, StyledDocument doc, int pos) throws BadLocationException{
		String[] tokens = line.split("e:evidence");
		
		String token1 = tokens[0];
		String[] subTokens = token1.split(":");
		Style[] styles = {doc.getStyle("regular"), doc.getStyle("object"), doc.getStyle("verb"), doc.getStyle("subject")};
		doc.insertString(pos, subTokens[0], styles[0]); pos=pos+subTokens[0].length();
		doc.insertString(pos, ":", styles[0]); pos++;
		doc.insertString(pos, subTokens[1], styles[1]); pos=pos+subTokens[1].length();
		doc.insertString(pos, ":", styles[0]); pos++;
		doc.insertString(pos, subTokens[2], styles[2]); pos=pos+subTokens[2].length();
		doc.insertString(pos, ":", styles[0]); pos++;
		doc.insertString(pos, subTokens[3], styles[3]); pos=pos+subTokens[3].length();
		
		
		doc.insertString(pos, "e:evidence", doc.getStyle("evidence")); pos = pos+"e:evidence".length();
		
		doc.insertString(pos, tokens[1], styles[0]); pos = pos+tokens[1].length();
		doc.insertString(pos, "\n", styles[0]); pos++;
		
		return pos;
	}
	

}

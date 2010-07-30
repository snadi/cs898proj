package cs898.project;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Utils {

	public Utils() {/* static class */ }
        private static Font buttonFont = new Font(null, Font.BOLD, 12);
        private static Font labelFont = new Font(Font.SERIF, Font.BOLD, 14);
        private static Color scenarioTabColor = new Color(217,230,231);
        private static Color queryTabColor = new Color(217,230,231);//new Color(255,239,213);
	
	public static String fileToText(File file){
		if(!file.exists() || !file.canRead()){
			return null;
		}
		
		try{
			StringBuffer sb = new StringBuffer();
			BufferedReader r = new BufferedReader(new FileReader(file));
			
			String line = null;
			while ((line = r.readLine()) != null) {
				sb.append(line).append("\n");
			} 
			
			r.close();
			return sb.toString();
			
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
		 
	}
	
	public static boolean saveToFile(File f, String content){
		if(!f.canWrite()){
			return false;
		}
		
		try{
			BufferedWriter w = new BufferedWriter(new FileWriter(f));
			
			w.write(content);
			w.flush();
			w.close();
			
			return true;
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
		 
	}
	
	public static String getQueryName(String queryFileContent){
		String[] lines = queryFileContent.split("\n");
		for (String line : lines) {
			if (line.toUpperCase().contains("QUERY_NAME:")) {
				return line.split(":")[1].trim();
			}
		}
		
		return null;
	}
	
	public static String parseScenarioNameFromDir(String dirName){
		
		String[] tokens = dirName.split("_");
		if(tokens == null || tokens.length < 2){
			//dir name does not contain the scenario info
			return dirName;
		}
		
		return tokens[1];
	}

	public static boolean isProofSuccessful(String result){
		return (result == null)? false : result.toLowerCase().contains("# proof found");
	}

        public static Font getButtonFont(){
            return buttonFont;
        }

        public static Font getLabelFont(){
            return labelFont;
        }

        public static Color getScenarioTabColor(){
            return scenarioTabColor;
        }

        public static Color getQueryTabColor(){
            return queryTabColor;
        }
	
}

package cs898.project.engine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import euler.EulerRunner;

public class EulerEngineWrapper {

	public static String evaluate(String facts, String rules, String query) throws Exception{
		File factsFile = null;
		File rulesFile = null;
		File queryFile = null;
		String result;
		
		try {
			
			factsFile = createTempFile("facts", facts);
			//update the facts file location.
			rules = rules.replaceAll("facts.n3", factsFile.getName());

			rulesFile = createTempFile("rules", rules);
			queryFile = createTempFile("query", query);
			
			String args[] = { "-think", factsFile.getAbsolutePath(), rulesFile.getAbsolutePath(), queryFile.getAbsolutePath() };
			result = EulerRunner.doProof(args);
			
		} finally {
			factsFile.deleteOnExit();
			rulesFile.deleteOnExit();
			queryFile.deleteOnExit();
		}
		
		return result;
	}

	private static File createTempFile(String prefix, String content) throws IOException{
		File f = File.createTempFile(prefix, ".n3");
		
		BufferedWriter w = new BufferedWriter(new FileWriter(f));
		w.write(content);
		w.flush();
		w.close();
		
		return f;
	}
	
//	private String getFileContent(File f) throws IOException{
//		BufferedReader r = new BufferedReader(new FileReader(f));
//		
//		StringBuffer sb = new StringBuffer();
//		String line = null;
//		while ((line = r.readLine()) != null) {
//			sb.append(line).append("\n");
//		}
//		
//		return sb.toString();
//	}
	

	
}

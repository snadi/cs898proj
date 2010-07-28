package cs898.project;



import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public class Configuration {

	private Configuration() { /* static class*/ }
	
	public static String getRulesFileLocation(){
		return "setup/rules.n3";
	}

	public static String getFactsFileLocation(){
		return "setup/facts.n3";
	}

	public static Doctor[] listDoctors(){
		Doctor[] doctors = Doctor.values();
		Arrays.sort( doctors, new DoctorNameComparator());
		
		return doctors;
	}
	
	public static Document[] listDocuments(){
		Document[] docs = Document.values();
		//Arrays.sort( docs, new DocumentNameComparator());
		
		return docs;
	}

	public static Scenario query(Doctor d, Document doc){
		Scenario[] scenarios = Scenario.values();
		for (Scenario s : scenarios) {
			if(s.doctor.equals(d) && s.document.equals(doc)){
				return s;
			} 
		}
		
		return null;
	}
	
	public static String getProjectFactsFromFile(){
		return Utils.fileToText(new File(getFactsFileLocation()));
	}
	
	public static String getProjectRulesFromFile(){
		return Utils.fileToText(new File(getRulesFileLocation()));
	}
	
	
	private static final String MEM_OF = "Member of: "; 
	public static enum Doctor {
		DR_SMITH("Dr. Smith", MEM_OF + "\n 1. Grand River\n 2. St. Catherines\n 3.St. Mary's hospitals\nIs on shift at Grand River hospital\n"),
		DR_JANE ("Dr. Jane" , MEM_OF + "\nSt. Mary's hospitals\nIs on shift at St. Mary's hospital"),		
		NURSE_ALEX("Nurse Alex", MEM_OF + "\nSt. Mary's hospital\nIs on shift at St. Mary's");
		
		private String name;
		private String info;
		
		private Doctor(String name, String info) {
			this.name = name;
			this.info = info;
		}

		public String getName(){ return name; }
		public String getInformation(){ return info; }

	}

	
	private static final String BELONGS_TO ="Belongs to:";
	
	private static final String TOM_INFO = BELONGS_TO + " Tom " 
											+ "& he is treated at Grand River hospital by Dr. Smith.\n"
											+ "and allows access to his sensitive records.";
	
	private static final String JOHN_INFO = BELONGS_TO + " John " 
								+ "& he is treated at Grand River hospital by Dr. Smith and Nurse Alex\n"
								+ "and has an optin policy for his records.";

	private static final String WENDY_INFO = BELONGS_TO + " Wendy " 
									+ "& she is brought in the Emergency at St. Mary's hospital\n" 
									+ "treated by Dr. Jane\n"
									+ "but has an optout emergency policy.";
	
	public static enum Document {
		XRAY1("XRay 1", JOHN_INFO),
                BLOODTEST("Blood test", BELONGS_TO + " Tim"),
                 CTSCAN3("CTSCAN3", BELONGS_TO + " Sally "),
                 CTSCAN1("CTSCAN1", BELONGS_TO + " Peter "),
		CTSCAN2("CTSCAN2", TOM_INFO),		
		HIVRep1("HIV Rep1", "HIVRep1 is sensitive\n" + TOM_INFO),
		STD1("STD 1", "STD1 is sensitive\n" + JOHN_INFO),
                MRI1("MRI 1", BELONGS_TO + " Jack "),
                XRAY2("XRay 2", WENDY_INFO),
		XRAY3("XRay 3", BELONGS_TO + " Jenna ");
		
		
		
       
		
		private String name;
		private String info;
		
		private Document(String name, String info) {
			this.name = name;
			this.info = info;
		}
		
		public String getName(){ return name; }
		public String getInformation(){ return info; }
	}
	
	public static enum Scenario {
		Scenario1 (Doctor.DR_SMITH,  Document.XRAY1      , "setup/scen_1.png",  ":DrSmith   :access :XRay1"),
		Scenario2 (Doctor.DR_SMITH,  Document.BLOODTEST  , "setup/scen_2.png",  ":DrSmith   :access :BloodTest"),
		Scenario3 (Doctor.DR_SMITH,  Document.CTSCAN3    , "setup/scen_3.png" , ":DrSmith   :access :CTScan3"),
		Scenario4 (Doctor.DR_SMITH,  Document.CTSCAN1    , "setup/scen_5.png",  ":DrSmith   :access :CTScan1"),
                Scenario5 (Doctor.DR_SMITH,  Document.CTSCAN2    , "setup/scen_9.png",  ":DrSmith   :access :CTScan2"),
		Scenario6(Doctor.DR_SMITH,  Document.HIVRep1    , "setup/scen_10.png", ":DrSmith   :access :HIVRep1"),
		Scenario7(Doctor.DR_SMITH,  Document.STD1       , "setup/scen_11.png", ":DrSmith   :access :STD1"),
		Scenario8(Doctor.DR_SMITH,  Document.MRI1       , "setup/scen_12.png", ":DrSmith   :access :MRI1"),		
                Scenario9 (Doctor.DR_JANE,   Document.BLOODTEST  , "setup/scen_4.png",  ":DrJane    :access :BloodTest"),
		Scenario10 (Doctor.DR_JANE,   Document.XRAY2      , "setup/scen_6.png",  ":DrJane    :access :XRay2"),
		Scenario11 (Doctor.NURSE_ALEX,Document.XRAY2      , "setup/scen_7.png",  ":NurseAlex :access :XRay2"),
		Scenario12 (Doctor.DR_JANE,   Document.XRAY3      , "setup/scen_8.png",  ":DrJane    :access :XRay3");
		
		
		private Doctor doctor;
		private Document document;
		private String icon;
		private String query;
		
		private Scenario(Doctor d, Document doc, String icon, String q) {
			this.doctor = d;
			this.document = doc;
			this.icon = icon;
			this.query = q;
		}
		
		public Doctor getDoctor()     { return doctor;   }
		public Document getDocument() { return document; }
		public String getIcon()       { return icon;     }
		public String getQuery()      { return query;    }

	}
	
//	private static class DocumentNameComparator implements Comparator<Document> {
//		@Override
//		public int compare(Document o1, Document o2) {
//			return o1.getName().compareTo(o2.getName());
//		}
//	}
	
	private static class DoctorNameComparator implements Comparator<Doctor> {
		@Override
		public int compare(Doctor o1, Doctor o2) {
			return o1.getName().compareTo(o2.getName());
		}
	}
}

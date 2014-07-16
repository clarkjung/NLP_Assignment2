import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;


public class Train {
	
	static String ENGLIS_STOP_WORDS[] ={
		"a", "about", "above", "above", "across", "after", "afterwards", "again", "against", "all", "almost",
		"alone", "along", "already", "also","although","always","am","among", "amongst", "amoungst", "amount",
		"an", "and", "another", "any","anyhow","anyone","anything","anyway", "anywhere", "are", "around", "as", 
		"at", "back","be","became", "because","become","becomes", "becoming", "been", "before", "beforehand",
		"behind", "being", "below", "beside", "besides", "between", "beyond", "bill", "both", "bottom","but",
		"by", "call", "can", "cannot", "cant", "co", "con", "could", "couldnt", "cry", "de", "describe", "detail",
		"do", "done", "down", "due", "during", "each", "eg", "eight", "either", "eleven","else", "elsewhere", "empty",
		"enough", "etc", "even", "ever", "every", "everyone", "everything", "everywhere", "except", "few", "fifteen", 
		"fify", "fill", "find", "fire", "first", "five", "for", "former", "formerly", "forty", "found", "four", "from",
		"front", "full", "further", "get", "give", "go", "had", "has", "hasnt", "have", "he", "hence", "her", "here",
		"hereafter", "hereby", "herein", "hereupon", "hers", "herself", "him", "himself", "his", "how", "however",
		"hundred", "ie", "if", "in", "inc", "indeed", "interest", "into", "is", "it", "its", "itself", "keep", "last",
		"latter", "latterly", "least", "less", "ltd", "made", "many", "may", "me", "meanwhile", "might", "mill", "mine",
		"more", "moreover", "most", "mostly", "move", "much", "must", "my", "myself", "name", "namely", "neither", "never",
		"nevertheless", "next", "nine", "no", "nobody", "none", "noone", "nor", "not", "nothing", "now", "nowhere", "of",
		"off", "often", "on", "once", "one", "only", "onto", "or", "other", "others", "otherwise", "our", "ours", "ourselves",
		"out", "over", "own","part", "per", "perhaps", "please", "put", "rather", "re", "same", "see", "seem", "seemed",
		"seeming", "seems", "serious", "several", "she", "should", "show", "side", "since", "sincere", "six", "sixty",
		"so", "some", "somehow", "someone", "something", "sometime", "sometimes", "somewhere", "still", "such", "system",
		"take", "ten", "than", "that", "the", "their", "them", "themselves", "then", "thence", "there", "thereafter",
		"thereby", "therefore", "therein", "thereupon", "these", "they", "thickv", "thin", "third", "this", "those",
		"though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "top", "toward", "towards",
		"twelve", "twenty", "two", "un", "under", "until", "up", "upon", "us", "very", "via", "was", "we", "well", "were",
		"what", "whatever", "when", "whence", "whenever", "where", "whereafter", "whereas", "whereby", "wherein",
		"whereupon", "wherever", "whether", "which", "while", "whither", "who", "whoever", "whole", "whom", "whose",
		"why", "will", "with", "within", "without", "would", "yet", "you", "your", "yours", "yourself", "yourselves","the" 
		};

	private ArrayList<HashMap<String, Integer>> list;
	private String filePath;
	
	public Train(String filePath){
		this.list = new ArrayList<HashMap<String, Integer>>();
		this.filePath = filePath;
	}
	
	public void run(){
		File folder = new File(filePath);
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {
			
			if(file.isDirectory()){
				
				System.out.println("directory name: " + file.getName());
				
				String subFilePath = filePath + "/" + file.getName();
				
				File subFolder = new File(subFilePath);
				File[] subListOfFiles = subFolder.listFiles();
				
				HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
				hashMap.put(file.getName(), -1);
				
				//each category folder
				for(File subFile : subListOfFiles) {
					String returnPath = subFilePath + "/" + subFile.getName();
					makeUnigram(hashMap, returnPath);
				}
				
				list.add(hashMap);
				
				
			}else if(file.isFile()){
				System.out.println("file name: " + file.getName());
			}
			
		}
		
	}
	
	private void makeUnigram(HashMap<String, Integer> hashMap, String returnPath){
		
		
		try {

			// Create object of FileReader
			FileReader inputFile = new FileReader(returnPath);

			// Instantiate the BufferedReader Class
			BufferedReader bufferReader = new BufferedReader(inputFile);

			// Variable to hold the one line data
			String line;

			// Read file line by line and print on the console
			while ((line = bufferReader.readLine()) != null) {
				
				StringTokenizer st = new StringTokenizer(line);
				while (st.hasMoreElements()) {
					String str = st.nextElement().toString().toLowerCase();
					if(Arrays.asList(ENGLIS_STOP_WORDS).contains(str)) continue;
					
					hashMapAdd(hashMap, str);
				}
				
			}
			// Close the buffer reader
			bufferReader.close();
		} catch (Exception e) {
			System.out.println("Error while reading file line by line:"
					+ e.getMessage());
		}
	}
	
	private void hashMapAdd(HashMap<String, Integer> hashMap, String str){
		if(!hashMap.containsKey(str)) hashMap.put(str, 1);
		else{
			int tempInt = hashMap.get(str);
			hashMap.remove(str);
			hashMap.put(str, tempInt+1);
		}
	}
	
	public ArrayList<HashMap<String, Integer>> returnList(){
		return list;
	}
	
	
	
}

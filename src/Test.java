import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Test {

	static String ENGLIS_STOP_WORDS[] = { "a", "about", "above", "above",
			"across", "after", "afterwards", "again", "against", "all",
			"almost", "alone", "along", "already", "also", "although",
			"always", "am", "among", "amongst", "amoungst", "amount", "an",
			"and", "another", "any", "anyhow", "anyone", "anything", "anyway",
			"anywhere", "are", "around", "as", "at", "back", "be", "became",
			"because", "become", "becomes", "becoming", "been", "before",
			"beforehand", "behind", "being", "below", "beside", "besides",
			"between", "beyond", "bill", "both", "bottom", "but", "by", "call",
			"can", "cannot", "cant", "co", "con", "could", "couldnt", "cry",
			"de", "describe", "detail", "do", "done", "down", "due", "during",
			"each", "eg", "eight", "either", "eleven", "else", "elsewhere",
			"empty", "enough", "etc", "even", "ever", "every", "everyone",
			"everything", "everywhere", "except", "few", "fifteen", "fify",
			"fill", "find", "fire", "first", "five", "for", "former",
			"formerly", "forty", "found", "four", "from", "front", "full",
			"further", "get", "give", "go", "had", "has", "hasnt", "have",
			"he", "hence", "her", "here", "hereafter", "hereby", "herein",
			"hereupon", "hers", "herself", "him", "himself", "his", "how",
			"however", "hundred", "ie", "if", "in", "inc", "indeed",
			"interest", "into", "is", "it", "its", "itself", "keep", "last",
			"latter", "latterly", "least", "less", "ltd", "made", "many",
			"may", "me", "meanwhile", "might", "mill", "mine", "more",
			"moreover", "most", "mostly", "move", "much", "must", "my",
			"myself", "name", "namely", "neither", "never", "nevertheless",
			"next", "nine", "no", "nobody", "none", "noone", "nor", "not",
			"nothing", "now", "nowhere", "of", "off", "often", "on", "once",
			"one", "only", "onto", "or", "other", "others", "otherwise", "our",
			"ours", "ourselves", "out", "over", "own", "part", "per",
			"perhaps", "please", "put", "rather", "re", "same", "see", "seem",
			"seemed", "seeming", "seems", "serious", "several", "she",
			"should", "show", "side", "since", "sincere", "six", "sixty", "so",
			"some", "somehow", "someone", "something", "sometime", "sometimes",
			"somewhere", "still", "such", "system", "take", "ten", "than",
			"that", "the", "their", "them", "themselves", "then", "thence",
			"there", "thereafter", "thereby", "therefore", "therein",
			"thereupon", "these", "they", "thickv", "thin", "third", "this",
			"those", "though", "three", "through", "throughout", "thru",
			"thus", "to", "together", "too", "top", "toward", "towards",
			"twelve", "twenty", "two", "un", "under", "until", "up", "upon",
			"us", "very", "via", "was", "we", "well", "were", "what",
			"whatever", "when", "whence", "whenever", "where", "whereafter",
			"whereas", "whereby", "wherein", "whereupon", "wherever",
			"whether", "which", "while", "whither", "who", "whoever", "whole",
			"whom", "whose", "why", "will", "with", "within", "without",
			"would", "yet", "you", "your", "yours", "yourself", "yourselves",
			"the" };

	private String filePath;
	private HashMap<String, ArrayList<String>> resultHashMap;
	private HashMap<String, Integer> duplicatesHashMap;

	public Test(String filePath) {
		this.filePath = filePath;
		this.resultHashMap = new HashMap<String, ArrayList<String>>();
		this.duplicatesHashMap = new HashMap<String, Integer>();
	}
	
	private void addToDuplicatesHashMap(String subFileName){
		
		if(!duplicatesHashMap.containsKey(subFileName)) duplicatesHashMap.put(subFileName, 1);
		else{
			int temp = duplicatesHashMap.get(subFileName);
			duplicatesHashMap.remove(subFileName);
			duplicatesHashMap.put(subFileName, temp+1);
		}
		
	}

	private void findDuplicates() {

		File folder = new File(filePath);
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {

			if (file.isDirectory()) {
				
				System.out.println("directory name: " + file.getName());
				String subFilePath = filePath + "/" + file.getName();

				File subFolder = new File(subFilePath);
				File[] subListOfFiles = subFolder.listFiles();

				// each category folder
				for (File subFile : subListOfFiles) {
					String subFileName = subFile.getName();
					System.out.println("++++++++++" + file.getName() + ": " + subFileName + "++++++++++");
	
					addToDuplicatesHashMap(subFileName);

				}

			} else if (file.isFile()) {
				System.out.println("file name: " + file.getName());
			}

		}
	}

	public void run(ArrayList<HashMap<String, Integer>> list) {
		
		findDuplicates();
		System.out.println(duplicatesHashMap.toString());

		File folder = new File(filePath);
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {

			if (file.isDirectory()) {
				System.out.println("directory name: " + file.getName());
				String subFilePath = filePath + "/" + file.getName();

				File subFolder = new File(subFilePath);
				File[] subListOfFiles = subFolder.listFiles();

				ArrayList<String> arrayList = new ArrayList<String>();

				// each category folder
				for (File subFile : subListOfFiles) {
					String subFileName = subFile.getName();
					System.out.println("++++++++++" + file.getName() + ": " + subFileName + "++++++++++");
					String returnPath = subFilePath + "/" + subFileName;

					arrayList.add(guessCategory(list, returnPath, duplicatesHashMap.get(subFileName)));
				}

				resultHashMap.put(file.getName(), arrayList);

			} else if (file.isFile()) {
				System.out.println("file name: " + file.getName());
			}

		}

		printResult(resultHashMap);

	}// end run()

	// get percentage
	private void printResult(HashMap<String, ArrayList<String>> resultHashMap) {

		// sort by key
		Map<String, ArrayList<String>> treeMap = new TreeMap<String, ArrayList<String>>(
				resultHashMap);

		Iterator<String> it = treeMap.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();

			ArrayList<String> arrayList = new ArrayList<String>();
			arrayList = treeMap.get(key);
			int size = arrayList.size();
			double tp = 0.0;

			for (int i = 0; i < size; i++) {

				if(arrayList.get(i).contains(key)) tp++;

			}

			int count = 0;
			Iterator<String> it2 = treeMap.keySet().iterator();
			while (it2.hasNext()) {
				String key2 = it2.next();
				
				if(key.equals(key2)) continue;
				
				int size2 = treeMap.get(key2).size();

				for (int q = 0; q < size2; q++) {
					if (treeMap.get(key2).get(q).equals(key))
						count++;
				}
				
			}
			
	        DecimalFormat df = new DecimalFormat("#.##");
	        count += tp;

			double recall = tp / size;
			double precision = tp / count;
			double fmeasure = 2 * precision * recall / (precision + recall);

			System.out.println("========== " + key + " ==========");
			System.out.println("tp: " + df.format(tp) + ", count: " + count + ", Recall: " + df.format(recall) + ", Precision: "
					+ df.format(precision) + ", F-measure: " + df.format(fmeasure));

		}

	}

	// guess Category
	private String guessCategory(ArrayList<HashMap<String, Integer>> list,
			String returnPath, int duplicatesNumber) {

		HashMap<String, Double> saveMap = new HashMap<String, Double>();

		for (int i = 0; i < list.size(); i++) {

			String category = "";
			double logSum = 0.0;
			int count = 0;

			HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
			hashMap = list.get(i);
			double denominator = getHashMapSize(hashMap) + hashMap.size();

			try {

				String line;

				File file = new File(returnPath);
				Scanner in = new Scanner(file);

				while (in.hasNextLine()) {
					line = in.nextLine();

					StringTokenizer st = new StringTokenizer(line);
					while (st.hasMoreElements()) {
						// System.out.println(st.nextElement());
						String str = st.nextElement().toString().toLowerCase();
						if (Arrays.asList(ENGLIS_STOP_WORDS).contains(str))
							continue;

						logSum += Math.log((getFrequency(hashMap, str) + 1)
								/ denominator);
						count++;

					}

				}
				
				in.close();
				
			} catch (Exception e) {
				System.out.println("Error while reading file line by line:"
						+ e.getMessage());
			}

			category = getCategory(hashMap);

			saveMap.put(category, logSum / count);

		}// end for

		return getMaxFromSaveMap(saveMap, duplicatesNumber);
	}

	private String getMaxFromSaveMap(HashMap<String, Double> saveMap, int duplicatesNumber) {
		
		MyComparator comparator = new MyComparator(saveMap);
		Map<String, Double> sortedMap = new TreeMap<String, Double>(comparator);
		sortedMap.putAll(saveMap);
		
		String returnStr = "";
		int count = 0;
		
		Iterator<String> keySetIterator = sortedMap.keySet().iterator();
		while (keySetIterator.hasNext()) {
			String key = keySetIterator.next();
			returnStr += key;
			count ++;
			if(count >= duplicatesNumber) break;
		}
		
		return returnStr;

	}

	// get sum
	private double getHashMapSize(HashMap<String, Integer> hashMap) {

		int sum = 0;

		Iterator<String> keySetIterator = hashMap.keySet().iterator();
		while (keySetIterator.hasNext()) {
			String key = keySetIterator.next();

			if (hashMap.get(key) < 0)
				continue;

			sum += hashMap.get(key);
		}

		return (double) sum;
	}

	// get category
	private String getCategory(HashMap<String, Integer> hashMap) {

		Iterator<String> keySetIterator = hashMap.keySet().iterator();
		while (keySetIterator.hasNext()) {
			String key = keySetIterator.next();

			if (hashMap.get(key) == -1)
				return key;

		}

		return "no match category";
	}

	// get frequency
	private double getFrequency(HashMap<String, Integer> hashMap, String str) {
		if (hashMap.containsKey(str))
			return (double) hashMap.get(str);
		else
			return (double) 0;
	}

}

class MyComparator implements Comparator<Object> {

    Map<String, Double> map;

    public MyComparator(Map<String, Double> map) {
        this.map = map;
    }

    public int compare(Object o1, Object o2) {

        if (map.get(o2) == map.get(o1))
            return 1;
        else
            return ((Double) map.get(o2)).compareTo((Double)     
                                                            map.get(o1));

    }
}

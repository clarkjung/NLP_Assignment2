import java.util.ArrayList;
import java.util.HashMap;

public class Task1 {

	public static void main(String[] args) {
		
		long startTime = System.currentTimeMillis();
		
		String TrainFilePath = "../ohsumed-first-20000-docs/training";
		Train train = new Train(TrainFilePath);
		train.run();
		
		ArrayList<HashMap<String, Integer>> list = train.returnList();
		
		String TestFilePath = "../ohsumed-first-20000-docs/test";
		
		Test test2 = new Test(TestFilePath);
		test2.run(list);
		
		long estimatedTime = System.currentTimeMillis() - startTime;
		System.out.println("elapsed time: " + estimatedTime/60000 + "minutes");

	}

}



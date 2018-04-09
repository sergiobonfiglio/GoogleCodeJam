package codeJam2014.reorderingTrainCars;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class ReorderingTrainCars {

	public static void main(String[] args) throws Exception {

		String fileName = "example1.txt";

		Path mainDir = Paths.get(System.getProperty("user.dir") + "/CodeJam2014/"
				+ ReorderingTrainCars.class.getPackage().getName());

		Path inputFilePath = mainDir.resolve(fileName);
		Path outputFilePath = mainDir.resolve(fileName + ".out");

		List<String> inputLines = Files.readAllLines(inputFilePath, StandardCharsets.UTF_8);

		List<String> outputLines = new ArrayList<String>();

		long start = System.currentTimeMillis();

		int numCases = Integer.parseInt(inputLines.get(0));

		int firstLine = 1;
		for (int i = 0; i < numCases; i++) {

			int n = Integer.parseInt(inputLines.get(firstLine));

			System.out.println("n:" + n);

			String[] strings = inputLines.get(firstLine + 1).split(" ");
			List<String> stringsList = new ArrayList<String>(strings.length);

			for (String string : strings) {
				stringsList.add(string);
			}

			for (int j = 0; j < strings.length; j++) {
				System.out.print(strings[j] + ", ");
			}
			System.out.println();

			//int comb = getComb(strings);

			String result = "" ;//+ comb;

			String caseResult = "Case #" + (i + 1) + ": " + result;
			outputLines.add(caseResult);
			System.out.println(caseResult);
			System.out.println("------------");

			firstLine += 2;

		}
		System.out.println("Completed: " + (System.currentTimeMillis() - start) / 1000d + " s");

		StandardOpenOption option;
		if (!Files.exists(outputFilePath)) {
			option = StandardOpenOption.CREATE_NEW;
		} else {
			option = StandardOpenOption.TRUNCATE_EXISTING;
		}
		Files.write(outputFilePath, outputLines, option);

	}

	private static int getComb(ArrayList<String> strings) {
		
		for (int i = 0; i < strings.size(); i++) {
			List<String> cloned = (ArrayList<String>) strings.clone();
			cloned.remove(i);
			List<String> comp = getCompatible(strings.get(i), true,	 cloned);
			
			//int sub = getComb(cloned);
			
		}
		return 0;
	}

	private static List<String> getCompatible(String needle, boolean fromStart, List<String> cars) {
		List<String> comp = new ArrayList<String>(cars.size());

		for (String car : cars) {
			if (fromStart && car.startsWith(needle.substring(needle.length() - 1))) {
				comp.add(car);
			} else if (!fromStart && car.endsWith(needle.substring(needle.length() - 1))) {
				comp.add(car);
			}
		}
		return comp;
	}
}

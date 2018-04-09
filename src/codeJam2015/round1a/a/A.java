package codeJam2015.round1a.a;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class A {

	public static void main(String[] args) throws Exception {

		//String fileName = "example1.txt";
		//String fileName = "A-small-practice.in";
		 String fileName = "A-large-practice.in";

		Path mainDir = Paths.get(System.getProperty("user.dir") + "/CodeJam2015/"
				+ A.class.getPackage().getName().replace('.', java.io.File.separatorChar));

		Path inputFilePath = mainDir.resolve(fileName);
		Path outputFilePath = mainDir.resolve(fileName + ".out");

		List<String> inputLines = Files.readAllLines(inputFilePath, StandardCharsets.UTF_8);

		List<String> outputLines = new ArrayList<String>();

		long start = System.currentTimeMillis();

		int numCases = Integer.parseInt(inputLines.get(0));

		for (int i = 0; i < numCases; i++) {

			int firstLine = i * 2 + 1;
			int N = Integer.parseInt(inputLines.get(firstLine));
			// String[] parameters = inputLines.get(firstLine + 1).split(" ");
			int[] mArray = GetIntArray(inputLines.get(firstLine + 1));

			System.out.println(Arrays.toString(mArray));

			int y = getY(mArray);
			int z = getZ(mArray);

			String caseResult = "Case #" + (i + 1) + ": " + y + " " + z;
			outputLines.add(caseResult);
			System.out.println(caseResult);
			System.out.println("------------");

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

	private static int getZ(int[] mArray) {
		int res = 0;
		int maxDiff = 0;
		for (int i = 0; i < mArray.length - 1; i++) {
			int diff = mArray[i] - mArray[i + 1];
			if (diff > maxDiff)
				maxDiff = diff;
		}
		for (int i = 0; i < mArray.length - 1; i++) {
			res += Math.min(maxDiff, mArray[i]);
		}
		return res;
	}

	// any number of mushroom
	private static int getY(int[] mArray) {
		int res = 0;

		for (int i = 0; i < mArray.length - 1; i++) {
			int diff = mArray[i] - mArray[i + 1];
			if (diff > 0)
				res += diff;

		}

		return res;
	}

	private static int[] GetIntArray(String string) {

		String[] n = string.split(" ");

		int[] res = new int[n.length];
		for (int i = 0; i < res.length; i++) {
			res[i] = Integer.parseInt(n[i]);

		}
		return res;
	}

}

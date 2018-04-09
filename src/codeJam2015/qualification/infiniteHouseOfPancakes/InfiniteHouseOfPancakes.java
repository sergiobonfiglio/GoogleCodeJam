package codeJam2015.qualification.infiniteHouseOfPancakes;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class InfiniteHouseOfPancakes {

	public static void main(String[] args) throws Exception {

		// String fileName = "example1.txt";
		String fileName = "B-small-attempt0.in";
		// String fileName = "A-large.in";

		Path mainDir = Paths.get(System.getProperty("user.dir") + "/CodeJam2015/"
				+ InfiniteHouseOfPancakes.class.getPackage().getName().replace('.', java.io.File.separatorChar));

		Path inputFilePath = mainDir.resolve(fileName);
		Path outputFilePath = mainDir.resolve(fileName + ".out");

		List<String> inputLines = Files.readAllLines(inputFilePath, StandardCharsets.UTF_8);

		List<String> outputLines = new ArrayList<String>();

		long start = System.currentTimeMillis();

		int numCases = Integer.parseInt(inputLines.get(0));

		for (int i = 0; i < numCases; i++) {

			int firstLine = i * 2 + 1;

			int nonEmptyPlates = Integer.parseInt(inputLines.get(firstLine));
			String[] parameters = inputLines.get(firstLine + 1).split(" ");
			int[] pancakes = GetIntArray(parameters);

			// System.out.println("n:" + n + ",l:" + l););
			System.out.println(Arrays.toString(pancakes));

			ArrayList<Integer> list = new ArrayList<Integer>(pancakes.length);
			for (int j = 0; j < pancakes.length; j++) {
				list.add(pancakes[j]);
			}
			int min = getMin(list);

			String caseResult = "Case #" + (i + 1) + ": " + min;
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

	private static int getMin(List<Integer> list) {
		if (list.size() == 0)
			return 0;

		int max = Collections.max(list);
		if (max <= 3)
			return max;

		int n = numOfEquals(list, max);

		// String before = Arrays.toString(list.toArray());
		// System.out.println(before + ":" + max);

		list.removeIf(s -> s == max);

		int split1 = (int) Math.ceil(max / 2.0);
		int split2 = (int) (max / 2.0);
		if (max % 2 != 0 && n == 1) {
			int secondMax = list.size() > 0 ? Collections.max(list) : -1;
			int secondN = numOfEquals(list, secondMax);
			if (secondMax <= split1 + 1 && secondN == 1) {
				split2--;
				split1++;
			}
		}
		// if (max % 2 == 0) {
		// split1 =
		// split2 =
		// } else {
		// split2 = (int) (max / 2.0) - 1;// odd
		// split1 = max - split2; // even
		// }

		for (int i = 0; i < n; i++) {
			list.add(split1);
			list.add(split2);
		}

		// System.out.println(before + ":"+max+" -> " +
		// Arrays.toString(list.toArray())+":"+n);
		int rec = n + getMin(list);
		

		
		int res = Math.min(max, rec);

		// System.out.println("rec: "+rec+"("+n+"), max: "+max);
		// if(res == max)
		// System.out.println(before);

		return res;

	}

	private static int numOfEquals(List<? extends Integer> array, int n) {
		int res = 0;

		for (Integer i : array) {
			if (i == n)
				res++;
		}
		return res;
	}

	private static int[] GetIntArray(String[] string) {
		int[] res = new int[string.length];
		for (int i = 0; i < res.length; i++) {
			res[i] = Integer.parseInt(string[i]);

		}
		return res;
	}

}

package codeJam2015.qualification.ominousOmino;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OminousOmino {

	public static void main(String[] args) throws Exception {

		//String fileName = "example1.txt";
		 String fileName = "D-small-practice.in";
		// String fileName = "A-large.in";

		Path mainDir = Paths.get(System.getProperty("user.dir") + "/CodeJam2015/"
				+ OminousOmino.class.getPackage().getName().replace('.', java.io.File.separatorChar));

		Path inputFilePath = mainDir.resolve(fileName);
		Path outputFilePath = mainDir.resolve(fileName + ".out");

		List<String> inputLines = Files.readAllLines(inputFilePath, StandardCharsets.UTF_8);

		List<String> outputLines = new ArrayList<String>();

		long start = System.currentTimeMillis();

		int numCases = Integer.parseInt(inputLines.get(0));

		for (int i = 0; i < numCases; i++) {

			int firstLine = i + 1;
			String[] parameters = inputLines.get(firstLine).split(" ");

			int x = Integer.parseInt(parameters[0]);
			int r = Integer.parseInt(parameters[1]);
			int c = Integer.parseInt(parameters[2]);

			System.out.println("x:" + x + ",r:" + r + ",c:" + c);

			String min = getWinner(x, r, c);

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

	private static String getWinner(int x, int r, int c) throws Exception {

		int tot = r * c;
		int diff = tot - x;

		 int minRc=0;
		 int maxRc=0;
		 if (x == 1) {
		 minRc = 1;
		 maxRc = 1;
		 }else if(x==2){
		 minRc = 2;
		 maxRc = 2;
		 }else if(x==3){
		 minRc = 2;
		 maxRc = 3;
		 }else if(x==4){
		 minRc = 3;
		 maxRc = 4;
		 }

		// if ((r < x || c < x) && x > 3) {
		// return "RICHARD";
		// }
		//
		int minrc1 = Math.min(r, c);
		int maxrc1 = Math.max(r, c);
		if ((maxrc1 >= maxRc  && minrc1>= minRc) && diff % x == 0) {
			if(diff<0)throw new Exception();
			return "GABRIEL";
		} else {
			return "RICHARD";
		}

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

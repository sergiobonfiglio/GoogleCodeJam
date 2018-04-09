package codeJam2014.partElf2;

import java.math.BigInteger;
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

public class PartElf {

	public static void main(String[] args) throws Exception {

		String fileName = "A-large.in";
		// String fileName = "A-small-attempt0.in";

		Path mainDir = Paths.get(System.getProperty("user.dir") + "/CodeJam2014/"
				+ PartElf.class.getPackage().getName());

		Path inputFilePath = mainDir.resolve(fileName);
		Path outputFilePath = mainDir.resolve(fileName + ".out");

		List<String> inputLines = Files.readAllLines(inputFilePath, StandardCharsets.UTF_8);

		List<String> outputLines = new ArrayList<String>();

		long start = System.currentTimeMillis();

		int numCases = Integer.parseInt(inputLines.get(0));

		int firstLine = 1;
		for (int i = 0; i < numCases; i++) {

			String[] pq = inputLines.get(firstLine).split("/");
			System.out.println(inputLines.get(firstLine));
			// System.out.println(pq[0]);
			long p = Long.parseLong(pq[0]);
			long q = Long.parseLong(pq[1]);

			long min = getMinGenerations(p, q);

			String result = min < 0 ? "impossible" : "" + min;

			String caseResult = "Case #" + (i + 1) + ": " + result;
			outputLines.add(caseResult);
			System.out.println(caseResult);
			System.out.println("------------");

			firstLine += 1;

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

	private static long getMinGenerations(long p, long q) {
		if (q % 2 != 0) {
			return -1;
		}

		long g = 0;

		double div = p / (double) q;

		while (div < 1) {
			g++;
			div = 2 * div;
		}

		if (div ==1 || getDen(div - 1) % 2 == 0)
			return g;
		else
			return -1;

	}

	private static double getDen(Double a) {

		int c = a.toString().length() - 2;
		Integer n = (int) (a * Math.pow(10, c));
		int d = (int) Math.pow(10, c);

		int gcd = GCD(n, d);

		return (double) d / gcd;

	}

	public static int GCD(int a, int b) {
		if (b == 0)
			return a;
		return GCD(b, a % b);
	}

}

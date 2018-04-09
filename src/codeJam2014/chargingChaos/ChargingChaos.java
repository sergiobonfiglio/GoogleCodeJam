package codeJam2014.chargingChaos;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ChargingChaos {

	public static void main(String[] args) throws Exception {

		String fileName = "A-large-practice.in";

		Path mainDir = Paths.get(System.getProperty("user.dir") + "/CodeJam2014/"
				+ ChargingChaos.class.getPackage().getName());

		Path inputFilePath = mainDir.resolve(fileName);
		Path outputFilePath = mainDir.resolve(fileName + ".out");

		List<String> inputLines = Files.readAllLines(inputFilePath, StandardCharsets.UTF_8);

		List<String> outputLines = new ArrayList<String>();

		long start = System.currentTimeMillis();

		int numCases = Integer.parseInt(inputLines.get(0));

		for (int i = 0; i < numCases; i++) {

			int firstLine = (i) * 3 + 1;

			String[] parameters = inputLines.get(firstLine).split(" ");
			int n = Integer.parseInt(parameters[0]);
			int l = Integer.parseInt(parameters[1]);
			System.out.println("n:" + n + ",l:" + l);

			String[] outlets = inputLines.get(firstLine + 1).split(" ");
			String[] devices = inputLines.get(firstLine + 2).split(" ");

			int min = getMinSwithces(outlets, devices);

			String result = min < 0 ? "NOT POSSIBLE" : "" + min;

			String caseResult = "Case #" + (i + 1) + ": " + result;
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

	public static int getMinSwithces(String[] outlets, String[] devices) {

		// dev-out
		String[][] matrix = new String[outlets.length][outlets.length];
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				matrix[i][j] = getDiff(devices[i], outlets[j]);
			}
		}
		//print(matrix);

		int min = -1;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {

				String diff = matrix[i][j];
				int sw = countOnes(diff);
				//System.out.println("search " + diff);

				boolean allFound = true;
				for (int k = 0; k < matrix.length && allFound; k++) {
					if (k != i) {// different dev
						boolean found = false;
						for (int l = 0; l < matrix[0].length && !found; l++) {
							if (matrix[k][l].equals(diff) && l != j) {
								// diff out
								found = true;
							}
						}
						allFound &= found;
					}
				}

				if (allFound && (sw < min || min == -1)) {
					min = sw;
				}
			}
		}
		return min;
	}

	public static void print(String[][] a) {

		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[0].length; j++) {
				System.out.print(a[i][j] + " ");
			}
			System.out.println();
		}

	}

	public static String applyMask(String str, String mask) {
		String res = "";
		for (int i = 0; i < str.length(); i++) {
			if (mask.charAt(i) == '0') {
				res += str.charAt(i);
			} else {
				res += str.charAt(i) == '1' ? '0' : '1';
			}
		}
		return res;
	}

	private static boolean isCompatible(String diffDevOut, String string) {
		String and = getAnd(diffDevOut, string);

		return countOnes(and) == and.length();
	}

	public static int countOnes(String str) {
		int ones = 0;
		if (str != null) {
			for (int i = 0; i < str.length(); i++) {
				ones += str.charAt(i) == '1' ? 1 : 0;
			}
		}
		return ones;
	}

	private static String getAnd(String dev, String out) {
		if (out == null || out.length() == 0)
			return dev;
		String diff = new String();
		for (int i = 0; i < dev.length(); i++) {
			diff += dev.charAt(i) != out.charAt(i) ? "0" : "1";
		}

		System.out.println(dev + "&" + out + "=" + diff);
		return diff;
	}

	private static String getDiff(String dev, String out) {
		String diff = new String();
		for (int i = 0; i < dev.length(); i++) {
			diff += dev.charAt(i) != out.charAt(i) ? "1" : "0";
		}
		return diff;
	}

}

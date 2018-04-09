package codeJam2014.repeater;

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

public class Repeater {

	public static void main(String[] args) throws Exception {

		String fileName = "A-large-practice.in";
		// String fileName = "A-small-attempt0.in";

		Path mainDir = Paths.get(System.getProperty("user.dir") + "/CodeJam2014/"
				+ Repeater.class.getPackage().getName());

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

			String[] strings = new String[n];
			for (int j = 0; j < strings.length; j++) {
				strings[j] = inputLines.get(firstLine + j + 1);
				// System.out.println("w" + j + ": " + strings[j]);
			}

			int min = getMinMoves(strings);

			String result = min < 0 ? "Fegla Won" : "" + min;

			String caseResult = "Case #" + (i + 1) + ": " + result;
			outputLines.add(caseResult);
			System.out.println(caseResult);
			System.out.println("------------");

			firstLine += n + 1;

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

	private static int getMinMoves(String[] strings) {

		List<String> common = getLetters(strings[0]);

		for (int i = 1; i < strings.length; i++) {
			List<String> lett = getLetters(strings[i]);
			if (common.size() != lett.size())
				return -1;
			for (int j = 0; j < lett.size(); j++) {
				if (!common.get(j).equals(lett.get(j))) {
					return -1;
				}
			}
		}

		// commonsub
		// int[] minSig = getSig(strings[0], common.size());
		// for (int i = 1; i < strings.length; i++) {
		// int[] sig = getSig(strings[i], common.size());
		// for (int j = 0; j < sig.length; j++) {
		// if (sig[j] < minSig[j]) {
		// minSig[j] = sig[j];
		// }
		// }
		// }
		//
		// int[] maxSig = getSig(strings[0], common.size());
		// for (int i = 1; i < strings.length; i++) {
		// int[] sig = getSig(strings[i], common.size());
		// for (int j = 0; j < sig.length; j++) {
		// if (sig[j] > maxSig[j]) {
		// maxSig[j] = sig[j];
		// }
		// }
		// }
		// String comMax = "";
		// for (int i = 0; i < maxSig.length; i++) {
		// for (int j = 0; j < maxSig[i]; j++) {
		// comMax += common.get(i);
		// }
		// }
		//
		// String com = "";
		// for (int i = 0; i < minSig.length; i++) {
		// for (int j = 0; j < minSig[i]; j++) {
		// com += common.get(i);
		// }
		// }
		//
		// int[] avgSig = new int[maxSig.length];
		// for (int i = 0; i < maxSig.length; i++) {
		// avgSig[i] = (maxSig[i] + minSig[i]) / 2;
		// }
		//
		// String comAvg = "";
		// for (int i = 0; i < avgSig.length; i++) {
		// for (int j = 0; j < avgSig[i]; j++) {
		// comAvg += common.get(i);
		// }
		// }

		int[][] medSig = new int[strings.length][common.size()];
		for (int i = 0; i < strings.length; i++) {
			medSig[i] = getSig(strings[i], common.size());
		}
		int[] medSig2 = new int[common.size()];
		for (int i = 0; i < medSig2.length; i++) {
			int[] all = new int[strings.length];
			for (int j = 0; j < strings.length; j++) {
				all[j] = medSig[j][i];
			}
			Arrays.sort(all);
			medSig2[i] = all[all.length / 2];
		}

		String comMedian = "";
		for (int i = 0; i < medSig2.length; i++) {
			for (int j = 0; j < medSig2[i]; j++) {
				comMedian += common.get(i);
			}
		}

		// List<String> a = new ArrayList<String>();
		// for (int i = 0; i < strings.length; i++) {
		// a.add(strings[i]);
		// }
		// a.add(com);
		// strings = a.toArray(strings);
		// System.out.println(com);

		// diff
		int[][] diffs = new int[strings.length][strings.length];
		// int addDiff = 0;
		// int maxDiff = 0;
		// int avgDiff = 0;
		int meanDiff = 0;
		for (int i = 0; i < diffs.length; i++) {
			// addDiff += getDiff(strings[i], com, common);
			// maxDiff += getDiff(strings[i], comMax, common);
			// avgDiff += getDiff(strings[i], comAvg, common);
			meanDiff += getDiff(strings[i], comMedian, common);
			for (int j = 0; j < diffs.length; j++) {
				if (i != j) {
					diffs[i][j] = getDiff(strings[i], strings[j], common);
					// System.out.println("diff: " + strings[i] + ", " +
					// strings[j] + "=" + diffs[i][j]);
				} else {
					diffs[i][j] = 0;
				}
			}
		}

		int min = Integer.MAX_VALUE;
		for (int i = 0; i < diffs.length; i++) {
			int sum = 0;
			for (int j = 0; j < diffs.length; j++) {
				sum += diffs[i][j];
			}
			if (sum < min)
				min = sum;
		}
		// int res = Math.min(Math.min(addDiff, Math.min(maxDiff,
		// Math.min(avgDiff, meanDiff))), min);
		int res = Math.min(meanDiff, min);
		// for (int i = 0; i < diffs.length; i++) {
		// for (int j = 0; j < diffs.length; j++) {
		// System.out.print(diffs[i][j] + " ");
		// }
		// System.out.println();
		// }
		// System.out.println();

		System.out.println("res: " + res);
		return res;
	}

	private static int[] getSig(String s, int n) {
		if (s.length() == 0)
			return null;
		int[] sig = new int[n];
		char f = s.charAt(0);
		sig[0] = 1;
		int i = 0;
		for (int j = 1; j < s.length(); j++) {
			if (s.charAt(j) == f) {
				sig[i]++;
			} else {
				sig[++i]++;
				f = s.charAt(j);
			}
		}
		return sig;
	}

	private static int getDiff(String string, String string2, List<String> common) {

		int[] s1 = getSig(string, common.size());
		int[] s2 = getSig(string2, common.size());
		// int last1 = 0;
		// int last2 = 0;
		//
		// for (int i = 0; i < common.size(); i++) {
		// int c1 = 0;
		// int c2 = 0;
		// while (string.length() > last1 &&
		// common.get(i).equals(string.charAt(last1) + "")) {
		// c1++;
		// last1++;
		// }
		// s1[i] = c1;
		//
		// while (string2.length() > last2 &&
		// common.get(i).equals(string2.charAt(last2) + "")) {
		// c2++;
		// last2++;
		// }
		// s2[i] = c2;
		// }
		// System.out.print("s1:" + string);
		// for (int i = 0; i < s2.length; i++) {
		// System.out.print(s1[i] + ",");
		// }
		// System.out.println();
		//
		// System.out.print("s2:" + string2);
		// for (int i = 0; i < s2.length; i++) {
		// System.out.print(s2[i] + ",");
		// }
		// System.out.println();

		int diff = 0;
		for (int i = 0; i < s2.length; i++) {
			diff += Math.abs(s1[i] - s2[i]);
		}
		return diff;
	}

	private static List<String> getLetters(String str) {
		ArrayList<String> commonLetters = new ArrayList<String>();

		if (str.length() > 0) {
			char firstChar = str.charAt(0);
			commonLetters.add(firstChar + "");
			for (int j = 1; j < str.length(); j++) {
				char currentChar = str.charAt(j);
				if (currentChar != firstChar) {
					commonLetters.add(currentChar + "");
					firstChar = currentChar;
				}
			}
		}

		return commonLetters;
	}
}

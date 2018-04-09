package codeJam2015.round1a.b;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class B {

	public static void main(String[] args) throws Exception {

		// String fileName = "example1.txt";
		// String fileName = "B-small-practice.in";
		String fileName = "B-large-practice.in";

		Path mainDir = Paths.get(System.getProperty("user.dir") + "/CodeJam2015/"
				+ B.class.getPackage().getName().replace('.', java.io.File.separatorChar));

		Path inputFilePath = mainDir.resolve(fileName);
		Path outputFilePath = mainDir.resolve(fileName + ".out");

		List<String> inputLines = Files.readAllLines(inputFilePath, StandardCharsets.UTF_8);

		List<String> outputLines = new ArrayList<String>();

		long start = System.currentTimeMillis();

		int numCases = Integer.parseInt(inputLines.get(0));

		for (int i = 0; i < numCases; i++) {

			int firstLine = i * 2 + 1;
			int[] BN = GetIntArray(inputLines.get(firstLine));
			int B = BN[0];
			int N = BN[1];

			int[] mArray = GetIntArray(inputLines.get(firstLine + 1));

			System.out.println("B:" + B + ", N:" + N);
			System.out.println(Arrays.toString(mArray));

			int y = getY2(N, mArray);

			String caseResult = "Case #" + (i + 1) + ": " + y;
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

	private static HashMap<String, Long> mcdCache = new HashMap<String, Long>();

	public static long mcd(long a, long b) {
		String key = a + "_" + b;
		if (mcdCache.containsKey(key)) {
			return mcdCache.get(key);
		}
		while (a != b)
			if (a > b)
				a -= b;
			else
				b -= a;
		mcdCache.put(key, a);
		return a;
	}

	public static long mcm(long a, long b) {
		if (a == b)
			return a;
		return (a * b) / mcd(a, b);
	}

	public static int mcm(int[] arr) throws Exception {
		if (arr.length == 1) {
			return arr[0];
		}
		long m = arr[0];
		for (int i = 1; i < arr.length; i++) {
			m = mcm(m, arr[i]);
			if (m < 0)
				throw new Exception();
		}

		if (m > Integer.MAX_VALUE)
			throw new Exception();

		return (int) m;
	}

	private static int getY2(int n, int[] mArray) throws Exception {
		if (mArray.length >= n)
			return n;
		int b = mArray.length;

		// int mcm = mcm(mArray);
		// System.out.println("mcm:" + mcm);
		//
		//
		// int[] d = new int[mcm];
		// d[0] = b;
		// for (int i = 1; i < mcm; i++) {
		//
		// for (int j = 0; j < mArray.length; j++) {
		// if (mArray[j] <= i && i % mArray[j] == 0) {
		// d[i]++;
		// }
		// }
		// }
		List<Integer> dList = new ArrayList<Integer>();

		dList.add(b);

		int last = 0;
		int ii = 1;

		while (last != b) {
			int s = 0;
			for (int j = 0; j < mArray.length; j++) {
				if (mArray[j] <= ii && ii % mArray[j] == 0) {
					s++;
				}
			}
			if (s != b)
				dList.add(s);
			last = s;
			ii++;
		}
		int[] d = new int[dList.size()];
		int di = 0;
		for (Integer integer : dList) {
			d[di++] = integer;
		}
		System.out.println(Arrays.toString(d));

		System.out.println("mcm*:" + d.length);
//		int mcm = mcm(mArray);
//		if (mcm != d.length) {
//			System.out.println("mcm:" + mcm);
//			throw new Exception();
//		}

		int dSum = Arrays.stream(d).sum();
		int tLeft = (int) ((n - 1) / (double) dSum);

		int res = n - tLeft * dSum;

		System.out.println("sum: " + dSum + ",tLeft:" + tLeft + ", res: " + res);
		int bucket;
		int nn;
		if (res == 0) {
			bucket = d.length;
			nn = 0;
		} else {
			int resD = 0;
			int i = 0;

			int ss = 0;
			while (resD < res) {
				ss = res - resD;
				resD += d[i++];

			}
			bucket = i - 1;
			nn = ss;
		}

		System.out.println("bucket:" + bucket + ",nn:" + nn);

		for (int j = 0; j < mArray.length; j++) {
			if (bucket == 0 || (mArray[j] <= bucket && (bucket) % mArray[j] == 0)) {
				nn--;
				if (nn <= 0)
					return j + 1;
			}
		}
		throw new Exception();
		// return -1;
	}

	private static int getY(int n, int[] mArray) {

		if (mArray.length >= n)
			return n;

		// int res = n;
		int max = Arrays.stream(mArray).max().getAsInt();
		int min = Arrays.stream(mArray).min().getAsInt();

		int[] cc = new int[max + 2];
		for (int i = 0; i < mArray.length; i++) {
			cc[mArray[i]]++;
		}

		int res = n;
		int served = mArray.length;
		int t = 1;
		// do {
		// // System.out.println("served:" + served+",t:"+t);
		// for (int i = 1; i < max + 1 && i < t; i++) {
		// if (t % i == 0) {
		// // System.out.println("served: " + served + ", i:" + i +
		// // ",t:" + t);
		// served += cc[i];
		// }
		// }
		// if (served == n) {
		// System.out.println("served:" + served + ", n:" + n + ",t:" + t);
		// int last = -1;
		// for (int j = 0; j < mArray.length; j++) {
		// if ((t % mArray[j] == 0)) {
		// last = j + 1;
		// }
		// }
		// return last;
		// } else if (served >= n) {
		// // System.out.println("served:" + served + ", n:" + n + ",t:" +
		// // t);
		// for (int j = 0; j < mArray.length; j++) {
		// if ((t % mArray[j] == 0)) {
		// return j + 1;
		// }
		// }
		// return -2;
		// }
		//
		// t += cc[min];
		// } while (served < n);

		for (int i = 0; i < cc.length; i++) {

		}

		System.out.println(((n - 1) % mArray.length));

		// res = sortPos[((n-1) % mArray.length)]+1;

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

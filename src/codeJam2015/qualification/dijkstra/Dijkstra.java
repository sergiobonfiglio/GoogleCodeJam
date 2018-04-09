package codeJam2015.qualification.dijkstra;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Dijkstra {

	public static final int s1 = 0;
	public static final int si = 1;
	public static final int sj = 2;
	public static final int sk = 3;
	public static final int neg1 = 4;
	public static final int negi = 5;
	public static final int negj = 6;
	public static final int negk = 7;

	public static int[][] mult2 = { { s1, si, sj, sk }, { si, neg1, sk, negj }, { sj, negk, neg1, si },
			{ sk, sj, negi, neg1 } };
	public static String[][] mult = { { "1", "i", "j", "k" }, { "i", "-1", "k", "-j" }, { "j", "-k", "-1", "i" },
			{ "k", "j", "-i", "-1" } };

	public static long[][] preMult2 = new long[8][8];
	// private static String[][][] preComputedMult = new String[4][4];

	private static HashMap<String, HashMap<String, String>> preCompMult2 = new HashMap<String, HashMap<String, String>>();
	private static HashMap<String, String> preCompMult = new HashMap<String, String>(8 * 8);

	public static void main(String[] args) throws Exception {

		int[] t = { s1, si, sj, sk, neg1, negi, negj, negk };
		for (int i = 0; i < t.length; i++) {
			for (int j = 0; j < t.length; j++) {
				preMult2[t[i]][t[j]] = mult(t[i], t[j]);
			}
		}

		//String fileName = "example1.txt";
//		String fileName = "C-small-attempt1.in";
		 String fileName = "C-large.in";

		Path mainDir = Paths.get(System.getProperty("user.dir") + "/CodeJam2015/"
				+ Dijkstra.class.getPackage().getName().replace('.', java.io.File.separatorChar));

		Path inputFilePath = mainDir.resolve(fileName);
		Path outputFilePath = mainDir.resolve(fileName + ".out");

		List<String> inputLines = Files.readAllLines(inputFilePath, StandardCharsets.UTF_8);

		List<String> outputLines = new ArrayList<String>();

		long start = System.currentTimeMillis();

		int numCases = Integer.parseInt(inputLines.get(0));

		for (int i = 0; i < numCases; i++) {

			int firstLine = i * 2 + 1;

			String[] param = inputLines.get(firstLine).split(" ");
			int L = Integer.parseInt(param[0]);
			long X = Long.parseLong(param[1]);
			String str = inputLines.get(firstLine + 1);

			System.out.println("L:" + L + ", X:" + X + ", STR: " + str);

			boolean solvable = isSolvable2(L, X, strToIntArray(str));
//			 boolean solvable = isSolvable(L, X, strToIntArray(str));
			// boolean solvable2 = isSolvable(L, X, str, 0, 0);
			//
			// if (solvable2 != solvable) {
			// throw new Exception();
			// }

			String caseResult = "Case #" + (i + 1) + ": " + (solvable ? "YES" : "NO");
			outputLines.add(caseResult);
			System.out.println(caseResult);
			System.out.println("------------");
		}
		System.out.println("Completed: " + (System.currentTimeMillis() - start) / 1000d + " s");

		// System.out.println("hit: " + hit / (double) tot * 100);

		StandardOpenOption option;
		if (!Files.exists(outputFilePath)) {
			option = StandardOpenOption.CREATE_NEW;
		} else {
			option = StandardOpenOption.TRUNCATE_EXISTING;
		}
		Files.write(outputFilePath, outputLines, option);

	}

	static long totFindTime = 0;

	private static boolean isSolvable(int l, int x, String str, int start, int rec) {

		String target = "";
		if (rec == 0)
			target = "i";
		else if (rec == 1)
			target = "j";
		else if (rec == 2)
			target = "k";

		if (l * x - start < 3 - rec)
			return false;

		if (l * x - start == 1 && (str.charAt(start % str.length()) + "").equals(target))
			return true;

		// find splits
		// long startTime = System.currentTimeMillis();
		List<Integer> possSplit1 = new ArrayList<Integer>();
		String firstChunk = null;
		for (int i = start; i < l * x - 2 + rec; i++) {
			firstChunk = firstChunk == null ? "" + str.charAt(i % str.length()) : mult(firstChunk,
					"" + str.charAt(i % str.length()));

			if (firstChunk.equals(target)) {
				possSplit1.add(i + 1);
			}
		}
		// totFindTime += System.currentTimeMillis() - startTime;

		if (possSplit1.size() == 0) {
			return false;
		} else if (rec == 2) {
			return true;
		} else {
			// System.out.println("rec:" + rec + ", " +
			// Arrays.toString(possSplit1.toArray()));
			for (Integer sp : possSplit1) {
				if (isSolvable(l, x, str, sp, rec + 1)) {
					return true;
				}
			}
			return false;
		}
	}

	private static int target(int rec) {
		switch (rec) {
		case 0:
			return si;
		case 1:
			return sj;
		case 2:
			return sk;
		default:
			return -1;
		}

	}

	private static HashMap<Long, Boolean> results = new HashMap<Long, Boolean>();

	private static int hit = 0;
	private static int tot = 0;

	private static boolean isSolvable(int l, long x, int[] str) throws Exception {

		int world = -1;
		for (int i = 0; i < str.length; i++) {
			world = (int) (i == 0 ? str[i] : preMult2[world][str[i]]);
		}

		results = new HashMap<Long, Boolean>();
		return isSolvable(world, l, x, str, 0, 0, -1, -1);

	}

	private static boolean isSolvable2(int l, long x, int[] str) throws Exception {

		if (l * x < 3) {
			return false;
		}

		// forward
		List<Long> forwardCand = new ArrayList<Long>();
		int firstChunk = str[0];
		for (long i2 = 1; i2 < l * x - 2; i2++) {
			if (firstChunk == si) {
				forwardCand.add(i2);// first excluded
			}
			firstChunk = (int) preMult2[firstChunk][str[(int) (i2 % l)]];
		}
		if (firstChunk == si) {
			forwardCand.add(l * x - 2);
		}

		if (forwardCand.size() == 0) {
			System.out.println("no fw");
			return false;
		}

		// backward
		List<Long> backwardCand = new ArrayList<Long>();
		int lastChunk = str[(int) ((l * x - 1) % l)];
		for (long i2 = l * x - 2; i2 > 0; i2--) {
			if (lastChunk == sk) {
				backwardCand.add(i2); // first excluded
			}
			lastChunk = (int) preMult2[str[(int) (i2 % l)]][lastChunk];
		}
		if (lastChunk == sk) {
			backwardCand.add(l * x - 2);
		}
		if (backwardCand.size() == 0) {
			System.out.println("no back");
			return false;
		}

		// pieces
		List<Long> pieces = new ArrayList<Long>(forwardCand.size() + backwardCand.size());
		pieces.addAll(forwardCand);
		pieces.addAll(backwardCand);
		Collections.sort(pieces);
		System.out.println("pieces:" + pieces.size());
		// System.out.println(" "+ Arrays.toString(pieces.toArray()));
		HashMap<Long, Integer> piecesVal = new HashMap<Long, Integer>();

		int mc = str[(int) (pieces.get(0) % l)];
		for (long ip = pieces.get(0) + 1; ip <= pieces.get(1); ip++) {
			mc = (int) preMult2[mc][str[(int) (ip % l)]];
		}
		piecesVal.put(pieces.get(0), mc);

		for (int i = 1; i < pieces.size() - 1; i++) {
			if (pieces.get(i + 1) - pieces.get(i) > 1) {
				// System.out.println("p1:"+pieces.get(i)+",p2:"+(pieces.get(i+1)));

				int medChunk = str[(int) ((pieces.get(i) + 1) % l)];
				// System.out.print(str[(int) (pieces.get(i) % l)]);
				for (long ip = pieces.get(i) + 2; ip <= pieces.get(i + 1); ip++) {
					// System.out.print(str[(int) (ip % l)]);
					medChunk = (int) preMult2[medChunk][str[(int) (ip % l)]];
				}
				piecesVal.put(pieces.get(i) + 1, medChunk);

				// System.out.println("="+medChunk);
			}
		}
		// System.out.println("pieces computed.");

		for (Long sp1 : forwardCand) {
			for (Long sp2 : backwardCand) {
				if (sp2 >= sp1) {
//					System.out.println("sp1:"+sp1+",sp2:"+sp2);

					Integer medChunk = null;
					if (sp1 == sp2) {
						medChunk = str[(int) (sp1 % l)];
					} else {
						long min = Long.MAX_VALUE;
						long max = Long.MIN_VALUE;
						for (Long st : piecesVal.keySet()) {
							if (st >= sp1 && st <= sp2) {
								min = min < st ? min : st;
								max = max > st ? max : st;
								if (medChunk == null) {
									medChunk = piecesVal.get(st);
								} else {
									medChunk = (int) preMult2[medChunk][piecesVal.get(st)];
								}
							}							
						}
						if(sp1 < min){
							for (long i =min-1; i >= sp1; i--) {
								medChunk = (int) preMult2[str[(int) (i % l)]][medChunk];
							}
						}
//						System.out.println("min:"+min+", max:"+max);
						//System.out.println("min:"+Collections.min(piecesVal.keySet())+",max:"+Collections.max(piecesVal.keySet()));
						// System.out.println();
						// System.out.println(medChunk);
					}

//					 System.out.println("medChunk2");					
//					int medChunk2 = str[(int) (sp1 % l)];
////					 System.out.print(str[(int) (sp1 % l)]);
//					for (long i = sp1 + 1; i <= sp2; i++) {
////						 System.out.print(str[(int) (i % l)]);
//						medChunk2 = (int) preMult2[medChunk2][str[(int) (i % l)]];
//					}
////					 System.out.println("="+medChunk2);
//
//					if (medChunk != medChunk2) {
//						System.out.println("error");
//					}

					if (medChunk == sj) {
						System.out.println("Solved: " + sp1 + "," + sp2);
						return true;
					}
				}
			}
		}
		return false;

	}

	private static boolean isSolvable(int preMultWorld, int l, long x, int[] str, long start, int rec, long split1,
			long split2) throws Exception {

		tot++;

		if (l * x - start < 3 - rec) {
			// results.put(key, false);
			return false;
		}
		long target = target(rec);
		if (l * x - start == 1 && (str[(int) (start % l)] == target)) {
			// results.put(key, true);
			return true;
		}

		// 1/2(k1 + k2)(k1 + k2 + 1) + k2
		long key = (long) (0.5 * (start + rec) * (start + rec + 1) + rec);
		// long key =(long) (Math.pow(2, start) * Math.pow(3, rec));
		if (results.containsKey(key)) {
			hit++;
			return results.get(key);
		}

		// find splits
		List<Long> possSplit1 = new ArrayList<Long>();
		if (rec == 2) {
			int fc = str[(int) (start % l)];
			long in = start + 1;
			while (in < l * x - 2 + rec && in % l != 0) {
				fc = (int) preMult2[fc][str[(int) (in % l)]];
				in++;
			}
			while ((in < l * x)) {
				fc = (int) preMult2[fc][preMultWorld];
				in += l;
			}

//			int firstChunk = -1;
//
//			for (long i2 = start; i2 < l * x - 2 + rec; i2++) {
//				firstChunk = (int) (i2 == start ? str[(int) (i2 % l)] : preMult2[firstChunk][str[(int) (i2 % l)]]);
//			}
//			if (firstChunk != fc) {
//				System.out.println("Err:" + start + "," + in);
//				throw new Exception();
//			}

			results.put(key, fc == target);
			return fc == target;

		}

		// System.out.println("c:"+c+":"+Arrays.toString(db1.toArray()));

		int firstChunk = -1;

		for (long i = start; i < l * x - 2 + rec; i++) {
			firstChunk = (int) (i == start ? str[(int) (i % l)] : preMult2[firstChunk][str[(int) (i % l)]]);

			if (firstChunk == target) {
//				System.out.println("rec: " + rec + ",i:" + i);
				// possSplit1.add(i + 1);
				long sp1 = rec == 0 ? i + 1 : split1;
				long sp2 = rec == 1 ? i + 1 : split2;
				if (rec < 2 && isSolvable(preMultWorld, l, x, str, i + 1, rec + 1, sp1, sp2)) {
					results.put(key, true);
					return true;
				}
			}
		}

		if (rec == 2 && firstChunk == target) {
			// results.put(key, true);
			System.out.println("Solved:" + split1 + "," + split2);
			return true;
		} else if (rec != 2 && possSplit1.size() == 0) {
			results.put(key, false);
			return false;
			// } else if (rec < 2 && possSplit1.size() > 0) {
			// System.out.println("rec:" + rec + ", " +
			// Arrays.toString(possSplit1.toArray()));
			// // System.out.println("poss: "+possSplit1.size());
			// for (Long sp : possSplit1) {
			// long sp1 = rec == 0 ? sp : split1;
			// long sp2 = rec == 1 ? sp : split2;
			// if (isSolvable(preMultWorld, l, x, str, sp, rec + 1, sp1, sp2)) {
			// results.put(key, true);
			// return true;
			// }
			// }
			// results.put(key, false);
			// return false;
		} else {
			results.put(key, false);
			return false;
		}
	}

	static long totMultTime = 0;

	private static boolean isNeg(String a) {

		return a.length() != 1 && a.charAt(0) == '-';
	}

	private static boolean isNeg(int a) {
		switch (a) {
		case neg1:
		case negi:
		case negj:
		case negk:
			return true;
		default:
			return false;
		}
	}

	private static int abs(int a) {
		switch (a) {
		case neg1:
			return s1;
		case negi:
			return si;
		case negj:
			return sj;
		case negk:
			return sk;
		default:
			return a;
		}
	}

	private static int neg(int a) {
		switch (a) {
		case neg1:
			return s1;
		case negi:
			return si;
		case negj:
			return sj;
		case negk:
			return sk;
		case s1:
			return neg1;
		case si:
			return negi;
		case sj:
			return negj;
		case sk:
			return negk;
		default:
			return -1;
		}
	}

	private static int toInt(char a) {
		switch (a) {
		// case '1':
		// return s1;
		case 'i':
			return si;
		case 'j':
			return sj;
		case 'k':
			return sk;

		default:
			return -1;
		}
	}

	private static int[] strToIntArray(String str) {
		int[] res = new int[str.length()];
		for (int i = 0; i < res.length; i++) {
			res[i] = toInt(str.charAt(i));
		}
		return res;
	}

	private static String mult(String a, String b) {

		boolean signNeg1 = isNeg(a) ^ isNeg(b);

		String res = mult[pos(a)][pos(b)];

		boolean resSign = isNeg(res);

		boolean totSign = resSign ^ signNeg1;

		String resWoSign = res.length() == 1 ? res : res.substring(1);
		String resStr = (totSign ? "-" + resWoSign : resWoSign);

		return resStr;
	}

	private static int mult(int a, int b) {

		boolean signNeg1 = isNeg(a) ^ isNeg(b);

		int res = mult2[abs(a)][abs(b)];

		boolean resSign = isNeg(res);

		boolean totSign = resSign ^ signNeg1;

		return (totSign ? neg(abs(res)) : abs(res));

	}

	static long totPosTime = 0;

	private static int pos(String a) {
		int res = -1;
		if (a.equals("1") || a.equals("-1"))
			res = 0;
		else if (a.equals("i") || a.equals("-i"))
			res = 1;
		else if (a.equals("j") || a.equals("-j"))
			res = 2;
		else if (a.equals("k") || a.equals("-k"))
			res = 3;
		else
			res = -1;

		return res;
	}

}

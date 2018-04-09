package codeJam2015.qualification.standingOvation;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StandingOvation {

	public static void main(String[] args) throws Exception {

		String fileName = "example1.txt";
		// String fileName = "A-small-attempt1.in";
		// String fileName = "A-large.in";

		Path mainDir = Paths.get(System.getProperty("user.dir") + "/CodeJam2015/"
				+ StandingOvation.class.getPackage().getName().replace('.', java.io.File.separatorChar));

		Path inputFilePath = mainDir.resolve(fileName);
		Path outputFilePath = mainDir.resolve(fileName + ".out");

		List<String> inputLines = Files.readAllLines(inputFilePath, StandardCharsets.UTF_8);

		List<String> outputLines = new ArrayList<String>();

		long start = System.currentTimeMillis();

		int numCases = Integer.parseInt(inputLines.get(0));

		for (int i = 0; i < numCases; i++) {

			String[] parameters = inputLines.get(i + 1).split(" ");
			int sMax = Integer.parseInt(parameters[0]);
			int[] audience = GetAudienceArray(parameters[1]);

			// System.out.println("n:" + n + ",l:" + l););
			System.out.println(Arrays.toString(audience));

			int min = getMinNumOfFriends(sMax, audience);

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

	private static int getMinNumOfFriends(int sMax, int[] audience) {
		int res = 0;
		int people = 0;
		int standing = audience[0];
		for (int i = 1; i < audience.length; i++) {
			if (audience[i] > 0 && i > standing) {
				res += i - standing;
				standing += i - standing;
			}

			standing += audience[i];
		}
		return res;
		// for (int i = 0; i < audience.length - 1; i++) {
		// people += audience[i];
		// }
		// res = audience.length - 1 - people;
		// res = res < 0 ? 0 : res;
		//
		// return res;
	}

	private static int[] GetAudienceArray(String string) {
		int[] res = new int[string.length()];
		for (int i = 0; i < res.length; i++) {
			res[i] = Integer.parseInt(string.charAt(i) + "");

		}
		return res;
	}

}

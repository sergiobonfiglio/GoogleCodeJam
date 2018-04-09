package codeJam2016.round1b.a;

import com.sun.deploy.util.ArrayUtil;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class A {


    public static void main(String[] args) throws Exception {

//        String fileName = "example1.txt";
        String fileName = "A-small-attempt1.in";
//		 String fileName = "A-large-practice.in";

        Path mainDir = Paths.get(System.getProperty("user.dir") + "/CodeJam2016/"
                + A.class.getPackage().getName().replace('.', java.io.File.separatorChar));

        Path inputFilePath = mainDir.resolve(fileName);
        Path outputFilePath = mainDir.resolve(fileName + ".out");

        List<String> inputLines = Files.readAllLines(inputFilePath, StandardCharsets.UTF_8);

        List<String> outputLines = new ArrayList<String>();

        long start = System.currentTimeMillis();

        int numCases = Integer.parseInt(inputLines.get(0));

        for (int i = 0; i < numCases; i++) {

            int firstLine = i + 1;
            String S = inputLines.get(firstLine);

            String sol = solve(S);


            String caseResult = "Case #" + (i + 1) + ": " + sol;
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

    private static String[] digits = {"ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE"};
    private static HashMap[] digitsCounts = new HashMap[digits.length];


    static {
        for (int i = 0; i < digits.length; i++) {
            digitsCounts[i] = buildCount(digits[i]);
        }
    }

    private static HashMap<Character, Integer> buildCount(String s) {
        HashMap<Character, Integer> charCounts = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char key = s.charAt(i);
            Integer actual = charCounts.getOrDefault(key, 0);
            charCounts.put(key, actual + 1);
        }
        return charCounts;
    }


    private static String solve2(HashMap<Character, Integer> charCounts, List<Integer> allowedDigits) {
        String result = "";
        HashMap<Character, Integer> clonedCounts = (HashMap<Character, Integer>) charCounts.clone();
        String currDigit = digits[allowedDigits.get(0)];
        int howMany = Integer.MAX_VALUE;
        for (int j = 0; j < currDigit.length() && howMany > 0; j++) {
            char currChar = currDigit.charAt(j);
            Integer found = clonedCounts.getOrDefault(currChar, 0);
            int foundInDigit = (int) digitsCounts[allowedDigits.get(j)].getOrDefault(currChar, 0);

            if (found > 0 && foundInDigit > 0) {
                howMany = Math.min(howMany, found / foundInDigit);
            } else {
                howMany = 0;
            }
        }

        if (howMany != Integer.MAX_VALUE && howMany > 0) {
            for (int j = 0; j < howMany; j++) {
                result += allowedDigits.get(0);
            }
            for (int k = 0; k < currDigit.length(); k++) {
                char currChar = currDigit.charAt(k);
                Integer currCount = clonedCounts.get(currChar);
                assert currCount > 0;
                clonedCounts.put(currChar, currCount - howMany);
            }
        }
        result += solve2(clonedCounts, allowedDigits.subList(1, allowedDigits.size()));


        boolean allLetters = true;
        for (Integer c : clonedCounts.values()) {
            allLetters &= c == 0;
        }

        if (!allLetters) {
            //try solve
            String newRes = "";
            for (int i = 0; i < digits.length; i++) {
                List<Integer> newAllowed = new ArrayList<>();
                for (int j = 0; j < digits.length; j++) {
                    if (i != j) {
                        newAllowed.add(j);
                    }
                }
                solve2(charCounts, newAllowed);
            }
        }

        return result;
    }

    private static String solve(String s) throws Exception {
        HashMap<Character, Integer> charCounts = buildCount(s);


        String result = "";
        for (int i = 0; i < digits.length; i++) {
            int howMany = Integer.MAX_VALUE;
            for (int j = 0; j < digits[i].length() && howMany > 0; j++) {
                char currChar = digits[i].charAt(j);
                Integer found = charCounts.getOrDefault(currChar, 0);
                int foundInDigit = (int) digitsCounts[i].getOrDefault(currChar, 0);

                if (found > 0 && foundInDigit > 0) {
                    howMany = Math.min(howMany, found / foundInDigit);
                } else {
                    howMany = 0;
                }
            }

            if (howMany != Integer.MAX_VALUE && howMany > 0) {
                for (int j = 0; j < howMany; j++) {
                    result += i;
                }
                for (int k = 0; k < digits[i].length(); k++) {
                    char currChar = digits[i].charAt(k);
                    Integer currCount = charCounts.get(currChar);
                    assert currCount > 0;
                    charCounts.put(currChar, currCount - howMany);
                }
            }

        }

        boolean allLetters = true;
        for (Integer c : charCounts.values()) {
            allLetters &= c == 0;
        }

        if (!allLetters) {
            System.out.println("not all letters were consumed!");
            charCounts = buildCount(s);
            result = "";
            for (int i = digits.length - 1; i >= 0; i--) {
                int howMany = Integer.MAX_VALUE;
                for (int j = 0; j < digits[i].length() && howMany > 0; j++) {
                    char currChar = digits[i].charAt(j);
                    Integer found = charCounts.getOrDefault(currChar, 0);
                    int foundInDigit = (int) digitsCounts[i].getOrDefault(currChar, 0);

                    if (found > 0 && foundInDigit > 0) {
                        howMany = Math.min(howMany, found / foundInDigit);
                    } else {
                        howMany = 0;
                    }
                }

                if (howMany != Integer.MAX_VALUE && howMany > 0) {
                    for (int j = 0; j < howMany; j++) {
                        result = i + result;
                    }
                    for (int k = 0; k < digits[i].length(); k++) {
                        char currChar = digits[i].charAt(k);
                        Integer currCount = charCounts.get(currChar);
                        assert currCount > 0;
                        charCounts.put(currChar, currCount - howMany);
                    }
                }

            }
        }

        allLetters = true;
        for (Integer c : charCounts.values()) {
            allLetters &= c == 0;
        }
        if (!allLetters) {
            throw new Exception();
        }

        return result;

    }


}

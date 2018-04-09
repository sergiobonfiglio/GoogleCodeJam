package codeJam2016.qualification.revengeOfThePancakes;

import javax.naming.InsufficientResourcesException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class RevengeOfThePancakes {

    public static void main(String[] args) throws IOException {
        Path mainDir = Paths.get(System.getProperty("user.dir") + "/CodeJam2016/"
                + RevengeOfThePancakes.class.getPackage().getName().replace('.', java.io.File.separatorChar));

//        String fileName = "example1.txt";
//        String fileName = "B-small-attempt0.in";
        String fileName = "B-large.in";

        Path inputFilePath = mainDir.resolve(fileName);
        Path outputFilePath = mainDir.resolve(fileName + ".out");

        List<String> inputLines = Files.readAllLines(inputFilePath, StandardCharsets.UTF_8);
        List<String> outputLines = new ArrayList<String>();

        long start = System.currentTimeMillis();

        int numCases = Integer.parseInt(inputLines.get(0));

        for (int i = 0; i < numCases; i++) {

            String line = inputLines.get(i + 1);
//            int n = Integer.parseInt();

            String caseResult = "Case #" + (i + 1) + ": " + count(line);
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


    private static int count(String s) {

        int numSeq = getNumSeq(s);
        boolean isLastPlus = s.charAt(s.length() - 1) == '+' ? true : false;

        if (numSeq == 1) {
            return isLastPlus ? 0 : 1;
        } else {

            return isLastPlus ? numSeq-1 : numSeq;
        }



    }


    private static int getNumSeq(String s) {
        int sequence = 0;
        if (s != null && s.length() > 0) {

            char firstC = s.charAt(0);
            int oldSign = firstC == '+' ? 1 : -1;
            int seqLen = 1;
            for (int i = 1; i < s.length(); i++) {
                int currSign = s.charAt(i) == '+' ? 1 : -1;
                if (oldSign != currSign) {
                    sequence++;
                    seqLen = 1;
                    oldSign = currSign;
                }
            }
            sequence++;

        }
        return sequence;
    }

    private static ArrayList<Integer> getSeq(String s) {
        ArrayList<Integer> sequence = new ArrayList<>();
        if (s != null && s.length() > 0) {

            char firstC = s.charAt(0);
            int oldSign = firstC == '+' ? 1 : -1;
            int seqLen = 1;
            for (int i = 1; i < s.length(); i++) {
                int currSign = s.charAt(i) == '+' ? 1 : -1;
                if (oldSign != currSign) {
                    sequence.add(seqLen * oldSign);
                    seqLen = 1;
                    oldSign = currSign;
                }
            }
            sequence.add(seqLen * oldSign);

        }
        return sequence;
    }

}
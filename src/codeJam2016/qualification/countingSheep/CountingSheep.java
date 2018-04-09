package codeJam2016.qualification.countingSheep;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

class CountingSheep {

    public static void main(String[] args) throws IOException {
        Path mainDir = Paths.get(System.getProperty("user.dir") + "/CodeJam2016/"
                + CountingSheep.class.getPackage().getName().replace('.', java.io.File.separatorChar));

//        String fileName = "example1.txt";
//        String fileName = "A-small-attempt0.in";
        String fileName = "A-large.in";

        Path inputFilePath = mainDir.resolve(fileName);
        Path outputFilePath = mainDir.resolve(fileName + ".out");

        List<String> inputLines = Files.readAllLines(inputFilePath, StandardCharsets.UTF_8);
        List<String> outputLines = new ArrayList<String>();

        long start = System.currentTimeMillis();

        int numCases = Integer.parseInt(inputLines.get(0));

        for (int i = 0; i < numCases; i++) {

            int n = Integer.parseInt(inputLines.get(i + 1));

            String caseResult = "Case #" + (i + 1) + ": " + count(n);
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


    private static String count(int n) {

        if (n == 0) {
            return "INSOMNIA";
        } else {

            Set<Character> seenDigits = new HashSet<>(10);

            int currentCount = n;
            do {
                String digits = Integer.toString(currentCount);
                int added = 0;
                for (int i = 0; i < digits.length() && added < 10 && seenDigits.size() < 10; i++) {
                    added += seenDigits.add(digits.charAt(i)) ? 1 : 0;
                }

                currentCount += n;

            } while (seenDigits.size() < 10);

            currentCount -= n;


            return "" + currentCount;
        }

    }

}
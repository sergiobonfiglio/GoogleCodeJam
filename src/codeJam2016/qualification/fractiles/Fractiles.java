package codeJam2016.qualification.fractiles;

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

class Fractiles {

    public static void main(String[] args) throws IOException {
        Path mainDir = Paths.get(System.getProperty("user.dir") + "/CodeJam2016/"
                + Fractiles.class.getPackage().getName().replace('.', java.io.File.separatorChar));

//        String fileName = "example1.txt";
//        String fileName = "D-small-attempt0.in";
        String fileName = "D-small-attempt2.in";
//        String fileName = "D-large.in";

        Path inputFilePath = mainDir.resolve(fileName);
        Path outputFilePath = mainDir.resolve(fileName + ".out");

        List<String> inputLines = Files.readAllLines(inputFilePath, StandardCharsets.UTF_8);
        List<String> outputLines = new ArrayList<String>();

        long start = System.currentTimeMillis();

        int numCases = Integer.parseInt(inputLines.get(0));

        for (int i = 0; i < numCases; i++) {

            String line = inputLines.get(i + 1);
            String[] params = line.split(" ");
            int k = Integer.parseInt(params[0]);
            int c = Integer.parseInt(params[1]);
            int s = Integer.parseInt(params[2]);

            String caseResult = "Case #" + (i + 1) + ": " + solve(k, c, s);
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


    private static String solve(int k, int c, int s) {
        String result = "";

        if (k <= 1) {
            return "1";
        } else {

            for (int i = 0; i < k; i++) {
                result += (i + 1) + " ";
            }
        }
        return result;

    }

}
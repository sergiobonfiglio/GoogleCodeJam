package codeJam2018.qualification.savingTheUniverse;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));

//        String dir = Solution.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath() + Solution.class.getPackage().getName().replaceAll("\\.", "/");
//        in = new Scanner(new File(dir + "\\example.txt"));

        int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int d = in.nextInt();
            String p = in.next();
            System.out.println("Case #" + i + ": " + solve(d, p));
        }
    }

    private static String swap(String p, int i) {
        char[] chars = p.toCharArray();
        char tmp = chars[i];
        chars[i] = chars[i + 1];
        chars[i + 1] = tmp;
        return new String(chars);
    }

    private static String solve(int d, String p) {

        if (p.length() < 2 || p.equals("CC")) {
            return "0";
        } else if (!p.contains("C") && d < p.length()) {
            return "IMPOSSIBLE";
        }

        long dam = countDamage(p);
        if (dam <= d) {
            return "0";
        } else {

            while (p.charAt(p.length() - 1) == 'C') {
                p = p.substring(0, p.length() - 1);
            }

            long mult = p.chars().filter(c -> c == 'C').count() * 2;
            long curDam = dam;
            int swap = 0;
            while (curDam > d && p.length() > 0 && mult > 0) {

                int lastSc = p.lastIndexOf("CS");
                if (lastSc == -1) {
                    break;
                }
                p = swap(p, lastSc);
                curDam -= mult / 2;

                if (p.charAt(p.length() - 1) == 'C') {
                    p = p.substring(0, p.length() - 1);
                }

                mult = p.chars().filter(c -> c == 'C').count() * 2;
                swap++;
            }

            if (curDam <= d) {
                return String.valueOf(swap);
            } else {
                return "IMPOSSIBLE";
            }

        }
    }

    private static long countDamage(String p) {
        final long[] damage = {1};
        final long[] sum = {0};
        p.chars().forEach(c -> {
            if (c == 'C') {
                damage[0] *= 2;
            } else {
                sum[0] += damage[0];
            }
        });

        return sum[0];
    }
}
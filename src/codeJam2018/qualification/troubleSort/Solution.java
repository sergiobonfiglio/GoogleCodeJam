package codeJam2018.qualification.troubleSort;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));

        String dir = Solution.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath() + Solution.class.getPackage().getName().replaceAll("\\.", "/");
        in = new Scanner(new File(dir + "\\example.txt"));

        int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
        long start = System.currentTimeMillis();

        for (int i = 1; i <= t; ++i) {
            int n = in.nextInt();
            int[] arr = new int[n];
            for (int j = 0; j < n; j++) {
                arr[j] = in.nextInt();
            }

            System.out.println("Case #" + i + ": " + solve2(arr));

            for (int k = 0; k < 100; k++) {
                double len = Math.pow(10, 5);
//                double max = Math.pow(10, 9);
                double max = 100;
                arr = new int[(int) len];
                for (int j = 0; j < arr.length; j++) {
                    arr[j] = (int) (Math.random() * max);
                }

//            System.out.println(Arrays.toString(arr));
                System.out.println("Special Case #" + i + ": " + solve2(arr));
            }
            System.out.println("time: " + (System.currentTimeMillis() - start) + "ms");
        }
    }

    private static String solve2(int[] arr) {
        boolean done = false;
        int minIx = 1;
        while (!done) {
            done = true;
            for (int i = arr.length - 1; i > minIx; i--) {
                if (arr[i] < arr[i - 2]) {
                    done = false;
                    int tmp = arr[i];
                    arr[i] = arr[i - 2];
                    arr[i - 2] = tmp;
                }
            }

            //fino a minIx OK
            for (int i = 1; i < minIx; i++) {
                if (arr[i - 1] > arr[i]) {
                    return "" + (i - 1);
                }
            }
            minIx++;
        }

        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1] > arr[i]) {
                return "" + (i - 1);
            }
        }

        return "OK";
    }

    private static String solve(int[] arr) {

        troubleSort2(arr);


        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1] > arr[i]) {
                return "" + (i - 1);
            }
        }

        return "OK";

    }

    private static int[] troubleSort2(int[] arr) {
        boolean done = false;
        int minIx = 1;
        while (!done) {
            done = true;
            for (int i = arr.length - 1; i > minIx; i--) {
                if (arr[i] < arr[i - 2]) {
                    done = false;
                    int tmp = arr[i];
                    arr[i] = arr[i - 2];
                    arr[i - 2] = tmp;
                }
            }
//            System.out.println(Arrays.toString(arr));

            //fino a minIx OK
//            System.out.println("controllo fino a "+minIx);
            for (int i = 1; i < minIx; i++) {
                if (arr[i - 1] > arr[i]) {
                    System.out.println("AAAAA:" + (i - 1));
//                    return "" + (i - 1);
                }
            }
            minIx++;
        }
        return arr;
    }


    private static int[] troubleSort(int[] arr) {
        boolean done = false;
        while (!done) {
            done = true;
            for (int i = 0; i < arr.length - 2; i++) {
                if (arr[i] > arr[i + 2]) {
                    done = false;
                    int tmp = arr[i];
                    arr[i] = arr[i + 2];
                    arr[i + 2] = tmp;
                }
            }

        }
        return arr;
    }

}
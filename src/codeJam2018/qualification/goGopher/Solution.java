package codeJam2018.qualification.goGopher;

import java.util.Scanner;

public class Solution {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();

        for (int i = 0; i < t; ++i) {
//            System.out.println("" + 2 + " " + 2);//bug?
            int minArea = in.nextInt();
//            System.err.println("t:" + t + ", minArea:" + minArea);

            double sqrt = Math.sqrt(minArea);
            boolean[][] area = new boolean[(int) Math.floor(sqrt)][(int) Math.ceil(sqrt)];
//            System.out.println(area.length + "x" + area[0].length);
            int ex = 0;

            boolean failed = false;
            while (failed == false) {
//                for (int j = 0; j < area.length; j++) {
//                    System.out.println(Arrays.toString(area[j]));
//                }
//                System.out.println("---------------------------");

                for (int r = 1; r < area.length - 1; r++) {
                    for (int c = 1; c < area[r].length - 1 && !failed; c++) {

                        if (!allPrepared(area, r - 1, c - 1)) {
                            System.out.println((r + 1) + " " + (c + 1));
                            ex++;
                            //read
                            int i1 = in.nextInt();
                            int j1 = in.nextInt();
                            if ((i1 == -1 && j1 == -1)) {
                                failed = true;
                                System.err.println("exit:" + ex + "," + i1 + " " + j1);
                                System.exit(0);
                            } else if (i1 == 0 && j1 == 0) {
                                failed = true;
                            } else {
                                area[i1 - 1][j1 - 1] = true;
                            }
                        } else {
                        }


                    }
                }

            }

        }
    }


    private static boolean allPrepared(boolean[][] area, int top, int left) {
        boolean allPrepared = true;
        for (int r = top; r < top + 3 && allPrepared; r++) {
            for (int c = left; c < left + 3 && allPrepared; c++) {
                allPrepared = allPrepared && area[r][c];
            }
        }
        return allPrepared;
    }

}
package codeJam2014.minesweeperMaster;

import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class MineSweeperMaster {

    public static void main(String[] args) throws Exception {
	String mainDir = "/Users/sergio/Downloads";
	Path path = FileSystems.getDefault().getPath(mainDir, "C-large.in");
	Path outputFile = FileSystems.getDefault().getPath(mainDir, "output.txt");

	List<String> inputLines = Files.readAllLines(path, StandardCharsets.UTF_8);

	List<String> outputLines = new ArrayList<String>();

	long start = System.currentTimeMillis();

	int numCases = Integer.parseInt(inputLines.get(0));
	inputLines = inputLines.subList(1, inputLines.size());
	for (int i = 0; i < numCases; i++) {

	    String[] parameters = inputLines.get(i).split(" ");
	    int r = Integer.parseInt(parameters[0]);
	    int c = Integer.parseInt(parameters[1]);
	    int m = Integer.parseInt(parameters[2]);

	    System.out.println(r + ", " + c + ", " + m);

	    char[][] solution = GetSolution2(r, c, m);

	    String result;

	    if (solution == null)
		result = "Impossible";
	    else
		result = stringMatrix(solution);

	    String caseResult = "Case #" + (i + 1) + ":\n" + result;
	    outputLines.add(caseResult);
	    System.out.println(caseResult);

	}
	System.out.println("Completed: " + (System.currentTimeMillis() - start) / 1000d + " s");

	StandardOpenOption option;
	if (!Files.exists(outputFile)) {
	    option = StandardOpenOption.CREATE_NEW;
	} else {
	    option = StandardOpenOption.TRUNCATE_EXISTING;
	}
	Files.write(outputFile, outputLines, option);

    }

    private static char[][] GetSolution2(int r, int c, int m) {

	char[][] matrix = null;
	matrix = new char[r][c];

	if (isSolvable(matrix, 0, 0, r, c, m, false)) {

	    // System.out.println("is solvable!");
	    for (int i = 0; i < matrix.length; i++) {
		for (int j = 0; j < matrix[0].length; j++) {
		    if (matrix[i][j] != '*')
			matrix[i][j] = '.';
		}
	    }
	    matrix[r - 1][c - 1] = 'c';
	    return matrix;
	}
	return null;

    }

    private static String stringMatrix(char[][] matrix) {
	String str = "";
	for (int i = 0; i < matrix.length; i++) {
	    for (int j = 0; j < matrix[0].length; j++) {
		str += matrix[i][j];
	    }
	    str += "\n";
	}
	// remove last return
	str = str.substring(0, str.length() - 1);
	return str;
    }

    private static boolean isSolvable(char[][] matrix, int startR, int startC, int r, int c, int m,
	    boolean hasNeighborhood) {
	// System.out.println("solvable? r: " + r + ", c: " + c + ", m: " + m +
	// ", N: " + hasNeighborhood + "startR: "
	// + startR + ",startC: " + startC);

	int max = Math.max(r, c);
	int min = Math.min(r, c);

	if (!hasNeighborhood && ((min == 1 && m < max) || m == 0 || m == r * c - 1)) {
	    printMatrix(matrix, startR, startC, r, c, m, true);

	    // System.out.print("->4");
	    return true;
	}

	int fullRows = m / min;
	int remainingMines = m % min;

	if (fullRows <= max - 2 && remainingMines == 0) {
	    // System.out.print("->1");
	    printMatrix(matrix, startR, startC, r, c, m - remainingMines, min == c);
	    return true;
	} else if (fullRows < max - 2 && remainingMines <= min - 2) {
	    // System.out.print("->2");
	    printMatrix(matrix, startR, startC, r, c, fullRows * min + remainingMines, min == c);
	    return true;
	} else if (fullRows == 0 && remainingMines <= max - 2 && min > 2) {
	    // System.out.print("->3");
	    printMatrix(matrix, startR, startC, r, c, remainingMines, min == r);
	    return true;
	} else if (fullRows > 0 && min > 2) {
	    // System.out.print("->6");
	    int minesPlaced = min;
	    int minesToPlace = m - minesPlaced;
	    if (max == r) {
		printMatrix(matrix, startR, startC, 1, c, minesPlaced, true);
		return isSolvable(matrix, startR + 1, startC, r - 1, c, minesToPlace, true);
	    } else {
		printMatrix(matrix, startR, startC, r, 1, minesPlaced, false);
		return isSolvable(matrix, startR, startC + 1, r, c - 1, minesToPlace, true);
	    }
	} else if (fullRows == 0 && max - remainingMines / max > 1 && min > 2) {
	    // System.out.print("->7");
	    if (max == r) {
		printMatrix(matrix, startR, startC, r, 1, Math.min(max - 2, remainingMines), true);
		return isSolvable(matrix, startR, startC + 1, r, c - 1, remainingMines - (min - 2), true);
	    } else {
		printMatrix(matrix, startR, startC, 1, c, Math.min(max - 2, remainingMines), false);
		return isSolvable(matrix, startR + 1, startC, r - 1, c, remainingMines - (min - 2), true);
	    }
	} else {
	    // System.out.print("->5f");
	    return false;
	}
    }

    public static void printMatrix(char[][] matrix, int startR, int startC, int r, int c, int m, boolean horizontally) {
	// System.out.println("printing: startR" + startR + ", startC" + startC
	// + ",r: " + r + ",c:" + c + ",h: "
	// + horizontally + ",m: " + m);
	if (horizontally) {
	    for (int i = startR; i < startR + r; i++) {
		for (int j = startC; j < startC + c && m-- > 0; j++) {
		    matrix[i][j] = '*';
		}
	    }
	} else {
	    for (int j = startC; j < startC + c; j++) {
		for (int i = startR; i < startR + r && m-- > 0; i++) {
		    matrix[i][j] = '*';
		}
	    }
	}

    }

}

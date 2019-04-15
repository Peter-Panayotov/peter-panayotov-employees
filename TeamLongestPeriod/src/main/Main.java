package main;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;

import model.LongestPeriodCalculator;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		Scanner consoleReader = new Scanner(System.in);
		boolean isFileExists;
		String path;

		do {
			System.out.print("Enter the path to the text file: ");
			path = consoleReader.nextLine();
			isFileExists = checkIfFileExists(path);
		} while (!isFileExists);

		consoleReader.close();
		LongestPeriodCalculator calc = new LongestPeriodCalculator(path);
		calc.calculate();
	}

	private static boolean checkIfFileExists(String filePath) {
		File csvFile = new File(filePath);
		if (csvFile.exists()) {
			return true;
		}

		System.out.println("The data file does not exist.");
		return false;
	}
}

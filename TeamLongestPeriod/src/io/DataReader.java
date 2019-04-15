package io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class DataReader {
	private Scanner reader;

	public DataReader(String path) throws FileNotFoundException {
		reader = new Scanner(new BufferedReader(new FileReader(path)));
		reader.useDelimiter(System.lineSeparator());
	}

	public String[] readLine() {
		// Returns empty array if end of stream.
		if (isEndOfStream()) {
			return new String[0];
		}

		return readData(reader.next());
	}
	
	private String[] readData(String currentLine) {
		String[] tokens = currentLine.split(",");
		for (int i = 0; i < tokens.length; i++) {
			tokens[i] = tokens[i].trim();
		}

		return tokens;
	}

	private boolean isEndOfStream() {
		if (!reader.hasNext()) {
			reader.close();

			return true;
		}

		return false;
	}
}

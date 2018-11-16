package sc.lab2_2;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import sc.lab2_2.tools.InputTools;

public class Lab2_2 {
	public static void main(String[] args) {
		File inputFile = InputTools.getFile("Path to the input file : ", true);
		File outputFile = InputTools.getFile("Path to the output file : ", false);
		reverseLines(inputFile, outputFile);
		printFileContents(inputFile);
		printFileContents(outputFile);

		exit(0);
	}

	private static void reverseLines(File inputFile, File outputFile) {
		try (FileReader fileReader = new FileReader(inputFile)) {
			Boolean append = false;

			// If output file exists
			if (outputFile.exists()) {
				System.out.println("Output file already exists.");
				append = askToAppend(outputFile);

				// If user decides not to append nor overwrite the output file
				if (append == null) {
					exit(-1);
				}
			}

			try (FileWriter fileWriter = new FileWriter(outputFile, append)) {
				char readChar;
				StringBuilder line = new StringBuilder();

				while ((readChar = (char) fileReader.read()) != (char) -1) {
					if (readChar == '\n') {
						line.reverse();
						line.append(readChar); // Append with the newline character
						fileWriter.write(line.toString());
						line.setLength(0); // Clear the buffer
						continue;
					}

					line.append(readChar);
				}

			} catch (Exception e) {
				throw e;
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	// Ask to append or overwrite the file
	private static Boolean askToAppend(File outputFile) {
		if (InputTools.getBoolean(String.format("Append? [%s/%s] : ", InputTools.sTrue, InputTools.sFalse),
				InputTools.sTrue, InputTools.sFalse)) {
			return true;
		} else if (InputTools.getBoolean(String.format("Overwrite? [%s/%s] : ", InputTools.sTrue, InputTools.sFalse),
				InputTools.sTrue, InputTools.sFalse)) {
			return false;
		}

		return null;
	}

	private static void printFileContents(File file) {
		System.out.println("File " + file.getPath());
		System.out.println("START OF FILE");

		try (FileReader fileReader = new FileReader(file)) {
			char readChar;

			while ((readChar = (char) fileReader.read()) != (char) -1) {
				System.out.print(readChar);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("END OF FILE");
	}

	private static void exit(int status) {
		System.out.println("Exiting...");
		InputTools.scanner.close();
		System.exit(status);
	}
}

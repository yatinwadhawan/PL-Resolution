import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SAT {

	public static int[][] matrix;
	public static int TOTAL_TABLE;
	public static int TOTAL_GUEST;
	public static ArrayList<Clauses> sentence = new ArrayList<Clauses>();

	// Main Function
	public static void main(String[] args) throws IOException {
		SAT s = new SAT();
		String data = s
				.takeInputFromFile("/Users/yatinwadhawan/Documents/workspace/HW3/src/input");
		s.parseInput(data);
		if (TOTAL_TABLE != 0 && TOTAL_GUEST != 0) {
			boolean flag = PLRESOLVE.PL_RESOLVE();
			if (flag) {
				s.putOutputToFile(
						"/Users/yatinwadhawan/Documents/workspace/HW3/src/output",
						"1");
			} else {
				s.putOutputToFile(
						"/Users/yatinwadhawan/Documents/workspace/HW3/src/output",
						"0");
			}
		} else {
			s.putOutputToFile(
					"/Users/yatinwadhawan/Documents/workspace/HW3/src/output",
					"0");
		}
	}

	// Open the Input file and extract values from it.
	public String takeInputFromFile(String path) throws IOException {
		StringBuffer output = new StringBuffer();
		File file = new File(path);
		BufferedReader bufferReader = new BufferedReader(new FileReader(file));
		char[] buffer = new char[1024];
		int length = 0;
		while ((length = bufferReader.read(buffer)) != -1) {
			output.append(String.valueOf(buffer, 0, length));
		}
		bufferReader.close();
		return output.toString();
	}

	public void parseInput(String data) {
		String[] ls = data.split("\n");
		String[] valueList = ls[0].replaceAll("(\\r|\\n)", "").split(" ");
		TOTAL_TABLE = Integer.parseInt(valueList[0]);
		TOTAL_GUEST = Integer.parseInt(valueList[1]);
		if (TOTAL_TABLE != 0 && TOTAL_GUEST != 0) {
			matrix = new int[TOTAL_GUEST][TOTAL_GUEST];
			int count = 0;
			for (int i = 1; i < ls.length; i++) {
				String[] value = ls[i].replaceAll("(\\r|\\n)", "").split(" ");
				for (int j = 0; j < value.length; j++) {
					matrix[count][j] = Integer.parseInt(value[j]);
				}
				count++;
			}
		}
	}

	// Print the output in the file
	public void putOutputToFile(String path, String data) throws IOException {
		FileWriter fileWriter = null;
		try {
			File newTextFile = new File(path);
			fileWriter = new FileWriter(newTextFile);
			fileWriter.write(data);
			fileWriter.close();
		} catch (IOException ex) {
		}

	}

}

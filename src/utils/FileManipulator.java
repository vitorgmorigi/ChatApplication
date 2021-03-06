package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileManipulator {

	public ArrayList<String> reader(String path) throws IOException {
		BufferedReader buffRead = new BufferedReader(new FileReader(path));
                ArrayList<String> rows = new ArrayList<>();
		String row = "";
		while (true) {
			if (row != null) {
				System.out.println(row);

			} else
				break;
			row = buffRead.readLine();
                        rows.add(row);
		}
		buffRead.close();
                return rows;
	}

	public void writer(String path, String text) throws IOException {
		BufferedWriter buffWrite = new BufferedWriter(new FileWriter(path, true));             
		buffWrite.append(text);
                buffWrite.newLine();
		buffWrite.close();
	}

}
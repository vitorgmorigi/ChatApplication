package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManipulator {

	public void reader(String path) throws IOException {
		BufferedReader buffRead = new BufferedReader(new FileReader(path));
		String linha = "";
		while (true) {
			if (linha != null) {
				System.out.println(linha);

			} else
				break;
			linha = buffRead.readLine();
		}
		buffRead.close();
	}

	public void writer(String path, String text) throws IOException {
		BufferedWriter buffWrite = new BufferedWriter(new FileWriter(path, true));             
		buffWrite.append(text);
                buffWrite.newLine();
		buffWrite.close();
	}

}
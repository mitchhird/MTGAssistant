package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import models.cardModels.Set;

public class JSONFileReader {

	private static void readJSONFile (Path fileToRead) throws Exception {
		if (Files.exists(fileToRead)) {	
			Set testSet = ModelHelper.toModelFromJSONFile(fileToRead, Set.class);
			System.out.println();
		} else {
			throw new IOException("File to read is not available");
		}
	}
	
	public static void main (String[] args) {
		try {
			readJSONFile(Paths.get("./external_resources/JSON_DB_FILES/LEA.json"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

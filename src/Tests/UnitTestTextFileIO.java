package Tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import Todo.*;

public class UnitTestTextFileIO {
	
	@Test
	public void testAddingToFile(){
		String newFileName = "testAddToFile.txt";
		String stringToWrite = "testing add";
		
		//0) Delete file if exist
		File file = new File(newFileName);
		if(file.exists())
			file.delete();
		
		//1)Create a file
		TextBuddy textBuddy = new TextBuddy(newFileName);
		
		//2)Add a string
		try {
			
			textBuddy.getTextFileIO().WriteLine(stringToWrite);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//3)Read from file, should only read 1 line
		ArrayList<String> lines = new ArrayList<String>();
		try {
			lines = textBuddy.getTextFileIO().ReadLines();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertEquals(stringToWrite, lines.get(0));
	}

	
	@Test
	public void testClearFile(){
		String newFileName = "testClearFile.txt";
		String stringToWrite = "testing clear";
		int expectedResult = 0;
		
		
		
		//0) Delete file if exist
		File file = new File(newFileName);
		if(file.exists())
			file.delete();
		
		//1)Create a file
		TextBuddy textBuddy = new TextBuddy(newFileName);
		TextFileIO textFileIO = textBuddy.getTextFileIO();
		
		//2) Add a string
		textBuddy.addCommand(new String[]{ stringToWrite });
		
		
		try {
			//3) Clear file
			textFileIO.clearFile();
			//4) File should contains 0 lines;
			assertEquals(expectedResult, textFileIO.ReadLines().size());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
}

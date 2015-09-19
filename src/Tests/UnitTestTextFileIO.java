package Tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import Todo.*;

public class UnitTestTextFileIO {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T extends Comparable> boolean isSorted(List<T> listOfT) {
	    T previous = null;
	    for (T t: listOfT) {
	        if (previous != null && t.compareTo(previous) < 0) return false;
	        previous = t;
	    }
	    return true;
	}
	
	
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

}

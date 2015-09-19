package Tests;

import static org.junit.Assert.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import Todo.*;


public class UnitTestTextBuddy {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T extends Comparable> boolean isSorted(List<T> listOfT) {
	    T previous = null;
	    for (T t: listOfT) {
	        if (previous != null && t.compareTo(previous) < 0) return false;
	        previous = t;
	    }
	    return true;
	}
	
	

	/*
	 * Test Domain (From File) :12 hello words search.txt
	 * Number of lines added: 1
	 * Expected number of results : 1
	 */
	@Test
	public void testAddingToFile(){
		
		String newFileName = "testAddingToFile.txt";
		String stringToWrite = "testing add";
		String addResult = null;
		String expectedResult = String.format(TextBuddy.DISPLAY_ADD_MSG, newFileName, stringToWrite);
		
		//0) Delete file if exist
		File file = new File(newFileName);
		if(file.exists())
			file.delete();
		
		//1)Create a file
		TextBuddy textBuddy = new TextBuddy(newFileName);
		
		addResult = textBuddy.addCommand(new String[] { stringToWrite });
	
		assertEquals(expectedResult, addResult);
		
	}
	
	@Test
	public void testClearFile(){
		String newFileName = "testAddingToFile.txt";
		String stringToWrite = "testing add";
		String expectedResult = String.format(TextBuddy.DISPLAY_CLEAR_MSG, newFileName);
		String clearResult = null;
		
		//0) Delete file if exist
		File file = new File(newFileName);
		if(file.exists())
			file.delete();
		
		//1)Create a file
		TextBuddy textBuddy = new TextBuddy(newFileName);
		
		//2) Add a string
		textBuddy.addCommand(new String[]{ stringToWrite });
		
		//3) Clear file
		clearResult = textBuddy.clearCommand();
		
		//4) File should contains 0 lines;
		assertEquals(expectedResult, clearResult);
	}
	
	@Test
	public void testDeleteFromFile(){
		String newFileName = "testDeletingFromFile.txt";
		String stringToWrite = "testing Delete";
		String deleteText = stringToWrite;
		String expectedResult = String.format(TextBuddy.DISPLAY_DELETE_MSG, newFileName, deleteText);
		String deleteResult = null;
		
		//0) Delete file if exist
		File file = new File(newFileName);
		if(file.exists())
			file.delete();
		
		//1)Create a file
		TextBuddy textBuddy = new TextBuddy(newFileName);
		
		//2) Add a string
		textBuddy.addCommand(new String[]{ stringToWrite });
		
		//3) Delete 1 line
		deleteResult = textBuddy.deleteCommand(1);
		
		assertEquals(expectedResult, deleteResult);
	}
	
	
	/*
	 * The domain of search is a predefined text file.
	 * Name of file: "12 hello words search.txt"
	 *  
	 */
	@Test
	public void testSearchingInFile() {

		TextBuddy textBuddy = new TextBuddy("12 hello words search.txt");
		String searchString = "hello";
		int expectedNumberOfResults = 12;
		ArrayList<String> results = new ArrayList<String>();
		
		try {
			results = textBuddy.getTextFileIO().search(searchString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(results.size(), expectedNumberOfResults);
	}
	
	/*
	 * An empty string, "" occurs in every string. So technically,
	 * an empty file contains no string, 0 bytes,  thus should have no matches even to an empty string.
	 */
	@Test
	public void testSearchingInEmptyFile() {

		TextBuddy textBuddy = new TextBuddy("empty.txt");
		int expectedNumberOfResults = 0;
		ArrayList<String> results = new ArrayList<String>();
		try {
			results = textBuddy.getTextFileIO().search("");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertEquals(results.size(), expectedNumberOfResults);
	}
	
	@Test
	public void testSearchingMissingStringInFile() {
		TextBuddy textBuddy = new TextBuddy("12 hello words search.txt");
		String searchString = "vishnu priya <3";
		int expectedNumberOfResults = 0;
		
		ArrayList<String> results = new ArrayList<String>();
		try {
			results = textBuddy.getTextFileIO().search(searchString);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertEquals(results.size(), expectedNumberOfResults);
	}

}

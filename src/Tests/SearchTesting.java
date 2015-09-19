package Tests;

import static org.junit.Assert.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import Todo.*;


public class SearchTesting {
	
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
	 * Search keyword : "hello"
	 * Expected number of results : 12
	 */
	@Test
	public void testIfSorted(){
		TextBuddy textBuddy = new TextBuddy("12 hello words search.txt");
		ArrayList<String> lines = textBuddy.getAllLines();
		
		lines = textBuddy.getTextFileIO().sort(lines);
		
		assertEquals(true, isSorted(lines));
	}
	

	/*
	 * Test Domain (From File) :12 hello words search.txt
	 * Number of lines added: 1
	 * Expected number of results : 1
	 */
	@Test
	public void testAddOneLineToFile(){
		
		String newFileName = "testAddingToFile.txt";
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
		ArrayList<String> lines =  textBuddy.getAllLines();

		assertEquals(stringToWrite, lines.get(0));
		
	}
	
	
	@Test
	public void testDeleteFromFile(){
		
	}
	
	@Test
	public void testDisplayFromFile(){
		
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

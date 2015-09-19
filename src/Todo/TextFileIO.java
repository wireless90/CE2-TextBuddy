/***
* Author: Muhammad Razali
* Year/Sem: 2/1
* MetricNo: A0133267H
* This class contains function calls for both reading
* and writing to text files.
*/

package Todo;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;


public class TextFileIO{
	
	
	/* Variables */
	public static final String ERROR_FILE_NOT_FOUND = "The specified file is not found";
	public static final String ERROR_FILE_IO_ERROR = "IOException Occured";
	public static final String ERROR_FILE_CLEAR = "Error while clearing file";
	public static final String ERROR_FILE_READ = "Error while reading file";
	public static final String ERROR_FILE_DELETE = "Error while deleting file";
	
	private String fileName;
	
	/* Constructor */
	public TextFileIO(String fileName){
		this.fileName = fileName;
	}
	
	/* Getters */
	public String GetFileName() {
		return fileName; 
	}
	
	
	
	public ArrayList<String> search(String searchString) throws IOException{
		/* Get all lines from file */
		ArrayList<String> lines = ReadLines();
		ArrayList<String> matches = new ArrayList<String>();
		
		/* Collate all matches */
		for(String line : lines)
			if(line.contains(searchString))
				matches.add(line);
		return matches;
	}
	
	public ArrayList<String> sort(ArrayList<String> listToSort){
		Collections.sort(listToSort);		
		return listToSort;
	}
	
	public ArrayList<String> sortFile(ArrayList<String> listToSort) throws IOException{
		ArrayList<String> sortedList = sort(listToSort);
		File file = new File(fileName);
		file.delete();
		
		for(String line : sortedList){
			WriteLine(line);
		}
		
		return sortedList;
	}
	
	public String ReadLine() throws IOException{		
		Path path = Paths.get(fileName);
		BufferedReader reader = Files.newBufferedReader(path);
		
		String line = reader.readLine();
		reader.close();
		
		return line;
	}
	
	public ArrayList<String> ReadLines() throws IOException	{
		Path path = Paths.get(fileName);
		ArrayList<String> lines = new ArrayList<String>();
		
		if(!Files.exists(path)){
			return lines;
		}
		
		BufferedReader reader = Files.newBufferedReader(path);
		String line = null;		
		//Read each line and store
		while((line = reader.readLine()) != null){
			lines.add(line);
		}
		
		reader.close();		
		return lines;
	}
	
	public void WriteLine(String line) throws IOException{
		Path path = Paths.get(fileName);
		BufferedWriter writer;
		
		if(Files.exists(path)){
			writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND);
		} else{			
			writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE);
		}
		
		writer.write(line);	
		writer.newLine();
		writer.close();
	}
	
	public String deleteLine(int num) throws IOException{
		
		/* 
		 * Create a temp file and read all lines
		 * from original file, disregarding the line at num.
		 * Then the original file is deleted.
		 * Temp file renamed to original file.
		 */
		File tempFile = new File("temp.txt");
		File currentFile = new File(fileName);
		
        BufferedReader reader = new BufferedReader(new FileReader(currentFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        
        String line = null;
        String deletedLine = null;
        
        /* Write all lines except the deleted line to a new file */
        int i = 1;
        while((line = reader.readLine()) != null){
        	if(i!= num){
	        	writer.write(line);
	        	writer.newLine();
        	}else{
        		deletedLine = line;
        	}
        		
        	i++;
        }
        
        reader.close();
        writer.close();
        
        move(tempFile, currentFile);
        
        return deletedLine;
	}
	
	private void move(File sourceFile, File destFile)
	{
		if(Files.exists(destFile.toPath())){
			destFile.delete();
		}
		
		sourceFile.renameTo(destFile);
	}
	
	public void clearFile() throws IOException{
		Path path = Paths.get(fileName);
		BufferedWriter writer = Files.newBufferedWriter(path);
		writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND);
		
		writer.close();
	}
	
	public void WriteLines(ArrayList<String> lines) throws IOException{
		Path path = Paths.get(fileName);
		BufferedWriter writer = Files.newBufferedWriter(path);
		
		for(String line : lines){
			writer.write(line);
			writer.newLine();
		}
		
		writer.close();
		
	}
	
}

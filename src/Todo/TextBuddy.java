
/**
* Author: Muhammad Razali
* Year/Sem: 2/1
* MetricNo: A0133267H
*
* This class manipulates a text file specified by the user, by four operations.
* These include adding of lines, deletion of lines, clearing all the lines in the file and displaying all the content of the text file.
* If the user wishes to display an already empty file, the user will be provided with feedback that the file is empty.
* After each user operation, the file will be saved.
* 
* Assumption 1: All commands entered by the user have valid syntax formats
* Assumption 2: User does not delete from an empty file.
*/

package Todo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class TextBuddy{
	
	/****** Variables ******/
	private static final String ERROR_INSUFFICIENT_ARGS = "Insufficient argument supplied.\nTextBuddy <filename>";
	
	public static final String DISPLAY_WELCOME_MSG = "Welcome to TextBuddy. %1$s is ready for use";
	public static final String DISPLAY_COMMAND_PROMPT = "command: ";
	public static final String DISPLAY_CLEAR_MSG = "all content deleted from %1$s";
	public static final String DISPLAY_EMPTY_MSG = "%1$s is empty";
	public static final String DISPLAY_DELETE_MSG = "deleted from %1$s: \"%2$s\"";
	public static final String DISPLAY_ADD_MSG = "added to %1s: \"%2$s\"";
	public static final String DISPLAY_SEARCH_MSG = "searching string  \"%1s\"";
	public static final String DISPLAY_SEARCH_NOT_FOUND_MSG = "string  \"%1s\" not found";
	public static final String DISPLAY_SORTED_MSG = "file has been sorted in ascending order";
	
	private TextFileIO textFileIO;
	private Scanner scanner;
	private String fileName;
	
 	private enum CommandType{
		ADD, DELETE, DISPLAY, EXIT, CLEAR, SEARCH, SORT
	};
	
	public TextFileIO getTextFileIO() { 
		return this.textFileIO; 
		}
	


	public static void main(String[] args){
		if(!hasSufficientCommandLineArguments(args)){
			displayInsufficientArgsError();
			return;
		}
		
		String fileName = getFileName(args);
		
		runTextBuddy(fileName);
		
	}


	public TextBuddy(String fileName){
		this.fileName = fileName;
		textFileIO = new TextFileIO(fileName);
		scanner = new Scanner(System.in);
	}


	private static boolean hasSufficientCommandLineArguments(String[] commandLineArgs){
		
		if(commandLineArgs.length < 1){
			return false;
		}
		return true;
	}
	
	private static void displayInsufficientArgsError(){
		print(ERROR_INSUFFICIENT_ARGS, true);
	}
	
	private static String getFileName(String[] args){
		return args[0];
	}
	
	private static void runTextBuddy(String fileName)
	{
		TextBuddy textBuddy = new TextBuddy(fileName);
		textBuddy.run();
	}
		
	public static void print(String message, boolean isNewLine)	{
		if(isNewLine){
			System.out.println(message);
		}else{
			System.out.print(message);
		}
	}
		
	private String printClearMessage(){
		String stringToPrint = String.format(DISPLAY_CLEAR_MSG, fileName);
		print(stringToPrint, true);
		printNewLine();
		
		return stringToPrint;
	}
	
	private void printNewLine(){
		print("", true);
	}
	
	private void printDisplayEmptyMessage(){
		String stringToPrint = String.format(DISPLAY_EMPTY_MSG, fileName);
		print(stringToPrint, true);
		printNewLine();
	}
	
	private String printDeleteMessage(String deletedText){
		String stringToPrint = String.format(DISPLAY_DELETE_MSG, fileName, deletedText);
		print(stringToPrint, true);
		printNewLine();
		
		return stringToPrint;
	}
	
	private String printAddMessage(String addedText){
		String stringToPrint = String.format(DISPLAY_ADD_MSG, fileName, addedText);
		print(stringToPrint, true);
		printNewLine();
		return stringToPrint;
	}
	
	/* Methods */
	public void run(){
		boolean hasMoreCommands;
		
		displayWelcomeMessage();
		
		do{
			promptForCommand();
			String[] commandAndArgs = getCommandAndArgs();
			hasMoreCommands = processCommand(commandAndArgs);
		}while(hasMoreCommands == true);
	}
	
	private String[] getCommandAndArgs(){
		String[] splitCommand = scanner.nextLine().split(" ");
		printNewLine();
		return splitCommand;
	}

	private void displayWelcomeMessage(){
		String welcomeMessage =  String.format(DISPLAY_WELCOME_MSG, fileName);
		
		print(welcomeMessage, true);
		printNewLine();
	}

	private void promptForCommand() {
		print(DISPLAY_COMMAND_PROMPT, false);
	}
	
	private CommandType getCommandType(String command)
	{
		String lowerCaseCommand = command.toLowerCase();
		
		switch(lowerCaseCommand){
		case "add":
			return CommandType.ADD;
		case "delete":
			return CommandType.DELETE;
		case "display":
			return CommandType.DISPLAY;
		case "clear":
			return CommandType.CLEAR;
		case "exit":
			return CommandType.EXIT;
		case "search":
			return CommandType.SEARCH;
		case "sort":
			return CommandType.SORT;
			
		default:
			return null;
		}
		
	}
	
	private boolean processCommand(String args[])	{
		
		CommandType commandType = getCommandType(args[0]);
		switch(commandType)	{
		case ADD:
			addCommand(args);
			return true;
		
		case DISPLAY:
			displayCommand();
			return true;
			
		case CLEAR:
			clearCommand();
			return true;
			
		case DELETE:
			int lineNumber = Integer.valueOf(args[1]); 
			deleteCommand(lineNumber);
			return true;
			
		case EXIT:
			return false;
		
		case SEARCH:
			String searchString = args[1];
			searchCommand(searchString);
			return true;
		case SORT:
			sortCommand();
			return true;
		
		default:
			return false;
		}
		
	}
	
	public void sortCommand() {
		ArrayList<String> lines = getAllLines();
		try {
			textFileIO.sortFile(lines);
		} catch (IOException e) {
			print(TextFileIO.ERROR_FILE_READ, true);
			print(e.getMessage(), true);
		}
		
		print(DISPLAY_SORTED_MSG, true);
	}
	
	private void searchCommand(String searchString) {
		ArrayList<String> matches = new ArrayList<String>();
		int numOfMatches = 1;
		
		print(String.format(DISPLAY_SEARCH_MSG, searchString), true);
		
		try {
			matches = textFileIO.search(searchString);
		} catch (IOException e) {
			print(TextFileIO.ERROR_FILE_READ, true);
			print(e.getMessage(), true);
			return;
		}
		
		/* Prints "no matches" */
		if(matches.size() == 0)
		{
			print(String.format(DISPLAY_SEARCH_NOT_FOUND_MSG, searchString), true);
			return;
		}
		
		/* Prints all the matches found */
		for(String stringMatch : matches){
			
			print(numOfMatches + ") " +  stringMatch, true);
		}
	}

	public String addCommand(String[] words)	{
		StringBuilder sb = new StringBuilder();
		String result;
		//Add all the words to into 1 single string with a space " "
		//i = 1 as '0' contains the "add" command
		for(int i = 1; i < words.length - 1; i++){
			sb.append(words[i] + " ");
		}
		
		//add the last word without space
		sb.append(words[words.length - 1]);
		
		String addedText = sb.toString();
		
		try {
			textFileIO.WriteLine(addedText);
			result = printAddMessage(addedText);
			printNewLine();
		} catch (IOException e) {
			print(e.getMessage(), true);
			return null;
		}
		
		return result;
	}
	
	public String clearCommand(){
		String resultOfClearingFile = null;
		
		try	{
			textFileIO.clearFile();
			resultOfClearingFile = printClearMessage();
			printNewLine();
		}catch(IOException ioe)	{
			print(TextFileIO.ERROR_FILE_CLEAR, true);
			print(ioe.getMessage(), true);
		}
		return resultOfClearingFile;
	}
	
	public String deleteCommand(int n){
		String resultOfDeletion = null;
		
		try {
			String deletedLine = textFileIO.deleteLine(n);
			resultOfDeletion = printDeleteMessage(deletedLine);
			printNewLine();
		} catch (IOException e) {			
			print(TextFileIO.ERROR_FILE_DELETE, true);
			print(e.getMessage(), true);
		}
		
		return resultOfDeletion;
	}
	
	public ArrayList<String> getAllLines()
	{
		ArrayList<String> lines = null;
			
		try {
			lines = textFileIO.ReadLines();
		} catch (IOException e) {
			print(TextFileIO.ERROR_FILE_READ, true);
			print(e.getMessage(), true);
			return null;
		}
		
		return lines;
	}
	
	private void displayCommand(){
		ArrayList<String> lines;
		
		lines = getAllLines();
		if(lines.size() == 0){
			printDisplayEmptyMessage();
			printNewLine();
			return;
		}
		
		
		for(int i = 1; i <= lines.size(); i++){
			print(i + ". " + lines.get(i-1), true);
		}
		printNewLine();
		
		
	}

	public String getFileName(){
		return fileName;
	}
	
}
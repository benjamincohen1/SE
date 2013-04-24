/**
    @author Jack Lawrence
*/

import java.util.*;

/** An abstract superclass that provides some helper functions for CLI menu parsing. */
public abstract class CommandLineInterface {

    private static Scanner _inputScanner = new Scanner(System.in);
    
    /** 
        Retrieve the application-wide input scanner. 
        
        Use this or things are gonna break.
    */
    public static Scanner inputScanner() {
        return _inputScanner;
    }

    /**
        Parse, validate, and return positive integer arguments on a command.
        
        Ex: "add 5 7" => [5, 7]
        
        Fails validation if there are not exactly expectedArgumentCount positive numbers.
        Validation failure results in a null return value.
        
        @param input The input string. Ex: "add 5 7"
        @param expectedArgumentCount The number of positive integer arguments you expect.
        
        @return An array of positive integer arguments or null if validation failed.
    */
    public static ArrayList<Integer> positiveIntegerArgumentsOnCommand(String input, int expectedArgumentCount) {
        boolean failed = true;
        ArrayList<Integer> integerArgs = null;
        String[] theArgs = input.split("\\s+"); // split on whitespace. Ex: "add 5 7" => ["add", "5", "7"].
        try { // Put all the things in a try block so we can catch command parsing errors.
            if (theArgs.length == expectedArgumentCount+1) {
                integerArgs = allArgsPositiveIntegers(theArgs);
                failed = false;
            }
        }
        catch (NumberFormatException numberException) {failed = true;}
        catch (IllegalArgumentException argumentException) {failed = true;}
        
        if (failed) {
            System.err.println(String.format("Error: %s requires %d positive integer argument%s.", theArgs[0], expectedArgumentCount, (expectedArgumentCount > 1)?"s":""));
            return null;
        }
        
        return integerArgs;
    }

    /**
        Prompts for user input and returns a positive integer entered by the user.
        
        Prints an error message and continues prompting for user input until a
        positive integer is entered.
        
        @param prompt The text to prompt the user with. Ex: "Row: "
        
        @return The positive integer entered by the user.
    */
    public static int scanPositiveInteger(String prompt) {
        int scannedValue = 1;
        boolean validValue = false;
        while (!validValue) {
            System.out.print(prompt);
            try {
                scannedValue = Integer.parseInt(inputScanner().nextLine().toLowerCase());
                if (scannedValue > 0) {
                    validValue = true;
                }
            }
            catch (NumberFormatException numberException) {
                validValue = false;
            }
            
            if (!validValue) {
                System.err.println("Unrecognized input. Requires one positive integer.");
            }
        }
        
        return scannedValue;
    }
    
    private static ArrayList<Integer> allArgsPositiveIntegers(String[] theArgs) throws NumberFormatException, IllegalArgumentException {
        // start indexing at 1 to avoid parsing the command name.
        ArrayList<Integer> parsedArgs = new ArrayList<Integer>();
        for (int i = 1; i < theArgs.length; i++) {
            Integer parsedInt = Integer.parseInt(theArgs[i]);
            if (parsedInt < 1) {
                throw new IllegalArgumentException();
            }
            else {
                parsedArgs.add(parsedInt);
            }
        }
        
        return parsedArgs;
    }
	
}

/* 
 * Programmer: David Kopp
 * Project #: Project 1
 * File Name: TestBFStableMatchingAlgorithm.java
 * Date: 9-15-15
 * Class: Comp496ALG
 * Description: This class demonstrates the GS Stable Matching Algorithm. Also reads data from
 * 				a input text file.
 */

package project1;

// Import Statements
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// Class for GS Stable Matching Algorithm
public class GSStableMatchingAlgorithm {
	
	// Variables
	private int numberOfPairs;
	
	// Input File
	File file;
	
	// Data Structures
	private int[] marriagePairList;
	private boolean[] maleAvailabilityList;
	private boolean[] femaleAvailabilityList;
	private int[][] malePreferences;
	private int[][] femalePreferences;
	
	// Constructor for class object
	public GSStableMatchingAlgorithm(String inputfile)
	{
		file = new File(inputfile);
		readInputData();
	}
	
	// Calulates the matches using the GS Stable Matching Algorithm
	public void calculateMatches()
	{
		while (isMaleFree())
		{
			int man = freeMale();
			for (int i = 0; i < numberOfPairs && maleAvailabilityList[man]; i++)
			{
				int female = malePreferences[man][i];
				if (femaleAvailabilityList[female])
				{
					marriagePairList[man] = female;
					maleAvailabilityList[man] = false;
					femaleAvailabilityList[female] = false;
				}
				else
				{
					int engagedTo = findPartner(female);
					if (!femalePrefers(engagedTo, female, man))
					{
						marriagePairList[engagedTo] = -1;
						maleAvailabilityList[engagedTo] = true;
						marriagePairList[man] = female;
						maleAvailabilityList[man] = false;
					}
				}
			}
		}
		printPairs();
	}
	
	// Prints the marriagePairList of the calculated GS stable match
	public void printPairs()
	{
		System.out.print("[");
		for (int i = 0; i < numberOfPairs; i++)
		{
			System.out.print("(" + i + "," + marriagePairList[i] + ")");
			if (i != numberOfPairs - 1)
			{
				System.out.print(",");
			}
			else
			{
				System.out.print("]\n");
			}
		}
	}
	
	// Finds the male partner that the female is engaged to
	public int findPartner(int female)
	{
		int male = -1;
		for (int i = 0; i < numberOfPairs; i++)
		{
			if (marriagePairList[i] == female)
			{
				male = i;
			}
		}
		return male;
	}
	
	// Returns True is there is a male who is not engaged
	public boolean isMaleFree()
	{
		boolean result = false;
		for (int i = 0; i < maleAvailabilityList.length; i++)
		{
			if (maleAvailabilityList[i] == true)
			{
				result = true;
			}
		}
		return result; 
	}
	
	// Returns the male that is free and not engaged to a female
	public int freeMale()
	{
		int male = -1;
		for (int i = 0; i < maleAvailabilityList.length; i++)
		{
			if (maleAvailabilityList[i] == true)
			{
				male = i;
				break;
			}
		}
		return male;
	}
	
	// Returns true if the female is not engaged and false if they are
	public boolean isWomanFree() 
	{
		boolean result = false;
		for (int i = 0; i < numberOfPairs; i++)
		{
			if (femaleAvailabilityList[i] == true)
			{
				result = true;
			}
		}
		return result;
	}
	
	// Returns true if the female engaged prefers her match rather than to another man's offer
	public boolean femalePrefers(int engagedTo, int female, int man)
	{
		boolean result = false;
		for (int i = 0; i < numberOfPairs; i++)
		{
			if (femalePreferences[female][i] == engagedTo)
			{
				result = true;
				break;
			}
			else if (femalePreferences[female][i] == man)
			{
				break;
			}
		}
		return result;
	}
	
	// Private Methods
	// Reads the input of the text file and initializes variables and data structures
	private void readInputData()
	{
		try
		{
			Scanner input = new Scanner(file);
			numberOfPairs = input.nextInt();
			marriagePairList = new int[numberOfPairs];
			malePreferences = new int[numberOfPairs][numberOfPairs];
			femalePreferences = new int[numberOfPairs][numberOfPairs];			
			maleAvailabilityList = new boolean[numberOfPairs];
			femaleAvailabilityList = new boolean[numberOfPairs];
			
			initArray(marriagePairList);
			initArray(maleAvailabilityList);
			initArray(femaleAvailabilityList);
			
			for (int i = 0; i < numberOfPairs; i++)
			{
				for (int j = 0; j < numberOfPairs; j++)
				{
					malePreferences[i][j] = input.nextInt();
				}
			}
			
			for (int i = 0; i < numberOfPairs; i++)
			{
				for (int j = 0; j < numberOfPairs; j++)
				{
					femalePreferences[i][j] = input.nextInt();
				}
			}		
			input.close();
		} // End of Try
		catch (FileNotFoundException e)
		{
			System.out.println(e);
			System.exit(1);
		} // End of Catch
		calculateMatches();
	}
	
	// Initiates an array to -1 
	private void initArray(int[] inputArray)
	{
		for (int i = 0; i < numberOfPairs; i++)
		{
			inputArray[i] = -1;
		}
	}
	
	// Initiates an array to true
	private void initArray(boolean[] inputArray)
	{
		for (int i = 0; i < numberOfPairs; i++)
		{
			inputArray[i] = true;
		}
	}
}
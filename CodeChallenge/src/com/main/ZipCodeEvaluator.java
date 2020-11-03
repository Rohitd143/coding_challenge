package com.main;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The project provides a solution (developed using JAVA language) to the following problem statement
 *   "Sometimes items cannot be shipped to certain zip codes, and the rules for these restrictions are stored
 *   as a series of ranges of 5 digit codes. For example if the ranges are but could be a range of any 
 *    zip code [94133,94133] [94200,94299] [94600,94699] Then the item can be shipped to zip code 94199, 
 *    94300, and 65532, but cannot be shipped to 94133, 94650, 94230, 94600, or 94299."
	
 *	The input can contain multiple ranges of inputs, which can have duplicate and overlapping intervals 
 *	of zip codes. The output should give the least possible intervals of valid ship-codes to which the 
 *	items can be shipped to.

	* @author  Rohit Deshmukh
	* @version 1.0
	* @since   2020-10-31
*/
public class ZipCodeEvaluator {
	
	/**
	   * This method loops through the list of objects provided in input
	   * Sorts the list based on minValue, if minValue is same, it looks for the maxValue
	   
	   * Store the first object values in a temporary object
	   * keep comparing this first object values with the other object values from the list
	   * 
	   * Based on conditions, we add an interval object to list and update the temp object with the values
	   * from the current object from list 
	   * 
	   * @param intervalList This is the only parameter to consolidateIntervals method
	   * @return List<ZipCodeInterval> This returns the consolidated zipcode intervals
	*/
	private List<ZipCodeInterval> consolidateIntervals(List<ZipCodeInterval> intervalList) {
		
		int intervalListSize = intervalList.size();
		
		//Final output will be stored in this list
		List<ZipCodeInterval> consolidatedIntervalList = new ArrayList<ZipCodeInterval>();
		
		
		if (intervalListSize > 0) {
			
			//Sort the intervals by minValue (If minValue is same for two intervals, looks for the maxValue of those intervals)
			Collections.sort(intervalList);

			//Take the first interval from list as base value
			ZipCodeInterval firstInterval = intervalList.get(0);

			//Loop through the list from second value in List
			for (int i = 1; i < intervalListSize; i++) {
				
				ZipCodeInterval currInterval = intervalList.get(i);
				
				
				//94100 - 94120 and 94121 - 94200
				//OR 94100 - 94200 and 94150 - 94250
				if ((firstInterval.getMaxValue() + 1 == currInterval.getMinValue()
						|| firstInterval.getMaxValue() >= currInterval.getMinValue())) {

					//94100 - 94200 and 94150 - 94250
					if (firstInterval.getMaxValue() < currInterval.getMaxValue()
							&& firstInterval.getMinValue() != currInterval.getMinValue()) {
						firstInterval.setMaxValue(currInterval.getMaxValue());
					}

				} else {
					consolidatedIntervalList.add(firstInterval);
					firstInterval = intervalList.get(i);
				}
			}
			consolidatedIntervalList.add(firstInterval);
		}
		
		return consolidatedIntervalList;
	}

	/**
	   * Using an InputStreamReader, read each line from the input file
	   * Split the line data based on delimiter comma, it should contain 2 values, 
	   * If not, throw an exception and stop execution
	   
	   * Store the first object values in a temporary object
	   * keep comparing this first object values with the other object values from the list
	   * 
	   * Create a new ZipCodeInterval object for each line in the input
	   * From the splitted values, assign the smaller number to minValue and bigger number to maxValue of 
	   * 		ZipCodeInterval object
	 
	   * If any code from these values is < 10000 or > 99999, throw an exception and stop execution
	   * 
	   * Add all the objects to a list and return the list of objects
	   
	   * @param inputFile This is the only parameter to readIntervalsFromInputFlie method.. Name 
	   * of the input file
	   * @return List<ZipCodeInterval> This returns the list of zipcode interval objects
	*/
	private List<ZipCodeInterval> readIntervalsFromInputFlie(String inputFile) throws NumberFormatException, InvalidZipCodeException {
		ArrayList<ZipCodeInterval> intervalsList = new ArrayList<ZipCodeInterval>();
		BufferedReader bufferedReader = null;

		String currentLine;
		
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("resources/"+inputFile);
		InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
		bufferedReader = new BufferedReader(streamReader);

		try {
			currentLine = bufferedReader.readLine();
			while (currentLine != null) {
				//Assuming that the input will have zipCode intervals separated by Comma
				String[] zipCodes = currentLine.split(",");
				if(zipCodes.length != 2) {
					throw new InvalidZipCodeException("The ZipCode intervals are not valid, Either they are not separated by comma"
							+ "OR the interval size is not equal to 2");
				}
				if (isIntervalValueValid(Integer.parseInt(zipCodes[0])) && isIntervalValueValid(Integer.parseInt(zipCodes[1]))) {
					
					ZipCodeInterval interval  = Integer.parseInt(zipCodes[0]) <= Integer.parseInt(zipCodes[1]) ? 
								new ZipCodeInterval(Integer.parseInt(zipCodes[0]), Integer.parseInt(zipCodes[1])) :
								new ZipCodeInterval(Integer.parseInt(zipCodes[1]), Integer.parseInt(zipCodes[0]));
					
					intervalsList.add(interval);
					currentLine = bufferedReader.readLine();
				}
				else {
					throw new InvalidZipCodeException("The supplied zip code interval is invalid: "+Integer.parseInt(zipCodes[0])+" - "+Integer.parseInt(zipCodes[1]));
				}
			}
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		return intervalsList;
	}

	/**
	   * This method checks if the zipCode is valid or not
	   * 
	   * @param zipCode 
	   * @return boolean
	*/
	public boolean isIntervalValueValid(int zipCode) {
		if (zipCode >= 10000 && zipCode <= 99999) {
			return true;
		}
		return false;
	}

	/**
	   * This method writes the final output to a file using FileWriter
	   * 
	   * 
	   * @param consolidatedIntervalsList .. List of consolidated interval objects 
	   * @param outputFile.. Name of the file where all the output will be written to
	*/
	private void writeOutput(List<ZipCodeInterval> consolidatedIntervalsList, String outputFile) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (consolidatedIntervalsList.size() > 0) {
			for (ZipCodeInterval interval : consolidatedIntervalsList) {
				try {
					writer.write(interval.toString() + "\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	   * This method is a wrapper method that delegates the calls to different methods in sequence
	   * 
	   * @param inputFile Name of the input file from which the inputs are to be read from
	   * @param outputFile  Name of the file where all the output will be written to
	   * @return List<ZipCodeInterval> Final list of ZipCodeInterval objects
	*/
	public List<ZipCodeInterval> findFinalIntervals(String inputFile, String outputFile) throws InvalidZipCodeException {
		
		List<ZipCodeInterval> finalIntervals = new ArrayList<ZipCodeInterval>();

		List<ZipCodeInterval> intervalsList = readIntervalsFromInputFlie(inputFile);

		finalIntervals = consolidateIntervals(intervalsList);

		writeOutput(finalIntervals, outputFile);

		return finalIntervals;
	}
	
	/**
	   * This is the main method where the actual execution starts from. This delegates control to findFinalIntervals and in the end prints, the output list to console
	   * 
	   * @param args Array of Input arguments in String form
	   
	*/
	public static void main(String[] args) throws InvalidZipCodeException {
		
		ZipCodeEvaluator zipCodeEvaluatorObj = new ZipCodeEvaluator();
		List<ZipCodeInterval> finalIntervals = zipCodeEvaluatorObj.findFinalIntervals("input.txt", "output.txt");
		
		if(finalIntervals.isEmpty()) {
			System.out.println("No Intervals found ");
		}
		else {
			System.out.println("Final Intervals to be considered are : ");
			for(ZipCodeInterval interval : finalIntervals) {
				System.out.println(interval.getMinValue() + " - "+interval.getMaxValue());
			}
			
		}
		
	}

}

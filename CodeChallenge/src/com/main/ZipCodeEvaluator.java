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

public class ZipCodeEvaluator {
	
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

	public boolean isIntervalValueValid(int zipCode) {
		if (zipCode >= 10000 && zipCode <= 99999) {
			return true;
		}
		return false;
	}

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

	public List<ZipCodeInterval> findFinalIntervals(String inputFile, String outputFile) throws InvalidZipCodeException {
		
		List<ZipCodeInterval> finalIntervals = new ArrayList<ZipCodeInterval>();

		List<ZipCodeInterval> intervalsList = readIntervalsFromInputFlie(inputFile);

		finalIntervals = consolidateIntervals(intervalsList);

		writeOutput(finalIntervals, outputFile);

		return finalIntervals;
	}
	
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

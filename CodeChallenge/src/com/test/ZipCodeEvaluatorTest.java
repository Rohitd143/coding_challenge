package com.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.main.InvalidZipCodeException;
import com.main.ZipCodeEvaluator;
import com.main.ZipCodeInterval;

public class ZipCodeEvaluatorTest {
	
	ZipCodeEvaluator zipCodeEvaluatorObj = new ZipCodeEvaluator();
	
	
	
	@Test
	public void testSuccessCase() throws InvalidZipCodeException {
		List<ZipCodeInterval> consolidatedZipCodes = 
				zipCodeEvaluatorObj.findFinalIntervals("input.txt", "output.txt");
		assertTrue(!consolidatedZipCodes.isEmpty() && consolidatedZipCodes.size() == 3);
	}
	
	@Test
	public void testInvalidZipCodesCase() {
		try {
			zipCodeEvaluatorObj.findFinalIntervals("InvalidZipCodes.txt", "output.txt");
			Assert.fail("This statement should not have executed");
		}
		catch(InvalidZipCodeException ex) {
			System.out.println("Expected exception occurred");
		}
	}
	
	@Test
	public void testInvalidInputData() {
		try {
			zipCodeEvaluatorObj.findFinalIntervals("InvalidZipCodes.txt", "output.txt");
			Assert.fail("This statement should not have executed");
		}
		catch(InvalidZipCodeException ex) {
			System.out.println("Expected exception occurred");
		}
	}
	
	@Test
	public void testIfZipCodeIsValid() {
		assertTrue(zipCodeEvaluatorObj.isIntervalValueValid(90100));
	}
	
	@Test
	public void testIfZipCodeIsInValid() {
		assertFalse(zipCodeEvaluatorObj.isIntervalValueValid(1000));
	}
	
}

#Introduction 
	The project provides a solution (developed using JAVA language) to the following problem statement
		"Sometimes items cannot be shipped to certain zip codes, and the rules for these restrictions are stored as a series of ranges of 5 digit codes. For example if the ranges are but could be a range of any zip code [94133,94133] [94200,94299] [94600,94699] Then the item can be shipped to zip code 94199, 94300, and 65532, but cannot be shipped to 94133, 94650, 94230, 94600, or 94299."

	The input can contain multiple ranges of inputs, which can have duplicate and overlapping intervals of zip codes. The output should give the least possible intervals of valid ship-codes to which the items can be shipped to.
	
	
#Configuration
	The project has the main java files located in src folder under com.main package.
	
	Test files for unit testing the code are stored in src folder under com.test package.
	
	The project contains a sample input text file (named Input) in the src/resources folder. It contains the shipping code intervals separated by comma. Each new line in the file refers to a different interval of Shipping codes.
	
	After the program is executed, it creates an output file that contains the result of the program. The output file will be named output and will be stored right under the project main directory.
	

#Prerequisites
	It requires JDK and JRE installed in the machine where the program is to be run.
	It is good to have an IDE (like Eclipse/STS/or any other IDE) for easier execution.

#Installation	
	Download the source code and import the JAVA project into an IDE of your choice. 
	
#Data Validity
	All the values for Shipping codes in input should be 5 digit numbers.
	Each line in the Input should contain 2 values and each value has to be separated by Comma
	
	If any of these validations does not meet, it throws a Custom exception represented using InvalidZipCodeException.java  
	
#Unit Tests
	Test files are written using Junit tool. Different cases are added in 	ZipCodeEvaluatorTest.java.
	It contains tests for negative (exception) and positive cases as well.		
	
#Execution
	Right click on the ZipCodeEvaluator.java and choose "Run as Java Application" action. 
	
	It runs the program by considering the Input (inside resources folder) as input and creates the output file inside project directory.
	
	To test different combinations of input, modify the data in Input file and rerun the ZipCodeEvaluator.java and it would override the data in output file for every execution.
	
	To run junits, select ZipCodeEvaluatorTest inside com.test package, right click and choose run as "Junit Test".
	
#Sample Output
	The output will be written to a text file and also will be printed in Console
	
	Example of Output file
			
		94090-94091
		94100-94399
		94600-94790
		
	Output that is printed in console
		
		Final Intervals to be considered are : 
		94090 - 94091
		94100 - 94399
		94600 - 94790
		
			
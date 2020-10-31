package com.main;
public class ZipCodeInterval implements Comparable<ZipCodeInterval>{
	
	private Integer minValue;
	private Integer maxValue;

	public ZipCodeInterval(Integer minVal, Integer maxVal) {
		this.minValue = minVal;
		this.maxValue = maxVal;
	}
	
	public ZipCodeInterval() {
		this.minValue = 0;
		this.maxValue = 0;
	}

	public String toString() {
		return this.minValue + "-" + this.maxValue;
	}

	public Integer getMinValue() {
		return minValue;
	}

	public void setMinValue(Integer minValue) {
		this.minValue = minValue;
	}

	public Integer getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Integer maxValue) {
		this.maxValue = maxValue;
	}
	
	public int compareTo(ZipCodeInterval intervalToCompareWith) {
		Integer minValue1 = this.getMinValue();
		Integer minValue2 = intervalToCompareWith.getMinValue();
		int comp = minValue1.compareTo(minValue2);

		if (comp != 0) {
			return comp;
		} else {
			Integer maxValue1 = this.getMaxValue();
			Integer maxValue2 = intervalToCompareWith.getMaxValue();
			return maxValue1.compareTo(maxValue2);
		}
	}

	
}

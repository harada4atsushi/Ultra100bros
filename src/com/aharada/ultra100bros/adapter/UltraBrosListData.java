package com.aharada.ultra100bros.adapter;


public class UltraBrosListData {

	private String brosNo;
	private int isTransformed;
	private int rareLevel;
	private int isDisplayed;
	
	public int getIsDisplayed() {
		return isDisplayed;
	}

	public void setIsDisplayed(int isDisplayed) {
		this.isDisplayed = isDisplayed;
	}
	
	public int getRareLevel() {
		return rareLevel;
	}

	public void setRareLevel(int rareLevel) {
		this.rareLevel = rareLevel;
	}

	public int getIsTransformed() {
		return isTransformed;
	}

	public void setIsTransformed(int isTransformed) {
		this.isTransformed = isTransformed;
	}
	
	public String getBrosNo() {
		return brosNo;
	}

	public void setBrosNo(String brosNo) {
		this.brosNo = brosNo;
	}
}

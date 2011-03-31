package com.alpha;

import android.graphics.Bitmap;

public class Listing implements Comparable<Listing> {

	private int id;
	private String name;
	private Bitmap logo;
	private Bitmap card;
	private String language;
	private int enteredDigits;
	private int totalDigits;
	private int enteredDigits2;
	private int totalDigits2;
	private String leftAddition;
	private int maxDigits;
	private String mapSearch;

	public Listing() {

	}

	public Listing(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
/*
	public Bitmap getLogoImage() {
		return logo;
	}

	public void setLogoImage(Bitmap logo) {
		this.logo = logo;
	}

	public Bitmap getCardImage() {
		return card;
	}

	public void setCardImage(Bitmap card) {
		this.card = card;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public int getEnteredDigits() {
		return enteredDigits;
	}

	public void setEnteredDigits(int enteredDigits) {
		this.enteredDigits = enteredDigits;
	}

	public int getTotalDigits() {
		return totalDigits;
	}

	public void setTotalDigits(int totalDigits) {
		this.totalDigits = totalDigits;
	}

	public int getEnteredDigits2() {
		return enteredDigits2;
	}

	public void setEnteredDigits2(int enteredDigits2) {
		this.enteredDigits2 = enteredDigits2;
	}

	public int getTotalDigits2() {
		return totalDigits2;
	}

	public void setTotalDigits2(int totalDigits2) {
		this.totalDigits2 = totalDigits2;
	}

	public String getLeftAddition() {
		return leftAddition;
	}

	public void setLeftAddition(String leftAddition) {
		this.leftAddition = leftAddition;
	}

	public int getMaxDigits() {
		return maxDigits;
	}

	public void setMaxDigits(int maxDigits) {
		this.maxDigits = maxDigits;
	}

	public String getMapSearch() {
		return mapSearch;
	}

	public void setMapSearch(String mapSearch) {
		this.mapSearch = mapSearch;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(getName());
		return sb.toString();
	}
*/
	
	public int compareTo(Listing another) {
		if (!(another instanceof Listing)) {
			throw new ClassCastException("Invalid object");
		} else {
			return this.name.compareTo(((Listing) another).getName());
		}
	}
}
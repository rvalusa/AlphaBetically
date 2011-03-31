package com.alpha;

import android.graphics.Bitmap;

public class Data implements Comparable<Data> {

	private int id;
	private String code;
	private int programId;
	private String programName;
	private Bitmap logo;
	private Bitmap card;
	private String mapSearch;
	private String language;
	private int orderNumber;
	private int usageNumber;
	private String cname;
	private String csymbology;
	private boolean isCustom;

	public Data() {

	}

	public Data(int id, String code, int programId) {
		this.id = id;
		this.code = code;
		this.programId = programId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public boolean getIsCustom() {
		return isCustom;
	}

	public void setIsCustom(boolean isCustom) {
		this.isCustom = isCustom;
	}


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getProgramId() {
		return programId;
	}

	public void setProgramId(int programId) {
		this.programId = programId;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getCsymbology() {
		return csymbology;
	}

	public void setCsymbology(String csymbology) {
		this.csymbology = csymbology;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

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


	public int getUsageNumber() {
		return usageNumber;
	}

	public void setUsageNumber(int usageNumber) {
		this.usageNumber = usageNumber;
	}
	
	public String getMapSearch() {
		return mapSearch;
	}

	public void setMapSearch(String mapSearch) {
		this.mapSearch = mapSearch;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(getCode());
		return sb.toString();
	}

//	@Override
	public int compareTo(Data another) {
		if (!(another instanceof Data)) {
			throw new ClassCastException("Invalid object");
		} else {
			return this.programName.compareToIgnoreCase(((Data) another).getProgramName());
		}
	}

}
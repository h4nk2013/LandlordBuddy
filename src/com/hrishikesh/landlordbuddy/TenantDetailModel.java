package com.hrishikesh.landlordbuddy;

public class TenantDetailModel {
	private int id;
	private String mYear;
	private String mMonth;
	private int mRent;
	private int mPaid;
	private int mTenantId;
	private int mDue;
	public TenantDetailModel() {
		// TODO Auto-generated constructor stub
	}
	public TenantDetailModel(int key, String year, String month, int rent, int paid, int tenatid) {
		// TODO Auto-generated constructor stub
		id = key;
		mMonth = month;
		mRent = rent;
		mPaid = paid;
		mYear = year;
		setTenantId(tenatid);
	}
	
	public String getMonth() {
		return mMonth;
	}
	public void setMonth(String month) {
		this.mMonth = month;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPaid() {
		return mPaid;
	}
	public void setPaid(int paid) {
		this.mPaid = paid;
	}
	public int getRent() {
		return mRent;
	}
	public void setRent(int rent) {
		this.mRent = rent;
	}

	public String getYear() {
		return mYear;
	}

	public void setYear(String mYear) {
		this.mYear = mYear;
	}
	public int getTenantId() {
		return mTenantId;
	}
	public void setTenantId(int mTenantId) {
		this.mTenantId = mTenantId;
	}
	public int getDue() {
		return mDue;
	}
	public void setDue(int mDue) {
		this.mDue = mDue;
	}

}

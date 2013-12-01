package com.hrishikesh.landlordbuddy;

import java.util.ArrayList;
import java.util.List;

public class TenantModel {
	private String mName;
	private String mAddress;
	private String mPhone;
	private String mEmail;
	private int mDeposit;
	private String mPaid;
	private String mDue;
	private String mDate;
	private int id;
	
	public TenantModel(){
		
	}
	
	public TenantModel(String Name, String Address, String Phone, String Email, int Deposit){
		mName = Name;
		mAddress = Address;
		mPhone = Phone;
		mEmail = Email;
		mDeposit = Deposit;
		
	}
	

	public TenantModel(String Name, String Paid, String Due, String Date){
		mName = Name;
		mPaid = Paid;
		mDue = Due;
		mDate = Date;
	}
	
	
	public String getAddress() {
		return mAddress;
	}


	public void setAddress(String address) {
		this.mAddress = address;
	}


	public String getPhone() {
		return mPhone;
	}


	public void setPhone(String phone) {
		this.mPhone = phone;
	}


	public String getEmail() {
		return mEmail;
	}


	public void setEmail(String email) {
		this.mEmail = email;
	}


	public int getDeposit() {
		return mDeposit;
	}


	public void setDeposit(int deposit) {
		this.mDeposit = deposit;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	
	@Override
	public String toString(){
		return mName + " " + mPaid + " " + mDue + " " + mDate;
	}
	
	public static List<TenantModel> generateList(){
		List<TenantModel> tenantModels = new ArrayList<TenantModel>();
		tenantModels.add(new TenantModel("Hrishikesh Sardar", "$1900", "$300", "30th September"));
		tenantModels.add(new TenantModel("Harsh Shah", "$1900", "$0", "30th September"));
		tenantModels.add(new TenantModel("Himanshu Adhwaryu", "$1900", "$300", "12th September"));
		
		
		return tenantModels;
	}
	
	
	public String getName() {
		return mName;
	}
	public void setName(String Name) {
		this.mName = Name;
	}
	public String getPaid() {
		return mPaid;
	}
	public void setPaid(String Paid) {
		this.mPaid = Paid;
	}
	public String getDue() {
		return mDue;
	}
	public void setDue(String Due) {
		this.mDue = Due;
	}
	public String getDate() {
		return mDate;
	}
	public void setDate(String Date) {
		this.mDate = Date;
	}

}

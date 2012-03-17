package com.itpredial.tinyphone.client.examples;

import java.awt.Point;

public class Caller {
	String phoneNumber;
	String label;
	String uniqueID;
	float audioLevel;
	String lastKeypress="?";
	
	public Caller(String uniqueID, String phoneNumber){
		this.uniqueID = uniqueID;
		this.phoneNumber = phoneNumber;
		if (phoneNumber.length()>7){
			int start = phoneNumber.length()-7;
			StringBuilder sb = new StringBuilder(phoneNumber);
			sb.replace(start, start+3, "xxx");
			label = sb.toString();
		}
	}
	
	public boolean isCaller(String checkID){
		return uniqueID.equals(checkID);
	}
}

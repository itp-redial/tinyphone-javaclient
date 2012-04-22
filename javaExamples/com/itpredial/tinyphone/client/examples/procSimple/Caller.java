package com.itpredial.tinyphone.client.examples.procSimple;

import java.awt.Point;

public class Caller {
	String phoneNumber;
	String label;
	String uniqueID;
	float audioLevel;
	String[] args;
	String lastKeypress="?";
	
	public Caller(String uniqueID, String phoneNumber, String callerLabel){
		this.uniqueID = uniqueID;
		this.phoneNumber = phoneNumber;
		this.label = callerLabel;
	}
	
	public boolean isCaller(String checkID){
		return uniqueID.equals(checkID);
	}
	
	public String[] getArgs(){
		return args;
	}
	
	public void setArgs(String[] args){
		this.args = args;
	}
}

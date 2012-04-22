package com.itpredial.tinyphone.client.examples;

import com.itpredial.tinyphone.client.TinyphoneClient;
import com.itpredial.tinyphone.client.TinyphoneEvent;

public class SimpleApp {
	TinyphoneClient tp;
	
	public SimpleApp(String host, int port, String phoneNumber){
		tp = new TinyphoneClient(this, host, port,phoneNumber);
		tp.start();
	}
	
	//called by tinyphone client when there's a new caller (required)
	public void newCallerEvent(TinyphoneEvent event){
		System.out.println("new caller Reflection worked!");
		System.out.println(event.toString());
	}
	//called by tinyphone client when there's an audio level (optional)
	public void audioLevelEvent(TinyphoneEvent event){
		System.out.println("audio level Reflection worked!");
		System.out.println(event.toString());
	}
	//called by tinyphone client when there's a new key press (optional)
	public void keypressEvent(TinyphoneEvent event){
		System.out.println("keypress Reflection worked!");
		System.out.println(event.toString());
	}
	//called by tinyphone client when a caller hangs up (required)
	public void hangupEvent(TinyphoneEvent event){
		System.out.println("hangup Reflection worked!");
		System.out.println(event.toString());
	}
	
	public static void main(String[] args){
		//params are host, port, phone number
		SimpleApp sa = new SimpleApp("ast1.itp-redial.com",12002,"1(360)989-1290");
	}
}

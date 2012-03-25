package com.itpredial.tinyphone.client.examples.procSimple;

import java.util.ArrayList;

import com.itpredial.tinyphone.client.TinyphoneClient;
import com.itpredial.tinyphone.client.TinyphoneEvent;

import processing.core.PApplet;
import processing.core.PFont;

public class ProcessingSimple extends PApplet{
	String host = "your-host.com";
	int port = 12002;
	String phoneNumber = "1(999)888-7777";
	TinyphoneClient tp;
	ArrayList<Caller> callers = new ArrayList<Caller>();
	public void setup(){
		size(300,200);
		PFont myFont = createFont("Helvetica", 12);
		textFont(myFont);
		smooth();
		noStroke();
		tp = new TinyphoneClient(this, host, port,phoneNumber);
		tp.start();
	}

	public void draw(){
		background(20);
		text("Call "+phoneNumber,20,height-20);
		synchronized(callers){
			for (int i = 0; i < callers.size(); i++) {
				drawCallers(callers.get(i),i);
			}
		}
	}
	
	void drawCallers(Caller caller, int order){
		text(caller.phoneNumber+" ("+caller.label+")",50,30*order+20);
		text(caller.lastKeypress,20,30*order+30);
		rect(40,30*order+20,200*caller.audioLevel,10);
	}
	//called by tinyphone client when there's a new caller (required)
	public void newCallerEvent(TinyphoneEvent event){
		Caller caller = new Caller(event.getId(), event.getCallerNumber());
		synchronized(callers){
			callers.add(caller);
		}
	}
	//called by tinyphone client when there's an audio level (optional)
	public void audioLevelEvent(TinyphoneEvent event){
		synchronized(callers){
			Caller caller = getCaller(event.getId());
			if (caller != null){
				float audioLevel = (Float.parseFloat(event.getValue())/32764);
				caller.audioLevel = audioLevel;
			}
		}
	}
	//called by tinyphone client when there's a new key press (optional)
	public void keypressEvent(TinyphoneEvent event){
		synchronized(callers){
			Caller caller = getCaller(event.getId());
			if (caller != null){
				caller.lastKeypress = event.getValue();
			}
		}
	}

	Caller getCaller(String id){
		for (int i = 0; i < callers.size(); i++) {
			Caller caller = callers.get(i);
			if (caller.isCaller(id)){
				return caller;
			}
		}
		return null;
	}
	//called by tinyphone client when a caller hangs up (required)
	public void hangupEvent(TinyphoneEvent event){
		synchronized(callers){
			for (int i = 0; i < callers.size(); i++) {
				Caller caller = callers.get(i);
				if (caller.isCaller(event.getId())){
					callers.remove(i);
					break;
				}
			}
		}
	}

}

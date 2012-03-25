package com.itpredial.tinyphone.client.examples.procGame;

import java.util.ArrayList;

import com.itpredial.tinyphone.client.TinyphoneClient;
import com.itpredial.tinyphone.client.TinyphoneEvent;

import processing.core.PApplet;
import processing.core.PFont;

public class ProcessingGame extends PApplet{
	String host = "ast1.itp-redial.com";
	int port = 12002;
	String phoneNumber = "1(360)516-2008";
	TinyphoneClient tp;
	ArrayList<Caller> callers = new ArrayList<Caller>();
	public void setup(){
		size(640,480);
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
				Caller caller = callers.get(i);
				caller.calc();
				caller.render();
			}
		}
	}

	//called by tinyphone client when there's a new caller (required)
	public void newCallerEvent(TinyphoneEvent event){
		Caller caller = new Caller(this,event.getId(), event.getCallerNumber());
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
				caller.newKeypress(event.getValue());
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

package com.itpredial.tinyphone.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.UnknownHostException;

import com.itpredial.tinyphone.client.TinyphoneEvent.EventType;

public class TinyphoneClient extends Thread {
	Object parent;
	Socket client;
	DataOutputStream outToServer;
	BufferedReader inFromServer;
	String phoneNumber;
	String uniqueID = ""+System.currentTimeMillis()+(int)(Math.random()+1000);
	public TinyphoneClient(Object parent,String host, int port, String phoneNumber){
		try {
			this.parent = parent;
			//clean out any number formatting
			this.phoneNumber = phoneNumber.replaceAll("[\\s\\(\\)-]", "");
			client = new Socket(host, port);
			outToServer = new DataOutputStream(client.getOutputStream());
			inFromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getPhoneNumber(){
		return phoneNumber;
	}

	public void run(){
		boolean looping = true;
		try {
			outToServer.writeBytes("CONNECT,"+uniqueID+","+phoneNumber+",CR" + '\n');
			while(looping){
				String message = inFromServer.readLine();
				if (message != null){
					message = message.replaceAll("[{}\"]", "");
					String[] params =message.split(",");
					TinyphoneEvent event = new TinyphoneEvent();
					for (int i = 0; i < params.length; i++) {
						String[] kv = params[i].split(":");
						if (kv[0].equals("id")){
							event.setId(kv[1]);
						} else if (kv[0].equals("event")){
							event.setEventType(kv[1]);
						}else if (kv[0].equals("value")){
							event.setValue(kv[1]);
						}
					}
					switch(event.getEventType()){
					case NEW_CALLER: handleNewCaller(event);break;
					case AUDIO_LEVEL: handleAudioLevel(event);break;
					case KEYPRESS: handleKeypress(event);break;
					case HANGUP: handleHangup(event);break;
					}
					//System.out.println("FROM SERVER: " + message);
				} else {
					looping = false;
				}
			}
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void handleHangup(TinyphoneEvent event) {
		invokeMethod("hangupEvent", event, true);
	}

	private void handleKeypress(TinyphoneEvent event) {
		invokeMethod("keypressEvent", event,false);
	}

	private void handleAudioLevel(TinyphoneEvent event) {
		invokeMethod("audioLevelEvent", event,false);
	}

	private void handleNewCaller(TinyphoneEvent event) {
		String[] kv = event.getValue().split("\\|");
		String callerNumber = kv[0];
		event.setCallerNumber(callerNumber);
		//create caller label, which can be used for privacy
		if (callerNumber.length()>7){
			int start = callerNumber.length()-7;
			StringBuilder sb = new StringBuilder(callerNumber);
			sb.replace(start, start+3, "xxx");
			event.setCallerLabel(sb.toString());
		}
		if (kv.length > 1){
			String[] args = new String[kv.length-1];
			for (int i = 1; i < kv.length; i++) {
				args[i-1]=kv[i];
			}
			event.setArgs(args);
		}
		invokeMethod("newCallerEvent", event,true);
	}

	private void invokeMethod(String methodName, TinyphoneEvent event, boolean required){
		try {
			Class cls = parent.getClass();
			Class partypes[] = {TinyphoneEvent.class};
			Method meth = cls.getMethod(methodName, partypes);
			Object arglist[] = {event};
			meth.invoke(parent, arglist);
		}
		catch (Throwable e) {
			if (required){
				System.err.println("missing required method "+methodName+"(TinyphoneEvent event)");
				System.err.println(e);
			}
		}
	}

	//FROM SERVER: {"id":"1331862623.194","event":"new_call","value":"6466429290|13605162008"}
	//FROM SERVER: {"id":"1331862623.194","event":"audio_level","value":"0"}
	//FROM SERVER: {"id":"1331862623.194","event":"hangup","value":"1"}
}

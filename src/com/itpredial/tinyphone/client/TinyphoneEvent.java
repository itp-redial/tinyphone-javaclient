package com.itpredial.tinyphone.client;

public class TinyphoneEvent {
	
	public enum EventType {NEW_CALLER,AUDIO_LEVEL,KEYPRESS,HANGUP,SMS}
	String id;
	String callerNumber;
	String callerLabel;
	String[] args;
	EventType eventType;
	String value;
	/**
	 * @return the id
	 */
	
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the callerNumber
	 */
	public String getCallerNumber() {
		return callerNumber;
	}
	
	public void setArgs(String[] args){
		this.args = args;
	}
	
	public String[] getArgs(){
		return args;
	}
	
	public void setCallerLabel(String label){
		callerLabel = label;
	}
	
	public String getCallerLabel(){
		return callerLabel;
	}
	
	/**
	 * @param callerNumber the callerNumber to set
	 */
	public void setCallerNumber(String callerNumber) {
		this.callerNumber = callerNumber;
	}
	/**
	 * @return the eventType
	 */
	public String getEventTypeString() {
		return eventType.toString();
	}
	
	public EventType getEventType() {
		return eventType;
	}
	/**
	 * @param eventType the eventType to set
	 */
	public void setEventType(String eventType) {
		if (eventType.startsWith("new")){
			this.eventType = EventType.NEW_CALLER;
		} else if (eventType.startsWith("key")){
			this.eventType = EventType.KEYPRESS;
		}else if (eventType.startsWith("aud")){
			this.eventType = EventType.AUDIO_LEVEL;
		}else if (eventType.startsWith("han")){
			this.eventType = EventType.HANGUP;
		}else if (eventType.startsWith("sms")){
			this.eventType = EventType.SMS;
		}
	}
	
	public boolean typeEquals(EventType type){
		return eventType.equals(type);
	}
	
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	public String toString(){
		String str = "Tinyphone Event "+eventType+": id="+id;
		if (eventType.equals(EventType.NEW_CALLER) || eventType.equals(EventType.SMS)){
			str += "\n   caller Number="+callerNumber;
			str += ", caller Label="+callerLabel+"\n";
			if (args != null){
				str +="   args=[";
				for (int i = 0; i < args.length; i++) {
					str +="\""+args[i]+"\",";
				}
				str = str.substring(0,str.length()-1);
				str +="]";
			}
			if (eventType.equals(EventType.SMS)){
				str += "message: "+value;
			}
		} else {
			str +=", value="+value;
		}
		return str;
	}
}

import com.itpredial.tinyphone.client.*;

TinyphoneClient tp;
//add TinyPhone's host name or IP address here
String host = "change-to-your-host.com";
//change to TinyPhone's port
int port = 12002;
//change the phone number to the one that you're using.
String phoneNumber = "1(999)999-9999";

void setup() {
  //start tinyphone as part of setup.
  tp = new TinyphoneClient(this, host, port, phoneNumber);
  tp.start();
}

void draw(){
 //add drawing logic 
}

//called by tinyphone client when there's a new caller (required)
public void newCallerEvent(TinyphoneEvent event) {
  //handle the new caller.  You may want to create a Caller object now
  //println(event.toString());
}
//called by tinyphone client when there's an audio level (optional)
public void audioLevelEvent(TinyphoneEvent event) {
  //use event.getId() to find appropriate caller
  //event.getValue() for audio levels will be 0-32768.
  //you may want to normalize that value to 0-1.
  float audioLevel=float(event.getValue())/32768;
  //println(event.getId()+" audio level="+audioLevel);
}
//called by tinyphone client when there's a new key press (optional)
public void keypressEvent(TinyphoneEvent event) {
  //use event.getId() to find appropriate caller
  char keypress = event.getValue().charAt(0);
  //println(event.getId()+" keypress="+keypress);
}
//called by tinyphone client when a caller hangs up (required)
public void hangupEvent(TinyphoneEvent event) {
  //you may want to remove the caller object that's associated with event.getId()
  //println(event.toString());
}


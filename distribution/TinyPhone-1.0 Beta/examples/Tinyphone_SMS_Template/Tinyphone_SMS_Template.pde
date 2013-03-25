import com.itpredial.tinyphone.client.*;

TinyphoneClient tp;
//add TinyPhone's host name or IP address here
String host = "change-to-your-host.com";
//change to TinyPhone's port
int port = 12002;
//change the phone number to the one that you're using.
String phoneNumber = "1(917)999-9999";

void setup() {
  //start tinyphone as part of setup.
  tp = new TinyphoneClient(this, host, port, phoneNumber);
  tp.start();
}

void draw(){
 //add drawing logic 
}

  //called by tinyphone client when there's a new key press (optional)
public void smsEvent(TinyphoneEvent event){
  //handle SMS message
  //event.getCallerNumber() = sender's SMS number
  //event.getCallerLabel() = sender's obscured SMS number (for privacy)
  //event.getValue() = SMS message
  System.out.println(event.toString());
}


####Tinyphone Java-Processing Client
Tinyphone apps can be built in Java or Processing using the Tinyphone Java client. The java client makes it possible to handle phone call events inside your Processing or Java app.

The tinyphone client is expecting to connect to the Tinyphone server.  Make sure that you have installed Asterisk and the Tinyphone server.

These instructions will primarily cover using the tinyphone client in Processing. If you’re using Java, I’m going to assume you know what you’re doing, and can navigate the Java examples at github.com.

    https://github.com/itp-redial/tinyphone-javaclient
The Tinyphone Processing library can be downloaded from [here:](https://github.com/downloads/itp-redial/tinyphone-javaclient/TinyPhone.zip)

Unzip the file, and place the folder in your Processing libraries folder.  On Mac OSX the location is usually at ~/Documents/Processing/libraries.

Open up Processing and go to File -> Examples…

Choose Contributed Libraries -> TinyPhone.  You’ll see 3 examples.  Tinyphone_Template is the bare bones code for a Tinyphone App.  At the top you’ll see the code that configures the tinyphone app with the information necessary to connect to the tinyphone server.

````

    import com.itpredial.tinyphone.client.*;
	TinyphoneClient tp;
	//add TinyPhone's host name or IP address here
	String host = "change-to-your-host.com";
	//change to TinyPhone's port
	int port = 12002;
	//change the phone number to the one that you're using.
	String phoneNumber = "1(360)999-9999";
	
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
````

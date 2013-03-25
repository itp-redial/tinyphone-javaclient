import com.itpredial.tinyphone.client.*;
//add TinyPhone's host name or IP address here
String host = "your-host-here.com";
//change to TinyPhone's port
int port = 12002;
//change the phone number to the one that you're using.
String phoneNumber = "1(999)999-9999";
TinyphoneClient tp;
ArrayList<Caller> callers = new ArrayList<Caller>();
void setup() {
  size(640, 480);
  PFont myFont = createFont("Helvetica", 12);
  textFont(myFont);
  smooth();
  noStroke();
  tp = new TinyphoneClient(this, host, port, phoneNumber);
  tp.start();
}

void draw() {
  background(20);
  text("Call "+phoneNumber, 20, height-20);
  synchronized(callers) {
    for (int i = 0; i < callers.size(); i++) {
      Caller caller = callers.get(i);
      caller.calc();
      caller.render();
    }
  }
}

//called by tinyphone client when there's a new caller (required)
public void newCallerEvent(TinyphoneEvent event) {
  Caller caller = new Caller(event.getId(), event.getCallerNumber(), event.getCallerLabel());
  synchronized(callers) {
    callers.add(caller);
  }
}
//called by tinyphone client when there's an audio level (optional)
public void audioLevelEvent(TinyphoneEvent event) {
  synchronized(callers) {
    Caller caller = getCaller(event.getId());
    if (caller != null) {
      float audioLevel = (Float.parseFloat(event.getValue())/32764);
      caller.audioLevel = audioLevel;
    }
  }
}
//called by tinyphone client when there's a new key press (optional)
public void keypressEvent(TinyphoneEvent event) {
  synchronized(callers) {
    Caller caller = getCaller(event.getId());
    if (caller != null) {
      caller.newKeypress(event.getValue());
    }
  }
}

Caller getCaller(String id) {
  for (int i = 0; i < callers.size(); i++) {
    Caller caller = callers.get(i);
    if (caller.isCaller(id)) {
      return caller;
    }
  }
  return null;
}
//called by tinyphone client when a caller hangs up (required)
public void hangupEvent(TinyphoneEvent event) {
  synchronized(callers) {
    for (int i = 0; i < callers.size(); i++) {
      Caller caller = callers.get(i);
      if (caller.isCaller(event.getId())) {
        callers.remove(i);
        break;
      }
    }
  }
}


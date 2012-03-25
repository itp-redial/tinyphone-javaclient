class Caller {
  String phoneNumber;
  String label;
  String uniqueID;
  float audioLevel;
  private PVector velocity = new PVector(0, 0);
  private PVector location;
  public Caller(String uniqueID, String phoneNumber) {
    //start more or less in the middle of the screen
    int startX = (width/4)+(int)random(width/2);
    int startY = (height/4)+(int)random(height/2);
    location = new PVector(startX, startY);
    this.uniqueID = uniqueID;
    this.phoneNumber = phoneNumber;
    if (phoneNumber.length()>7) {
      int start = phoneNumber.length()-7;
      StringBuilder sb = new StringBuilder(phoneNumber);
      sb.replace(start, start+3, "xxx");
      label = sb.toString();
    }
  }

void calc() {
    location.add(velocity);
    if (location.x<0) {
      location.x = 0;
      velocity.mult(-1);
    } 
    else if (location.x>width) {
      location.x = width;
      velocity.mult(-1);
    }
    else if (location.y<0) {
      location.y = 0;
      velocity.mult(-1);
    }
    else if (location.y>height) {
      location.y = height;
      velocity.mult(-1);
    }
  }

void render() {
    text(label, location.x, location.y-12);
    rect(location.x, location.y, 20, 20);
  }

  public void newKeypress(String keypress) {
    char keychar = keypress.charAt(0);
    switch(keychar) {
    case '2':
      velocity.set(0, -1, 0);
      break; //up
    case '8':
      velocity.set(0, 1, 0);
      break; //down
    case '4':
      velocity.set(-1, 0, 0);
      break; //left
    case '6':
      velocity.set(1, 0, 0);
      break; //right
    }
  }

  public boolean isCaller(String checkID) {
    return uniqueID.equals(checkID);
  }
}


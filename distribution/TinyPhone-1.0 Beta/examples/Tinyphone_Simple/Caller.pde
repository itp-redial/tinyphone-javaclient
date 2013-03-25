class Caller {
  String label;
  String phoneNumber;
  String uniqueID;
  float audioLevel;
  String[] args;
  String lastKeypress="?";

  public Caller(String uniqueID, String callerNumber, String callerLabel) {
    this.uniqueID = uniqueID;
    this.phoneNumber = callerNumber;
    this.label = callerLabel;
  }

  public boolean isCaller(String checkID) {
    return uniqueID.equals(checkID);
  }

  public String[] getArgs() {
    return args;
  }

  public void setArgs(String[] args) {
    this.args = args;
  }
}

public class Key {

    private String label;
    private int theType;

   public Key(String theLabel, int theType){
       this.label = theLabel.toLowerCase();
       this.theType = theType;
   }
// initialize our variables and constructor

    public int compareTo(Key k) {
        if (label.equals(k.label) && theType == k.theType) {
            // if both label and type equal then return 0 to indicate the keys are equal
            return 0;
        }
        if (this.label.compareTo(k.label) > 0 || this.label.equals(k.label) && theType > k.theType) {
            // if label is greater or the labels are equal and the type is greater, we return 1 since our K is greater
            return 1;
        } else {
            // else this would mean that it is the opposite then it would be smaller, so we return -1 as needed
            return -1;
        }
    }

    public String getLabel() {
        return label;
    }

    public int getType() {
        return theType;
    }
    //getters for instance variables
}
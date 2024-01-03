public class Record {
    private Key theKey;
    private String theData;

    public Record(Key k, String theData) {
        this.theKey = k;
        this.theData = theData;
    }
    public String getDataItem() {
        return theData;

    }
    public Key getKey() {
        return theKey;
    }
    // creating our key and data instance variables for our Record class which uses a key and data to create a
    // record object along with our getters
}

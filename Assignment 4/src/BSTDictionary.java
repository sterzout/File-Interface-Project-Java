public class BSTDictionary implements BSTDictionaryADT{
    private BinarySearchTree newTree;
    public BSTDictionary(BinarySearchTree newTree){
        this.newTree = newTree;
        // create a new tree for dictionary
    }
    @Override
    public Record get(Key k) {
        BSTNode record = newTree.get(newTree.getRoot(),k);

        return record.getRecord();

        // return get record
    }

    @Override
    public void put(Record d) throws DictionaryException {
        newTree.insert(newTree.getRoot(),d);
        // return insert record

    }

    @Override
    public void remove(Key k) throws DictionaryException {
        newTree.remove(newTree.getRoot(),k);
        // return get key

    }

    @Override
    public Record successor(Key k) {
        return newTree.successor(newTree.getRoot(),k).getRecord();
        // return successor record

    }

    @Override
    public Record predecessor(Key k) {
        return newTree.predecessor(newTree.getRoot(),k).getRecord();
        // return predecessor record

    }

    @Override
    public Record smallest() {
        return newTree.smallest(newTree.getRoot()).getRecord();
        // return smallest record
    }

    @Override
    public Record largest() {
        return newTree.largest(newTree.getRoot()).getRecord();
        // return largest record
    }
}

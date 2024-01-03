public class BSTNode {

    private Record item;
    private BSTNode left;
    private BSTNode right;
    private BSTNode parent;

    public BSTNode(Record item){
        this.item = item;
        this.left = null;
        this.right = null;
        this.parent = null;
        // since this is just initialize the item to item but the rest to null to make sure it has no parents or children
        // it is simply a node with no links
    }
    public Record getRecord(){
        return this.item;
    }
    public void setRecord (Record d){
        this.item = d;
    }
    public BSTNode getLeftChild(){
        return this.left;
    }
    public BSTNode getRightChild(){
        return this.right;

    }
    public BSTNode getParent(){
        return this.parent;

    }
    public void setLeftChild(BSTNode u){
        this.left = u;
    }
    public void setRightChild(BSTNode u){
        this.right = u;
    }
    public void setParent(BSTNode u){
        this.parent = u;
    }

    // all above are getters and setters of right, left and parent
    public boolean isLeaf(){
        if (left == null && right == null){
            return true;
        }
        return false;
        // if it is a leaf this makes left and right null else return false
    }
}

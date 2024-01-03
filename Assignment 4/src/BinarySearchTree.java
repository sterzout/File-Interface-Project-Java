public class BinarySearchTree {
    private BSTNode root;

    public BinarySearchTree() {
        root = new BSTNode(null);
    }

    public BSTNode getRoot() {
        return root;
    }

    public BSTNode get(BSTNode r, Key k) {
        if (r == null){
            return null;
        }
//  if r is null there is no get node
        if (r.isLeaf()) {
            return r;
        }
//  if r is a leaf, we return r
        if (r.getRecord().getKey().compareTo(k) == 0) {
            return r;
        }
//  if the compare value is 0 this means it is equal to our k and the correct node has been found
        if (r.getRecord().getKey().compareTo(k) < 0) {
            return get(r.getRightChild(), k);
//  if the compareTo says our node is larger, we go to the right
        } else {
            return get(r.getLeftChild(), k);
//  else it means we have a smaller k, so we go left
        }
    }

    public void insert(BSTNode r, Record d) throws DictionaryException {
        // as shown in class, retrieve the correct node through get
        if (r == null){
            return;
        }
        BSTNode p = get(r, d.getKey());
        // as shown in class, retrieve the correct node through get
        if (p.isLeaf()) {
            p.setRecord(d);
            p.setRightChild(new BSTNode(null));
            p.setLeftChild(new BSTNode(null));
            p.getLeftChild().setParent(p);
            p.getRightChild().setParent(p);
            // after retrieving the correct node through get you must set the new parents of the new children of p and
            //initialize the children to null.
        } else {
            throw new DictionaryException("Invalid");
            // if p is not a leaf we throw our exception
        }
    }

    public void remove(BSTNode r, Key k) throws DictionaryException {
        if (r == null) {
            return;
        }
        BSTNode target = get(r, k);
        // Find the node with the key k
        if (target.isLeaf()) {
            // if target is a leaf then we do nothing as we cannot remove it
            throw new DictionaryException("Node is not in tree");
        } else {
            if (target.getLeftChild().isLeaf() || target.getRightChild().isLeaf()) {
                // if either the left or the right child of our node is a leaf we have two cases for this
                if (target.getLeftChild().isLeaf()) {
                    // if the left child is a leaf then we set c prime to the other child and the pPrime parent equal to parent of target
                    BSTNode cPrime = target.getRightChild();
                    BSTNode pPrime = target.getParent();
                    // then we check if that parent is null so that it is not a root
                    if (pPrime != null) {
                        if (target == pPrime.getLeftChild()) {
                            pPrime.setLeftChild(cPrime);
                    // if it is not a root then we set the target to the left child of parent which is cPrime which is a leaf
                            // this removes the node
                        } else {
                            pPrime.setRightChild(cPrime);
                    //else we set it to the leaf child if its the right child which removes it
                        }
                        cPrime.setParent(pPrime);
                        // after the removal then we set out cPrime child back to the pPrime parent since our target no longer
                        // has pPrime as a parent
                    } else if (pPrime == null) {
                        root = cPrime;
                        cPrime.setParent(null);
                        // on the other hand if the pPrime parent being retrieved is null then we know we are at a root
                        // hence, we must set the root to pPrime and set that parent to null
                    }

                } else if (target.getRightChild().isLeaf()) {
                    BSTNode cPrime = target.getLeftChild();
                    BSTNode pPrime = target.getParent();

                    if (pPrime != null) {
                        if (target == pPrime.getLeftChild()) {
                            pPrime.setLeftChild(cPrime);
                        } else {
                            pPrime.setRightChild(cPrime);
                        }
                        cPrime.setParent(pPrime);
                    } else if (pPrime == null) {
                        root = cPrime;
                        cPrime.setParent(null);
                    }
                }
                // this is the exact same code but for the rightChild being a leaf


            } else if (!(target.getRightChild().isLeaf() || target.getLeftChild().isLeaf())) {
                BSTNode smallestNode = smallest(target.getRightChild());
                target.setRecord(smallestNode.getRecord());
                remove(smallestNode, smallestNode.getRecord().getKey());
                // if none of the children are leaves, we know we are at an internal node, then we must
                //find the smallest node on the right subtree of that target since this would find the
                // next biggest value to 6, the successor in other words. After doing so it sets its record
                // to this and  removes that node recursively with the cases we did above
            }
        }
    }



    public BSTNode successor (BSTNode r, Key k){
        BSTNode current = get(r, k);
        if (current == null) {
            return null;
        }
        // if get returns null that means r was null and no tree
        if (!(current.getRightChild().isLeaf())) {
            return smallest(current.getRightChild());
            // if the current is not a leaf and an internal node, return the largest value in its right subtree
        } else {
            BSTNode parentOfNode = current.getParent();
            while (!(parentOfNode == null) && current == parentOfNode.getRightChild()) {
                current = parentOfNode;
                parentOfNode = parentOfNode.getParent();
                // if the current is a leaf recursively call back up the tree until we find its next largest value which
                // would be until we find that specific parent
            }
            return parentOfNode;
            // we return our successor
        }
    }

    public BSTNode predecessor (BSTNode r, Key k){
        BSTNode current = get(r, k);
        if (current == null) {
            return null;
        }
        if (!(current.getLeftChild().isLeaf())) {
            return largest(current.getLeftChild());
        } else {
            BSTNode parentOfNode = current.getParent();
            while (parentOfNode != null && current == parentOfNode.getLeftChild()) {
                current = parentOfNode;
                parentOfNode = parentOfNode.getParent();
            }
            return parentOfNode;
            // this follows the same procedure as the successor but instead of performing this left children
        }
    }
    public BSTNode smallest (BSTNode r){
        if (r == null) {
            return null;
        }
//  if r is null there will be no smallest node
        BSTNode p = r;
        while (!p.isLeaf()){
            p = p.getLeftChild();
        }
        return p.getParent();
// else as taught in class, we traverse down to the left most child to find our smallest node until we reach a leaf,
// then we would return the leafs parent as the leafs does not store the value, it would be its parent
    }
    public BSTNode largest (BSTNode r){
        if (r == null) {
            return null;
        }
        BSTNode p = r;
        while (!p.isLeaf()){
            p = p.getRightChild();
        }
        return p.getParent();
//  exact same as smallest but on the right most child instead
    }
}
/*

 Edgar Lara Sanchez

 */
public class BinTree<T extends Comparable<T>> {

    //Members
    private Node<T> root;

    //Default Constructor
    public BinTree() {
        root = null;
    }

    //Overloaded Constructor
    public BinTree(Node<T> r) {
        root = r;
    }

    // GETTER (ACCESSOR)
    public Node<T> getRoot() {
        return root;
    }

    // FUNCTIONS
    //Insert Function
    public void insert(T data) {
        root = insertNode(root, data);
    }

    //Insert Function
    public Node<T> insertNode(Node<T> cur, T newNode) {
        //Creation of new node once we reach the correct place to insert the node
        if (cur == null) {
            cur = new Node<>(newNode);
            return cur;
        }

        // Recursion down the tree
        if (newNode.compareTo(cur.getData()) < 0) {
            cur.setLeft(insertNode(cur.getLeft(), newNode));
        } else if (newNode.compareTo(cur.getData()) > 0) {
            cur.setRight(insertNode(cur.getRight(), newNode));
        }

        return cur;
    }

    public Node<T> search(T data) {
        Node<T> key = new Node<>(data);

        return searchNode(this.root, key);
    }

    //Search Function
    public Node<T> searchNode(Node<T> root, Node<T> key) {

        // if root is exponent is present at root or the root called is null
        if (root == null || key.compareTo(root) == 0) {
            return root;
        }

        // Key is less than root's epxonent
        if (root.compareTo(key) > 0) {
            return searchNode(root.getLeft(), key);
        }

        // Key is greater than root's exponent
        return searchNode(root.getRight(), key);
    }

    //Remove Function
    public Node<T> remove(T data) {
        

        return removeNode(this.root, data);
    }
    
    public Node<T> removeNode(Node<T> cur, T key) {

        // If the tree is empty or node that needs to be deleted is not in the tree
        if (cur == null) {
            return cur;
        }

        //Go down the tree until node that needs to be deleted is found
        if (key.compareTo(cur.getData()) < 0) {
            cur.setLeft(removeNode(cur.getLeft(), key));
        } else if (key.compareTo(cur.getData()) > 0) {
            cur.setRight(removeNode(cur.getRight(), key));
        } else {
            //This is were the node that needs to be deleted is found
            // Only one or no child
            // Returns a node so that the child becomes its own parent
            if (cur.getLeft() == null) {
                return cur.getRight();
            } else if (cur.getRight() == null) {
                return cur.getLeft();
            }
            // 2 children case
            // Get smallest node in the right subtree to delete right node
            Node<T> suc = cur.getRight();
            while(suc.getLeft() != null){
                suc = suc.getLeft();
            }
            cur.setData(suc.getData());

            // Delete the smallest node 
            cur.setRight(removeNode(cur.getRight(), cur.getData()));
        }
        return cur;
    }

}

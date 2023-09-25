/*
 Edgar Lara Sanchez
NetID: exl190014
 */
public class Node<T extends Comparable<T>> implements Comparable<Node<T>> {

    private T data;
    private Node<T> left;
    private Node<T> right;

    //Default constructor
    

    //Overloaded constructor
    public Node(T t) {
        data = t;
        left = null;
        right = null;
    }

    // SETTERS (MUTATORS)
    public void setLeft(Node<T> l) {
        left = l;
    }

    public void setRight(Node<T> r) {
        right = r;
    }

    public void setData(T t) {
        data = t;
    }

    // GETTERS (ACCESSORS)
    public Node<T> getLeft() {
        return left;
    }

    public Node<T> getRight() {
        return right;
    }

    public T getData() {
        return data;
    }

    @Override
    public int compareTo(Node<T> node) {
        
        if (this.data.compareTo(node.getData()) == 0) {
            return 0;
        } else {
            return (this.data.compareTo(node.getData()));
        }

    }
}
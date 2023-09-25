/* 
 * Name: Edgar Lara Sanchez
 * NetID: exl190014
 * @param <T>
 */
public class LinkList<T> {

    private Node<T> head;

    //Default constructor for Linked List
    public LinkList() {
        head = null;
    }

    //Overloaded Constructor
    public LinkList(Node<T> n) {
        head = n;
    }

    //Setter and getter for head
    public void setHead(Node<T> n) {
        head = n;
    }

    public Node<T> getHead() {
        return head;
    }

    //Adds node to the end of the list
    public void appendList(Node<T> n) {
        if (head != null) {
            Node<T> cur = this.head;
            while (cur.getNext() != null)//this reaches end of the list
            {
                cur = cur.getNext();
            }
            cur.setNext(n);//sets node to the end of the list
        } else {
            head = n;
        }
    }

    //Displays the data of the players
    public void printStats(Node<T> headNode) {
        if (headNode != null) {
            System.out.print(headNode.toString());//print the stats of the players
            headNode = headNode.getNext();

            printStats(headNode); //Recursive call to print the stats in the linked list
        }
    }

    //Sort the list alphabetically
    public Node<T> sortAlpha(Node<T> h) {
        boolean switched = true;
        while (switched) //keep looping until no switching is needed
        {
            switched = false;
            //Start from the top again
            h = this.head;
            Node<T> cur = this.head.getNext();

            while (cur != null)//loop through each set of nodes
            {
                if (h.compareTo(cur) > 0) {
                    //switch the nodes if not in order
                    Node<T> holder = new Node<>();
                    holder.setData(h.getData());
                    h.setData(cur.getData());
                    cur.setData(holder.getData());
                    switched = true;
                }
                //move on to next set of nodes to compare
                h = h.getNext();
                cur = cur.getNext();
                //will reach null end eventually and that is when the while loop ends
            }
        }
        return head;

    }
}

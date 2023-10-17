/* 
 * Name: Edgar Lara Sanchez
 * @param <T>
 */
public class Node<T> implements Comparable<Node<T>>{
    
    private T data; //player object stored in data
    private Node<T> next;
    
    //Default constructor
    public Node()
    {
        data = null;
        next = null;
    }
    //Overloaded constructor with data
    public Node(T data)
    {
        this.data = data;
    }
    
    
    
    //Setters and getters for node
    public void setData(T stats){
        data = stats;
    }
    
    public void setNext(Node<T> n){
        next = n;
    }
    
    public T getData(){
        return data;
    }
    
    public Node<T> getNext(){
        return next;
    }
    
    @Override
    public String toString(){
        String str; //main string that will be returned
        String batAvg = String.format("%.3f",((Player)data).getBA());//modify string for print format
        String obp = String.format("%.3f",((Player)data).getOBP());//modify string for print format
        
        //using (Player) to cast data as a player object to use player methods
        str = ((Player)data).getName() + "\t" + ((Player)data).getAtBats() + "\t"
                + ((Player)data).getHits() + "\t"
                + ((Player)data).getWalks() + "\t"
                + ((Player)data).getStrikeouts() + "\t"
                + ((Player)data).getHitsByPitch() + "\t"
                + ((Player)data).getSacrifices() + "\t"
                + batAvg + "\t"
                + obp + "\n";
        
        return str;
    }
    
    //Compare to function comparing the names of the players for alphabetical arrangement
    @Override
    public int compareTo(Node<T> node){
        //in main "this.compareTo(node)"
        if(((Player)this.data).getName().compareTo(((Player)node.getData()).getName()) == 0)
        {
            return 0;
        }
        else
        {
            return ((Player)this.data).getName().compareTo(((Player)node.getData()).getName());
        }
        
    }
}

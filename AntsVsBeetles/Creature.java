abstract class Creature {

    protected int turn;   // turn number of creature
    protected boolean hasMoved; // moved status of creature

    //DEFAULT CONSTRUCTOR
    public Creature() {
        turn = 0;
        hasMoved = false;
    }

    //OVERLOADED CONSTRUCTOR
    // turn of the creature
    // creature has or hasn't moved
    public Creature(int t, boolean m) {
        turn = t;
        hasMoved = m;
    }

    
    //SETTERS AND GETTERS FOR TURN AND MOVED
    public void setTurn(int t) {
        turn = t;
    }

    public void setMoved(boolean move) {
        hasMoved = move;
    }

    public int getTurn() {
        return turn;
    }

    public boolean getMoved() {
        return hasMoved;
    }


    //ABSTRACT METHOD FOR MOVE 
    abstract int move(String s);



    abstract boolean breed(int numTurns);
       
}

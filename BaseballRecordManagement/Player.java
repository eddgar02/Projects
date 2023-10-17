
/* 

 */

public class Player {

    //Stats
    private String name;
    private int hits;
    private int outs;
    private int strikeouts;
    private int walks;
    private int hitsByPitch;
    private int sacrifices;

    //Defualt Constructor
    public Player() {
        name = null;
        hits = 0;
        outs = 0;
        strikeouts = 0;
        walks = 0;
        hitsByPitch = 0;
        sacrifices = 0;
    }

    //Overloaded constructor
    public Player(String name, int h, int o, int k, int w, int p, int s) {
        this.name = name;
        hits = h;
        outs = o;
        strikeouts = k;
        walks = w;
        hitsByPitch = p;
        sacrifices = s;
    }

    //Setters and getters for the stats
    public void setName(String n) {
        name = n;
    }

    public void setHits(int h) {
        hits = h;
    }

    public void setOuts(int o) {
        outs = o;
    }

    public void setStrikeouts(int k) {
        strikeouts = k;
    }

    public void setWalks(int w) {
        walks = w;
    }

    public void setHitsByPitch(int p) {
        hitsByPitch = p;
    }

    public void setSacrifices(int s) {
        sacrifices = s;
    }

    public String getName() {
        return name;
    }

    public int getHits() {
        return hits;
    }

    public int getOuts() {
        return outs;
    }

    public int getStrikeouts() {
        return strikeouts;
    }

    public int getWalks() {
        return walks;
    }

    public int getHitsByPitch() {
        return hitsByPitch;
    }

    public int getSacrifices() {
        return sacrifices;
    }

    /*
     * Methods for calculations
     */
    
    
    //Calculates At Bats
    public int getAtBats() {
        return hits + outs + strikeouts;
    }

    
    //Calculates Batting average
    public double getBA() {
        if ((hits + outs + strikeouts) == 0) {
            return 0.0;
        } else {
            return (double) hits / (hits + outs + strikeouts);
        }
    }

    
    
    //Calculates the on base percentage
    public double getOBP() {
        if ((hits + outs + strikeouts + walks + hitsByPitch + sacrifices) == 0) {
            return 0.0;
        } else {
            return ((double)hits + walks + hitsByPitch)/(hits + outs + strikeouts + walks + hitsByPitch + sacrifices);
        }
        }
    }

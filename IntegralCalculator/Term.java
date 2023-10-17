/*
 Edgar Lara Sanchez
 */
public class Term implements Comparable<Term>{

    private int coefficient;
    private int exponent;

    //Default Constructor
    public Term() {
        coefficient = 0;
        exponent = 0;
    }

    //Overloaded Constructor
    public Term(int c, int e) {
        coefficient = c;
        exponent = e;
    }

    // Setters (Mutators)
    public void setCoefficient(int c) {
        coefficient = c;
    }

    public void setExponent(int e) {
        exponent = e;
    }

    // Getters (Accessors)
    public int getCoefficient() {
        return coefficient;
    }

    public int getExponent() {
        return exponent;
    }
    
    @Override
    public int compareTo(Term comparedTerm){
        //in main "this.compareTo(node)"
        if(this.exponent == comparedTerm.getExponent()){
            return 0;
        }
        if(this.exponent >= comparedTerm.getExponent()){
            return 1;
        }
        return -1;
        
    }
}

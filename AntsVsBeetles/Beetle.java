
public class Beetle extends Creature {

    static final int breedTurns = 8; // number of turns for beetle to breed
    static final int starveTurns = 5; // number of turns for beetle to starve
    static final int gridDimension = 10; // Dimensions of game area

    private int hunger; // counter for the beetle's hunger

    
    //DEFAULT BEETLE CONSTRUCTOR
    Beetle() {
        super();
        hunger = 0;

    }

    //OVERLOADED BEETLE CONSTRUCTOR
    Beetle(int t, boolean m, int h) {
        super(t, m);
        hunger = h;

    }
    
    //SETTER AND GETTER FOR HUNGER
    public void setHunger(int h){
        hunger = h;
    }
    
    public int getHunger(){
        return hunger;
    }
    
    
   //BEETLE MOVE METHOD
   @Override
   public int move(String str){
      int direction = 0;
      int minValue = gridDimension;
      
      String firstFour = str.substring(0,4);
      if (firstFour.equals("NNNN"))//checking for farthest edge since no Ant in any direction
      {
          int highestValue = 0;
          int edgeTie =0;
          
          for(int i = 4;i<8;i++)//indexes 4,5,6,7 contain spaces until edge in 
                                //direction N,E,S,W respectively
          {
              if(Character.getNumericValue(str.charAt(i))>highestValue){
               highestValue = Character.getNumericValue(str.charAt(i));
               direction = i-4;
            }
          }
          //Checking for edge length ties 
          for(int i = 4;i<8;i++){
              if(Character.getNumericValue(str.charAt(i)) == highestValue){
                  edgeTie++;
              }
          }
          if(edgeTie==4)//use N,E,S,W priority if edges are all equally spaced
          {
              return 0;
          }
          if(edgeTie >1)//use N,E,S,W priority if more than one edge is equally spaced
          {
              if(Character.getNumericValue(str.charAt(4)) == highestValue){
                  return 0;//move North
              }
              if(Character.getNumericValue(str.charAt(5)) == highestValue){
                  return 1;//move East
              }
              if(Character.getNumericValue(str.charAt(6)) == highestValue){
                  return 2;//move South
              }
              if(Character.getNumericValue(str.charAt(7)) == highestValue){
                  return 3;//move West
              }
          }
          
          return direction;
      }
      
      //loop through all values of the string to get closest ant direction
      for(int i=0;i<4;i++)//first four characters of string contain spaces until beetle
      {
         
         if(str.charAt(i) =='N'){
            continue;
         } else {
            if(Character.getNumericValue(str.charAt(i))<minValue){
               minValue = Character.getNumericValue(str.charAt(i));
               direction = i; 
            }
         }
      }
      //Checking for ties in spaces
      int multipleClosest = 0;
      for(int i = 0;i<4;i++){
          if(Character.getNumericValue(str.charAt(i)) == minValue){
              multipleClosest++;
              
          }
      }
      
      if (multipleClosest > 1)//checking if there are multiple ants with the same closest distance
      {
          int maxValue = 0;
          int adjTies =0;
          //indexes 8,9,10,11 represent number of adjacent ant in N,E,S,W directions respectively
          for(int i = 8;i<12;i++){
              if(Character.getNumericValue(str.charAt(i-8)) == minValue){
                  if(Character.getNumericValue(str.charAt(i)) > maxValue){
                  maxValue = Character.getNumericValue(str.charAt(i));
                  direction = i-8;
                  }
              }
          }
          
          
          //Checking for ties in number of adjacent ants 
          for(int i = 8;i<12;i++){
              if(Character.getNumericValue(str.charAt(i)) == maxValue){
                  adjTies++;
              }
          }
          if(adjTies==4){
              return 0; //using N,E,S,W priority
          } else if(adjTies >1)//use N,E,S,W priority if mutliple ants 
                               //with equal highest value of adjacent ants
          {
              if (Character.getNumericValue(str.charAt(8)) ==maxValue && Character.getNumericValue(str.charAt(0))==minValue)
              {
                  return 0; //move north 
              }
              if (Character.getNumericValue(str.charAt(9)) ==maxValue && Character.getNumericValue(str.charAt(1))==minValue)
              {
                  return 1; //move east 
              }
              if (Character.getNumericValue(str.charAt(10)) ==maxValue && Character.getNumericValue(str.charAt(2))==minValue)
              {
                  return 2; //move south 
              }
              if (Character.getNumericValue(str.charAt(11)) ==maxValue && Character.getNumericValue(str.charAt(3))==minValue)
              {
                  return 3; //move west 
              }
              
          }
          return direction;
      }
      
      
      return direction;
   }
   
   
   //BEETLE BREED METHOD
   @Override
   public boolean breed(int numTurns){
        return numTurns % breedTurns == 0;
       
   }
   
   //BEETLE STARVE FUNCTION
   public boolean starve(int counter){
        return counter == starveTurns;
   }
    
}

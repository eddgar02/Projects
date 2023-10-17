public class Ant extends Creature {
   static final int gridDimension = 10; // Dimensions of game area
   static final int turnsBreed = 3; // number of turns for ants to breed
   
   //DEFAULT ANT CONSTRUCTOR
    Ant() {
        super();
    }
    
    //OVERLOADED ANT CONSTRUCTOR
    Ant(int t, boolean m){
       super(t,m);
    
    }
   
   
   //MOVE METHOD FOR ANTS
   @Override
   public int move(String str){

      int direction = 0;
      int minValue = gridDimension;
      int multipleBeetles = 0;
      int tieBeetles = 0;
      
      //loop through all values of the string 
      for(int i=0;i<str.length();i++){
         
         if(str.charAt(i) !='N'){
            if(Character.getNumericValue(str.charAt(i))<minValue){
               minValue = Character.getNumericValue(str.charAt(i));
               direction = i; 
               
            }
            multipleBeetles++;
         }
      }
      
      //Checking for tie spaces
      for(int i =0; i<str.length();i++){
          if(str.charAt(i) == str.charAt(direction)){
              tieBeetles++; //if there is a tie for closest beetles
          }
      }

      if(tieBeetles == 4)//use N,E,S,W priority if beetles are in all directions 
                         // and are all equally spaced
      {
          return 0;
      } else if(tieBeetles >1)
      {
          //use N,E,S,W priority if more than one beetle is equally spaced
          if(str.charAt(0) == 'N')
          {
              return 0; //move North
          } else if (str.charAt(1) == 'N'){
              return 1; //move east
          } else if (str.charAt(2) == 'N'){
              return 2; //move South
          } else if(str.charAt(3) == 'N'){
              return 3; //move West
          } else {
              tieBeetles = 0;
          }
          if(multipleBeetles ==4)//if surrounded and there are multiple closest beetles
                                 //try to move to farthest beetle
          {
              int greatest =0; 
              for (int i =0; i<str.length();i++){
                  if(Character.getNumericValue(str.charAt(i)) > greatest){
                      greatest= Character.getNumericValue(str.charAt(i));
                      direction = i;
                  } 
              }
              //checking for ties for farthest distance
              int t =0;
              for (int i =0; i<str.length();i++){
                  if(Character.getNumericValue(str.charAt(i)) == greatest){
                      t++;
                  }
              }
              if(t == 1){
                  return direction;
              } else if (t > 1)//use N,E,S,W priority if there are multiple farthest beetle
                               //and ant is surrounded
              {
                  if(Character.getNumericValue(str.charAt(0)) == greatest){
                      return 0;//move North
                  }
                  if(Character.getNumericValue(str.charAt(1)) == greatest){
                      return 1;//move East
                  }
                  if(Character.getNumericValue(str.charAt(2)) == greatest){
                      return 2;//move South
                  }
                  if(Character.getNumericValue(str.charAt(3)) == greatest){
                      return 3;//move West
                  }
              }
              return direction;
          }
      }
      


       switch (direction) {
       // 0 means north is closest
           case 0:
               return 2;// move south
       // 1 means east beetle is closest
           case 1:
               return 3;// move west
       // 2 means south beetle is closest
           case 2:
               return 0;// move north
       // if(direction == 3)  3 means west is closest
           default:
               return 1;// move east
       }

   }
   
   //BREED METHOD FOR ANTS
   @Override
   public boolean breed(int numTurns){
       return numTurns % turnsBreed == 0;
   }
   
   
}

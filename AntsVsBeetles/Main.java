/*
 *Name: Edgar Lara Sanchez
 *ID: exl190014
 */




import java.util.*;
import java.io.*;

class Main {
   static final int theGridSize = 10;
   
   public static void main(String[] args)throws IOException{
      Scanner scan = new Scanner(System.in);
      System.out.println("What is the world name?");
      String filename = scan.nextLine();
      System.out.println("What is the ant's character?");
      char antChar = scan.next().charAt(0);
      System.out.println("What is the beetle's character?");
      char beetleChar = scan.next().charAt(0);
      System.out.println("How many turns do you want to play out?");
      int numTurns = scan.nextInt();
      
      //Creating a creature array to store the Ant and Beetle objects
      Creature [][] game = new Creature[theGridSize][theGridSize];
      //reading file grid and storing it to Creature game 2D array
      game = readFile(filename);
      
      //Set turns to zero
         for (int row = 0; row < theGridSize; row++) {
            for (int col = 0; col < theGridSize; col++) {
                if (game[row][col] instanceof Beetle || game[row][col] instanceof Ant) {
                    (game[row][col]).setMoved(false);//no object has been moved at start of turn
                    (game[row][col]).setTurn(0);//setting the turn of all beetle 
                }                               //and ant objects to zero
            }
         }

      /*
       *LOOPING THROUGH CREATURE ARRAY TO MOVE ANTS AND BEETLES
       */
      for(int turnCounter = 1; turnCounter<=numTurns;turnCounter++){
          
         //Loop through grid to move beetles
         for(int col = 0;col<theGridSize;col++)
         {
            for(int row = 0;row<theGridSize;row++)
            {
               //If that location has a beetle and it hasn't been moved
               if(game[row][col] instanceof Beetle &&(!(game[row][col].getMoved()))){
                  
                  int[] numSpaces ={0,0,0,0};//Store spaces until ant is reached
                  int[] edgeSpaces = {0,0,0,0};//Stores spaces until edge is found in that direction
                  int[] adjacentAnts ={0,0,0,0};//Stores number of adjacent ants for each ant
                  String str = ""; //String that will be passed to the move() method
                  
                  //Checking north for ants
                  if(row==0){
                     str = "N"; //this means no ants in north direction
                                //edgeSpaces is also zero since at North edge
                  } else {
                     for(int a = row-1;a>=0;a--)
                     {
                        if(!(game[a][col] instanceof Ant)){
                           numSpaces[0]++;//no ants are present
                           if(a==0 && numSpaces[0]==row){
                              str = "N";//if we reach the edge of the map
                                        //and there are no ants
                              edgeSpaces[0] = numSpaces[0];//get spaces until north edge
                           }
                        } else{
                           str = String.valueOf(numSpaces[0]);
                           adjacentAnts[0] = getAntNeighbors(game,a,col);//checking the number of adjacent ants
                           break; 
                        }
                     } 
                  }
                  
                  //Checking east for ants
                  if(col==theGridSize-1){
                     str = str + "N"; //second component of str is N which means
                                      //no ants in east direction
                                      //edgeSpaces is also zero since at East edge
                  } else {
                     for(int a = col+1;a<theGridSize;a++){
                        if(!(game[row][a] instanceof Ant)){
                           numSpaces[1]++;
                           if(a==(theGridSize-1) && numSpaces[1]==(9-col))//Reached edge of grid and no spaces
                           {
                              str = str + "N"; //no ants in east direction
                              edgeSpaces[1] = numSpaces[1];//get spaces until east edge
                           }
                        } else {
                           str = str + String.valueOf(numSpaces[1]);
                           adjacentAnts[1] = getAntNeighbors(game,row,a);//checking the number of adjacent ants
                           break;
                        }
                     }
                  }
                  //Checking south for ants
                  if(row==theGridSize-1){
                     str = str + "N"; //second component of str is 0 which means
                                      //no ants in south direction
                                      //edgeSpaces is also zero since at South edge
                  } else {
                     for(int a = row+1;a<theGridSize;a++){
                        if(!(game[a][col] instanceof Ant)){
                           numSpaces[2]++;
                           if(a==theGridSize-1 && numSpaces[2]==(9-row)){
                              str = str + "N"; //no ants in south direction
                              edgeSpaces[2] = numSpaces[2];//get spaces until south edge
                           }
                        } else {
                           str = str + String.valueOf(numSpaces[2]);
                           adjacentAnts[2] = getAntNeighbors(game,a,col);//checking the number of adjacent ants
                           break;
                        }
                     }
                  }
                  //Checking west for ants
                  if(col==0){
                     str = str + "N"; //second component of str is 0 which means
                                      //no ants in east direction
                                      //edgeSpaces is also zero since at West edge
                  } else {
                     for(int a = col-1;a>=0;a--){
                        if(!(game[row][a] instanceof Ant)){
                           numSpaces[3]++;
                           if(a==0 && numSpaces[3]==col){
                              str = str + "N"; //no ants in east direction
                              edgeSpaces[3] = numSpaces[3];//get spaces until west edge
                           }
                        } else {
                           str = str + String.valueOf(numSpaces[3]);
                           adjacentAnts[3] = getAntNeighbors(game,row,a);//checking the number of adjacent ants
                           break;
                        }
                     }
                  }
                  //Adding spaces until edge to String str 
                  //indexes 4,5,6,7 contain spaces until edge in directions N,E,S,W respectively
                  for(int i =0; i<edgeSpaces.length;i++){
                      str = str + String.valueOf(edgeSpaces[i]);
                  }
                  
                  //Adding number of adjacent ants in each direction
                  //indexes 8,9,10,11 represent number of adjacent ant in N,E,S,W directions respectively
                  for(int i =0; i<adjacentAnts.length;i++){
                      str = str + String.valueOf(adjacentAnts[i]);
                  }

                  //Calling the Beetle move() method
                  int decision = game[row][col].move(str);
                  
                  //Executing the decision made by the move() method
                  
                  if(decision == 0) // decision to move north
                  {
                     if(row -1 >= 0 && game[row-1][col] instanceof Beetle){
                        game[row][col].setMoved(true); //no movement since North isn't available
                        int increase = ((Beetle)game[row][col]).getHunger() + 1;
                        ((Beetle)game[row][col]).setHunger(increase); //increasing hunger counter by one
                     } else if (row -1 >= 0)//check for north edge of the map
                     {
                        int increase = ((Beetle)game[row][col]).getHunger() + 1;
                        ((Beetle)game[row][col]).setHunger(increase); //increasing hunger counter by one
                        if(game[row-1][col] instanceof Ant){
                            ((Beetle) game[row][col]).setHunger(0); //setting hunger to zero if it eats an Ant
                        }
                        game[row - 1][col] = (Beetle) game[row][col];
                        game[row][col]=null;
                        game[row-1][col].setMoved(true);
                     }
                  } else if(decision == 1) // decision to move east
                  {
                     if(col+1<10 && game[row][col+1] instanceof Beetle){
                        game[row][col].setMoved(true);//no movement since East isn't available
                        int increase = ((Beetle)game[row][col]).getHunger() + 1;
                        ((Beetle)game[row][col]).setHunger(increase); //increasing hunger counter by one
                     } else if (col+1<10) //check for eastern edge of the map
                     {
                        int increase = ((Beetle)game[row][col]).getHunger() + 1;
                        ((Beetle)game[row][col]).setHunger(increase); //increasing hunger counter by one
                        if(game[row][col+1] instanceof Ant){
                            ((Beetle) game[row][col]).setHunger(0); //setting hunger to zero if it eats an Ant
                        }
                        game[row][col+1] = (Beetle) game[row][col];
                        game[row][col] = null;
                        game[row][col+1].setMoved(true);
                     }
                  } else if (decision == 2) //decision to move south
                  {
                     if(row+1<10 && game[row+1][col] instanceof Beetle){
                        game[row][col].setMoved(true);//no movement since South isn't available
                        int increase = ((Beetle)game[row][col]).getHunger() + 1;
                        ((Beetle)game[row][col]).setHunger(increase); //increasing hunger counter by one
                     } else if(row+1<10) //check for southern edge
                     {
                        int increase = ((Beetle)game[row][col]).getHunger() + 1;
                        ((Beetle)game[row][col]).setHunger(increase); //increasing hunger counter by one
                        if(game[row+1][col] instanceof Ant){
                            ((Beetle) game[row][col]).setHunger(0); //setting hunger to zero if it eats an Ant
                        }
                        game[row+1][col] = (Beetle) game[row][col];
                        game[row][col] = null;
                        game[row+1][col].setMoved(true);
                     }
                  } else if (decision == 3) // deicision to move west
                  {
                     if(col-1 >=0 && game[row][col-1] instanceof Beetle){
                        game[row][col].setMoved(true);//no movement since West isn't available
                        int increase = ((Beetle)game[row][col]).getHunger() + 1;
                        ((Beetle)game[row][col]).setHunger(increase); //increasing hunger counter by one
                     } else if(col-1 >=0) //check for western edge
                     {
                        int increase = ((Beetle)game[row][col]).getHunger() + 1;
                        ((Beetle)game[row][col]).setHunger(increase); //increasing hunger counter by one
                        if(game[row][col-1] instanceof Ant){
                            ((Beetle) game[row][col]).setHunger(0); //setting hunger to zero if it eats an Ant
                        }
                        game[row][col-1] = (Beetle) game[row][col];
                        game[row][col] = null;
                        game[row][col-1].setMoved(true);
                     }
                  }
                  
               }
            }
            
         }
         //Loop through grid to move ants
         for(int col = 0;col<theGridSize;col++)
         {
            for(int row = 0;row<theGridSize;row++)
            {
               //If that location has an Ant and it hasn't been moved
               if(game[row][col] instanceof Ant && (!(game[row][col].getMoved()))){
                  
                  int[] numSpaces ={0,0,0,0};//Used to store spaces until Beetle is present
                  String str = ""; //String used to pass into move() method
                  
                  //Checking north for Beetle
                  if(row==0){
                     str = "N"; //this means no Beetle in north direction
                  } else {
                     for(int a = row-1;a>=0;a--)
                     {
                        if(game[a][col] instanceof Beetle){
                           str = String.valueOf(numSpaces[0]);
                           break;
                        } else{
                           numSpaces[0]++;//no Beetle are present
                           if(a==0 && numSpaces[0]==row){
                              str = "N";//if we reach the edge of the map
                                        //and there are no Beetle
                           }
                        }
                     } 
                  }
                  
                  //Checking east for Beetle
                  if(col==theGridSize-1){
                     str = str + "N"; //second component of str is 0 which means
                                      //no Beetle in east direction
                  } else {
                     for(int a = col+1;a<theGridSize;a++){
                        if(game[row][a] instanceof Beetle){
                           str = str + String.valueOf(numSpaces[1]);
                           break;
                        } else {
                           numSpaces[1]++;
                           if(a==(theGridSize-1) && numSpaces[1]==(9-col)){
                              str = str + "N"; //no Beetle in east direction
                           }
                        }
                     }
                  }
                  
                  //Checking south for Beetle
                  if(row==theGridSize-1){
                     str = str + "N"; //second component of str is 0 which means
                                      //no Beetle in south direction
                  } else {
                     for(int a = row+1;a<theGridSize;a++){
                        if(game[a][col] instanceof Beetle){
                           str = str + String.valueOf(numSpaces[2]);
                           break;
                        } else {
                           numSpaces[2]++;
                           if(a==theGridSize-1 && numSpaces[2]==(9-row)){
                              str = str + "N"; //no Beetle in south direction
                           }
                        }
                     }
                  }
                  
                  //Checking west for Beetle
                  if(col==0){
                     str = str + "N"; //second component of str is 0 which means
                                      //no Beetle in east direction
                  } else {
                     for(int a = col-1;a>=0;a--){
                        if(game[row][a] instanceof Beetle){
                           str = str + String.valueOf(numSpaces[3]);
                           break;
                        } else {
                           numSpaces[3]++;
                           if(a==0 && numSpaces[3]==col){
                              str = str + "N"; //no Beetle in west direction
                           }
                        }
                     }
                  }
                  //Call move method
                  int decision = game[row][col].move(str);
                  
                  //Executing the movement of ants
                  
                  if(str.equals("NNNN")){
                      game[row][col].setMoved(true);//No movement since no beetle in any 
                                                    //orthogonal direction
                      } else if(decision == 0) // decision to move north
                  {
                     if(row -1 >= 0 && (game[row-1][col] instanceof Ant || game[row-1][col] instanceof Beetle)){
                        game[row][col].setMoved(true); //no movement since North isn't available
                     } else if (row -1 >= 0)//check for north edge of the map
                     {
                        game[row - 1][col] = (Ant)game[row][col];
                        game[row][col]=null;
                        game[row-1][col].setMoved(true);
                     }
                  } else if(decision == 1) // decision to move east
                  {
                     if(col+1<10 && (game[row][col+1] instanceof Ant || game[row][col+1] instanceof Beetle)){
                        game[row][col].setMoved(true);//no movement since East isn't available
                     } else if (col+1<10) //check for eastern edge of the map
                     {
                        game[row][col+1] = (Ant)game[row][col];
                        game[row][col] = null;
                        game[row][col+1].setMoved(true);
                     }
                  } else if (decision == 2) //decision to move south
                  {
                     if(row+1<10 && (game[row+1][col] instanceof Ant || game[row+1][col] instanceof Beetle)){
                        game[row][col].setMoved(true);//no movement since South isn't available
                     } else if(row+1<10) //check for southern edge
                     {
                        game[row+1][col] = (Ant)game[row][col];
                        game[row][col] = null;
                        game[row+1][col].setMoved(true);
                     }
                  } else if (decision == 3) // deicision to move west
                  {
                     if(col-1 >=0 && (game[row][col-1] instanceof Ant || game[row][col-1] instanceof Beetle)){
                        game[row][col].setMoved(true);//no movement since West isn't available
                     } else if(col-1 >=0) //check for western edge
                     {
                        game[row][col-1] = (Ant)game[row][col];
                        game[row][col] = null;
                        game[row][col-1].setMoved(true);
                     }
                  }
               }
            }
            
         }
         // Increase turn counter for all creatures
            for (int col = 0; col < theGridSize; col++) {
                for (int row = 0; row < theGridSize; row++) {
                   //Looping through each creature
                    if (game[row][col] instanceof Beetle || game[row][col] instanceof Ant) {
                        
                        (game[row][col]).setTurn((game[row][col].getTurn()) + 1); // Increment turn of creature in game afer moving
                        
                        game[row][col].setMoved(false);//reset all to false again to start next turn
                        
                    }
                }
            }
            
         //Starving the beetles
         for (int col = 0; col < theGridSize; col++)
         {
             for (int row = 0; row < theGridSize; row++) 
             {
                 if(game[row][col] instanceof Beetle){
                     int s = ((Beetle)game[row][col]).getHunger();
                     if (((Beetle)game[row][col]).starve(s)){
                         game[row][col] = null; //starves beetle if starve function returns true
                     }
                 }
             }
         }
            
            
         // Breeding the Ants
         for (int col = 0; col < theGridSize; col++) 
            {
                for (int row = 0; row < theGridSize; row++) {
                    if (game[row][col] instanceof Ant && (game[row][col]).getTurn() != 0) // Ants breed
                    {
                        //calling boolean breed(int turn) function
                        //will return true if the ants can breed
                        if (((Ant) game[row][col]).breed(((Ant) game[row][col]).getTurn())){
                            if(row!=0 && game[row-1][col] == null)//Breed North if possible
                            {
                                game[row-1][col] = new Ant();
                            } else if(col!=9 && game[row][col+1] == null)//Breed East if possible
                            {
                                game[row][col+1] = new Ant();
                            } else if(row!=9 && game[row+1][col] == null)//Breed South if possible
                            {
                                game[row+1][col] = new Ant();
                            }else if(col!=0 && game[row][col-1] == null)//Breed West if possible
                            {
                                game[row][col-1] = new Ant();
                            } else 
                            {
                                continue;//No breeding
                            }
                            
                        }
                    }
                }
            }
         // Breeding the Beetles
         for (int col = 0; col < theGridSize; col++) 
            {
                for (int row = 0; row < theGridSize; row++) {
                    if (game[row][col] instanceof Beetle && (game[row][col]).getTurn() != 0) // Beetles breed
                    {
                        //calling boolean breed(int turn) function
                        //will return true if the beetles can breed
                        if (((Beetle) game[row][col]).breed(((Beetle) game[row][col]).getTurn())){
                            if(row!=0 && game[row-1][col] == null)//Breed North if possible
                            {
                                game[row-1][col] = new Beetle();
                            } else if(col!=9 && game[row][col+1] == null)//Breed East if possible
                            {
                                game[row][col+1] = new Beetle();
                            } else if(row!=9 && game[row+1][col] == null)//Breed South if possible
                            {
                                game[row+1][col] = new Beetle();
                            }else if(col!=0 && game[row][col-1] == null)//Breed West if possible
                            {
                                game[row][col-1] = new Beetle();
                            } else 
                            {
                                continue;//No breeding
                            }
                            
                        }
                    }
                }
            }
         
         
         displayGrid(game,turnCounter,antChar,beetleChar);
      }
      
      
      
   }
   
   //Method to read file and return a creature 2D array (serves as the grid)
   public static Creature[][] readFile(String filename) throws IOException{
      File inputFile = new File(filename);
      Scanner scanner = new Scanner(inputFile);
      
      if(inputFile.canRead()==false){
         System.out.println("File could not be accessed.");
      }
      
      Creature [][] actualGrid = new Creature[theGridSize][theGridSize];
      String fileRow; //use to store row in every iteration
      
        //Loop that goes through all file rows until not data is left to read
        while (scanner.hasNextLine()) {
            //for loop goes through every character in the row of the file
            for (int i = 0; i < theGridSize;i++) {
                fileRow = scanner.nextLine();                
                char[] line = fileRow.toCharArray();//converts the String fileRow to
                                                    //char array
                for (int j = 0; j < theGridSize; j++) {
                    switch (line[j]) {
                        // checking char array for empty spaces
                        // (no creature present on grid)
                        case ' ':
                            actualGrid[i][j]=null;
                            break;
                        // checking for presence of ant 
                        case 'a':
                            actualGrid[i][j] = new Ant();
                            break;
                        // checking for presence of beetle 
                        case 'B':
                            actualGrid[i][j] = new Beetle();
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        scanner.close();
        return actualGrid; //this will return a grid with all the creatures
   }
   //Method to get number of adjacent ants in diagonal and orthogonal direction
   public static int getAntNeighbors(Creature[][] game, int row, int col){
       int numAnts = 0;
       
       for(int i = row -1;i < row+2;i++)//loop through neighbor rows
       {
           for(int j = col-1;j < col+2;j++)//loop through neighbor columns
           {
               if(i==row && j==col)//this skips the ant that we are checking for
               {
                   continue;
               } else if(j>=theGridSize || j<0)//if column is out of the game boundaries
               {
                   continue;
               } else if(i>=theGridSize || i<0)//if row is out of the game boundaries
               {
                   continue;
               } else if(game[i][j] instanceof Ant){
                   numAnts++;
               }
           }
       }
       return numAnts;
       
       
       
       
       
   }
   
   
   
   
   
   
   
   
   
   
    //Method to display game after each turn 
    public static void displayGrid(Creature[][] game, int turn, char ant, char beetle) {
        System.out.println("TURN " + turn);
        for (int row = 0; row < theGridSize; row++) {
            for (int col = 0; col < theGridSize; col++) {
                if (col == theGridSize - 1) //Print new line after this last column
                {
                    if (game[row][col] == null) {
                        System.out.println(" ");
                    } else if (game[row][col] instanceof Ant) {
                        System.out.println(ant);
                    } else if (game[row][col] instanceof Beetle) {
                        System.out.println(beetle);
                    }
                } else //Print values of each column in the specified row
                {
                    if (game[row][col] == null) {
                        System.out.print(" ");
                    } else if (game[row][col] instanceof Ant) {
                        System.out.print(ant);
                    } else if (game[row][col] instanceof Beetle) {
                        System.out.print(beetle);
                    }
                }
            }
        }
        System.out.println();
    }

}

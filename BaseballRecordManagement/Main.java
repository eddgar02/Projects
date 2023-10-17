
/* 
 * Name: Edgar Lara Sanchez

 */
import java.util.*;
import java.io.*;

public class Main {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        //Creating scanner object
        Scanner scanner = new Scanner(System.in);
        //Prompt user for file name
        String fileName;//string used to store file 
        //System.out.println("Filename:");
        fileName = "nintendoStats.txt";
        System.out.println("NAME    PA      HITS    WALKS   SO      HBP     S       BA      ONP");
        System.out.println("-----------------------------------------------------------------------------");


        //New scanner and file object to read file
        File newFile = new File(fileName);
        Scanner inFS = new Scanner(newFile);
        //Check if file can be accessed
        if (newFile.canRead() == false) {
            System.out.println("File cannot be accessed");
        }

        LinkList<Player> list = new LinkList<>();// new linked list that is Player object type

        while (inFS.hasNext())//loop will continue until there are no more lines
        {
            Player curPlayer = null;
            boolean duplicate = false;//boolean used to see if new node should be created
            String name = inFS.next();//read name of player
            if (list.getHead() == null) {
                //If the list is empty create a new player 
                curPlayer = new Player();
                curPlayer.setName(name);
            } else {
                Node<Player> cur = list.getHead();
                while (cur != null) {
                    if ((cur.getData()).getName().equals(name)) {
                        curPlayer = cur.getData();
                        duplicate = true;
                        break;
                    } else {
                        cur = cur.getNext();
                    }
                }
                if (cur == null) //if no duplicate names are found, then a new player is created           
                {
                    curPlayer = new Player();
                    curPlayer.setName(name);
                }
            }

            //Reading in the stats and converting into a string
            String stats = inFS.next();
            //Converting string to characters
            char[] str = stats.toCharArray();
            for (int i = 0; i < str.length; i++) {
                switch (str[i]) //Increasing each stat by one if character is encountered 
                {
                    case 'H':
                        curPlayer.setHits(curPlayer.getHits() + 1);
                        break;
                    case 'O':
                        curPlayer.setOuts(curPlayer.getOuts() + 1);
                        break;
                    case 'K':
                        curPlayer.setStrikeouts(curPlayer.getStrikeouts() + 1);
                        break;
                    case 'W':
                        curPlayer.setWalks(curPlayer.getWalks() + 1);
                        break;
                    case 'P':
                        curPlayer.setHitsByPitch(curPlayer.getHitsByPitch() + 1);
                        break;
                    case 'S':
                        curPlayer.setSacrifices(curPlayer.getSacrifices() + 1);
                        break;
                }
            }
            //Creation of node with player passed into as its data
            if (duplicate != true)//if the player does not have the same name, then create a new node
            {
                Node<Player> n = new Node<>(curPlayer);
                list.appendList(n); //add node to the end of the list
            }
        }

        //Sort the linked list alphabetically
        list.sortAlpha(list.getHead());
        //Print the stats of all the players
        list.printStats(list.getHead());

        inFS.close(); //close the file since we no longer need it
        System.out.println();

        //Fidnding the leaders
        System.out.println("LEAGUE LEADERS");

        System.out.println("BATTING AVERAGE");

        double maxBA = 0;
        double BA;
        boolean isFirst = true; //used to begin using commas after the first player if tie 
                                //is present
        int displayCounter = 0;

        //FIND HIGHEST BATTING AVERAGE VALUE STAT
        for (Node<Player> iter = list.getHead(); iter != null; iter = iter.getNext()) {
            //Current node's BA
            BA = iter.getData().getBA();
            //New assignement to maxBA if current is bigger
            if (maxBA < BA) {
                maxBA = BA;
            }
        }
        System.out.printf("%.3f\t", maxBA);
        for (Node<Player> iter = list.getHead(); iter != null; iter = iter.getNext()) {
            //Current node's BA
            BA = iter.getData().getBA();

            if (BA == maxBA) {
                if (isFirst == false) {
                    System.out.printf(", ");
                }
                
                System.out.print(iter.getData().getName());//Display name

                displayCounter++; //Player displayed so counter will increase
            }
            if (BA == maxBA) {
                isFirst = false;
            }
        }
        

        //Find Second highest batting average
        double secondBA = 0;
        isFirst = true;
        //Do not print second place if more than three player have been displayed
        if (displayCounter < 3 && list.getHead().getNext() != null) {
            for (Node<Player> iter = list.getHead(); iter != null; iter = iter.getNext()) {
                //Current node's BA
                BA = iter.getData().getBA();
                //New assignment to secondBA if current is bigger and is not equal to 1st place
                if (!(Math.abs(BA - maxBA) <= 0.0001) && secondBA < BA) {
                    secondBA = BA;
                }
            }
            System.out.printf("\n%.3f\t", secondBA);//Display stat
            
            for (Node<Player> iter = list.getHead(); iter != null; iter = iter.getNext()) {

                //Current node's BA
                BA = iter.getData().getBA();

                if (BA == secondBA) {
                    if (isFirst == false) {
                        System.out.printf(", ");
                    }
                    
                    System.out.print(iter.getData().getName());//Display name

                    displayCounter++; //Player displayed so counter will increase
                }
                if (BA == secondBA) {
                    isFirst = false;
                }
            }
        }
        
        
        //Find Third highest batting average
        double thirdBA = 0;
        isFirst = true;
        //Do not print second place if more than three player have been displayed
        if (displayCounter < 3 && list.getHead().getNext() != null && list.getHead().getNext().getNext() != null) {
            for (Node<Player> iter = list.getHead(); iter != null; iter = iter.getNext()) {
                //Current node's BA
                BA = iter.getData().getBA();
                //New assignement to thirdBA if current is bigger and is not equal to 1st place and second place
                if (!(Math.abs(BA - maxBA) <= 0.0001) && !(Math.abs(BA - secondBA) <= 0.0001) && thirdBA < BA) {
                    thirdBA = BA;
                }
            }
            System.out.printf("\n%.3f\t", thirdBA);
            for (Node<Player> iter = list.getHead(); iter != null; iter = iter.getNext()) {

                //Current node's BA
                BA = iter.getData().getBA();

                if (BA == thirdBA) {
                    if (isFirst == false) {
                        System.out.printf(", ");
                    }
                    
                    System.out.print(iter.getData().getName());//Display name

                    displayCounter++; //Player displayed so counter will increase
                }
                if (BA == thirdBA) {
                    isFirst = false;
                }
            }
        }
        System.out.println();
                
        System.out.println("\nON-BASE PERCENTAGE");

        double maxOBP = 0;
        double OBP;
        isFirst = true; //used to begin using commas after the first player if tie 
                         //is present
        displayCounter = 0;

        //FIND HIGHEST ON BASE PERCENTAGE VALUE STAT
        for (Node<Player> iter = list.getHead(); iter != null; iter = iter.getNext()) {
            //Current node's OBP
            OBP = iter.getData().getOBP();
            //New assignement to maxBA if current is bigger
            if (maxOBP < OBP) {
                maxOBP = OBP;
            }
        }
        System.out.printf("%.3f\t", maxOBP);
        for (Node<Player> iter = list.getHead(); iter != null; iter = iter.getNext()) {
            //Current node's OBP
            OBP = iter.getData().getOBP();

            if (OBP == maxOBP) {
                if (isFirst == false) {
                    System.out.printf(", ");
                }
                
                System.out.print(iter.getData().getName());//Display name

                displayCounter++; //Player displayed so counter will increase
            }
            if (OBP == maxOBP) {
                isFirst = false;
            }
        }
        
        //Find Second highest OBP
        double secondOBP = 0;
        isFirst = true;
        //Do not print second place if more than three player have been displayed
        if (displayCounter < 3 && list.getHead().getNext() != null) {
            for (Node<Player> iter = list.getHead(); iter != null; iter = iter.getNext()) {
                //Current node's OBP
                OBP = iter.getData().getOBP();
                //New assignment to secondOBP if current is bigger and is not equal to 1st place
                if (!(Math.abs(OBP - maxOBP) <= 0.0001) && secondOBP < OBP) {
                    secondOBP = OBP;
                }
            }
            System.out.printf("\n%.3f\t", secondOBP);//Display stat
            
            for (Node<Player> iter = list.getHead(); iter != null; iter = iter.getNext()) {

                //Current node's OBP
                OBP = iter.getData().getOBP();

                if (OBP == secondOBP) {
                    if (isFirst == false) {
                        System.out.printf(", ");
                    }
                    
                    System.out.print(iter.getData().getName());//Display name

                    displayCounter++; //Player displayed so counter will increase
                }
                if (OBP == secondOBP) {
                    isFirst = false;
                }
            }
        }
        
        
        //Find Third highest OBP
        double thirdOBP = 0;
        isFirst = true;
        //Do not print second place if more than three player have been displayed
        if (displayCounter < 3 && list.getHead().getNext() != null && list.getHead().getNext().getNext() != null) {
            for (Node<Player> iter = list.getHead(); iter != null; iter = iter.getNext()) {
                //Current node's OBP
                OBP = iter.getData().getOBP();
                //New assignement to third if current is bigger and is not equal to 1st place and second place
                if (!(Math.abs(OBP - maxOBP) <= 0.0001) && !(Math.abs(OBP - secondOBP) <= 0.0001) && thirdOBP < OBP) {
                    thirdOBP = OBP;
                }
            }
            System.out.printf("\n%.3f\t", thirdOBP);
            for (Node<Player> iter = list.getHead(); iter != null; iter = iter.getNext()) {

                //Current node's OBP
                OBP = iter.getData().getOBP();

                if (OBP == thirdOBP) {
                    if (isFirst == false) {
                        System.out.printf(", ");
                    }
                    
                    System.out.print(iter.getData().getName());//Display name

                    displayCounter++; //Player displayed so counter will increase
                }
                if (OBP == thirdOBP) {
                    isFirst = false;
                }
            }
            
        }
        System.out.println();
        
        System.out.println("\nHITS");

        int maxHits = 0;
        int hits;
        isFirst = true; //used to begin using commas after the first player if tie 
                         //is present
        displayCounter = 0;

        //FIND HIGHEST HITS VALUE STAT
        for (Node<Player> iter = list.getHead(); iter != null; iter = iter.getNext()) {
            //Current node's hits
            hits = iter.getData().getHits();
            //New assignement to maxHits if current is bigger
            if (maxHits < hits) {
                maxHits = hits;
            }
        }
        System.out.printf("%d\t", maxHits);
        for (Node<Player> iter = list.getHead(); iter != null; iter = iter.getNext()) {
            //Current node's hits
            hits = iter.getData().getHits();

            if (hits == maxHits) {
                if (isFirst == false) {
                    System.out.printf(", ");
                }
                
                System.out.print(iter.getData().getName());//Display name

                displayCounter++; //Player displayed so counter will increase
            }
            if (hits == maxHits) {
                isFirst = false;
            }
        }
        
        //Find Second highest hits
        int secondhits = 0;
        isFirst = true;
        //Do not print second place if more than three player have been displayed
        if (displayCounter < 3 && list.getHead().getNext() != null) {
            for (Node<Player> iter = list.getHead(); iter != null; iter = iter.getNext()) {
                //Current node's hits
                hits = iter.getData().getHits();
                //New assignment to secondOBP if current is bigger and is not equal to 1st place
                if (hits != maxHits && secondhits < hits) {
                    secondhits = hits;
                }
            }
            System.out.printf("\n%d\t", secondhits);//Display stat
            
            for (Node<Player> iter = list.getHead(); iter != null; iter = iter.getNext()) {

                //Current node's hits
                hits = iter.getData().getHits();

                if (hits == secondhits) {
                    if (isFirst == false) {
                        System.out.printf(", ");
                    }
                    
                    System.out.print(iter.getData().getName());//Display name

                    displayCounter++; //Player displayed so counter will increase
                }
                if (hits == secondhits) {
                    isFirst = false;
                }
            }
        }
        
        
        //Find Third highest hits
        int thirdhits = 0;
        isFirst = true;
        //Do not print second place if more than three player have been displayed
        if (displayCounter < 3 && list.getHead().getNext() != null && list.getHead().getNext().getNext() != null) {
            for (Node<Player> iter = list.getHead(); iter != null; iter = iter.getNext()) {
                //Current node's hits
                hits = iter.getData().getHits();
                //New assignement to third if current is bigger and is not equal to 1st place and second place
                if (hits != maxHits && hits !=secondhits && thirdhits < hits) {
                    thirdhits = hits;
                }
            }
            System.out.printf("\n%d\t", thirdhits);
            for (Node<Player> iter = list.getHead(); iter != null; iter = iter.getNext()) {

                //Current node's hits
                hits = iter.getData().getHits();

                if (hits == thirdhits) {
                    if (isFirst == false) {
                        System.out.printf(", ");
                    }
                    
                    System.out.print(iter.getData().getName());//Display name

                    displayCounter++; //Player displayed so counter will increase
                }
                if (hits == thirdhits) {
                    isFirst = false;
                }
            }
        }
        System.out.println();
        
        System.out.println("\nWALKS");

        int maxwalks = 0;
        int walks;
        isFirst = true; //used to begin using commas after the first player if tie 
                         //is present
        displayCounter = 0;

        //FIND HIGHEST WALKS VALUE STAT
        for (Node<Player> iter = list.getHead(); iter != null; iter = iter.getNext()) {
            //Current node's walks
            walks = iter.getData().getWalks();
            //New assignement to maxwalks if current is bigger
            if (maxwalks < walks) {
                maxwalks = walks;
            }
        }
        System.out.printf("%d\t", maxwalks);
        for (Node<Player> iter = list.getHead(); iter != null; iter = iter.getNext()) {
            //Current node's walks
            walks = iter.getData().getWalks();

            if (walks == maxwalks) {
                if (isFirst == false) {
                    System.out.printf(", ");
                }
                
                System.out.print(iter.getData().getName());//Display name

                displayCounter++; //Player displayed so counter will increase
            }
            if (walks == maxwalks) {
                isFirst = false;
            }
        }
        
        //Find Second highest hits
        int secondw = 0;
        isFirst = true;
        //Do not print second place if more than three player have been displayed
        if (displayCounter < 3 && list.getHead().getNext() != null) {
            for (Node<Player> iter = list.getHead(); iter != null; iter = iter.getNext()) {
                //Current node's walks
                walks = iter.getData().getWalks();
                //New assignment to second if current is bigger and is not equal to 1st place
                if (walks != maxwalks && secondw < walks) {
                    secondw = walks;
                }
            }
            System.out.printf("\n%d\t", secondw);//Display stat
            
            for (Node<Player> iter = list.getHead(); iter != null; iter = iter.getNext()) {

                //Current node's walks
                walks = iter.getData().getWalks();

                if (walks == secondw) {
                    if (isFirst == false) {
                        System.out.printf(", ");
                    }
                    
                    System.out.print(iter.getData().getName());//Display name

                    displayCounter++; //Player displayed so counter will increase
                }
                if (walks == secondw) {
                    isFirst = false;
                }
            }
        }
        
        
        //Find Third highest walks
        int thirdw = 0;
        isFirst = true;
        //Do not print second place if more than three player have been displayed
        if (displayCounter < 3 && list.getHead().getNext() != null && list.getHead().getNext().getNext() != null) {
            for (Node<Player> iter = list.getHead(); iter != null; iter = iter.getNext()) {
                //Current node's walks
                walks = iter.getData().getWalks();
                //New assignement to third if current is bigger and is not equal to 1st place and second place
                if (walks != maxwalks && walks !=secondw && thirdw < walks) {
                    thirdw = walks;
                }
            }
            System.out.printf("\n%d\t", thirdw);
            for (Node<Player> iter = list.getHead(); iter != null; iter = iter.getNext()) {

                //Current node's walks
                walks = iter.getData().getWalks();

                if (walks == thirdw) {
                    if (isFirst == false) {
                        System.out.printf(", ");
                    }
                    
                    System.out.print(iter.getData().getName());//Display name

                    displayCounter++; //Player displayed so counter will increase
                }
                if (walks == thirdw) {
                    isFirst = false;
                }
            }
        }
        System.out.println();
        
        System.out.println("\nSTRIKEOUTS");

        int minStrike = list.getHead().getData().getStrikeouts();
        int strikes;
        isFirst = true; //used to begin using commas after the first player if tie 
                         //is present
        displayCounter = 0;

        //FIND LOWEST STRIKEOUTS VALUE STAT
        for (Node<Player> iter = list.getHead(); iter != null; iter = iter.getNext()) {
            //Current node's strikeouts
            strikes = iter.getData().getStrikeouts();
            //New assignement to minStrike if current is bigger
            if (minStrike > strikes) {
                minStrike = strikes;
            }
        }
        System.out.printf("%d\t", minStrike);
        for (Node<Player> iter = list.getHead(); iter != null; iter = iter.getNext()) {
            //Current node's strikes
            strikes= iter.getData().getStrikeouts();

            if (strikes == minStrike) {
                if (isFirst == false) {
                    System.out.printf(", ");
                }
                
                System.out.print(iter.getData().getName());//Display name

                displayCounter++; //Player displayed so counter will increase
            }
            if (strikes == minStrike) {
                isFirst = false;
            }
        }
        
        //Find Second lowest strikeouts
        int secondStrikes = list.getHead().getData().getStrikeouts();
        isFirst = true;
        //Do not print second place if more than three player have been displayed
        if (displayCounter < 3 && list.getHead().getNext() != null) {
            for (Node<Player> iter = list.getHead(); iter != null; iter = iter.getNext()) {
                //Current node's strikes
                strikes = iter.getData().getStrikeouts();
                //New assignment to second if current is bigger and is not equal to 1st place
                if (strikes != minStrike && secondStrikes > strikes) {
                    secondStrikes = strikes;
                }
            }
            System.out.printf("\n%d\t", secondStrikes);//Display stat
            
            for (Node<Player> iter = list.getHead(); iter != null; iter = iter.getNext()) {

                //Current node's strikeouts
                strikes = iter.getData().getStrikeouts();

                if (strikes == secondStrikes) {
                    if (isFirst == false) {
                        System.out.printf(", ");
                    }
                    
                    System.out.print(iter.getData().getName());//Display name

                    displayCounter++; //Player displayed so counter will increase
                }
                if (strikes == secondStrikes) {
                    isFirst = false;
                }
            }
        }
        
        
        //Find Third lowest strikeouts
        int thirdStrikes = Integer.MAX_VALUE;
        isFirst = true;
        //Do not print second place if more than three player have been displayed
        if (displayCounter < 3 && list.getHead().getNext() != null && list.getHead().getNext().getNext() != null) {
            for (Node<Player> iter = list.getHead(); iter != null; iter = iter.getNext()) {
                //Current node's strikes
                strikes = iter.getData().getStrikeouts();
                //New assignement to third if current is bigger and is not equal to 1st place and second place
                if (strikes != minStrike && strikes !=secondStrikes && thirdStrikes > strikes) {
                    thirdStrikes = strikes;
                }
            }
            System.out.printf("\n%d\t", thirdStrikes);
            for (Node<Player> iter = list.getHead(); iter != null; iter = iter.getNext()) {

                //Current node's strikes
                strikes = iter.getData().getStrikeouts();

                if (strikes == thirdStrikes) {
                    if (isFirst == false) {
                        System.out.printf(", ");
                    }
                    
                    System.out.print(iter.getData().getName());//Display name

                    displayCounter++; //Player displayed so counter will increase
                }
                if (strikes == thirdStrikes) {
                    isFirst = false;
                }
            }
            
        }
        System.out.println();
        
        System.out.println("\nHIT BY PITCH");

        int maxHBP = 0;
        int HBP;
        isFirst = true; //used to begin using commas after the first player if tie 
                         //is present
        displayCounter = 0;

        //FIND HIGHEST HBP VALUE STAT
        for (Node<Player> iter = list.getHead(); iter != null; iter = iter.getNext()) {
            //Current node's HBP
            HBP = iter.getData().getHitsByPitch();
            //New assignement to maxHBP if current is bigger
            if (maxHBP < HBP) {
                maxHBP = HBP;
            }
        }
        System.out.printf("%d\t", maxHBP);
        for (Node<Player> iter = list.getHead(); iter != null; iter = iter.getNext()) {
            //Current node's HBP
            HBP= iter.getData().getHitsByPitch();

            if (HBP == maxHBP) {
                if (isFirst == false) {
                    System.out.printf(", ");
                }
                
                System.out.print(iter.getData().getName());//Display name

                displayCounter++; //Player displayed so counter will increase
            }
            if (HBP == maxHBP) {
                isFirst = false;
            }


        }
        
        //Find Second highest HBP
        int secondHBP = 0;
        isFirst = true;
        //Do not print second place if more than three player have been displayed
        if (displayCounter < 3 && list.getHead().getNext() != null) {
            for (Node<Player> iter = list.getHead(); iter != null; iter = iter.getNext()) {
                //Current node's HBP
                HBP = iter.getData().getHitsByPitch();
                //New assignment to second if current is bigger and is not equal to 1st place
                if (HBP != maxHBP && secondHBP < HBP) {
                    secondHBP = HBP;
                }
            }
            System.out.printf("\n%d\t", secondHBP);//Display stat
            
            for (Node<Player> iter = list.getHead(); iter != null; iter = iter.getNext()) {

                //Current node's HBP
                HBP = iter.getData().getHitsByPitch();

                if (HBP == secondHBP) {
                    if (isFirst == false) {
                        System.out.printf(", ");
                    }
                    
                    System.out.print(iter.getData().getName());//Display name

                    displayCounter++; //Player displayed so counter will increase
                }
                if (HBP == secondHBP) {
                    isFirst = false;
                }
            }
        }
        
        
        //Find Third highest HBP
        int thirdHBP = 0;
        isFirst = true;
        //Do not print third place if more than three player have been displayed
        if (displayCounter < 3 && list.getHead().getNext() != null && list.getHead().getNext().getNext() != null) {
            for (Node<Player> iter = list.getHead(); iter != null; iter = iter.getNext()) {
                //Current node's HBP
                HBP = iter.getData().getHitsByPitch();
                //New assignement to third if current is bigger and is not equal to 1st place and second place
                if (HBP != maxHBP && HBP !=secondHBP && thirdHBP < HBP) {
                    thirdHBP = HBP;
                }
            }
            System.out.printf("\n%d\t", thirdHBP);
            for (Node<Player> iter = list.getHead(); iter != null; iter = iter.getNext()) {

                //Current node's HBP
                HBP = iter.getData().getHitsByPitch();

                if (HBP == thirdHBP) {
                    if (isFirst == false) {
                        System.out.printf(", ");
                    }
                    
                    System.out.print(iter.getData().getName());//Display name

                    displayCounter++; //Player displayed so counter will increase
                }
                if (HBP == thirdHBP) {
                    isFirst = false;
                }
            }
        }
        System.out.println();
        System.out.println();
    }

}

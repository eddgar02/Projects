
import java.io.*;
import java.util.*;

public class IntegralCalculator {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        Scanner scan = new Scanner(System.in);
        String filename = scan.nextLine();

        File file = new File(filename);
        Scanner inFS = new Scanner(file);

        if (!inFS.hasNext()) {
            System.out.println("File cannot be accessed!");
        }

        while (inFS.hasNextLine()) {
            //Create new binary tree every time while loop restarts
            BinTree<Term> bt = new BinTree<>();

            String equation = inFS.nextLine();
            String tempString = ""; //String used to seperate equation into terms
            boolean nodeExists; //Used for repeated terms with the same exponent
            boolean definite = false; //Used to check if there are boundaries for the intergral

            int pipe = equation.indexOf("|");
            int a = 0; //lower bound
            int b = 0; //upper bound

            if (pipe > 0) //If the pipe was at index zero then it would be indefinite
            {
                definite = true;
                a = Integer.parseInt(equation.substring(0, pipe)); //Convert substring of part before the pipe into an integer
                b = Integer.parseInt(equation.substring(pipe + 1, equation.indexOf(" "))); //Convert substring of part after the pipe into an integer
            }
            // this is where the equation actually starts not including the pipe and bounds
            int start = (equation.substring(0, equation.indexOf(" "))).length() + 1;

            int coefficient;
            int exponent;
            Term curData;

            //Looping through each character in the equation to divide into parts
            for (int i = start; i < equation.length(); i++) {
                // If character is not an operator or space or the letter 'd' add to string to create node
                if (equation.charAt(i) == '^' || Character.isDigit(equation.charAt(i)) || equation.charAt(i) == 'x') {

                    tempString += equation.charAt(i); // Combine character with string

                } else if (equation.charAt(i) == '-' && !Character.isDigit(equation.charAt(i - 1)) && equation.charAt(i - 1) != 'x') //this else checks '-' which is special since it could be added to current string or used to start a new string
                {

                    tempString += equation.charAt(i);// Combine character with string

                } else if (!(tempString.equals("")) && !(tempString.equals("-"))) {
                    nodeExists = false;
                    coefficient = 0;
                    exponent = 0;
                    int indexOfX = tempString.indexOf('x');
                    int indexOfCarrot = tempString.indexOf('^');
                    if (indexOfX >= 0) // If the equation has 'x'
                    {
                        if (indexOfX == 0) // x has no coefficient in front
                        {
                            coefficient = 1;
                        } else {
                            if (tempString.charAt(0) == '-' && tempString.charAt(1) == 'x') //if there's only a negative sign
                            {
                                coefficient = -1;
                            } else {
                                //otherwise get string turned into an int and store as the coefficent
                                coefficient = Integer.parseInt(tempString.substring(0, indexOfX));
                            }
                        }
                        if (indexOfCarrot == -1) // If x has a power of one
                        {
                            exponent = 1;
                        } else {
                            //otherwise get string turned into an int and store as the exponent
                            exponent = Integer.parseInt(tempString.substring(indexOfCarrot + 1));   // Get exponent of equation substring
                        }
                    } else // If there is no presence of 'x'
                    {
                        coefficient = Integer.parseInt(tempString.substring(0));
                    }

                    if (bt.getRoot() == null) //Start the instantiation of nodes if has not been done so already
                    {
                        curData = new Term();
                        curData.setCoefficient(coefficient);
                        curData.setExponent(exponent);
                    } else {
                        curData = new Term();
                        curData.setExponent(exponent);
                        //Check if a node with the same exponent exists
                        Node<Term> comparedNode = bt.search(curData);
                        if (comparedNode != null) // Combine it node already exists
                        {
                            curData = comparedNode.getData();
                            nodeExists = true;
                            curData.setCoefficient(curData.getCoefficient() + coefficient); // Add their coefficients
                        } else // If node doesn't exists, create new Term object
                        {
                            curData = new Term();
                            //Adding exponents and coefficients together
                            curData.setCoefficient(coefficient);
                            curData.setExponent(exponent);
                        }
                    }
                    // If node does not already exist a new node will be inserted to the binary tree
                    if (!nodeExists) {
                        bt.insert(curData);
                    }

                    tempString = ""; // Resetting tempString

                    if (equation.charAt(i) == '-') //although '+' is not part of terms
                    //'-' is a part so it should be added
                    {
                        tempString += equation.charAt(i);
                    }
                }

            }

            //End of loop and creation of nodes
            //This method will print out each terms indefinite integral
            standardForm(bt, bt.getRoot());

            //Definite Integral
            if (definite && a == b) {
                System.out.print(", " + a + "|" + b + " = " + 0);
            } else if (definite) {
                double lowerSum = recursiveIntegral(bt.getRoot(), a);
                double upperSum = recursiveIntegral(bt.getRoot(), b);
                System.out.printf(", " + a + "|" + b + " = " + "%.3f", upperSum - lowerSum);
            } else {
                // Indefinite Integral 
                System.out.print(" + C");
            }

            //Start a new line 
            System.out.println();

        }
        //Closing all scanners
        scan.close();
        inFS.close();

    }

    public static void standardForm(BinTree<Term> tree, Node<Term> n) {
        //start at the root node
        if (n != null) {
            standardForm(tree, n.getRight()); //go all the way right
            integrate(tree, n); //integrate node and print term
            standardForm(tree, n.getLeft()); // go all the way left
        }
    }

    public static double recursiveIntegral(Node<Term> root, int bound) {
        if (root == null) {
            return 0;
        }

        return (definiteIntegration(root, bound) + recursiveIntegral(root.getRight(), bound) + recursiveIntegral(root.getLeft(), bound));

    }

    public static double definiteIntegration(Node<Term> root, int bound) {

        double x_toThePowerOf = Math.pow(bound, root.getData().getExponent() + 1);
        double coeff = (double) root.getData().getCoefficient() / (root.getData().getExponent() + 1);
        double sum = x_toThePowerOf * coeff;
        return sum;
    }

    public static Node<Term> getRightMostNode(Node<Term> root) {
        if (root == null) {
            return null;
        }
        if (root.getRight() == null) {
            return root;
        }
        return getRightMostNode(root.getRight());
    }

    //This method will integrate the nodes and print them out as well
    public static void integrate(BinTree<Term> tree, Node<Term> root) {

        boolean firstNode = false;
        Node<Term> rightMost = getRightMostNode(tree.getRoot());

        // Check if it is the first node by comparing the exponents
        if (root.getData().getExponent() == rightMost.getData().getExponent()) {
            firstNode = true;
        }

        //Store the coefficient and exponent
        int coefficient = root.getData().getCoefficient();
        int exponent = root.getData().getExponent();

        boolean notAFraction = false;
        //Increase exponent by one
        exponent += 1;

        if (!firstNode) //if it is not the first node, then a plus or minus sign will not be printed
        {
            if ((exponent > 0 && coefficient > 0) || (exponent < 0 && coefficient < 0)) // Coefficient and exponent are either both positive or both negative
            {
                System.out.print(" + ");
                coefficient = Math.abs(coefficient);

            } else if ((exponent < 0 && coefficient > 0) || (exponent > 0 && coefficient < 0)) //Coeffcient and exponent are opposite signs
            {
                System.out.print(" - ");
                coefficient = Math.abs(coefficient); //made into absolute value in the case of a fraction

            }
            if (coefficient % exponent == 0) // Integrated term coefficient is not a fraction
            {
                coefficient = coefficient / Math.abs(exponent);
                notAFraction = true;
            }
            if (exponent == 1) // There is no carrot printed
            {
                if (coefficient != 1) // Print coefficient if it's not one
                {
                    System.out.print(coefficient);
                }
                System.out.print("x");
            } else // There is a carrot
            {
                if (notAFraction) {
                    if (coefficient != 1) // Print coefficient if it's not one
                    {
                        System.out.print(coefficient);
                    }
                } else {
                    // Pass in absolute value of exponent when simplifying to prevent double negative
                    System.out.print("(" + simplifyFraction(coefficient, Math.abs(exponent)) + ")");
                }
                System.out.print("x^" + exponent); // Divisor is same number as integrated exponent
            }
        } else //If it is the first node
        {
            if (coefficient % exponent == 0) // Integrated term coefficient is not a fraction
            {
                coefficient = coefficient / Math.abs(exponent);
                notAFraction = true;
            }
            if (exponent == 1) // There is no carrot printed
            {
                if (coefficient != 1) // Print coefficient if it's not one
                {
                    if (coefficient == -1) // Print minus sign instead of '-1'
                    {
                        System.out.print("-");
                    } else {
                        System.out.print(coefficient);
                    }
                }
                System.out.print("x");
            } else // There is a carrot
            {
                if (notAFraction) {
                    if (coefficient != 1) // If coefficient greater than 1, need to display coefficient
                    {
                        if (coefficient == -1) // Print minus sign if coefficient is -1 instead of -1 coefficient
                        {
                            System.out.print("-");
                        } else {
                            System.out.print(coefficient);
                        }
                    }   // Otherwise don't display a coefficient because it is 1
                } else {
                    System.out.print("(" + simplifyFraction(coefficient, Math.abs(exponent)) + ")");
                }
                if (coefficient != 0) {
                    System.out.print("x^" + exponent);
                }
            }
        }

    }

    //Find the reduced fraction
    public static String simplifyFraction(int a, int b) {
        int gcd = a;
        int y = b;
        int x;
        while (y != 0) {
            x = y;
            y = gcd % y;
            gcd = x;
        }
        gcd = Math.abs(gcd);

        return (a / gcd) + "/" + (b / gcd);
    }

}

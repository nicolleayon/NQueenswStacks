//=============================================================================
// Nicolle Ayon
// HW4: Solves the NQueens problem using stack (without recursion)
// NQueens.java
//=============================================================================
import java.io.*;
import java.util.*;

class NQueens{

    public static void main(String[] args) throws IOException{
 
        //check number of command line arguments
        //must be at least 2 <input file> <output file>
        if(args.length != 2){
            System.out.println("Usage: java -jar NQueens.jar <input file><output file>");
            System.exit(1);
        }

        //opens/reads in files
        Scanner in = new Scanner(new File(args[0])); //reads from input
        PrintWriter out = new PrintWriter(new FileWriter(args[1])); //print in output file

        while(in.hasNextLine()){ //will iterate while input file has a next line

            //trim leading/trailing spaces & add 1 trailing space so split works on blank lines
            String line = in.nextLine().trim() + " ";

            //split line around white space and store in string array
            String[] stringToken = line.split("\\s+");

            if(stringToken.length < 3){
                System.out.println("No solution; Input has less than 3 numbers");
                out.println("No solution");
                break;
            }

            if(stringToken.length%2 == 0){ //if the string array has an even number of characters, that means there is a missing coordinate
                System.out.println("No solution; Missing pair of coordinates or missing chessBoard size. Even number input");
                out.println("No solution");
                break;
            }
            
            //will store stringToken as an int array instead of string array
            int[] token = new int[stringToken.length];

            //store the string token as ints 
            for(int i = 0; i < token.length; i++){
                token[i] = Integer.valueOf(stringToken[i]);
            }

            int cbSize = token[0]; //the first token is the ChessBoard size
            Stack<List<Queen>> solStack = new Stack<List<Queen>>(); //queenStack created
            List<Queen> queenList = new ArrayList<Queen>();
            boolean validInput = true;

            for(int i = 1; i < token.length; i=i+2){
                Queen newQueen = new Queen(token[i], token[i+1]);
                queenList.add(newQueen);

                //check to see if queens given attack each other
                if(queenList.size() > 1){  //board must have more than 1 queen 
                    for(int j = 0; j < queenList.size()-1; j++){ //from 0 to one-before-last
                        if(queenList.get(queenList.size()-1).isAttacking(queenList.get(j)) ) { //last queen added attacks any other queens on board?
                            out.println("No solution");
                            //System.out.println("No solution");
                            validInput = false;
                            break;  
                        }
                    }
                } //break lands here
                if(!validInput){ break; }//if last queen added attacks don't add any more things
            } //CHECKPOINT: given queens have either been added onto board, or there is no solution and stopped adding queens

            if(validInput){ //if all given queens were added with success
                //add current board state with all given queens
                solStack.push(queenList);
                //System.out.println("solStack: " + solStack);
                if(NQueens(solStack, cbSize)){ //check NQueens if 
                    //print board
                    //System.out.println(printBoard(solStack.peek()));
                    out.println( printBoard(solStack.peek()) );

                }
                else{ //NQueens returned false
                    //System.out.println("No solution");
                    out.println("No solution");
                }
            }
        }
        in.close();
        out.close();
    }

    public static boolean NQueens(Stack<List<Queen>> s, int n){
        //System.out.println("Size of board " + n);
        int Sn = 1; //int solution number
        
        for(int j = 1; j <= n; j++){ //col
            List<Queen> l = new ArrayList<>(s.peek()); //top of stack board state
            //System.out.println("peek: " + l);
            //System.out.println("stack: " + s);
            boolean existOnCol = false;
            
            for(int i = 1; i <= n; i++){ //row
                //System.out.println("j is "+ j + ", i is "+i);
                boolean isAttackBool = false; //--- moved to top of for-loop
                
                if(Sn < 1){ //if my stack gets to less than one, then we have taken the given queens off and that's a no solution
                    return false;
                }

                Queen real = new Queen(j,i);
               
                //make sure there is no queen in the same col where i'm placing my queen
                for(int m = 0; m < l.size(); m++){
                    if(real.equalsCol(l.get(m)))
                        existOnCol = true;
                } 
                if(existOnCol){ break; } //break out of "i" (row) for-loop so we can increment j (col)

                //nothing on same col -- SO NOW....
                //MAKE SURE THE QUEEN I PLACED IS NOT ATTACKING ANYTHING ELSE...if it is it won't go into any of the if-loops
                for(int m = 0; m < l.size(); m++){
                    if(real.isAttacking(l.get(m)))
                        isAttackBool = true;
                }

                if(isAttackBool == false){ 
                    l.add(real);
                    s.push(l);
                    Sn++;
                    
                    if(Sn == n){
                        return true;
                    }

                    break; //break out of i loop so we can increment j since this column is no longer valid bc we just placed a queen here
                }

                //BASE ???
                while( i == n){ //row = maxrow(aka "n")
                    //System.out.println("stack before pop: " + s);
                    List<Queen> fail = s.pop();
                    //System.out.println("stack AFTER pop: " + s) ;
                    Sn--; 
                    if(Sn < 1){ //if my stack gets to less than one, then we have taken the given queens off and that's a no solution
                        return false;
                    }

                    i = fail.get(fail.size()-1).getRow();
                    j = fail.get(fail.size()-1).getCol();
                    //System.out.println("j = " + fail.get( fail.size()-1).getCol() + ", i = " + fail.get(fail.size()-1).getRow());
                    l = new ArrayList<>(s.peek()); //now that we popped, update the board you're using

                }     
            } //break lands here
        } //went through whole all loops...queen placed on every column

    	return true;
    }
    public static String printBoard(List<Queen> solution){
        //organize last array list on stack to print out in column, row order
        int[] solutionArray = new int[(solution.size())*2]; //each pair of queens has 2 coord so multiply by 2
        int counter = 1;
        int arrayTag = 0;
        
        while(counter <= solution.size()){ //solution size is equal to "n" (aka size of board)
            for(int m = 0; m < solution.size(); m++) {
                if(solution.get(m).getCol() == counter){
                    solutionArray[arrayTag] = solution.get(m).getCol();
                    solutionArray[arrayTag+1] = solution.get(m).getRow();
                }
            }
            counter++;
            arrayTag = arrayTag +2; 
        }

        //Now we have an array that is alrady in order according to ascending column 

        String solutionString = "";

        for(int n = 0; n < solutionArray.length; n++){
            solutionString = solutionString + solutionArray[n] + " ";
        }

        return solutionString;
    }

}
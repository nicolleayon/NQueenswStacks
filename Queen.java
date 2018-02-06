//=============================================================================
// Nicolle Ayon
// Queen class for the NQueens class (the one that uses stacks)
// Queen.java
//=============================================================================

public class Queen{

	public int col; //DO I WANT TO MAKE THIS PRIVATE OR PUBLIC
	public int row;

    public Queen (int col, int row){
        this.col = col; 
        this.row = row; 
    }

    //checks to see if queen is attacking another ChessPiece of opposite color
    public boolean isAttacking(Queen q){ //ATTACKS ON SAME ROWS/COLS/DIAG
        if(col == q.col || row == q.row){ //checks if on same col/row
            //System.out.println("same col or row");
            return true; 
        }
        if(Math.abs(col-q.col) == Math.abs(row-q.row)){ //checks diagonals
            //System.out.println("on same diagonal");
            return true;
        }

        //System.out.println("returning false");
        return false; //this queen is NOT attacking the other queen
    }

    public String toString() {
        String queenObj = "(" + col + " " + row + ")";
        return queenObj;  
    }

    //@Override
    public boolean equalsCol(Queen q){
        if(this.col == q.col){
            return true;
        }
        return false;
    }

    public int getCol(){
    	return col;
    }

    public int getRow(){
    	return row;
    }
}
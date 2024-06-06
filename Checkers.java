import java.io.FileNotFoundException;

import components.*;

public class Checkers {
    public static void main(String[] args) throws FileNotFoundException {
        try{
        Board board = new Board();
        //board.printBoardCosnosle();
        board.displayBoard();
        board.refreshBoard();
        board.drawPices();
        
        }
        catch(FileNotFoundException e){
            throw e;
        }
    }
}
import java.io.FileNotFoundException;

import components.*;

public class Checkers {
    public static void main(String[] args) throws FileNotFoundException {
        try{
            Board board = new Board();
            board.displayBoard();
            board.refreshBoard();
            board.gameLoop();    
        }   
        catch(FileNotFoundException e){
            throw e;
        }
    }
}